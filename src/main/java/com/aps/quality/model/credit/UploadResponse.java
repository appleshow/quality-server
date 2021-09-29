package com.aps.quality.model.credit;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadResponse implements Serializable {
    private String name;
    private String url;

    public UploadResponse() {
    }

    public UploadResponse(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
