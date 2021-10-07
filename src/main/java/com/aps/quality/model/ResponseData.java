package com.aps.quality.model;

import com.aps.quality.util.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseData<T> implements Serializable {
    @ApiModelProperty(name = "errorCode", required = false, example = "10001", notes = "错误代码")
    private Integer errorCode;
    @ApiModelProperty(name = "errorMessage", required = false, example = "internal error", notes = "错误信息")
    private String errorMessage;
    @ApiModelProperty(name = "data", required = false, example = "{\"id\":1,\"name\":\"Kevin\"}", notes = "返回Json数据")
    private T data;

    public ResponseData(final String errorMessage) {
        this.errorCode = ErrorMessage.RUNTIME_EXCEPTION.getCode();
        this.errorMessage = errorMessage;
    }

    public ResponseData(final ErrorMessage error) {
        this.errorCode = error.getCode();
        this.errorMessage = error.getDescription();
    }

    public ResponseData(final ErrorMessage error, String message) {
        this.errorCode = error.getCode();
        this.errorMessage = String.format("%s: %s", error.getDescription(), message);
    }

    public ResponseData(final T data) {
        this.data = data;
    }

    @ApiModelProperty(name = "succeed", required = false, example = "true", notes = "是否成功")
    public boolean getSucceed() {
        return null == errorCode;
    }
}
