package com.aps.quality.service.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
@Component
public abstract class FileActionBase implements FileAction {
    @Value("${image.local.storage.path:image}")
    private String storagePath;

    @Override
    public boolean isMatch(String channel) {
        return false;
    }

    public String[] createImageFileName(final String fileType) {
        final String[] path = new String[2];
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final String uuid = UUID.randomUUID().toString();
        final String imageFileName = String.format("%s/%s/%d-%d/%s.%s",
                System.getProperty("user.dir"),
                storagePath,
                year,
                month,
                uuid,
                fileType);
        final File fileDir = new File(imageFileName);

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        path[0] = imageFileName;
        path[1] = String.format("%s/%d-%d/%s.%s", storagePath, year, month, uuid, fileType);

        return path;
    }
}
