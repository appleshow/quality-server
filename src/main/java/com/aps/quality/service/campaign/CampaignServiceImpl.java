package com.aps.quality.service.campaign;

import com.aps.quality.entity.*;
import com.aps.quality.mapper.CampaignInfoMapper;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.campaign.CreateCampaignRequest;
import com.aps.quality.model.campaign.SearchCampaignRequest;
import com.aps.quality.model.campaign.UpdateCampaignRequest;
import com.aps.quality.model.dto.CampaignInfoDto;
import com.aps.quality.repository.*;
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
import java.util.List;

@Slf4j
@Service
public class CampaignServiceImpl extends OperationLogService implements CampaignService {
    @Resource
    private CampaignInfoRepository campaignInfoRepository;

    @Resource
    private CampaignInfoMapper campaignInfoMapper;

    @Override
    public ResponseData<Boolean> create(final CreateCampaignRequest request) {
        log.info("call create()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        if (campaignInfoRepository.countByName(0, request.getCampaignName()).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.NAME_EXIST);
        }

        final CampaignInfo campaignInfo = new CampaignInfo();
        BeanUtils.copyProperties(request, campaignInfo, DataUtil.getNullPropertyNames(request));
        campaignInfo.setStatus(Const.Status.NORMAL.getCode());

        log.info("call campaignInfoRepository.save()");
        campaignInfo.beforeSave();
        campaignInfoRepository.save(campaignInfo);
        saveLog(Const.OperationType.CREATE, Const.OperationSubType.CAMPAIGN, request.getCampaignName(), request);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> update(final UpdateCampaignRequest request) {
        log.info("call update()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        final CampaignInfo campaignInfo = campaignInfoRepository.findById(request.getCampaignId()).orElse(null);
        if (null == campaignInfo) {
            return new ResponseData(ErrorMessage.CAMPAIGN_NOT_EXIST);
        }
        BeanUtils.copyProperties(request, campaignInfo, DataUtil.getNullPropertyNames(request));
        log.info("call campaignInfoRepository.save()");
        campaignInfo.beforeSave();
        campaignInfoRepository.save(campaignInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.CAMPAIGN, campaignInfo.getCampaignName(), request);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> delete(Integer id) {
        log.info("call delete()");

        final CampaignInfo campaignInfo = campaignInfoRepository.findById(id).orElse(null);
        if (null == campaignInfo) {
            return new ResponseData(ErrorMessage.CAMPAIGN_NOT_EXIST);
        }

        campaignInfoRepository.delete(campaignInfo);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> invalid(final Integer id) {
        log.info("call invalid({})", id);

        final CampaignInfo campaignInfo = campaignInfoRepository.findById(id).orElse(null);
        if (null == campaignInfo) {
            return new ResponseData(ErrorMessage.CAMPAIGN_NOT_EXIST);
        }
        campaignInfo.setStatus(Const.Status.INVALID.getCode());

        log.info("call campaignInfoRepository.save()");
        campaignInfoRepository.save(campaignInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.CAMPAIGN_STATUS, campaignInfo.getCampaignName(), Const.Status.INVALID.getDescription());

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> enable(Integer id) {
        log.info("call invalid({})", id);

        final CampaignInfo campaignInfo = campaignInfoRepository.findById(id).orElse(null);
        if (null == campaignInfo) {
            return new ResponseData(ErrorMessage.CAMPAIGN_NOT_EXIST);
        }
        campaignInfo.setStatus(Const.Status.NORMAL.getCode());

        log.info("call campaignInfoRepository.save()");
        campaignInfoRepository.save(campaignInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.USER_STATUS, campaignInfo.getCampaignName(), Const.Status.NORMAL.getDescription());

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Page<CampaignInfoDto>> findPageable(final SearchCampaignRequest request) {
        log.info("call findPageable(): {}", request);
        request.init();

        final Pageable pageable = request.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        log.info("call campaignInfoRepository.findPageable()");
        final Page<CampaignInfo> campaignInfoPage = campaignInfoRepository.findPageable(request, pageable);

        return new ResponseData<>(request.exchange(campaignInfoMapper, campaignInfoPage, pageable, CampaignInfoDto.class));
    }

    @Override
    public ResponseData<List<CampaignInfoDto>> find(final SearchCampaignRequest request) {
        log.info("call find(): {}", request);
        request.init();

        log.info("call campaignInfoRepository.find()");
        final List<CampaignInfo> campaignInfos = campaignInfoRepository.find(request);

        return new ResponseData<>(campaignInfoMapper.mapAsList(campaignInfos, CampaignInfoDto.class));
    }
}
