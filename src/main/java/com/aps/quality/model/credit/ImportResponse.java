package com.aps.quality.model.credit;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImportResponse implements Serializable {
    private Integer total;
    private Integer success;
    private Integer failed;
    private Integer newUser;
    private String error;

    public void init() {
        this.total = 0;
        this.success = 0;
        this.failed = 0;
        this.newUser = 0;
        this.error = null;
    }
}
