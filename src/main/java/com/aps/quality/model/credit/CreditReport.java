package com.aps.quality.model.credit;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CreditReport {
    String getGuid();

    Integer getTotal();

    Integer getSubmit();

    Integer getApprove();

    Integer getCertificate();

    String getCreditIds();

    Integer getCreditId();

    String getCampaignName();

    String getCampaignType();

    Integer getUserId();

    String getUserCode();

    String getUserName();

    String getUserGender();

    String getUserPhone();

    Integer getOrganizationId();

    String getUserOrganizationLink();

    BigDecimal getCredit();

    Date getCreditTime();

    String getInstructor();

    String getRejectReason();

    String getRejectBy();

    String getRejectUserType();

    String getRemark();

    Integer getStatus();

    Integer getVersion();

    String getCreateBy();

    String getCreateUserOrganizationLink();

    Date getCreateTime();
}
