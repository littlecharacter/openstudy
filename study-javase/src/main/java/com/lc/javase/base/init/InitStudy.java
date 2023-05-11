package com.lc.javase.base.init;

/**
 * @author gujixian
 * @since 2023/5/11
 */
public class InitStudy {
    public static int x = 5;
    static {
        System.out.println("Static block is called");
    }
    static {
        System.out.println(x);
    }
    public InitStudy() {
        System.out.println("Constructor is called");
    }

    {
        System.out.println("Constructor block is called");
    }


    public static void main(String[] args) {
        System.out.println("Main method is called");
        new InitStudy();

    }
}
