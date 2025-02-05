package com.my.socket.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleSocketServer {

    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String clientInputLine;
                while((clientInputLine = in.readLine()) != null) {
                    if (clientInputLine.isEmpty()) {
                        break;
                    }

                    out.write("HTTP/1.0 200 OK\r\n");
                    out.write("Date: " + new Date() + "\r\n");
                    out.write("Server: Custom Server\r\n");
                    out.write("Content-Type: text/html\r\n");
                    out.write("Content-Length: " + getBodyLength(getResponseBody()) + "\r\n");
                    out.write("\r\n");
                    out.write(getResponseBody());
                }
            }
        } catch (Exception e) {
            System.out.println("server create error");
        }
    }

    private static String getResponseBody() {
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

    private static int getBodyLength(String body) {
        return body.length();
    }
}
