package com.aps.quality.service.component;

import com.aps.quality.model.credit.RemoveUploadRequest;
import com.aps.quality.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class FileActionLocal extends FileActionBase {
    @Override
    public boolean isMatch(String channel) {
        return Const.FileActionChannel.LOCAL.equalWithCode(channel);
    }

    @Override
    public String saveFromBase64(final String imageBase64, final String contentType, final String fileType) {
        log.info("call saveImageFromBase64 for LOCAL");
        final String[] path = createImageFileName(fileType);
        try {
            final byte[] imageBytes = new BASE64Decoder().decodeBuffer(imageBase64);
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            final BufferedImage bufferedImage = ImageIO.read(inputStream);
            final File file = new File(path[0]);
            ImageIO.write(bufferedImage, fileType, file);

            return path[1];
        } catch (Exception e) {
            log.error("Write Base64 to file got an error: ", e);
            return null;
        }
    }

    @Override
    public String saveFromFile(final MultipartFile imageFile, final String contentType, final String fileType) {
        log.info("call saveImageFromFile for LOCAL");
        final String[] path = createImageFileName(fileType);
        try {
            final File file = new File(path[0]);
            imageFile.transferTo(file);

            return path[1];
        } catch (IOException e) {
            log.error("Write MultipartFile to file got an error: ", e);
            return null;
        }
    }

    @Override
    public boolean remove(RemoveUploadRequest request) {
        final File file = new File(String.format("%s/%s", System.getProperty("user.dir"), request.getStorageFileName()));

        if (file.exists()) {
            file.delete();
        }
        return true;
    }
}
