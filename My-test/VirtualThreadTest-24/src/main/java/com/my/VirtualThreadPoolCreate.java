package com.my;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VirtualThreadPoolCreate {

    public void createVirtualThreadPool() throws InterruptedException {
        // try-with-resources 구문을 사용하여 가상 스레드 풀 생성, ExecutorService를 자동으로 닫을 수 있음.
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 5; ++i) {
                final int taskId = i;
                // 가상 스레드 풀에 작업 제출
                executorService.submit(() -> { // 각 submit마다 새로운 가상 스레드가 생성
                    Thread.currentThread().setName("task-" + taskId);
                    System.out.println("Task " + taskId + " is running in thread: " + Thread.currentThread().getName());
                    try {
                        // 블로킹 작업 시뮬레이션
                        Thread.sleep(100 + (long)(Math.random() * 500)); // n초 대기
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Task " + taskId + " was interrupted.");
                    }
                    System.out.println("task " + taskId + " completed in thread: " + Thread.currentThread().getName());
                });
            }
        }// try 구문을 벗어나면 자동으로 executorService.close()가 호출되어 가상 스레드 풀을 종료합니다.
        System.out.println("모든 가상 스레드 작업제출 완료, 메인 스레드 작업 종료 대기 ...");
        TimeUnit.SECONDS.sleep(2); // 메인 스레드가 종료되기 전에 모든 가상 스레드가 완료될 때까지 대기
        System.out.println("메인 스레드 작업 완료");
    }
}
