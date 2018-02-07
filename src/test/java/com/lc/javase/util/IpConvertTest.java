package com.lc.javase.util;

import org.junit.Test;

public class IpConvertTest {
    @Test
    public void testIpToInteger() throws Exception {
        System.out.println(IpConvert.ipToInteger("255.255.255.255"));
        System.out.println(IpConvert.ipToInteger("127.255.255.255"));
        System.out.println(IpConvert.ipToInteger("128.0.0.0"));
        System.out.println(IpConvert.ipToInteger("0.0.0.0"));
    }

    @Test
    public void testIntegerToIp() throws Exception {
        System.out.println(IpConvert.integerToIp(IpConvert.ipToInteger("255.255.255.255")));
        System.out.println(IpConvert.integerToIp(IpConvert.ipToInteger("127.255.255.255")));
        System.out.println(IpConvert.integerToIp(IpConvert.ipToInteger("128.0.0.0")));
        System.out.println(IpConvert.integerToIp(IpConvert.ipToInteger("0.0.0.0")));
    }
}