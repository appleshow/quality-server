package com.aps.quality.service.authority;

import com.aps.quality.entity.GroupInfo;
import com.aps.quality.entity.GroupRoleInfo;
import com.aps.quality.entity.UserInfo;
import com.aps.quality.entity.UserRoleInfo;
import com.aps.quality.mapper.GroupInfoMapper;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.authority.AssignGroupToUserRequest;
import com.aps.quality.model.authority.CreateGroupRequest;
import com.aps.quality.model.authority.SearchGroupRequest;
import com.aps.quality.model.authority.UpdateGroupRequest;
import com.aps.quality.model.dto.GroupInfoDto;
import com.aps.quality.repository.GroupInfoRepository;
import com.aps.quality.repository.GroupRoleInfoRepository;
import com.aps.quality.repository.UserInfoRepository;
import com.aps.quality.repository.UserRoleInfoRepository;
import com.aps.quality.util.DataUtil;
import com.aps.quality.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupInfoRepository groupInfoRepository;
    @Resource
    private GroupRoleInfoRepository groupRoleInfoRepository;
    @Resource
    private UserRoleInfoRepository userRoleInfoRepository;
    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private GroupInfoMapper groupInfoMapper;

    @Override
    public ResponseData<GroupInfoDto> create(CreateGroupRequest request) {
        log.info("call create(): {}", request);
        if (!StringUtils.hasLength(request.getGroupName())) {
            return new ResponseData<>(ErrorMessage.GROUP_NAME_IS_NULL);
        }
        if (groupInfoRepository.countByGroupName(request.getGroupName()).orElse(0) > 0) {
            return new ResponseData<>(ErrorMessage.GROUP_NAME_EXIST);
        }

        final GroupInfo groupInfo = groupInfoMapper.map(request, GroupInfo.class);
        groupInfo.setStatus(1);

        log.info("call groupInfoRepository.save()");
        groupInfoRepository.save(groupInfo);

        return new ResponseData<>(groupInfoMapper.map(groupInfo, GroupInfoDto.class));
    }

    @Override
    public ResponseData<GroupInfoDto> update(UpdateGroupRequest request) {
        log.info("call update(): {}", request);
        if (null == request.getGroupId()) {
            return new ResponseData<>(ErrorMessage.ID_CAN_NOT_BE_NULL);
        }
        if (null == request.getVersion()) {
            return new ResponseData<>(ErrorMessage.VERSION_IS_NULL);
        }
        if (!StringUtils.hasLength(request.getGroupName())) {
            return new ResponseData<>(ErrorMessage.GROUP_NAME_IS_NULL);
        }
        if (!groupInfoRepository.findById(request.getGroupId()).isPresent()) {
            return new ResponseData<>(ErrorMessage.GROUP_NOT_EXIST);
        }
        final List<GroupInfo> groupInfoList = groupInfoRepository.findByGroupName(request.getGroupName());
        if (null != groupInfoList && groupInfoList.stream().anyMatch(r -> !r.getGroupId().equals(request.getGroupId()))) {
            return new ResponseData<>(ErrorMessage.GROUP_NAME_EXIST);
        }

        final GroupInfo groupInfo = new GroupInfo();
        BeanUtils.copyProperties(request, groupInfo, DataUtil.getNullPropertyNames(request));

        log.info("call groupInfoRepository.save()");
        groupInfoRepository.save(groupInfo);

        return new ResponseData<>(groupInfoMapper.map(groupInfo, GroupInfoDto.class));
    }

    @Override
    public ResponseData<Boolean> delete(Integer id) {
        groupInfoRepository.deleteById(id);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> assignGroupToUser(AssignGroupToUserRequest request) {
        log.info("call assignGroupToUser(): {}", request);
        final GroupInfo groupInfo = groupInfoRepository.findById(request.getGroupId()).orElse(null);
        if (null == groupInfo) {
            return new ResponseData<>(ErrorMessage.GROUP_NOT_EXIST);
        }
        final UserInfo userInfo = userInfoRepository.findById(request.getUserId()).orElse(null);
        if (null == userInfo) {
            return new ResponseData<>(ErrorMessage.USER_NOT_EXIST);
        }
        final List<GroupRoleInfo> groupRoleInfoList = groupRoleInfoRepository.findByGroupId(request.getGroupId());
        if (null != groupRoleInfoList) {
            groupRoleInfoList.stream()
                    .forEach(gr -> {
                        if (userRoleInfoRepository.countByUserIdAndRoleId(request.getUserId(), gr.getRoleId()).orElse(0) <= 0) {
                            final UserRoleInfo userRoleInfo = new UserRoleInfo();
                            userRoleInfo.setUserId(request.getUserId());
                            userRoleInfo.setRoleId(gr.getRoleId());
                            userRoleInfo.setStatus(gr.getStatus());

                            log.info("call userRoleInfoRepository.save()");
                            userRoleInfoRepository.save(userRoleInfo);
                        }
                    });
        }

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Page<GroupInfoDto>> findGroupPageable(SearchGroupRequest search) {
        log.info("call findGroupPageable(): {}", search);
        search.init();

        final Pageable pageable = search.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        log.info("call groupInfoRepository.findPageable()");
        final Page<GroupInfo> groupInfoPage = groupInfoRepository.findPageable(search, pageable);

        return new ResponseData<>(search.exchange(groupInfoMapper, groupInfoPage, pageable, GroupInfoDto.class));
    }

    @Override
    public ResponseData<List<GroupInfoDto>> findGroup(SearchGroupRequest search) {
        log.info("call findRole(): {}", search);
        search.init();

        log.info("call groupInfoRepository.find()");
        final List<GroupInfo> groupInfoList = groupInfoRepository.find(search);

        return new ResponseData<>(groupInfoMapper.mapAsList(groupInfoList, GroupInfoDto.class));
    }
}
