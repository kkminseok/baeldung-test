package com.my.compareEntity;

public record User(String name, int age)implements Comparable<User> {
    @Override
    public int compareTo(User o) {
        int res = name.compareTo(o.name);
        System.out.println(res);
        return res != 0 ? res : Integer.compare(age, o.age);
    }
}
