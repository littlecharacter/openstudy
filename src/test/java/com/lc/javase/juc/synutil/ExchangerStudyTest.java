package com.lc.javase.juc.synutil;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ExchangerStudyTest {
    private ExchangerStudy exchanger = new ExchangerStudy();

    @Test
    public void testExchange() throws Exception {
        exchanger.exchange();
        TimeUnit.MILLISECONDS.sleep(10000L);
    }
}