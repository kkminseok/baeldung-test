package com.my;


import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Arrays.stream("Hello.Java Test Quality.Test!".split(".")).toList().forEach(System.out::println);
    }
}