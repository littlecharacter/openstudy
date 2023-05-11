package com.lc.javase.base.init;

/**
 * @author gujixian
 * @since 2023/5/11
 */
public class InitStudy {
    // public static int x = 5; // 结果为 6,6,6
    public static InitStudy initStudy = new InitStudy();
    public static int x = 5; // 结果为 1,5,5

    static {
        System.out.println("static block is called");
        System.out.println("static block x:" + x);
    }

    public InitStudy() {
        System.out.println("constructor is called");
        System.out.println("constructor x:" + ++x);
    }

    {
        System.out.println("constructor block is called");
    }

    public static void main(String[] args) {
        System.out.println("main method is called");
        System.out.println("main x:" + InitStudy.x);
    }
}
