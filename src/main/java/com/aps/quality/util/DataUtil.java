package com.aps.quality.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class DataUtil {
    private DataUtil() {}

    public static String getAuthorityUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return removeAuthorityUserNamePrefix(authentication.getName());
    }

    public static String removeAuthorityUserNamePrefix(final String userName) {
        if (!StringUtils.hasLength(userName)) {
            return null;
        }
        final String userNameRemoved = Const.UserPrefix.removePrefixAll(userName);
        if (userNameRemoved.indexOf(":") > 0) {
            return userNameRemoved.split(":")[0];
        }
        return userNameRemoved;
    }

    public static String getAuthorityUserNameOriginal() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        if (!StringUtils.hasLength(authentication.getName())) {
            return null;
        }
        return authentication.getName();
    }

    public static Integer getAuthorityUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return authentication.getAuthorities()
                .stream()
                .filter(g -> g.getAuthority().startsWith(Const.USER_ID_PREFIX))
                .findFirst()
                .map(g -> Integer.valueOf(g.getAuthority().replace(Const.USER_ID_PREFIX, "")))
                .orElse(null);
    }

    public static Integer getAuthorityOrganizationId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return authentication.getAuthorities()
                .stream()
                .filter(g -> g.getAuthority().startsWith(Const.USER_ORGANIZATION_PREFIX))
                .findFirst()
                .map(g -> Integer.valueOf(g.getAuthority().replace(Const.USER_ORGANIZATION_PREFIX, "")))
                .orElse(null);
    }

    public static String getAuthorityOrganizationLink() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return authentication.getAuthorities()
                .stream()
                .filter(g -> g.getAuthority().startsWith(Const.USER_ORGANIZATION_LINK_PREFIX))
                .findFirst()
                .map(g -> g.getAuthority().replace(Const.USER_ORGANIZATION_LINK_PREFIX, ""))
                .orElse(null);
    }

    public static String getAuthorityUserType() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return authentication.getAuthorities()
                .stream()
                .filter(g -> g.getAuthority().startsWith(Const.USER_TYPE_PREFIX))
                .findFirst()
                .map(g -> g.getAuthority().replace(Const.USER_TYPE_PREFIX, ""))
                .orElse(null);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrapper = new BeanWrapperImpl(source);
        final PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
        final Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : descriptors) {
            final Object srcValue = wrapper.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        return emptyNames.toArray(new String[emptyNames.size()]);
    }

    public static String[] nvl(final String[] string, final String[] value) {
        return null == string || string.length <= 0 ? value : string;
    }

    public static Integer[] nvl(final Integer[] string, final Integer[] value) {
        return null == string || string.length <= 0 ? value : string;
    }

    public static String nvl(final String string, final String value) {
        return !StringUtils.hasLength(string) ? value : string;
    }

    public static Integer nvl(final Integer integer, final Integer value) {
        return null == integer ? value : integer;
    }

    public static Date nvl(final Date date, final Date value) {
        return null == date ? value : date;
    }

    public static String nvl(final String string) {
        return nvl(string, "");
    }

    public static BigDecimal nvl(final BigDecimal value) {
        return nvl(value, BigDecimal.ZERO);
    }

    public static BigDecimal nvl(BigDecimal value, BigDecimal defaultValue) {
        return null == value ? defaultValue : value;
    }

    public static String likeFormat(final String string) {
        return !StringUtils.hasLength(string) ? null : String.format("%%%s%%", string);
    }

    public static String hmacSHA256(final String data, final String key) throws InvalidKeyException, NoSuchAlgorithmException {
        final Mac mac = Mac.getInstance("HmacSHA256");
        final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKey);

        return byteArrayToHexString(mac.doFinal(data.getBytes()));
    }

    private static String byteArrayToHexString(byte[] b) {
        final StringBuilder builder = new StringBuilder();
        String hexString;

        for (int n = 0; b != null && n < b.length; n++) {
            hexString = Integer.toHexString(b[n] & 0XFF);
            if (hexString.length() == 1)
                builder.append('0');
            builder.append(hexString);
        }

        return builder.toString().toLowerCase();
    }

    public static String coverObject2Str(final Object obj) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper.writeValueAsString(obj);
    }

    public static String coverObject2StrWithoutException(final Object obj) {
        if (null == obj) {
            return null;
        }

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
