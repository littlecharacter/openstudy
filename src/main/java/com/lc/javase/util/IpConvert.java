package com.lc.javase.util;

public class IpConvert {

    public static Integer ipToInteger(String ipString){
        if (ipString == null) {
            return null;
        }
        String[] ips = ipString.split("\\.");
        int result = 0;
        //因为每个位置最大255，刚好在2进制里表示8位
        for(String ip: ips){
            result = (result << 8) | Integer.parseInt(ip);
        }
        return result;
    }

    public static String integerToIp(Integer ipInteger){
        //思路很简单，每8位拿一次，就是对应位的IP
        StringBuilder sb = new StringBuilder();
        for(int i = 3; i >= 0; i--){
            sb.append((ipInteger >> (8 * i)) & (0xff)).append(".");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
