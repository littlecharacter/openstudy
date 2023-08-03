package com.lc.pattern.builder.singleton;

/**
 * java.awt.Toolkit升级版
 * @author LittleCharacter
 */
public class ToolkitUp {
    //懒汉式
    private volatile static ToolkitUp toolkitUp;

    //私有化其空构造方法，这点很重要，不然可以new的！！！
    private ToolkitUp() {}

    public static ToolkitUp getDefaultToolkit() {
        // 双重检验 + 锁
        if (toolkitUp == null) {
            synchronized (ToolkitUp.class) {
                if (toolkitUp == null) {
                    toolkitUp = new ToolkitUp();
                }
            }
        }
        return toolkitUp;
    }
}
