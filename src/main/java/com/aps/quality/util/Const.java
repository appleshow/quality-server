package com.aps.quality.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public abstract class Const {
    public static final String YES = "Y";
    public static final String NO = "N";

    public static final String USER_ID_PREFIX = "#USER@";
    public static final String USER_TYPE_PREFIX = "#USERTYPE@";
    public static final String USER_ORGANIZATION_PREFIX = "#USERORGANIZATION@";
    public static final String USER_ORGANIZATION_LINK_PREFIX = "#USERORGANIZATIONLINK@";
    public static final String CREATE_TIME = "createTime";
    public static final String DEFAULT_PASSWORD = "asdiop@963";
    public static final Integer DEFAULT_NUMBER_PER_PAGE = 10;
    public static final Integer DEFAULT_PAGE_INDEX = 0;
    public static final Integer DEFAULT_MAX_PAGE_SIZE = 3000;
    public static final Integer IS_DEFAULT = 1;
    public static final Integer NOT_DEFAULT = 0;
    public static final String DEFAULT_TITLE = "默认播放列表";
    public static final String DEFAULT_FREQUENCY = "60000";


    @Getter
    public enum UserPrefix {
        PORTAL("f6ab0950c92411eb88b2000c29288000", "PORTAL", "PORTAL", "PORTAL"),
        DEVICE("0167478fc92511eb88b2000c29288000", "DEVICE", "DEVICE", "DEVICE"),
        ;

        private String code;
        private String clientId;
        private String scope;
        private String[] defaultRoles;

        UserPrefix(String code, String clientId, String scope, String... roles) {
            this.code = code;
            this.clientId = clientId;
            this.scope = scope;
            this.defaultRoles = roles;
        }

        public boolean isMatch(String name) {
            return null != name && name.startsWith(this.code);
        }

        public String removePrefix(final String value) {
            if (null == value) {
                return null;
            }

            return value.replace(this.code, "");
        }

        public static String removePrefixAll(final String value) {
            String unPrefixedValue = String.valueOf(DataUtil.nvl(value));
            for (UserPrefix userPrefix : UserPrefix.values()) {
                unPrefixedValue = unPrefixedValue.replace(userPrefix.getCode(), "");
            }

            return unPrefixedValue;
        }


        public static UserPrefix getInstanceByCode(String code) {
            final Optional<UserPrefix> userPrefixOptional = Arrays.stream(UserPrefix.values())
                    .filter(userPrefix -> userPrefix.getCode().equals(code))
                    .findAny();
            if (userPrefixOptional.isPresent()) {
                return userPrefixOptional.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Gender {
        MALE("M", "男"),
        FEMALE("F", "女"),
        CONFIDENTIAL("S", "保密"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private String code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        Gender(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(String code) {
            return null != code && this.code.equals(code);
        }

        public static Gender findByCode(String code) {
            Optional<Gender> status = Arrays.stream(Gender.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum Status {
        INVALID(0, "无效"),
        NORMAL(1, "正常"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private Integer code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        Status(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(Integer code) {
            return null != code && this.code.equals(code);
        }

        public static Status findByCode(Integer code) {
            Optional<Status> status = Arrays.stream(Status.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum DeviceStatus {
        INVALID(0, "无效"),
        NORMAL(1, "正常"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private Integer code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        DeviceStatus(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(Integer code) {
            return null != code && this.code.equals(code);
        }

        public static DeviceStatus findByCode(Integer code) {
            Optional<DeviceStatus> status = Arrays.stream(DeviceStatus.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum RoleStatus {
        INVALID(0, "无效"),
        NORMAL(1, "正常"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private Integer code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        RoleStatus(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(Integer code) {
            return null != code && this.code.equals(code);
        }

        public static RoleStatus findByCode(Integer code) {
            Optional<RoleStatus> status = Arrays.stream(RoleStatus.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum GroupStatus {
        INVALID(0, "无效"),
        NORMAL(1, "正常"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private Integer code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        GroupStatus(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(Integer code) {
            return null != code && this.code.equals(code);
        }

        public static GroupStatus findByCode(Integer code) {
            Optional<GroupStatus> status = Arrays.stream(GroupStatus.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum GroupRoleStatus {
        INVALID(0, "无效"),
        NORMAL(1, "正常"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private Integer code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        GroupRoleStatus(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(Integer code) {
            return null != code && this.code.equals(code);
        }

        public static GroupRoleStatus findByCode(Integer code) {
            Optional<GroupRoleStatus> status = Arrays.stream(GroupRoleStatus.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum MaterialStatus {
        INVALID(0, "无效"),
        NORMAL(1, "正常"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private Integer code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        MaterialStatus(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(Integer code) {
            return null != code && this.code.equals(code);
        }

        public static MaterialStatus findByCode(Integer code) {
            Optional<MaterialStatus> status = Arrays.stream(MaterialStatus.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum CreditStatus {
        NULL(null, ""),
        REJECT(5, "退回"),
        DRAFT(10, "草稿"),
        SUBMIT(20, "已提交"),
        APPROVING(30, "初审通过"),
        APPROVED(40, "定审通过"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private Integer code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        CreditStatus(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(Integer code) {
            return null != code && this.code.equals(code);
        }

        public static CreditStatus findByCode(Integer code) {
            Optional<CreditStatus> status = Arrays.stream(CreditStatus.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }

        public static boolean canBeSubmitted(Integer status) {
            if (null == status) {
                return false;
            }
            if (SUBMIT.getCode().compareTo(status) > 0) {
                return true;
            }

            return false;
        }

        public static boolean canBeApproved(Integer status) {
            if (null == status) {
                return false;
            }
            if (!APPROVED.getCode().equals(status)) {
                return true;
            }

            return false;
        }

        public static boolean canBeRejected(Integer status, String userType) {
            if (null == status) {
                return false;
            }
            if (UserType.ADMIN.equals(userType) || UserType.MASTER.equals(userType)) {
                return true;
            }
            if (SUBMIT.getCode().compareTo(status) <= 0 && !APPROVED.getCode().equals(status)) {
                return true;
            }

            return false;
        }
    }

    @Getter
    public enum OperationType {
        LOGIN("LOGIN"),
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        DELETE("DELETE"),
        QUERY("QUERY"),
        SYNC("SYNC"),
        ;

        private String code;

        OperationType(String code) {
            this.code = code;
        }

        public static OperationType getInstanceByCode(String code) {
            Optional<OperationType> ot = Arrays.stream(OperationType.values())
                    .filter(t -> t.getCode().equals(code))
                    .findAny();
            if (ot.isPresent()) {
                return ot.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    public enum OperationSubType {
        USER("USER"),
        USER_STATUS("USER_STATUS"),
        USER_PASSWORD("USER_PASSWORD"),
        DEVICE("DEVICE"),
        DEVICE_STATUS("DEVICE_STATUS"),
        ROLE("ROLE"),
        ROLE_STATUS("ROLE_STATUS"),
        ASSIGN_ROLE("ASSIGN_ROLE"),
        SEPARATE_ROLE("SEPARATE_ROLE"),
        GROUP("GROUP"),
        GROUP_STATUS("GROUP_STATUS"),
        ASSIGN_GROUP_TO_USER("ASSIGN_GROUP_TO_USER"),
        SEPARATE_GROUP_FROM_USER("SEPARATE_GROUP_FROM_USER"),
        ORGANIZATION("ORGANIZATION"),
        CAMPAIGN("CAMPAIGN"),
        CAMPAIGN_STATUS("CAMPAIGN_STATUS"),
        CREDIT("CREDIT"),
        ;

        private String code;

        OperationSubType(String code) {
            this.code = code;
        }

        public static OperationSubType getInstanceByCode(String code) {
            Optional<OperationSubType> ot = Arrays.stream(OperationSubType.values())
                    .filter(t -> t.getCode().equals(code))
                    .findAny();
            if (ot.isPresent()) {
                return ot.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum SSOType {
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private String code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        SSOType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(String code) {
            return null != code && this.code.equals(code);
        }

        public static SSOType findByCode(String code) {
            Optional<SSOType> status = Arrays.stream(SSOType.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum MetadataType {
        MATERIAL_TYPE("MATERIAL_TYPE", "素材类型"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private String code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        MetadataType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(String code) {
            return null != code && this.code.equals(code);
        }

        public static MetadataType findByCode(String code) {
            Optional<MetadataType> status = Arrays.stream(MetadataType.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum CampaignType {
        SX("SX", "思想道德素质拓展"),
        NL("NL", "能力素质拓展"),
        YS("YS", "人文艺术素质拓展"),
        SJ("SJ", "社会实践素质拓展"),
        QT("QT", "其它"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private String code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        CampaignType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(String code) {
            return null != code && this.code.equals(code);
        }

        public static CampaignType findByCode(String code) {
            Optional<CampaignType> status = Arrays.stream(CampaignType.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }

        public static CampaignType findByDescription(String description) {
            Optional<CampaignType> status = Arrays.stream(CampaignType.values())
                    .filter(d -> null != description && d.getDescription().equals(description))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum MaterialOrigin {
        LOCAL("LOCAL", "本地素材"),
        REMOTE("REMOTE", "远程素材"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private String code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        MaterialOrigin(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(String code) {
            return null != code && this.code.equals(code);
        }

        public static MaterialOrigin findByCode(String code) {
            Optional<MaterialOrigin> status = Arrays.stream(MaterialOrigin.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum MetadataSubtype {
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private String code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        MetadataSubtype(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(String code) {
            return null != code && this.code.equals(code);
        }

        public static MetadataSubtype findByCode(String code) {
            Optional<MetadataSubtype> status = Arrays.stream(MetadataSubtype.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum FileActionChannel {
        LOCAL("LOCAL", "本地文件存储"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private String code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        FileActionChannel(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(String code) {
            return null != code && this.code.equals(code);
        }

        public static FileActionChannel findByCode(String code) {
            Optional<FileActionChannel> status = Arrays.stream(FileActionChannel.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum ContentType {
        IMAGE_JPG("image/jpg", "image/jpg"),
        ;

        @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
        private String code;
        @ApiModelProperty(name = "description", required = false, example = "", notes = "描述")
        private String description;

        ContentType(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public boolean equalWithCode(String code) {
            return null != code && this.code.equals(code);
        }

        public static ContentType findByCode(String code) {
            Optional<ContentType> status = Arrays.stream(ContentType.values())
                    .filter(d -> null != code && d.getCode().equals(code))
                    .findAny();
            if (status.isPresent()) {
                return status.get();
            } else {
                return null;
            }
        }
    }

    public static class OrganizationType {
        // 根
        public final static String ROOT = "ROOT";
        // 团委直属
        public final static String YLC = "YLC";
        // 院系
        public final static String FACULTY = "FACULTY";
    }

    public static class UserType {
        // 管理员
        public final static String ADMIN = "ADMIN";
        // 校团委管理员
        public final static String MASTER = "MASTER";
        // 校团委审批员
        public final static String CHIEF = "CHIEF";
        // 团委直属 - 团委会/学生会
        public final static String YLC_L1 = "YLC_L1";
        // 团委直属 - 协会
        public final static String YLC_L2 = "YLC_L2";
        // 系管理员
        public final static String FACULTY = "FACULTY";
        // 班级管理员
        public final static String CLASS = "CLASS";
        // 班级学生
        public final static String STUDENT = "STUDENT";

        public static boolean canBeApprove(String userType) {
            if (!StringUtils.hasLength(userType)) {
                return false;
            }
            if (ADMIN.equals(userType) || MASTER.equals(userType) || CHIEF.equals(userType) || FACULTY.equals(userType)) {
                return true;
            }
            return false;
        }

        public static CreditStatus getCreditStatus(String userType) {
            if (ADMIN.equals(userType) || MASTER.equals(userType) || CHIEF.equals(userType)) {
                return CreditStatus.APPROVED;
            }
            if (FACULTY.equals(userType)) {
                return CreditStatus.APPROVING;
            }

            return CreditStatus.NULL;
        }
    }
}
