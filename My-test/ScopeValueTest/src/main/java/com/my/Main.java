package com.my;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {

    private final static ScopedValue<String> USER_ID = ScopedValue.newInstance();

    public static void main(String[] args){
        // 메잇느레드에서 USER_ID 바인딩

        Runtime.Version version = Runtime.version();
        System.out.println("Java 주 버전: " + version.major());
        System.out.println("Java 부 버전: " + version.minor());

        ScopedValue.where(USER_ID, "user-123")
                .run(() -> {
                    System.out.println("Main Thread - User ID: " + USER_ID.get());
                    System.out.println("USER ID Binding? " + USER_ID.isBound());

                    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                        Future<?> submit = executor.submit(() -> {
                            // 가상 스레드에서 USER_ID 접근
                            System.out.println("USER ID Binding? Virtual1 " + USER_ID.isBound());
                            System.out.println("Virtual Thread 1  - User Id: " + USER_ID.get());
                        });

                        Future<?> submit2 = executor.submit(() -> {
                            // 다른 가상 스레드에서도 동일한 USER_ID 값에 접근 가능
                            System.out.println("Virtual thread 2 - User ID: " + USER_ID.get());
                            // 새로운 스코프를 생성하여 USER_ID를 재바인딩
                            ScopedValue.where(USER_ID, "sub-user-456")
                                    .run(() -> {
                                        System.out.println("Virtual thread 2 (nested scope) - User ID: " + USER_ID.get());
                                    });
                            // 중첩 스코프를 벗어나면 이전 값으로 복원
                            System.out.println("Virtual thread 2 (after nested scope) - User ID: " + USER_ID.get());
                        });
                        try {
                            submit.get();
                            submit2.get();
                        } catch (InterruptedException ex1) {
                            System.out.println("error1:" + ex1.getMessage());
                        } catch (ExecutionException ex2) {
                            System.out.println("error2: " + ex2.getMessage());
                        }

                    }

                    // 스코프를 벗어나면 USER_ID 값에 접근할 수 없음 (NoSuchElementException 발생)
                    try {
                        System.out.println("Outside scope - User ID: " + USER_ID.get());
                    } catch (Exception e) {
                        System.out.println("Outside scope - Cannot access USER_ID: " + e.getMessage());
                    }
                });

    }
}

/*


    private static final ScopeValue<String> USER_ID = ScopeValue.newInstance();

    public static void main(String[] args) throws Exception {
        // 메인 스레드에서 USER_ID 바인딩
        ScopeValue.where(USER_ID, "user-123")
                  .run(() -> {
                      System.out.println("Main thread - User ID: " + USER_ID.get());

                      try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                          Future<?> future1 = executor.submit(() -> {
                              // 가상 스레드에서도 USER_ID 값에 접근 가능
                              System.out.println("Virtual thread 1 - User ID: " + USER_ID.get());
                          });

                          Future<?> future2 = executor.submit(() -> {
                              // 다른 가상 스레드에서도 동일한 USER_ID 값에 접근 가능
                              System.out.println("Virtual thread 2 - User ID: " + USER_ID.get());
                              // 새로운 스코프를 생성하여 USER_ID를 재바인딩
                              ScopeValue.where(USER_ID, "sub-user-456")
                                        .run(() -> {
                                            System.out.println("Virtual thread 2 (nested scope) - User ID: " + USER_ID.get());
                                        });
                              // 중첩 스코프를 벗어나면 이전 값으로 복원
                              System.out.println("Virtual thread 2 (after nested scope) - User ID: " + USER_ID.get());
                          });

                          future1.get();
                          future2.get();
                      }
                  });

        // 스코프를 벗어나면 USER_ID 값에 접근할 수 없음 (NoSuchElementException 발생)
        try {
            System.out.println("Outside scope - User ID: " + USER_ID.get());
        } catch (Exception e) {
            System.out.println("Outside scope - Cannot access USER_ID: " + e.getMessage());
        }
    }
}
 */