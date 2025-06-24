package com.my.springthumbnailatortest;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ThumbnailService {


    public void writeThumbnail(MultipartFile file, OutputStream os) throws IOException {
        Thumbnails.of(file.getInputStream())
                .size(450, 450)
                .toOutputStream(os);
    }
}
