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
    USER_CODE_INVALID(10004, "用户代码/学生学号 为空或无效"),
    FAIL_TO_GET_TOKEN(10005, "获取Token失败"),
    USER_INVALID(10006, "用户已失效"),
    CODE_EXIST(10007, "代码已存在"),
    NAME_INVALID(10008, "name为空或已存在"),
    DEVICE_INVALID(10009, "设备已失效"),
    READ_FILE_ERROR(10010, "读取文件失败"),
    INSUFFICIENT_PERMISSIONS(10011, "权限不足"),
    IMAGE_TYPE_NOT_SUPPORT(11003, "图片格式不支持；支持的图片格式：PNG、JPG、JPEG、BMP"),
    IMAGE_SIZE_NOT_SUPPORT(11004, "图片大小超过限制"),
    IMAGE_INVALID(11005, "无法解析图片"),
    IP_IS_NULL_OR_INVALID(11013, "IP地址为空或无效"),
    USER_NOT_EXIST(11014, "用户不存在"),
    USER_NAME_IS_NULL(11019, "用户名不能为空"),
    USER_CODE_EXIST(11020, "用户代码/学生学号 已存在"),
    ORGANIZATION_NOT_MATCH(11021, "录入学号对应的学生班级不匹配"),
    ROLE_NOT_EXIST(12001, "role不存在"),
    GROUP_NOT_EXIST(12002, "group不存在"),
    DEVICE_NOT_EXIST(12003, "device不存在"),
    ROLE_HAS_BEEN_USED(12003, "role已被group引用"),
    GROUP_HAS_BEEN_USED(12004, "group已被user引用"),
    PASSWORD_INVALID(12005, "用户密码不正确"),
    NEW_PASSWORD_SAME_OLD(12005, "用户新密码与旧密码一致"),
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
    IMPORT_FILE_CAMPAIGN_NAME_NULL(12013, "上传文件中 活动名称 不能为空"),
    IMPORT_FILE_CAMPAIGN_TYPE_NULL(12014, "上传文件中 活动类型 不能为空"),
    IMPORT_FILE_CREDIT_NULL(12015, "上传文件中 学分 不能为空"),
    IMPORT_FILE_USER_CODE_NULL(12016, "上传文件中 学生学号 不能为空"),
    IMPORT_FILE_CREDIT_TIME_NULL(12017, "上传文件中 活动开展时间 不能为空"),
    IMPORT_FILE_CAMPAIGN_TYPE_INVALID(12018, "上传文件中 活动类型 错误"),
    IMPORT_FILE_IS_EMPTY(12019, "上传文件中无数据"),
    IMPORT_FILE_CREDIT_TIME_INVALID(12020, "上传文件中 活到开展时间 格式错误"),
    IMPORT_FILE_USER_NAME_NULL(12021, "上传文件中 学生姓名 不能为空"),
    IMPORT_FILE_USER_GENDER_NULL(12022, "上传文件中 学生性别 不能为空"),
    USER_HAS_CREDIT(13001, "用户存在学分数据"),
    CHANGE_ORGANIZATION_MUST_IN_SAME_LEVEL(13002, "只能在相同级别的组织中变更"),
    USER_CODE_NOT_MATCH_USERNAME(13003, "学生学号和学生姓名不匹配"),

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
