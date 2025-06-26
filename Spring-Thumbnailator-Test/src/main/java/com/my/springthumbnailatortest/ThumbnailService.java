package com.my.springthumbnailatortest;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
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

    public void writeThumnailMovie(MultipartFile file, OutputStream os) throws IOException {
        String ffmpegPath = "/opt/homebrew/bin/ffmpeg";
        String ffprobePath = "/opt/homebrew/bin/ffprobe";


        FFmpeg ffmpeg = new FFmpeg(ffmpegPath);
        FFprobe ffprobe = new FFprobe(ffprobePath);

        File convFile = File.createTempFile("upload_", file.getOriginalFilename());
        file.transferTo(convFile);

        File tempImageFile = new File("temp_frame.jpg"); // 임시 추출 이미지 파일
        File thumbnailFile = new File("video_thumbnail.jpg"); // 최종 썸네일 파일

        // 1. FFmpeg을 사용하여 동영상에서 프레임 추출
        // 동영상 길이의 10% 지점의 프레임을 추출하는 예시
        try {
            FFmpegProbeResult probeResult = ffprobe.probe(convFile.getAbsolutePath());
            double durationSeconds = probeResult.format.duration;
            long seekTimeMillis = (long) (durationSeconds * 0.1 * 1000); // 동영상 길이의 10% 지점

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(convFile.getAbsolutePath())
                    .overrideOutputFiles(true) // 기존 파일 덮어쓰기
                    .addOutput(tempImageFile.getAbsolutePath())
                    .setFrames(1) // 한 프레임만 추출
                    .setFormat("image2") // 이미지 형식으로 출력
                    .addExtraArgs("-ss", String.valueOf(seekTimeMillis / 1000.0)) // 특정 시점으로 이동 (초 단위)
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            executor.createJob(builder).run();

            System.out.println("프레임 추출 완료: " + tempImageFile.getAbsolutePath());

            // 2. Thumbnailator를 사용하여 추출된 이미지 썸네일 생성 및 크기 조절
            Thumbnails.of(tempImageFile)
                    .size(320, 180) // 썸네일 크기 (가로, 세로)
                    .outputFormat("jpg")
                    .toOutputStream(os);

            System.out.println("썸네일 생성 완료: " + thumbnailFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("FFmpeg 또는 Thumbnailator 실행 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 임시 파일 삭제
            if (tempImageFile.exists()) {
                tempImageFile.delete();
            }
        }
    }
}
