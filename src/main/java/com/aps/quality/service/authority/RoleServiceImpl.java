package com.aps.quality.service.authority;

import com.aps.quality.entity.*;
import com.aps.quality.mapper.RoleInfoMapper;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.authority.*;
import com.aps.quality.model.dto.RoleInfoDto;
import com.aps.quality.repository.*;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleInfoRepository roleInfoRepository;
    @Resource
    private GroupInfoRepository groupInfoRepository;
    @Resource
    private GroupRoleInfoRepository groupRoleInfoRepository;
    @Resource
    private UserRoleInfoRepository userRoleInfoRepository;
    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private RoleInfoMapper roleInfoMapper;

    @Override
    public ResponseData<RoleInfoDto> create(CreateRoleRequest request) {
        log.info("Call create(): {}", request);
        if (!StringUtils.hasLength(request.getRoleName())) {
            return new ResponseData<>(ErrorMessage.ROLE_NAME_IS_NULL);
        }
        if (null == request.getFatherRoleId()) {
            return new ResponseData<>(ErrorMessage.FATHER_ROLE_ID_IS_NULL);
        }
        if (roleInfoRepository.countByRoleName(request.getRoleName()).orElse(0) > 0) {
            return new ResponseData<>(ErrorMessage.ROLE_NAME_EXIST);
        }
        if (null == request.getStatus()) {
            request.setStatus(1);
        }
        final RoleInfo roleInfo = roleInfoMapper.map(request, RoleInfo.class);

        log.info("Call roleInfoRepository.save()");
        roleInfoRepository.save(roleInfo);

        return new ResponseData<>(roleInfoMapper.map(roleInfo, RoleInfoDto.class));
    }

    @Override
    public ResponseData<RoleInfoDto> update(UpdateRoleRequest request) {
        log.info("Call update(): {}", request);
        if (null == request.getRoleId()) {
            return new ResponseData<>(ErrorMessage.ID_CAN_NOT_BE_NULL);
        }
        if (null == request.getVersion()) {
            return new ResponseData<>(ErrorMessage.VERSION_IS_NULL);
        }
        if (!StringUtils.hasLength(request.getRoleName())) {
            return new ResponseData<>(ErrorMessage.ROLE_NAME_IS_NULL);
        }
        if (!roleInfoRepository.findById(request.getRoleId()).isPresent()) {
            return new ResponseData<>(ErrorMessage.ROLE_NOT_EXIST);
        }
        final List<RoleInfo> roleInfoList = roleInfoRepository.findByRoleName(request.getRoleName());
        if (null != roleInfoList && roleInfoList.stream().anyMatch(r -> !r.getRoleId().equals(request.getRoleId()))) {
            return new ResponseData<>(ErrorMessage.ROLE_NAME_EXIST);
        }

        final RoleInfo roleInfo = new RoleInfo();
        BeanUtils.copyProperties(request, roleInfo, DataUtil.getNullPropertyNames(request));

        log.info("Call roleInfoRepository.save()");
        roleInfoRepository.save(roleInfo);

        final List<GroupRoleInfo> groupRoleInfoList = groupRoleInfoRepository.findByRoleId(request.getRoleId());
        if (null != groupRoleInfoList && !groupRoleInfoList.isEmpty()) {
            groupRoleInfoList.stream().forEach(gr -> gr.setStatus(roleInfo.getStatus()));

            log.info("Call groupRoleInfoRepository.saveAll()");
            groupRoleInfoRepository.saveAll(groupRoleInfoList);
        }
        final List<UserRoleInfo> userRoleInfoList = userRoleInfoRepository.findByRoleId(request.getRoleId()).orElse(new ArrayList<>());
        if (!userRoleInfoList.isEmpty()) {
            userRoleInfoList.stream().forEach(ur -> ur.setStatus(roleInfo.getStatus()));

            log.info("Call userRoleInfoRepository.saveAll()");
            userRoleInfoRepository.saveAll(userRoleInfoList);
        }

        return new ResponseData<>(roleInfoMapper.map(roleInfo, RoleInfoDto.class));
    }

    @Override
    public ResponseData<Boolean> delete(Integer id) {
        roleInfoRepository.deleteById(id);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> assignRoleToGroup(AssignRoleToGroupRequest request) {
        log.info("Call assignRoleToGroup(): {}", request);
        final RoleInfo roleInfo = roleInfoRepository.findById(request.getRoleId()).orElse(null);
        if (null == roleInfo) {
            return new ResponseData<>(ErrorMessage.ROLE_NOT_EXIST);
        }
        final GroupInfo groupInfo = groupInfoRepository.findById(request.getGroupId()).orElse(null);
        if (null == groupInfo) {
            return new ResponseData<>(ErrorMessage.GROUP_NOT_EXIST);
        }
        if (groupRoleInfoRepository.countByGroupIdAndRoleId(request.getGroupId(), request.getRoleId()).orElse(0) > 0) {
            return new ResponseData<>(ErrorMessage.ROLE_ALREADY_IN_GROUP);
        }

        final GroupRoleInfo groupRoleInfo = new GroupRoleInfo();
        groupRoleInfo.setGroupId(request.getGroupId());
        groupRoleInfo.setRoleId(request.getRoleId());
        groupRoleInfo.setStatus(roleInfo.getStatus());

        log.info("Call groupRoleInfoRepository.save()");
        groupRoleInfoRepository.save(groupRoleInfo);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> assignRoleToUser(AssignRoleToUserRequest request) {
        log.info("Call assignRoleToUser(): {}", request);
        final RoleInfo roleInfo = roleInfoRepository.findById(request.getRoleId()).orElse(null);
        if (null == roleInfo) {
            return new ResponseData<>(ErrorMessage.ROLE_NOT_EXIST);
        }
        final UserInfo userInfo = userInfoRepository.findById(request.getUserId()).orElse(null);
        if (null == userInfo) {
            return new ResponseData<>(ErrorMessage.USER_NOT_EXIST);
        }
        if (userRoleInfoRepository.countByUserIdAndRoleId(request.getUserId(), request.getRoleId()).orElse(0) > 0) {
            return new ResponseData<>(ErrorMessage.ROLE_ALREADY_IN_USER);
        }

        final UserRoleInfo userRoleInfo = new UserRoleInfo();
        userRoleInfo.setUserId(request.getUserId());
        userRoleInfo.setRoleId(request.getRoleId());
        userRoleInfo.setStatus(roleInfo.getStatus());

        log.info("Call userRoleInfoRepository.save()");
        userRoleInfoRepository.save(userRoleInfo);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> removeRoleFromGroup(RemoveRoleFromGroupRequest request) {
        log.info("Call removeRoleFromGroup(): {}", request);
        final List<GroupRoleInfo> groupRoleInfoList = groupRoleInfoRepository.findByGroupIdAndRoleId(request.getGroupId(), request.getRoleId());
        if (null != groupRoleInfoList) {
            groupRoleInfoList.stream().forEach(gr -> groupRoleInfoRepository.delete(gr));
        }

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> removeRoleFromUser(RemoveRoleFromUserRequest request) {
        log.info("Call removeRoleFromUser(): {}", request);
        final List<UserRoleInfo> userRoleInfoList = userRoleInfoRepository.findByUserIdAndRoleId(request.getUserId(), request.getRoleId()).orElse(new ArrayList<>());
        userRoleInfoList.stream().forEach(ur -> userRoleInfoRepository.delete(ur));

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Page<RoleInfoDto>> findRolePageable(SearchRoleRequest search) {
        log.info("Call findRolePageable(): {}", search);
        search.init();

        final Pageable pageable = search.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        log.info("Call roleInfoRepository.findPageable()");
        final Page<RoleInfo> auditInfoPage = roleInfoRepository.findPageable(search, pageable);

        return new ResponseData<>(search.exchange(roleInfoMapper, auditInfoPage, pageable, RoleInfoDto.class));
    }

    @Override
    public ResponseData<List<RoleInfoDto>> findRole(SearchRoleRequest search) {
        log.info("Call findRole(): {}", search);
        search.init();

        log.info("Call roleInfoRepository.find()");
        final List<RoleInfo> roleInfoList = roleInfoRepository.find(search);

        return new ResponseData<>(roleInfoMapper.mapAsList(roleInfoList, RoleInfoDto.class));
    }
}
