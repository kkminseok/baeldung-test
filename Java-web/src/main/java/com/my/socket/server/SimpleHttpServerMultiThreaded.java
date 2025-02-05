package com.my.socket.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpServerMultiThreaded {

    private final int port;
    private static final int THREAD_POOL_SIZE = 10;

    public SimpleHttpServerMultiThreaded(int port) {
        this.port = port;
    }

    void handleClient(Socket clientSocket) {
       try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
           String clientInputLine;
           while((clientInputLine = in.readLine()) != null) {
               if(clientInputLine.isEmpty()) {
                   break;
               }
           }
           LocalDateTime now = LocalDateTime.now();

           out.write("HTTP/1.0 200 OK\r\n");
           out.write("Date: " + now + "\r\n");
           out.write("Server: Custom Server\r\n");
           out.write("Content-Type: text/html\r\n");
           out.write("Content-Length: " + getBodyLength(getResponseBody()) + "\r\n");
           out.write("\r\n");
           out.write(getResponseBody());
       } catch (IOException e) {
           // ...
       } finally {
           try {
               clientSocket.close();
           } catch (IOException e) {
               // ...
           }
       }
    }

    private String getResponseBody() {
        return """
                    <html>
                        <head>
                            <title>Minseok Home</title>
                        </head>
                        <body>
                            <h1>Minseok Home Page</h1>
                            <p>Java Tutorials</p>
                            <ul>
                                <li>
                                    <a href="/get-started-with-java-series"> Java </a>
                                </li>
                                <li>
                                    <a href="/spring-boot"> Spring </a>
                                </li>
                                <li>
                                    <a href="/learn-jpa-hibernate"> Hibernate </a>
                                </li>
                            </ul>
                         </body>
                     </html>
                """;
    }

    private int getBodyLength(String body) {
        return body.length();
    }

    void start() throws IOException {
        try(ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(() -> handleClient(clientSocket));
            }
        }
    }
}
