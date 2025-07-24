package com.my;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureThread {

    public void createCompletableFutureThread() {
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            System.out.println("CompletableFuture 가상 스레드 작업 시작");

            CompletableFuture<String> future = CompletableFuture.supplyAsync(()-> {
                Thread.currentThread().setName("future1");
                System.out.println("작업 1 시작 - 스레드 이름: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(800); // 800ms 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("작업 1 중단됨");
                }
                return "Data from Service 1";
            }, executorService);

            CompletableFuture<String> combindFuture = future.thenApplyAsync(result -> {
                Thread.currentThread().setName("future2");
                System.out.println("작업 2 시작 - 스레드 이름: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500); // 500ms 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("작업 2 중단됨");
                }
                return result + "Data from Service 2";
            }, executorService);

            String finalResult = combindFuture.join();
            System.out.println("최종 결과: " + finalResult);
        } // try-with-resources 구문을 사용하여 ExecutorService를 자동으로 닫습니다.
        System.out.println("메인 스레드 종료");
    }
}
