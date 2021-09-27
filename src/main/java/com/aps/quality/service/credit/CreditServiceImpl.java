package com.aps.quality.service.credit;

import com.aps.quality.entity.CreditApprovalInfo;
import com.aps.quality.entity.CreditInfo;
import com.aps.quality.mapper.CreditInfoMapper;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.credit.CreateCreditRequest;
import com.aps.quality.model.credit.SearchCreditRequest;
import com.aps.quality.model.credit.UpdateCreditRequest;
import com.aps.quality.model.dto.CreditInfoDto;
import com.aps.quality.repository.CertificateInfoRepository;
import com.aps.quality.repository.CreditApprovalInfoRepository;
import com.aps.quality.repository.CreditInfoRepository;
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
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class CreditServiceImpl extends OperationLogService implements CreditService {
    @Resource
    private CreditInfoRepository creditInfoRepository;
    @Resource
    private CreditApprovalInfoRepository creditApprovalInfoRepository;
    @Resource
    private CertificateInfoRepository certificateInfoRepository;

    @Resource
    private CreditInfoMapper creditInfoMapper;

    @Override
    public ResponseData<Boolean> create(final CreateCreditRequest request) {
        log.info("call create()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }

        final CreditInfo creditInfo = new CreditInfo();
        BeanUtils.copyProperties(request, creditInfo, DataUtil.getNullPropertyNames(request));
        creditInfo.setStatus(Const.Status.NORMAL.getCode());
        if (null != creditInfo.getCreditTime()) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(creditInfo.getCreditTime());
            creditInfo.setCreditYear(calendar.get(Calendar.YEAR));
            creditInfo.setCreditMonth(calendar.get(Calendar.MONTH + 1));
        }

        log.info("call creditInfoRepository.save()");
        creditInfo.beforeSave();
        creditInfoRepository.save(creditInfo);
        saveLog(Const.OperationType.CREATE, Const.OperationSubType.CREDIT, String.valueOf(creditInfo.getCreditId()), request);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> update(final UpdateCreditRequest request) {
        log.info("call update()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        final CreditInfo creditInfo = creditInfoRepository.findById(request.getCreditId()).orElse(null);
        if (null == creditInfo) {
            return new ResponseData(ErrorMessage.CREDIT_NOT_EXIST);
        }
        if (!Const.Status.NORMAL.equalWithCode(creditInfo.getStatus())) {
            return new ResponseData(ErrorMessage.PROHIBIT_UPDATING_DATA);
        }

        BeanUtils.copyProperties(request, creditInfo, DataUtil.getNullPropertyNames(request));
        if (null != creditInfo.getCreditTime()) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(creditInfo.getCreditTime());
            creditInfo.setCreditYear(calendar.get(Calendar.YEAR));
            creditInfo.setCreditMonth(calendar.get(Calendar.MONTH + 1));
        }

        log.info("call creditInfoRepository.save()");
        creditInfo.beforeSave();
        creditInfoRepository.save(creditInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.CAMPAIGN, String.valueOf(request.getCreditId()), request);

        creditApprovalInfoRepository.findByCreditId(request.getCreditId()).ifPresent(cas -> cas.forEach(ca -> creditApprovalInfoRepository.delete(ca)));
        if (null != request.getApprovalUserIds() && request.getApprovalUserIds().length > 0) {
            Stream.of(request.getApprovalUserIds()).forEach(u -> {
                final CreditApprovalInfo creditApprovalInfo = new CreditApprovalInfo();
                creditApprovalInfo.setCreditId(request.getCreditId());
                creditApprovalInfo.setUserId(u);
                creditApprovalInfo.setStatus(Const.Status.NORMAL.getCode());

                creditApprovalInfoRepository.save(creditApprovalInfo);
            });
        }

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> delete(Integer id) {
        log.info("call delete()");

        final CreditInfo creditInfo = creditInfoRepository.findById(id).orElse(null);
        if (null == creditInfo) {
            return new ResponseData(ErrorMessage.CREDIT_NOT_EXIST);
        }
        if (!Const.Status.NORMAL.equalWithCode(creditInfo.getStatus())) {
            return new ResponseData(ErrorMessage.PROHIBIT_DELETE_DATA);
        }

        certificateInfoRepository.findByCreditId(id).ifPresent(cs -> cs.forEach(c -> certificateInfoRepository.delete(c)));
        creditApprovalInfoRepository.findByCreditId(id).ifPresent(cas -> cas.forEach(ca -> creditApprovalInfoRepository.delete(ca)));
        creditInfoRepository.delete(creditInfo);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> submit(final Integer id) {

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Page<CreditInfoDto>> findPageable(final SearchCreditRequest request) {
        log.info("call findPageable(): {}", request);
        request.init();

        final Pageable pageable = request.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        log.info("call creditInfoRepository.findPageable()");
        final Page<CreditInfo> creditInfoPage = creditInfoRepository.findPageable(request, pageable);

        return new ResponseData<>(request.exchange(creditInfoMapper, creditInfoPage, pageable, CreditInfoDto.class));
    }

    @Override
    public ResponseData<List<CreditInfoDto>> find(final SearchCreditRequest request) {
        log.info("call find(): {}", request);
        request.init();

        log.info("call campaignInfoRepository.find()");
        final List<CreditInfo> creditInfos = creditInfoRepository.find(request);

        return new ResponseData<>(creditInfoMapper.mapAsList(creditInfos, CreditInfoDto.class));
    }
}
