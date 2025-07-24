package com.my;

public class SingleVirtualThreadCreate {

    public void createSingleVirtualThread() throws InterruptedException {
        Thread myVirtualThread = Thread.ofVirtual()
                .name("my virtual thread") // 스레드 이름 붙이기
                .start(() -> {
                    System.out.println("가상 스레드 이름: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000); // 1초 대기 (네트워크 I/O, 파일 I/O 등 블로킹 작업을 시뮬레이션)
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("가상 스레드 작업 완료: " + Thread.currentThread().getName());
                });
        myVirtualThread.join(); // 가상스레드가 끝날 때까지 메인 스레드 대기
        System.out.println("메인 스레드 작업 완료");
    }
}
