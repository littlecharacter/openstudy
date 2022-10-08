package com.lc.javase.base;

public class GenericStudy<T> {
    private int id;
    private T data;

    public static void main(String[] args) {
        GenericStudy<Integer> x = new GenericStudy<>();
        x.setId(1);
        x.setData(2);
        int y = x.getData();
        System.out.println(y);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
