package com.lc.javase.pojo;

public class PointSafe {
    private int x;
    private int y;

    public PointSafe(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private PointSafe(int[] points) {
        this(points[0], points[1]);
    }

    private PointSafe(PointSafe point) {
        this(point.get());
    }

    public synchronized int[] get() {
        return new int[] {x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
