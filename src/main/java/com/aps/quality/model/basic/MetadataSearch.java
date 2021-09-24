package com.aps.quality.model.basic;

import com.aps.quality.model.PageableSearch;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MetadataSearch extends PageableSearch {
    @ApiModelProperty(name = "type", required = false, example = "", notes = "类型，精确匹配")
    private String type;
    @ApiModelProperty(name = "subtype", required = false, example = "", notes = "子类型，精确匹配")
    private String subtype;
    @ApiModelProperty(name = "category", required = false, example = "", notes = "大类，精确匹配")
    private String category;
    @ApiModelProperty(name = "subcategory", required = false, example = "", notes = "小类，精确匹配")
    private String subcategory;
    @ApiModelProperty(name = "code", required = false, example = "", notes = "代码，精确匹配")
    private String code;
    @ApiModelProperty(name = "subcode", required = false, example = "", notes = "子代码，精确匹配")
    private String subcode;
    @ApiModelProperty(name = "name", required = false, example = "", notes = "名称，模糊匹配")
    private String name;

    public void init() {
        name = DataUtil.likeFormat(name);
    }
}
