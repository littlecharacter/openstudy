package com.lc.javase.collection;

import org.junit.Test;

/**
 * TODO
 *
 * @author gujixian
 * @version 1.0
 * @date 2020-04-02
 */
public class CollectionStudyTest {

    CollectionStudy collectionStudy = new CollectionStudy();

    @Test
    public void testStudyHashMap() throws Exception {
        collectionStudy.studyHashMap();
    }

    @Test
    public void testStudyConcurrentHashMap() throws Exception {
        collectionStudy.studyConcurrentHashMap();
    }

    @Test
    public void testStudyLinkedHashMap() throws Exception {
        collectionStudy.studyLinkedHashMap();
    }
}