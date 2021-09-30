package com.aps.quality.service.component;

import org.springframework.web.multipart.MultipartFile;

public interface FileAction {
    boolean isMatch(final String channel);

    String saveFromBase64(final String imageBase64, final String contentType);

    String saveFromFile(final MultipartFile imageFile, final String contentType);
}