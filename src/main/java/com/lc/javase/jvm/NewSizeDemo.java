package com.lc.javase.jvm;

import java.util.ArrayList;
import java.util.List;

public class NewSizeDemo {
    public static void main(String[] args) {
        byte[] bytes = null;
        for (int i = 0; i < 10; i++) {
            bytes = new byte[1 * 1024 * 1024];
        }
        List<String> list = new ArrayList<>();
        list.size();
    }
}
