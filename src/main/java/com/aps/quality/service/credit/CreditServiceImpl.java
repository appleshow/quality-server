package com.aps.quality.service.credit;

import com.aps.quality.entity.CreditInfo;
import com.aps.quality.entity.UserInfo;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.credit.*;
import com.aps.quality.repository.CertificateInfoRepository;
import com.aps.quality.repository.CreditApprovalInfoRepository;
import com.aps.quality.repository.CreditInfoRepository;
import com.aps.quality.repository.UserInfoRepository;
import com.aps.quality.service.OperationLogService;
import com.aps.quality.service.component.FileAction;
import com.aps.quality.util.Const;
import com.aps.quality.util.DataUtil;
import com.aps.quality.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

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
    private UserInfoRepository userInfoRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Value("${file.upload.size:4}")
    private Integer fileSize;
    @Value(("${file.action.channel:NONE}"))
    private String fileActionChannel;
    @Resource
    private List<FileAction> fileActions;

    @Override
    public ResponseData<Boolean> create(final CreateCreditRequest request) {
        log.info("call create()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }

        if (null == request.getUserId()) {
            request.setUserId(createUser(request.getUserCode(), request.getUserName(), request.getUserGender(),
                    request.getUserPhone(), request.getOrganizationId(), request.getAtr1()));
        }

        final CreditInfo creditInfo = new CreditInfo();
        request.setAtr1(null);
        BeanUtils.copyProperties(request, creditInfo, DataUtil.getNullPropertyNames(request));
        creditInfo.setStatus(Const.CreditStatus.DRAFT.getCode());
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

        creditApprovalInfoRepository.findByCreditId(creditInfo.getCreditId()).ifPresent(cas -> cas.forEach(ca -> creditApprovalInfoRepository.delete(ca)));

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
        if (!(Const.CreditStatus.REJECT.equalWithCode(creditInfo.getStatus()) || Const.CreditStatus.DRAFT.equalWithCode(creditInfo.getStatus()))) {
            return new ResponseData(ErrorMessage.PROHIBIT_UPDATING_DATA);
        }

        if (null == request.getUserId()) {
            request.setUserId(createUser(request.getUserCode(), request.getUserName(), request.getUserGender(),
                    request.getUserPhone(), request.getOrganizationId(), request.getAtr1()));
        }

        request.setAtr1(null);
        BeanUtils.copyProperties(request, creditInfo, DataUtil.getNullPropertyNames(request));
        creditInfo.setStatus(Const.CreditStatus.DRAFT.getCode());
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
    public ResponseData<Page<CreditReport>> findPageable(final SearchCreditRequest request) {
        log.info("call findPageable(): {}", request);
        request.init();

        searchCheck(request);

        final Pageable pageable = request.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        if (request.isGroupByCampaign() && request.isGroupByUser()) {
            log.info("call creditInfoRepository.findByCampaignAndUserGroupPageable()");
            return new ResponseData<>(creditInfoRepository.findByCampaignAndUserGroupPageable(request, pageable));
        } else if (request.isGroupByCampaign()) {
            log.info("call creditInfoRepository.findByCampaignGroupPageable()");
            return new ResponseData<>(creditInfoRepository.findByCampaignGroupPageable(request, pageable));
        } else if (request.isGroupByUser()) {
            log.info("call creditInfoRepository.findByUserGroupPageable()");
            return new ResponseData<>(creditInfoRepository.findByUserGroupPageable(request, pageable));
        } else {
            log.info("call creditInfoRepository.findPageable()");
            return new ResponseData<>(creditInfoRepository.findPageable(request, pageable));
        }
    }

    @Override
    public ResponseData<List<CreditReport>> find(final SearchCreditRequest request) {
        log.info("call find(): {}", request);
        request.init();

        searchCheck(request);

        log.info("call creditInfoRepository.find()");
        return new ResponseData<>(creditInfoRepository.find(request));
    }

    @Override
    public ResponseData<List<CreditReport>> findSub(SearchCreditRequest request) {
        log.info("call findSub(): {}", request);
        request.init();

        searchCheck(request);

        log.info("call creditInfoRepository.findSub()");
        return new ResponseData<>(creditInfoRepository.findSub(request));
    }

    @Override
    public ResponseData<UploadResponse> upload(MultipartFile file) {
        final String fileOriginalName = file.getOriginalFilename();

        log.info("call upload(): {}", fileOriginalName);
        final ErrorMessage check = checkUploadImage(file);
        if (null != check) {
            return new ResponseData<>(check);
        }

        byte[] imageBytes = null;
        try (final InputStream inputStream = file.getInputStream()) {
            imageBytes = new byte[inputStream.available()];
            inputStream.read(imageBytes);
        } catch (Exception e) {
            log.error("Call imageFile.getInputStream got an error: ", e);
        }
        if (null != imageBytes) {
            final String imageFileName = fileActions.stream()
                    .filter(a -> a.isMatch(fileActionChannel))
                    .findAny()
                    .map(a -> a.saveFromFile(file, Const.ContentType.IMAGE_JPG.getCode()))
                    .orElse(null);

            if (null == imageFileName) {
                return new ResponseData<>(ErrorMessage.IMAGE_INVALID);
            }

            return new ResponseData<>(new UploadResponse(fileOriginalName, imageFileName));
        } else {
            return new ResponseData<>(ErrorMessage.IMAGE_INVALID);
        }
    }

    private Integer createUser(String userCode, String userName, String userGender, String userPhone, Integer organizationId, String atr1) {
        log.info("call createUser: {}", userCode);
        final UserInfo userInfoCheck = userInfoRepository.findByUserCode(userCode).orElse(null);
        if (null != userInfoCheck) {
            return userInfoCheck.getUserId();
        }

        final UserInfo userInfo = new UserInfo();
        userInfo.setUserCode(userCode);
        userInfo.setUserName(userName);
        userInfo.setUserPassword(passwordEncoder.encode(userCode));
        userInfo.setUserGender(userGender);
        userInfo.setUserPhone(userPhone);
        userInfo.setOrganizationId(organizationId);
        userInfo.setUserType(Const.UserType.STUDENT);
        userInfo.setAtr1(atr1);
        userInfo.setStatus(1);

        log.info("call userInfoRepository.save()");
        userInfoRepository.save(userInfo);

        return userInfo.getUserId();
    }

    private void searchCheck(final SearchCreditRequest request) {
        final String currentUserType = DataUtil.getAuthorityUserType();
        final Integer currentUserOrganizationId = DataUtil.getAuthorityOrganizationId();

        if (Const.UserType.YLC_L1.equals(currentUserType) || Const.UserType.YLC_L2.equals(currentUserType)) {
            request.setCreateBy(DataUtil.getAuthorityUserName());
            request.setOrganizationId(1);
        } else {
            request.setCreateBy(null);
            if (null == request.getOrganizationId()) {
                request.setOrganizationId(DataUtil.getAuthorityOrganizationId());
            } else if (request.getOrganizationId() < DataUtil.getAuthorityOrganizationId()) {
                request.setOrganizationId(currentUserOrganizationId);
            }
        }
        if (Const.UserType.STUDENT.equals(currentUserType)) {
            request.setStatus(Const.CreditStatus.APPROVED.getCode());
        }
    }

    private ErrorMessage checkUploadImage(final MultipartFile imageFile) {
        final String fileName = imageFile.getOriginalFilename();
        if (fileName == null || !fileName.matches("^.+(.JPEG|.jpeg|.JPG|.jpg|.BMP|.bmp|.PNG|.png)$")) {
            return ErrorMessage.IMAGE_TYPE_NOT_SUPPORT;
        }
        if (imageFile.getSize() > fileSize * 1024 * 1024) {
            return ErrorMessage.IMAGE_SIZE_NOT_SUPPORT;
        }

        return null;
    }
}
