package com.aps.quality.model.credit;

import com.aps.quality.entity.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ImportRequest implements Serializable {
    private String campaignType;
    private String campaignName;
    private String userCode;
    private String userName;
    private BigDecimal credit;
    private Date creditTime;
    private String instructor;
    private String remark;

    private UserInfo userInfo;
}
