package com.aps.quality.util;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    NULL(-1, ""),
    ID_CAN_NOT_BE_NULL(0, "ID不参为空"),
    CAN_NOT_FIND_DATA(1, "无法找到对应的数据"),
    PROHIBIT_UPDATING_DATA(3, "禁止更新数据"),
    PROHIBIT_DELETE_DATA(4, "数据无法被删除"),
    VERSION_INVALID(10000, "请尝试刷新数据后再操作"),
    VERSION_IS_NULL(10001, "请携带数据版本号进行更新操作"),
    RUNTIME_EXCEPTION(10002, "RuntimeException"),
    SERVER_ERROR(10003, "内部错误"),
    CODE_INVALID(10004, "code为空或无效"),
    FAIL_TO_GET_TOKEN(10005, "获取Token失败"),
    USER_INVALID(10006, "用户已失效"),
    CODE_EXIST(10007, "code已存在"),
    NAME_INVALID(10008, "name为空或已存在"),
    DEVICE_INVALID(10009, "设备已失效"),
    IMAGE_TYPE_NOT_SUPPORT(11003, "图片格式不支持；支持的图片格式：PNG、JPG、JPEG、BMP"),
    IMAGE_SIZE_NOT_SUPPORT(11004, "图片大小超过限制"),
    IMAGE_INVALID(11005, "无法解析图片"),
    IP_IS_NULL_OR_INVALID(11013, "IP地址为空或无效"),
    USER_NOT_EXIST(11014, "用户不存在"),
    USER_NAME_IS_NULL(11019, "用户名不能为空"),
    USER_CODE_EXIST(11020, "用户代码已存在"),
    ROLE_NOT_EXIST(12001, "role不存在"),
    GROUP_NOT_EXIST(12002, "group不存在"),
    DEVICE_NOT_EXIST(12003, "device不存在"),
    ROLE_HAS_BEEN_USED(12003, "role已被group引用"),
    GROUP_HAS_BEEN_USED(12004, "group已被user引用"),
    PASSWORD_INVALID(12005, "用户密码不正确"),
    FATHER_ORGANIZATION_ID_INVALID(12006, "fatherOrganizationId为空或无效"),
    ORGANIZATION_ID_INVALID(12007, "organizationId为空或无效"),
    ORGANIZATION_HAS_BEEN_USED(12008, "organization已被引用"),
    ORGANIZATION_NOT_EXIST(12002, "organization不存在"),
    GROUP_NAME_IS_NULL(12003, "参数 groupName 不能为空"),
    GROUP_NAME_EXIST(12004, "参数 groupName 不能为空"),
    ROLE_NAME_IS_NULL(12005, "参数 roleName 不能为空"),
    ROLE_NAME_EXIST(12006, "roleName 已存在"),
    FATHER_ROLE_ID_IS_NULL(12007, "参数 fatherRoleId 不能为空"),
    ROLE_ALREADY_IN_GROUP(12008, "role已存在group中"),
    ROLE_ALREADY_IN_USER(12009, "role已存在user中"),
    NAME_EXIST(12010, "name已存在"),
    CAMPAIGN_NOT_EXIST(12011, "活动不存在"),
    STUDENT_CODE_IS_NULL(12012, "参数 studentCode 不能为空"),
    CREDIT_IS_NULL(12012, "参数 credit 不能为空"),
    CREDIT_NOT_EXIST(12012, "学分不存在"),

    SSO_RESPONSE_STATE_INVALID(50001, "返回state无效"),
    SSO_RESPONSE_CODE_EMPTY(50002, "返回code为空"),
    SSO_TOKEN_ERROR(50003, "获取SSO Token失败"),
    SSO_USER_INFO_ERROR(50004, "获取SSO UserInfo 失败"),
    ;

    private Integer code;
    private String description;

    ErrorMessage(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
