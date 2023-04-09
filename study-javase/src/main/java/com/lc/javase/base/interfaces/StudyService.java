package com.lc.javase.base.interfaces;

/**
 * @author gujixian
 * @since 2022/8/31
 */
public interface StudyService extends PersonService {
    default void doIt() {
        System.out.println("study...");
    }
}
