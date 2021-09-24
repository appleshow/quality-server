package com.aps.quality.service.user;

import com.aps.quality.entity.*;
import com.aps.quality.mapper.UserInfoMapper;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.dto.UserInfoDto;
import com.aps.quality.model.user.CreateUserRequest;
import com.aps.quality.model.user.ResetPasswordRequest;
import com.aps.quality.model.user.SearchUserRequest;
import com.aps.quality.model.user.UpdateUserRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends OperationLogService implements UserService {
    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private OrganizationInfoRepository organizationInfoRepository;
    @Resource
    private RoleInfoRepository roleInfoRepository;
    @Resource
    private UserOrganizationInfoRepository userOrganizationInfoRepository;
    @Resource
    private UserRoleInfoRepository userRoleInfoRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public ResponseData<Boolean> create(final CreateUserRequest request) {
        log.info("call create()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        if (userInfoRepository.countByUserCode(0, request.getUserCode()).orElse(0) > 0) {
            return new ResponseData(ErrorMessage.CODE_EXIST);
        }
        if (null != request.getOrganizationIds() && request.getOrganizationIds().length > 0) {
            for (Integer organizationId : request.getOrganizationIds()) {
                final OrganizationInfo organizationInfo = organizationInfoRepository.findById(organizationId).orElse(null);
                if (null == organizationInfo) {
                    return new ResponseData(ErrorMessage.ORGANIZATION_NOT_EXIST);
                }
            }
        }
        if (null != request.getRoleIds() && request.getRoleIds().length > 0) {
            for (Integer roleId : request.getRoleIds()) {
                final RoleInfo roleInfo = roleInfoRepository.findById(roleId).orElse(null);
                if (null == roleInfo) {
                    return new ResponseData(ErrorMessage.ROLE_NOT_EXIST);
                }
            }
        }

        final UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(request, userInfo, DataUtil.getNullPropertyNames(request));
        userInfo.setUserPassword(passwordEncoder.encode(!StringUtils.hasLength(request.getUserPassword()) ? Const.DEFAULT_PASSWORD : request.getUserPassword()));
        userInfo.setStatus(Const.UserStatus.NORMAL.getCode());

        log.info("call  userInfoRepository.save()");
        userInfo.beforeSave();
        userInfoRepository.save(userInfo);
        saveLog(Const.OperationType.CREATE, Const.OperationSubType.USER, request.getUserCode(), request);

        addUserExtendInfo(userInfo.getUserId(), request.getOrganizationIds(), request.getRoleIds());

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> update(final UpdateUserRequest request) {
        log.info("call update()");

        final ErrorMessage check = request.check();
        if (ErrorMessage.NULL != check) {
            return new ResponseData(check);
        }
        final UserInfo userInfo = userInfoRepository.findById(request.getUserId()).orElse(null);
        if (null == userInfo) {
            return new ResponseData(ErrorMessage.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(request, userInfo, DataUtil.getNullPropertyNames(request));
        log.info("call userInfoRepository.save()");
        userInfo.beforeSave();
        userInfoRepository.save(userInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.USER, userInfo.getUserCode(), request);

        removeUserExtendInfo(userInfo.getUserId());
        addUserExtendInfo(userInfo.getUserId(), request.getOrganizationIds(), request.getRoleIds());

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> invalid(final Integer id) {
        log.info("call invalid({})", id);

        final UserInfo userInfo = userInfoRepository.findById(id).orElse(null);
        if (null == userInfo) {
            return new ResponseData(ErrorMessage.USER_NOT_EXIST);
        }
        userInfo.setStatus(Const.UserStatus.INVALID.getCode());

        log.info("call userInfoRepository.save()");
        userInfoRepository.save(userInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.USER_STATUS, userInfo.getUserCode(), Const.UserStatus.INVALID.getDescription());

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> enable(Integer id) {
        log.info("call invalid({})", id);

        final UserInfo userInfo = userInfoRepository.findById(id).orElse(null);
        if (null == userInfo) {
            return new ResponseData(ErrorMessage.USER_NOT_EXIST);
        }
        userInfo.setStatus(Const.UserStatus.NORMAL.getCode());

        log.info("call userInfoRepository.save()");
        userInfoRepository.save(userInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.USER_STATUS, userInfo.getUserCode(), Const.UserStatus.NORMAL.getDescription());

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> resetPassword(Integer id) {
        log.info("call resetPassword({})", id);

        final UserInfo userInfo = userInfoRepository.findById(id).orElse(null);
        if (null == userInfo) {
            return new ResponseData(ErrorMessage.USER_NOT_EXIST);
        }
        userInfo.setUserPassword(passwordEncoder.encode(Const.DEFAULT_PASSWORD));

        log.info("call userInfoRepository.save()");
        userInfoRepository.save(userInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.USER_PASSWORD, userInfo.getUserCode(), "Reset Password by ID");

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Boolean> setPassword(final ResetPasswordRequest request) {
        log.info("call setPassword()");

        final UserInfo userInfo = userInfoRepository.findById(DataUtil.getAuthorityUserId()).orElse(null);
        if (null == userInfo) {
            return new ResponseData(ErrorMessage.USER_NOT_EXIST);
        }
        if (passwordEncoder.matches(userInfo.getUserPassword(), request.getOldPassword())) {
            userInfo.setUserPassword(passwordEncoder.encode(request.getNewPassword()));
        } else {
            return new ResponseData(ErrorMessage.PASSWORD_INVALID);
        }

        log.info("call userInfoRepository.save()");
        userInfoRepository.save(userInfo);
        saveLog(Const.OperationType.UPDATE, Const.OperationSubType.USER_PASSWORD, userInfo.getUserCode(), "Set New Password");

        return new ResponseData<>(true);
    }

    @Override
    public ResponseData<Page<UserInfoDto>> findPageable(final SearchUserRequest request) {
        log.info("call findPageable(): {}", request);
        request.init();

        final Pageable pageable = request.getDefaultPageable(new Sort.Order(Sort.Direction.DESC, "createTime"));
        log.info("call userInfoRepository.findPageable()");
        final Page<UserInfo> userInfoPage = userInfoRepository.findPageable(request, pageable);

        return new ResponseData<>(request.exchange(userInfoMapper, userInfoPage, pageable, UserInfoDto.class));
    }

    @Override
    public ResponseData<List<UserInfoDto>> find(final SearchUserRequest request) {
        log.info("call find(): {}", request);
        request.init();

        log.info("call userInfoRepository.find()");
        final List<UserInfo> userInfos = userInfoRepository.find(request);

        return new ResponseData<>(userInfoMapper.mapAsList(userInfos, UserInfoDto.class));
    }

    private void removeUserExtendInfo(Integer userId) {
        log.info("call removeUserExtendInfo({})", userId);
        userOrganizationInfoRepository.findByUserId(userId).ifPresent(uos -> uos.forEach(uo -> userOrganizationInfoRepository.delete(uo)));
        userRoleInfoRepository.findByUserId(userId).ifPresent(urs -> urs.forEach(ur -> userRoleInfoRepository.delete(ur)));
    }

    private void addUserExtendInfo(Integer userId, Integer[] organizationIds, Integer[] roleIds) {
        if (null != organizationIds) {
            Arrays.stream(organizationIds).forEach(organizationId -> {
                final UserOrganizationInfo userOrganizationInfo = new UserOrganizationInfo();
                userOrganizationInfo.setUserId(userId);
                userOrganizationInfo.setOrganizationId(organizationId);
                userOrganizationInfo.beforeSave();
                log.info("call userOrganizationInfoRepository.save()");
                userOrganizationInfoRepository.save(userOrganizationInfo);
                saveLog(Const.OperationType.UPDATE, Const.OperationSubType.ASSIGN_GROUP_TO_USER, String.format("%d,%d", organizationId, userId), null);
            });
        }

        if (null != roleIds) {
            Arrays.stream(roleIds).forEach(roleId -> {
                final UserRoleInfo userRoleInfo = new UserRoleInfo();
                userRoleInfo.setUserId(userId);
                userRoleInfo.setRoleId(roleId);
                userRoleInfo.beforeSave();
                log.info("call userRoleInfoRepository.save()");
                userRoleInfoRepository.save(userRoleInfo);
                saveLog(Const.OperationType.UPDATE, Const.OperationSubType.ASSIGN_GROUP_TO_USER, String.format("%d,%d", roleId, userId), null);
            });
        }
    }
}
