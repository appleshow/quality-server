package com.aps.quality.service.organization;

import com.aps.quality.entity.OrganizationInfo;
import com.aps.quality.entity.OrganizationMappingInfo;
import com.aps.quality.mapper.OrganizationInfoMapper;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.dto.OrganizationInfoDto;
import com.aps.quality.model.organization.CreateOrganizationRequest;
import com.aps.quality.model.organization.SearchOrganizationRequest;
import com.aps.quality.model.organization.UpdateOrganizationRequest;
import com.aps.quality.repository.OrganizationInfoRepository;
import com.aps.quality.repository.OrganizationMappingInfoRepository;
import com.aps.quality.repository.UserInfoRepository;
import com.aps.quality.repository.UserOrganizationInfoRepository;
import com.aps.quality.service.OperationLogService;
import com.aps.quality.util.Const;
import com.aps.quality.util.DataUtil;
import com.aps.quality.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class OrganizationServiceImpl extends OperationLogService implements OrganizationService {
    @Resource
    private OrganizationInfoRepository organizationInfoRepository;
    @Resource
    private OrganizationMappingInfoRepository organizationMappingInfoRepository;
    @Resource
    private UserOrganizationInfoRepository userOrganizationInfoRepository;
    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private OrganizationInfoMapper organizationInfoMapper;

    @Override
    public ResponseData<Boolean> create(CreateOrganizationRequest request) {
        log.info("call create()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        log.info("call organizationInfoRepository.countByOrganizationName()");
        if (organizationInfoRepository.countByOrganizationName(0, request.getFatherOrganizationId(), request.getOrganizationName()).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.NAME_INVALID);
        }
        final OrganizationInfo organizationInfo = new OrganizationInfo();
        BeanUtils.copyProperties(request, organizationInfo, DataUtil.getNullPropertyNames(request));
        organizationInfo.setStatus(Const.Status.NORMAL.getCode());

        log.info("call organizationInfoRepository.save()");
        organizationInfoRepository.save(organizationInfo);
        saveLog(Const.OperationType.CREATE, Const.OperationSubType.ORGANIZATION, String.format("%d", organizationInfo.getOrganizationId()), request);

        final Set<Integer> fatherIds = new HashSet<>();
        fatherIds.add(organizationInfo.getOrganizationId());
        final Set<Integer> fatherIdsCheck = new HashSet<>();
        fatherIdsCheck.add(request.getFatherOrganizationId());
        while (!fatherIdsCheck.isEmpty()) {
            final List<Integer> tempIds = new ArrayList<>();
            fatherIdsCheck.forEach(id -> {
                fatherIds.add(id);
                tempIds.add(id);
            });
            fatherIdsCheck.clear();
            tempIds.forEach(id -> {
                log.info("call organizationMappingInfoRepository.findByChildOrganizationId({})", id);
                final List<OrganizationMappingInfo> organizationMappingInfoList = organizationMappingInfoRepository.findByChildOrganizationIdAndFlag(id, Const.YES).orElse(null);
                if (null != organizationMappingInfoList && !organizationMappingInfoList.isEmpty()) {
                    organizationMappingInfoList.forEach(om -> fatherIdsCheck.add(om.getFatherOrganizationId()));
                }
            });
        }

        fatherIds.forEach(f -> {
            final OrganizationMappingInfo organizationMappingInfo = new OrganizationMappingInfo();
            organizationMappingInfo.setFatherOrganizationId(f);
            organizationMappingInfo.setChildOrganizationId(organizationInfo.getOrganizationId());
            if (organizationInfo.getOrganizationId().equals(f)) {
                organizationMappingInfo.setFlag(Const.NO);
            } else {
                organizationMappingInfo.setFlag(Const.YES);
            }
            organizationMappingInfo.setStatus(1);

            organizationMappingInfoRepository.save(organizationMappingInfo);
        });

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> update(UpdateOrganizationRequest request) {
        log.info("call update()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        final OrganizationInfo organizationInfo = organizationInfoRepository.findById(request.getOrganizationId()).orElse(null);
        if (null == organizationInfo) {
            return new ResponseData(ErrorMessage.ORGANIZATION_ID_INVALID);
        }
        log.info("call organizationInfoRepository.countByOrganizationName()");
        if (organizationInfoRepository.countByOrganizationName(request.getOrganizationId(), organizationInfo.getFatherOrganizationId(), request.getOrganizationName()).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.NAME_INVALID);
        }
        BeanUtils.copyProperties(request, organizationInfo, DataUtil.getNullPropertyNames(request));
        log.info("call organizationInfoRepository.save()");
        organizationInfoRepository.save(organizationInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.ORGANIZATION, String.format("%d", organizationInfo.getOrganizationId()), request);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> delete(Integer id) {
        log.info("call delete({})", id);

        log.info("call userOrganizationInfoRepository.countByOrganizationId({})", id);
        if (userOrganizationInfoRepository.countByOrganizationId(id).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.ORGANIZATION_HAS_BEEN_USED);
        }
        log.info("call organizationMappingInfoRepository.countByFatherOrganizationId({})", id);
        if (organizationMappingInfoRepository.countByFatherOrganizationIdAndFlag(id, Const.YES).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.ORGANIZATION_HAS_BEEN_USED);
        }
        log.info("call userInfoRepository.countByOrganizationId({})", id);
        if (userInfoRepository.countByOrganizationId(id).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.ORGANIZATION_HAS_BEEN_USED);
        }

        log.info("call organizationMappingInfoRepository.delete(childOrganizationId {})", id);
        organizationMappingInfoRepository.findByChildOrganizationId(id).ifPresent(ms -> ms.forEach(m -> organizationMappingInfoRepository.delete(m)));
        final OrganizationInfo organizationInfo = organizationInfoRepository.findById(id).orElse(null);
        if (null != organizationInfo) {
            organizationInfoRepository.delete(organizationInfo);
            saveLog(Const.OperationType.DELETE, Const.OperationSubType.ORGANIZATION, String.format("%d", id), null);
        }

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Page<OrganizationInfoDto>> findPageable(SearchOrganizationRequest request) {
        log.info("call findPageable(): {}", request);
        request.init();

        final Pageable pageable = request.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        log.info("call organizationInfoPage.findPageable()");
        final Page<OrganizationInfo> organizationInfoPage = organizationInfoRepository.findPageable(request, pageable);

        return new ResponseData<>(request.exchange(organizationInfoMapper, organizationInfoPage, pageable, OrganizationInfoDto.class));
    }

    @Override
    public ResponseData<List<OrganizationInfoDto>> find(SearchOrganizationRequest request) {
        log.info("call find(): {}", request);
        request.init();

        log.info("call organizationInfoRepository.find()");
        final List<OrganizationInfo> organizationInfoList = organizationInfoRepository.find(request);

        return new ResponseData<>(organizationInfoMapper.mapAsList(organizationInfoList, OrganizationInfoDto.class));
    }

    @Override
    public ResponseData<List<OrganizationInfoDto>> findByUser(Integer userId) {
        log.info("call organizationInfoRepository.findByUser({})", userId);
        final List<OrganizationInfo> organizationInfoList = organizationInfoRepository.findByUser(userId);

        return new ResponseData<>(organizationInfoMapper.mapAsList(organizationInfoList, OrganizationInfoDto.class));
    }
}
