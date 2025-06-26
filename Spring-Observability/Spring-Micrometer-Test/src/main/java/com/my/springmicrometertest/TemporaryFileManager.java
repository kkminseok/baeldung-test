package com.my.springmicrometertest;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger; // 동시성 환경에서 안전한 정수 카운터

@Service
public class TemporaryFileManager {

    private final AtomicInteger activeFileCount; // 현재 활성화된 파일 개수를 추적
    private final Path tempDir; // 임시 파일이 저장될 디렉토리

    public TemporaryFileManager(MeterRegistry meterRegistry) throws IOException {
        this.activeFileCount = new AtomicInteger(0);

        // 임시 파일 저장 디렉토리 생성 (예: 프로젝트 루트/temp_files)
        this.tempDir = Paths.get("./temp_files");
        if (!Files.exists(tempDir)) {
            Files.createDirectories(tempDir);
        }

        // Gauge 등록: "app.temp.files.active"라는 이름으로 Gauge 등록
        // 이 Gauge는 activeFileCount의 현재 값을 보고합니다.
        meterRegistry.gauge("app.temp.files.active", activeFileCount);
        System.out.println("Gauge 'app.temp.files.active' registered.");
    }

    /**
     * 임시 파일을 생성하고 카운트를 증가시킵니다.
     * @return 생성된 파일의 경로
     */
    public Path createTempFile() throws IOException {
        String fileName = "temp_" + System.currentTimeMillis() + ".txt";
        Path filePath = tempDir.resolve(fileName);
        Files.createFile(filePath); // 실제 파일 생성
        activeFileCount.incrementAndGet(); // 카운트 증가
        System.out.println("Created temp file: " + filePath + ". Active count: " + activeFileCount.get());
        return filePath;
    }

    /**
     * 임시 파일을 삭제하고 카운트를 감소시킵니다.
     * @param filePath 삭제할 파일의 경로
     * @return 파일 삭제 성공 여부
     */
    public boolean deleteTempFile(Path filePath) throws IOException {
        boolean deleted = Files.deleteIfExists(filePath); // 실제 파일 삭제
        if (deleted) {
            activeFileCount.decrementAndGet(); // 카운트 감소
            System.out.println("Deleted temp file: " + filePath + ". Active count: " + activeFileCount.get());
        } else {
            System.out.println("Failed to delete temp file (not found): " + filePath);
        }
        return deleted;
    }

    /**
     * 현재 활성화된 파일의 개수를 반환 (Gauge가 이 메서드를 참조하지 않음)
     * Gauge는 AtomicInteger 객체 자체를 참조하고 있으므로 getter가 필요없지만,
     * 디버깅이나 다른 용도로 필요할 수 있음.
     */
    public int getActiveFileCount() {
        return activeFileCount.get();
    }
}