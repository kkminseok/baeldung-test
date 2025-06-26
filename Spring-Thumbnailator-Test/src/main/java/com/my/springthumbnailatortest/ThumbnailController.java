package com.my.springthumbnailatortest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ThumbnailController {

    private final ThumbnailService thumbnailService;

    public ThumbnailController(ThumbnailService thumbnailService) {
        this.thumbnailService = thumbnailService;
    }


    @PostMapping("/thumbnail")
    public void getThumbnail(@RequestPart MultipartFile file, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg"); // 또는 생성하는 이미지 타입에 맞게 설정
        thumbnailService.writeThumbnail(file, response.getOutputStream());
    }

    @PostMapping("/thumbnail/movie")
    public void getMovieThumbnail(@RequestPart MultipartFile file, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg"); // 또는 생성하는 이미지 타입에 맞게 설정
        thumbnailService.writeThumnailMovie(file, response.getOutputStream());
    }
}
