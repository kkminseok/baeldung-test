package com.my.springmicrometertest.controller;

import com.my.springmicrometertest.TemporaryFileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class GaugeController {

    @Autowired
    private TemporaryFileManager fileManager;

    // 생성된 파일 경로를 저장하여 나중에 삭제할 수 있도록 합니다.
    private final List<Path> createdFiles = new CopyOnWriteArrayList<>();

    @GetMapping("/gauge/create-file")
    public String createTempFile() throws IOException {
        Path newFile = fileManager.createTempFile();
        createdFiles.add(newFile);
        return "Temporary file created. Current active count: " + fileManager.getActiveFileCount();
    }

    @GetMapping("/gauge/delete-file")
    public String deleteTempFile() throws IOException {
        if (!createdFiles.isEmpty()) {
            // 가장 최근에 생성된 파일부터 삭제
            Path fileToDelete = createdFiles.remove(createdFiles.size() - 1);
            fileManager.deleteTempFile(fileToDelete);
            return "Temporary file deleted. Current active count: " + fileManager.getActiveFileCount();
        } else {
            return "No temporary files to delete.";
        }
    }

    @GetMapping("/gauge/current-count")
    public String getCurrentCount() {
        return "Current active temporary files: " + fileManager.getActiveFileCount();
    }
}
