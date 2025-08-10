package com.my.compareEntity;

public class IntObj implements Comparable<IntObj>{
    final int i;

    public IntObj(int i) {this.i = i;}
    @Override
    public int compareTo(IntObj o) {
        System.out.println(i  - o.i);
        return i - o.i;
    }
}
