package com.aps.quality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataDto implements Serializable {
    @ApiModelProperty(name = "metadataId", required = true, example = "", notes = "ID")
    private Integer metadataId;
    @ApiModelProperty(name = "type", required = true, example = "", notes = "类型，参考系统常量（metadataTypes）")
    private String type;
    @ApiModelProperty(name = "subtype", required = false, example = "", notes = "子类型，参考系统常量（metadataSubtypes）")
    private String subtype;
    @ApiModelProperty(name = "category", required = false, example = "", notes = "大类")
    private String category;
    @ApiModelProperty(name = "subcategory", required = false, example = "", notes = "小类")
    private String subcategory;
    @ApiModelProperty(name = "code", required = false, example = "", notes = "代码")
    private String code;
    @ApiModelProperty(name = "subcode", required = false, example = "", notes = "子代码")
    private String subcode;
    @ApiModelProperty(name = "name", required = false, example = "", notes = "名称")
    private String name;
    @ApiModelProperty(name = "intValue", required = false, example = "", notes = "整形数值")
    private Integer intValue;
    @ApiModelProperty(name = "numberValue", required = false, example = "", notes = "浮点型数值")
    private BigDecimal numberValue;
    @ApiModelProperty(name = "strValue", required = false, example = "", notes = "字符型数值")
    private String strValue;
    @ApiModelProperty(name = "dateValue", required = false, example = "", notes = "日期型数值")
    private Date dateValue;
    @ApiModelProperty(name = "atr1", required = false, example = "", notes = "备用")
    private String atr1;
    @ApiModelProperty(name = "atr2", required = false, example = "", notes = "备用")
    private String atr2;
    @ApiModelProperty(name = "atr3", required = false, example = "", notes = "备用")
    private String atr3;
    @ApiModelProperty(name = "atr4", required = false, example = "", notes = "备用")
    private String atr4;
    @ApiModelProperty(name = "atr5", required = false, example = "", notes = "备用")
    private String atr5;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;
    @ApiModelProperty(name = "version", required = false, example = "1", notes = "数据版本号。创建无须此参数，更新必须携带此参数")
    private Integer version;
    @ApiModelProperty(name = "createTime", required = true, example = "2020-12-25T06:55:14.000+0000", notes = "创建时间(UTC)")
    private Date createTime;
    @ApiModelProperty(name = "createBy", required = true, example = "test", notes = "创建人")
    private String createBy;
    @ApiModelProperty(name = "updateTime", required = true, example = "2020-12-25T06:55:14.000+0000", notes = "最后更新时间(UTC)")
    private Date updateTime;
    @ApiModelProperty(name = "updateBy", required = true, example = "test", notes = "最后更新人")
    private String updateBy;
}
