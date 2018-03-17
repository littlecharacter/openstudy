package com.lc.javase.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BaseStudy {
    Integer i1 = 128; //-127~128
    Integer i2 = 129;
    String s;
    List<Long> list = new ArrayList<>();
    Map<String, Long> map = new HashMap<>();
    List<Long> cList = new CopyOnWriteArrayList<>();
    Map<String, Long> cMap = new ConcurrentHashMap<>();
}
