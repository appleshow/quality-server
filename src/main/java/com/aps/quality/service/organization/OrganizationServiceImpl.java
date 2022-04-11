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
        log.info("Call create()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        log.info("Call organizationInfoRepository.countByOrganizationName()");
        if (organizationInfoRepository.countByOrganizationName(0, request.getFatherOrganizationId(), request.getOrganizationName()).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.NAME_INVALID);
        }
        final OrganizationInfo organizationInfo = new OrganizationInfo();
        BeanUtils.copyProperties(request, organizationInfo, DataUtil.getNullPropertyNames(request));
        organizationInfo.setStatus(Const.Status.NORMAL.getCode());

        log.info("Call organizationInfoRepository.save()");
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
                log.info("Call organizationMappingInfoRepository.findByChildOrganizationId({})", id);
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
        log.info("Call update()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        final OrganizationInfo organizationInfo = organizationInfoRepository.findById(request.getOrganizationId()).orElse(null);
        if (null == organizationInfo) {
            return new ResponseData(ErrorMessage.ORGANIZATION_ID_INVALID);
        }
        log.info("Call organizationInfoRepository.countByOrganizationName()");
        if (organizationInfoRepository.countByOrganizationName(request.getOrganizationId(), organizationInfo.getFatherOrganizationId(), request.getOrganizationName()).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.NAME_INVALID);
        }
        BeanUtils.copyProperties(request, organizationInfo, DataUtil.getNullPropertyNames(request));
        if (null != request.getNewFatherOrganizationId()) {
            log.info("Change father organization: {} from {} to {}", organizationInfo.getOrganizationId(), organizationInfo.getFatherOrganizationId(), request.getNewFatherOrganizationId());
            final int oldFatherOrganizationIdL2 = organizationInfo.getFatherOrganizationId();
            final int oldFatherOrganizationIdL1 = organizationInfoRepository.findById(oldFatherOrganizationIdL2).map(OrganizationInfo::getFatherOrganizationId).orElse(0);
            final int newFatherOrganizationIdL2 = request.getNewFatherOrganizationId();
            final int newFatherOrganizationIdL1 = organizationInfoRepository.findById(newFatherOrganizationIdL2).map(OrganizationInfo::getFatherOrganizationId).orElse(0);
            organizationInfo.setFatherOrganizationId(request.getNewFatherOrganizationId());

            final List<OrganizationMappingInfo> omiList = organizationMappingInfoRepository.findByFatherOrganizationId(organizationInfo.getOrganizationId()).orElse(new ArrayList<>());
            omiList.forEach(m -> {
                final List<OrganizationMappingInfo> omiChild = organizationMappingInfoRepository.findByChildOrganizationId(m.getChildOrganizationId()).orElse(new ArrayList<>());
                omiChild.forEach(mc -> {
                    if (mc.getFatherOrganizationId().equals(oldFatherOrganizationIdL1)) {
                        mc.setFatherOrganizationId(newFatherOrganizationIdL1);

                        organizationMappingInfoRepository.save(mc);
                    } else if (mc.getFatherOrganizationId().equals(oldFatherOrganizationIdL2)) {
                        mc.setFatherOrganizationId(newFatherOrganizationIdL2);

                        organizationMappingInfoRepository.save(mc);
                    }
                });
            });
        }
        log.info("Call organizationInfoRepository.save()");
        organizationInfoRepository.save(organizationInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.ORGANIZATION, String.format("%d", organizationInfo.getOrganizationId()), request);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> delete(Integer id) {
        log.info("Call delete({})", id);

        log.info("Call userOrganizationInfoRepository.countByOrganizationId({})", id);
        if (userOrganizationInfoRepository.countByOrganizationId(id).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.ORGANIZATION_HAS_BEEN_USED);
        }
        log.info("Call organizationMappingInfoRepository.countByFatherOrganizationId({})", id);
        if (organizationMappingInfoRepository.countByFatherOrganizationIdAndFlag(id, Const.YES).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.ORGANIZATION_HAS_BEEN_USED);
        }
        log.info("Call userInfoRepository.countByOrganizationId({})", id);
        if (userInfoRepository.countByOrganizationId(id).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.ORGANIZATION_HAS_BEEN_USED);
        }

        log.info("Call organizationMappingInfoRepository.delete(childOrganizationId {})", id);
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
        log.info("Call findPageable(): {}", request);
        request.init();

        final Pageable pageable = request.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        log.info("Call organizationInfoPage.findPageable()");
        final Page<OrganizationInfo> organizationInfoPage = organizationInfoRepository.findPageable(request, pageable);

        return new ResponseData<>(request.exchange(organizationInfoMapper, organizationInfoPage, pageable, OrganizationInfoDto.class));
    }

    @Override
    public ResponseData<List<OrganizationInfoDto>> find(SearchOrganizationRequest request) {
        log.info("Call find(): {}", request);
        request.init();

        log.info("Call organizationInfoRepository.find()");
        final List<OrganizationInfo> organizationInfoList = organizationInfoRepository.find(request);

        return new ResponseData<>(organizationInfoMapper.mapAsList(organizationInfoList, OrganizationInfoDto.class));
    }

    @Override
    public ResponseData<List<OrganizationInfoDto>> findByUser(Integer userId) {
        log.info("Call organizationInfoRepository.findByUser({})", userId);
        final List<OrganizationInfo> organizationInfoList = organizationInfoRepository.findByUser(userId);

        return new ResponseData<>(organizationInfoMapper.mapAsList(organizationInfoList, OrganizationInfoDto.class));
    }
}
