package com.my.socket.server;

import java.io.IOException;

public class SimpleHttpServerMain {

    public static void main(String[] args) {
        int port = 8080;
        SimpleHttpServerMultiThreaded server = new SimpleHttpServerMultiThreaded(port);
        try {
            server.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
