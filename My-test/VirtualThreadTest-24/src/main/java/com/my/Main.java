package com.my;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SingleVirtualThreadCreate singleVirtualThreadCreate = new SingleVirtualThreadCreate();
        VirtualThreadPoolCreate virtualThreadPoolCreate = new VirtualThreadPoolCreate();
        CompletableFutureThread completableFutureThread = new CompletableFutureThread();
        //singleVirtualThreadCreate.createSingleVirtualThread();
        //virtualThreadPoolCreate.createVirtualThreadPool();
        completableFutureThread.createCompletableFutureThread();
    }
}