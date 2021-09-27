package com.aps.quality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserConciseDto implements Serializable {
    private Integer userId;
    private String userName;
}
