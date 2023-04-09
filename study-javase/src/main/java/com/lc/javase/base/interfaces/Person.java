package com.lc.javase.base.interfaces;

/**
 * @author gujixian
 * @since 2022/8/31
 */
public class Person implements StudyService, PlayService {
    // 必须重写，类似多继承
    @Override
    public void doIt() {
        System.out.println("eat、play、study...");
    }

    public static void main(String[] args) {
        PersonService personService = new Person();
        personService.doIt();
        PlayService playService = new Person();
        playService.doIt();
        StudyService studyService = new Person();
        studyService.doIt();
    }
}
