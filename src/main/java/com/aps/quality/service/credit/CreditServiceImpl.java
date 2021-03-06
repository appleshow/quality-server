package com.aps.quality.service.credit;

import com.aps.quality.entity.*;
import com.aps.quality.mapper.CertificateInfoMapper;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.credit.*;
import com.aps.quality.model.dto.CertificateInfoDto;
import com.aps.quality.repository.*;
import com.aps.quality.service.OperationLogService;
import com.aps.quality.service.component.FileAction;
import com.aps.quality.util.Const;
import com.aps.quality.util.DataUtil;
import com.aps.quality.util.ErrorMessage;
import com.aps.quality.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    private OrganizationMappingInfoRepository organizationMappingInfoRepository;

    @Resource
    private CertificateInfoMapper certificateInfoMapper;

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
        log.info("Call create()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }

        if (null == request.getUserId()) {
            final Integer currentUserOrganizationId = DataUtil.getAuthorityOrganizationId();
            if (request.getOrganizationId().compareTo(currentUserOrganizationId) < 0) {
                return new ResponseData(ErrorMessage.ORGANIZATION_NOT_MATCH, request.getUserCode());
            }
            final UserInfo userInfo = createUser(request.getUserCode(), request.getUserName(), request.getUserGender(),
                    request.getUserPhone(), request.getOrganizationId(), request.getAtr1());
            if (!request.getOrganizationId().equals(userInfo.getOrganizationId())) {
                return new ResponseData(ErrorMessage.ORGANIZATION_NOT_MATCH, request.getUserCode());
            }
            request.setUserId(userInfo.getUserId());
        }

        final CreditInfo creditInfo = new CreditInfo();
        BeanUtils.copyProperties(request, creditInfo, DataUtil.getNullPropertyNames(request));
        creditInfo.setAtr1(null);
        creditInfo.setStatus(Const.CreditStatus.DRAFT.getCode());
        if (null != creditInfo.getCreditTime()) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(creditInfo.getCreditTime());
            creditInfo.setCreditYear(calendar.get(Calendar.YEAR));
            creditInfo.setCreditMonth(calendar.get(Calendar.MONTH) + 1);
        }

        log.info("Call creditInfoRepository.save()");
        creditInfo.beforeSave();
        creditInfoRepository.save(creditInfo);
        saveLog(Const.OperationType.CREATE, Const.OperationSubType.CREDIT, String.valueOf(creditInfo.getCreditId()), request);

        creditApprovalInfoRepository.findByCreditId(creditInfo.getCreditId()).ifPresent(cas -> cas.forEach(ca -> creditApprovalInfoRepository.delete(ca)));

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> update(final UpdateCreditRequest request) {
        log.info("Call update()");

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
            final Integer currentUserOrganizationId = DataUtil.getAuthorityOrganizationId();
            if (request.getOrganizationId().compareTo(currentUserOrganizationId) < 0) {
                return new ResponseData(ErrorMessage.ORGANIZATION_NOT_MATCH, request.getUserCode());
            }
            final UserInfo userInfo = createUser(request.getUserCode(), request.getUserName(), request.getUserGender(),
                    request.getUserPhone(), request.getOrganizationId(), request.getAtr1());
            if (!request.getOrganizationId().equals(userInfo.getOrganizationId())) {
                return new ResponseData(ErrorMessage.ORGANIZATION_NOT_MATCH, request.getUserCode());
            }
            request.setUserId(userInfo.getUserId());
        }

        BeanUtils.copyProperties(request, creditInfo, DataUtil.getNullPropertyNames(request));
        creditInfo.setAtr1(null);
        creditInfo.setStatus(Const.CreditStatus.DRAFT.getCode());
        if (null != creditInfo.getCreditTime()) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(creditInfo.getCreditTime());
            creditInfo.setCreditYear(calendar.get(Calendar.YEAR));
            creditInfo.setCreditMonth(calendar.get(Calendar.MONTH) + 1);
        }

        log.info("Call creditInfoRepository.save()");
        creditInfo.beforeSave();
        creditInfoRepository.save(creditInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.CAMPAIGN, String.valueOf(request.getCreditId()), request);

        creditApprovalInfoRepository.findByCreditId(request.getCreditId()).ifPresent(cas -> cas.forEach(ca -> creditApprovalInfoRepository.delete(ca)));

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> delete(Integer id) {
        log.info("Call delete()");

        final CreditInfo creditInfo = creditInfoRepository.findById(id).orElse(null);
        if (null == creditInfo) {
            return new ResponseData(ErrorMessage.CREDIT_NOT_EXIST);
        }
        if (Const.CreditStatus.DRAFT.getCode().compareTo(creditInfo.getStatus()) < 0) {
            return new ResponseData(ErrorMessage.PROHIBIT_DELETE_DATA);
        }

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> deleteBatch(Integer[] ids) {
        if (null == ids) {
            return new ResponseData<>(true);
        }

        final List<CreditInfo> creditInfoList = new ArrayList<>();
        for (Integer id : ids) {
            final CreditInfo creditInfo = creditInfoRepository.findById(id).orElse(null);
            if (null == creditInfo) {
                return new ResponseData(ErrorMessage.CREDIT_NOT_EXIST, String.valueOf(id));
            }
            if (Const.CreditStatus.DRAFT.getCode().compareTo(creditInfo.getStatus()) < 0) {
                return new ResponseData(ErrorMessage.PROHIBIT_DELETE_DATA, String.valueOf(id));
            }
            creditInfoList.add(creditInfo);
        }

        creditInfoList.stream().forEach(ci -> {
            certificateInfoRepository.findByCreditId(ci.getCreditId()).ifPresent(cs -> cs.forEach(c -> certificateInfoRepository.delete(c)));
            creditApprovalInfoRepository.findByCreditId(ci.getCreditId()).ifPresent(cas -> cas.forEach(ca -> creditApprovalInfoRepository.delete(ca)));
            creditInfoRepository.delete(ci);
        });

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> submit(final List<SubmitRequest> requests) {
        log.info("Call submit(): {}", requests);
        if (null == requests) {
            return new ResponseData<>(true);
        }
        final String currentUserType = DataUtil.getAuthorityUserType();
        final Integer status = Const.UserType.FACULTY.equals(currentUserType) || Const.UserType.YLC_L1.equals(currentUserType)
                ? Const.CreditStatus.APPROVING.getCode() : Const.CreditStatus.SUBMIT.getCode();
        requests.forEach(request -> {
            final List<Integer> creditIdList = new ArrayList<>();

            if (StringUtils.hasLength(request.getCreditIds())) {
                final String[] creditIds = request.getCreditIds().split(",");
                Arrays.stream(creditIds).forEach(creditId -> creditIdList.add(Integer.parseInt(creditId)));
            } else if (null != request.getCreditId()) {
                creditIdList.add(request.getCreditId());
            }

            creditIdList.forEach(creditId -> {
                final CreditInfo creditInfo = creditInfoRepository.findById(creditId).orElse(null);
                if (null != creditInfo && Const.CreditStatus.canBeSubmitted(creditInfo.getStatus())) {
                    creditInfo.setAtr1(null);
                    creditInfo.setAtr2(null);
                    creditInfo.setAtr3(null);
                    creditInfo.setStatus(status);

                    creditInfoRepository.save(creditInfo);
                }
            });
        });

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> reject(RejectRequest request) {
        log.info("Call reject(): {}", request);
        final String currentUserType = DataUtil.getAuthorityUserType();
        if (!Const.UserType.canBeApprove(currentUserType)) {
            return new ResponseData<>(ErrorMessage.INSUFFICIENT_PERMISSIONS);
        }
        if (null == request.getRequests()) {
            return new ResponseData<>(true);
        }

        request.getRequests().forEach(r -> {
            final List<Integer> creditIdList = new ArrayList<>();

            if (StringUtils.hasLength(r.getCreditIds())) {
                final String[] creditIds = r.getCreditIds().split(",");
                Arrays.stream(creditIds).forEach(creditId -> creditIdList.add(Integer.parseInt(creditId)));
            } else if (null != r.getCreditId()) {
                creditIdList.add(r.getCreditId());
            }

            creditIdList.forEach(creditId -> {
                final CreditInfo creditInfo = creditInfoRepository.findById(creditId).orElse(null);
                final Integer creditStatus = creditInfo.getStatus();
                if (null != creditInfo && Const.CreditStatus.canBeRejected(creditInfo.getStatus(), currentUserType)) {
                    creditInfo.setStatus(Const.CreditStatus.REJECT.getCode());
                    creditInfo.setAtr1(request.getReason());
                    creditInfo.setAtr2(DataUtil.getAuthorityUserName());
                    creditInfo.setAtr3(DataUtil.getAuthorityUserType());
                    creditInfoRepository.save(creditInfo);

                    final CreditApprovalInfo creditApprovalInfo = new CreditApprovalInfo();
                    creditApprovalInfo.setCreditId(creditId);
                    creditApprovalInfo.setUserId(DataUtil.getAuthorityUserId());
                    creditApprovalInfo.setType(creditInfo.getStatus());
                    creditApprovalInfo.setFlag(Const.YES);
                    creditApprovalInfo.setStatus(creditStatus);
                    creditApprovalInfo.setAtr1(request.getReason());
                    creditApprovalInfoRepository.save(creditApprovalInfo);
                }
            });
        });

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> approve(List<SubmitRequest> requests) {
        log.info("Call approve():", requests);
        final String userType = DataUtil.getAuthorityUserType();

        if (!Const.UserType.canBeApprove(userType)) {
            return new ResponseData<>(ErrorMessage.INSUFFICIENT_PERMISSIONS);
        }
        if (null == requests) {
            return new ResponseData<>(true);
        }

        requests.forEach(r -> {
            final List<Integer> creditIdList = new ArrayList<>();

            if (StringUtils.hasLength(r.getCreditIds())) {
                final String[] creditIds = r.getCreditIds().split(",");
                Arrays.stream(creditIds).forEach(creditId -> creditIdList.add(Integer.parseInt(creditId)));
            } else if (null != r.getCreditId()) {
                creditIdList.add(r.getCreditId());
            }

            creditIdList.forEach(creditId -> {
                final CreditInfo creditInfo = creditInfoRepository.findById(creditId).orElse(null);
                final Integer creditStatus = creditInfo.getStatus();
                if (null != creditInfo && Const.CreditStatus.canBeApproved(creditInfo.getStatus())) {
                    creditInfo.setStatus(Const.UserType.getCreditStatus(userType).getCode());
                    creditInfoRepository.save(creditInfo);

                    final CreditApprovalInfo creditApprovalInfo = new CreditApprovalInfo();
                    creditApprovalInfo.setCreditId(creditId);
                    creditApprovalInfo.setUserId(DataUtil.getAuthorityUserId());
                    creditApprovalInfo.setType(creditInfo.getStatus());
                    creditApprovalInfo.setFlag(Const.YES);
                    creditApprovalInfo.setStatus(creditStatus);
                    creditApprovalInfoRepository.save(creditApprovalInfo);
                }
            });
        });


        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Page<CreditReport>> findPageable(final SearchCreditRequest request) {
        log.info("Call findPageable(): {}", request);
        request.init();

        searchCheck(request);

        final Pageable pageable = request.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        if (request.isGroupByCampaign() && request.isGroupByUser()) {
            log.info("Call creditInfoRepository.findByCampaignAndUserGroupPageable()");
            return new ResponseData<>(creditInfoRepository.findByCampaignAndUserGroupPageable(request, pageable));
        } else if (request.isGroupByCampaign()) {
            log.info("Call creditInfoRepository.findByCampaignGroupPageable()");
            return new ResponseData<>(creditInfoRepository.findByCampaignGroupPageable(request, pageable));
        } else if (request.isGroupByUser()) {
            log.info("Call creditInfoRepository.findByUserGroupPageable()");
            return new ResponseData<>(creditInfoRepository.findByUserGroupPageable(request, pageable));
        } else {
            log.info("Call creditInfoRepository.findPageable()");
            return new ResponseData<>(creditInfoRepository.findPageable(request, pageable));
        }
    }

    @Override
    public ResponseData<List<CreditReport>> find(final SearchCreditRequest request) {
        log.info("Call find(): {}", request);
        request.init();

        searchCheck(request);
        final Sort sort = request.getDefaultSort(new Sort.Order(Sort.Direction.DESC, "createTime"));
        if (request.isGroupByCampaign() && request.isGroupByUser()) {
            log.info("Call creditInfoRepository.findByCampaignAndUserGroup()");
            return new ResponseData<>(creditInfoRepository.findByCampaignAndUserGroup(request, sort));
        } else if (request.isGroupByCampaign()) {
            log.info("Call creditInfoRepository.findByCampaignGroup()");
            return new ResponseData<>(creditInfoRepository.findByCampaignGroup(request, sort));
        } else if (request.isGroupByUser()) {
            log.info("Call creditInfoRepository.findByUserGroup()");
            return new ResponseData<>(creditInfoRepository.findByUserGroup(request, sort));
        } else {
            log.info("Call creditInfoRepository.find()");
            return new ResponseData<>(creditInfoRepository.find(request, sort));
        }
    }

    @Override
    public ResponseData<List<CreditReport>> findSub(SearchCreditRequest request) {
        log.info("Call findSub(): {}", request);
        request.init();

        searchCheck(request);
        if (request.isGroupByCampaign() && StringUtils.hasLength(request.getCampaignName())) {
            request.setCampaignName(request.getCampaignName().replace("%", ""));
        }
        log.info("Call creditInfoRepository.findSub()");
        final List<CreditReport> creditReportList = creditInfoRepository.findSub(request);

        return new ResponseData<>(creditReportList);
    }

    @Override
    public ResponseData<UploadResponse> uploadFile(MultipartFile file) {
        final String fileOriginalName = file.getOriginalFilename();
        final String[] fileTypes = fileOriginalName.split("\\.");

        log.info("Call upload(): {}", fileOriginalName);
        final ErrorMessage check = checkUploadImage(file);
        if (null != check) {
            return new ResponseData<>(check);
        }

        final String storageFileName = fileActions.stream()
                .filter(a -> a.isMatch(fileActionChannel))
                .findAny()
                .map(a -> a.saveFromFile(file, Const.ContentType.IMAGE_JPG.getCode(), fileTypes[fileTypes.length - 1]))
                .orElse(null);

        if (null == storageFileName) {
            return new ResponseData<>(ErrorMessage.IMAGE_INVALID);
        }

        return new ResponseData<>(new UploadResponse(fileOriginalName, storageFileName));

    }

    @Override
    public ResponseData<Boolean> removeUploadFile(RemoveUploadRequest request) {
        log.info("Call removeUploadFile(): {}", request);
        fileActions.stream()
                .filter(a -> a.isMatch(fileActionChannel))
                .findAny()
                .map(a -> a.remove(request))
                .orElse(true);

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> useUploadFile(List<UseUploadRequest> requests) {
        log.info("Call useUploadFile(): {}", requests);
        if (null == requests) {
            return new ResponseData<>(true);
        }
        requests.forEach(request -> {
            if (null == request.getFiles() || request.getFiles().length <= 0) {
            } else {
                final List<CreditInfo> creditInfoList = new ArrayList<>();

                if (StringUtils.hasLength(request.getCreditIds())) {
                    final String[] creditIds = request.getCreditIds().split(",");
                    Arrays.stream(creditIds).forEach(creditId -> creditInfoRepository.findById(Integer.parseInt(creditId)).ifPresent(c -> {
                        if (Const.CreditStatus.SUBMIT.getCode().compareTo(c.getStatus()) > 0) {
                            creditInfoList.add(c);
                        }
                    }));
                } else if (null != request.getCreditId()) {
                    creditInfoRepository.findById(request.getCreditId()).ifPresent(c -> {
                        if (Const.CreditStatus.SUBMIT.getCode().compareTo(c.getStatus()) > 0) {
                            creditInfoList.add(c);
                        }
                    });
                }

                creditInfoList.forEach(creditInfo -> {
                    certificateInfoRepository.findByCreditId(creditInfo.getCreditId()).ifPresent(cs -> cs.forEach(c -> certificateInfoRepository.delete(c)));
                    for (String file : request.getFiles()) {
                        final String[] findType = file.split("\\.");

                        final CertificateInfo certificateInfo = new CertificateInfo();
                        certificateInfo.setCreditId(creditInfo.getCreditId());
                        certificateInfo.setType(findType[findType.length - 1]);
                        certificateInfo.setUrl(file);
                        certificateInfo.setStatus(1);

                        certificateInfoRepository.save(certificateInfo);
                    }

                    creditInfo.setAtr5(request.getFiles().length);
                    creditInfoRepository.save(creditInfo);
                });
            }
        });

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<ImportResponse> importCredit(MultipartFile file) {
        int currentRow = 0;
        final ImportResponse response = new ImportResponse();
        final List<ImportCreditRequest> requestList = new ArrayList<>();
        final int maxRows = 1000;

        final InputStream inputStream = ExcelUtil.getInputStream(file);
        if (null == inputStream) {
            return new ResponseData(ErrorMessage.READ_FILE_ERROR);
        }

        try (final Workbook workbook = ExcelUtil.createWorkbook(inputStream, file.getOriginalFilename())) {
            if (null == workbook) {
                return new ResponseData(ErrorMessage.READ_FILE_ERROR);
            }
            final Sheet sheet = workbook.getSheetAt(0);
            if (null == sheet) {
                return new ResponseData(ErrorMessage.READ_FILE_ERROR);
            }
            response.init();
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (int row = 1; row <= maxRows; row++) {
                log.info("Deal with Row: {}", row);
                final ImportCreditRequest request = new ImportCreditRequest();

                currentRow = row;
                request.setCampaignType(ExcelUtil.getCellFormatValue(sheet, row, 0).trim());
                if (!StringUtils.hasLength(request.getCampaignType())) {
                    continue;
                }
                if (null == Const.CampaignType.findByDescription(request.getCampaignType())) {
                    return new ResponseData(ErrorMessage.IMPORT_FILE_CAMPAIGN_TYPE_INVALID);
                }
                request.setCampaignType(Const.CampaignType.findByDescription(request.getCampaignType()).getCode());
                request.setCampaignName(ExcelUtil.getCellFormatValue(sheet, row, 1).trim());
                if (!StringUtils.hasLength(request.getCampaignName())) {
                    return new ResponseData(ErrorMessage.IMPORT_FILE_CAMPAIGN_NAME_NULL);
                }
                request.setUserCode(ExcelUtil.getCellFormatValue(sheet, row, 2).trim());
                if (!StringUtils.hasLength(request.getUserCode())) {
                    return new ResponseData(ErrorMessage.IMPORT_FILE_USER_CODE_NULL);
                }
                request.setUserName(ExcelUtil.getCellFormatValue(sheet, row, 3).trim());
                if (!StringUtils.hasLength(request.getUserName())) {
                    return new ResponseData(ErrorMessage.IMPORT_FILE_USER_NAME_NULL);
                }
                final String credit = ExcelUtil.getCellFormatValue(sheet, row, 4).trim();
                if (!StringUtils.hasLength(credit)) {
                    return new ResponseData(ErrorMessage.IMPORT_FILE_CREDIT_NULL);
                }
                request.setCredit(new BigDecimal(credit));
                final String creditDate = ExcelUtil.getCellValueAsString(sheet.getRow(row).getCell(5));
                if (!StringUtils.hasLength(creditDate)) {
                    return new ResponseData(ErrorMessage.IMPORT_FILE_CREDIT_TIME_NULL);
                }
                try {
                    request.setCreditTime(simpleDateFormat.parse(creditDate));
                } catch (ParseException e) {
                    return new ResponseData(ErrorMessage.IMPORT_FILE_CREDIT_TIME_INVALID, String.format("?????? %d", row + 1));
                }

                request.setInstructor(ExcelUtil.getCellFormatValue(sheet, row, 6).trim());
                request.setRemark(ExcelUtil.getCellFormatValue(sheet, row, 7).trim());

                requestList.add(request);
            }
        } catch (Exception e) {
            log.error("Find Data form excel got an error: {}, {}", currentRow, e);
            return new ResponseData(ErrorMessage.RUNTIME_EXCEPTION, StringUtils.hasLength(e.getMessage()) ? String.format("%d,%s", currentRow, e.getMessage()) : String.format("%d,%s", currentRow, e.getClass().getName()));
        }

        if (requestList.isEmpty()) {
            return new ResponseData(ErrorMessage.IMPORT_FILE_IS_EMPTY);
        }
        for (ImportCreditRequest r : requestList) {
            final UserInfo userInfo = userInfoRepository.findByUserCode(r.getUserCode()).orElse(null);
            if (null == userInfo) {
                return new ResponseData(ErrorMessage.USER_NOT_EXIST, r.getUserCode());
            } else {
                if (null == userInfo.getUserName() || !userInfo.getUserName().equals(r.getUserName())) {
                    return new ResponseData(ErrorMessage.USER_CODE_NOT_MATCH_USERNAME, r.getUserCode());
                }
                final String currentUserType = DataUtil.getAuthorityUserType();
                final Integer currentUserOrganizationId = DataUtil.getAuthorityOrganizationId();
                if (Const.UserType.FACULTY.equals(currentUserType) || Const.UserType.CLASS.equals(currentUserType)) {
                    if (!organizationMappingInfoRepository.findByFatherOrganizationId(currentUserOrganizationId).orElse(new ArrayList<>())
                            .stream()
                            .anyMatch(om -> om.getChildOrganizationId().equals(userInfo.getOrganizationId()))) {
                        return new ResponseData(ErrorMessage.ORGANIZATION_NOT_MATCH, r.getUserCode());
                    }
                }
                r.setUserId(userInfo.getUserId());
            }
        }

        requestList.forEach(r -> {
            final CreditInfo creditInfo = new CreditInfo();
            creditInfo.setUserId(r.getUserId());
            creditInfo.setCampaignType(r.getCampaignType());
            creditInfo.setCampaignName(r.getCampaignName());
            creditInfo.setCredit(r.getCredit());
            creditInfo.setCreditTime(r.getCreditTime());
            creditInfo.setInstructor(r.getInstructor());
            creditInfo.setFlag(Const.NO);
            creditInfo.setRemark(r.getRemark());
            creditInfo.setStatus(Const.CreditStatus.DRAFT.getCode());
            if (null != creditInfo.getCreditTime()) {
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(creditInfo.getCreditTime());
                creditInfo.setCreditYear(calendar.get(Calendar.YEAR));
                creditInfo.setCreditMonth(calendar.get(Calendar.MONTH) + 1);
            }

            creditInfoRepository.save(creditInfo);
            response.setTotal(response.getTotal() + 1);
        });

        return new ResponseData<>(response);
    }

    @Override
    public ResponseData<List<CertificateInfoDto>> findSubCertification(Integer creditId) {
        log.info("Call findSubCertification(): {}", creditId);
        final List<CertificateInfo> certificateInfoList = certificateInfoRepository.findByCreditId(creditId).orElse(new ArrayList<>());

        return new ResponseData<>(certificateInfoMapper.mapAsList(certificateInfoList, CertificateInfoDto.class));
    }

    private UserInfo createUser(String userCode, String userName, String userGender, String userPhone, Integer organizationId, String atr1) {
        log.info("Call createUser: {}", userCode);
        final UserInfo userInfoCheck = userInfoRepository.findByUserCode(userCode).orElse(null);
        if (null != userInfoCheck) {
            return userInfoCheck;
        }

        final UserInfo userInfo = new UserInfo();
        userInfo.setUserCode(userCode.trim());
        userInfo.setUserName(userName.trim());
        userInfo.setUserPassword(passwordEncoder.encode(userCode));
        userInfo.setUserGender(userGender);
        userInfo.setUserPhone(userPhone);
        userInfo.setOrganizationId(organizationId);
        userInfo.setUserType(Const.UserType.STUDENT);
        userInfo.setAtr1(atr1);
        userInfo.setStatus(1);

        log.info("Call userInfoRepository.save()");
        userInfoRepository.save(userInfo);

        return userInfo;
    }

    private void searchCheck(final SearchCreditRequest request) {
        final String currentUserCode = DataUtil.getAuthorityUserName();
        final String currentUserType = DataUtil.getAuthorityUserType();
        final Integer currentUserOrganizationId = DataUtil.getAuthorityOrganizationId();
        final List<Integer> organizationIds = new ArrayList<>();

        request.setCreateBy(null);
        if (null != request.getOrganizationIds()) {
            organizationIds.addAll(Arrays.asList(request.getOrganizationIds()));
        }
        if (StringUtils.hasLength(request.getSearchType())
                || Const.UserType.YLC_L1.equals(currentUserType)
                || Const.UserType.YLC_L2.equals(currentUserType)) {
            request.setCreateBy(currentUserCode);
        } else if (!Const.UserType.STUDENT.equals(currentUserType)) {
            if (organizationIds.isEmpty()) {
                organizationIds.add(currentUserOrganizationId);
            }
        }
        if (!organizationIds.isEmpty()) {
            final List<Integer> userOrganizationIds = new ArrayList<>();
            organizationIds.forEach(oi -> {
                organizationMappingInfoRepository.findByFatherOrganizationId(oi).ifPresent(oms -> {
                    oms.stream().forEach(om -> {
                        if (!userOrganizationIds.contains(om.getChildOrganizationId())) {
                            userOrganizationIds.add(om.getChildOrganizationId());
                        }
                    });
                });
            });
            if (!userOrganizationIds.isEmpty()) {
                request.setOrganizationIds(new Integer[userOrganizationIds.size()]);
                request.setOrganizationIds(userOrganizationIds.toArray(request.getOrganizationIds()));
            }
        }

        if (Const.UserType.STUDENT.equals(currentUserType)) {
            request.setUserId(DataUtil.getAuthorityUserId());
            request.setStatus(Const.CreditStatus.APPROVED.getCode());
        }

        if (null == request.getOrganizationIds()) {
            request.setIgnoreOrganizationIds(true);
            request.setOrganizationIds(new Integer[]{});
        } else {
            request.setIgnoreOrganizationIds(false);
        }
    }

    private ErrorMessage checkUploadImage(final MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.matches("^.+(.JPEG|.jpeg|.JPG|.jpg|.BMP|.bmp|.PNG|.png|.PDF|.pdf)$")) {
            return ErrorMessage.IMAGE_TYPE_NOT_SUPPORT;
        }
        if (file.getSize() > fileSize * 1024 * 1024) {
            return ErrorMessage.IMAGE_SIZE_NOT_SUPPORT;
        }

        return null;
    }
}
