package com.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Jep418 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("google.com");
        System.out.println(inetAddress);
        // default - google.com/142.250.206.142
        // return Stream.of(InetAddress.getByAddress(host, new byte[]{1, 1, 1, 1}));
        // with SPI registered - google.com/1.1.1.1
    }
}
