package com.lc.javase.other.pojo;

public class PointMutable {
    public int x = 0;
    public int y = 0;

    public PointMutable() {}

    public PointMutable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PointMutable(PointMutable point) {
        this.x = point.x;
        this.y = point.y;
    }
}
