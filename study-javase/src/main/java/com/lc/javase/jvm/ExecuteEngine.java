package com.lc.javase.jvm;

public class ExecuteEngine {
    public volatile int flag = 0;
    public static void main(String[] args) {
        int a = 100;
        int b = 200;
        int c = new ExecuteEngine().calculate(a, b);
    }

    public int calculate(int a, int b) {
        flag = 1;
        return 10 * (a + b);
    }
}
