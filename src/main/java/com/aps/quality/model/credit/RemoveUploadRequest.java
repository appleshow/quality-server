package com.aps.quality.model.credit;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RemoveUploadRequest implements Serializable {
    @ApiModelProperty(name = "storageFileName", required = false, example = "", notes = "文件存储名")
    private String storageFileName;

    public RemoveUploadRequest() {

    }

    public RemoveUploadRequest(String storageFileName) {
        this.storageFileName = storageFileName;
    }
}
