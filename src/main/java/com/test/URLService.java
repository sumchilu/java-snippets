package com.test;

import java.util.Base64;
import java.util.HashMap;
import java.util.random.RandomGenerator;

public class URLService {
    HashMap<String, Long> ltos;
    HashMap<Long, String> stol;
    static long COUNTER=100000000000L;
    String elements;

    URLService() {
        ltos = new HashMap<String, Long>();
        stol = new HashMap<Long, String>();
        COUNTER = 100000000000L;
        elements = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";    }
    public String longToShort(String url) {
        String shorturl = base10ToBase62(COUNTER);
        ltos.put(url, COUNTER);
        stol.put(COUNTER, url);
        COUNTER++;
        return "http://tiny.url/" + shorturl;
    }
    public String shortToLong(String url) {
        url = url.substring("http://tiny.url/".length());
        int n = base62ToBase10(url);
        return stol.get(n);
    }

    public int base62ToBase10(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            n = n * 62 + convert(s.charAt(i));
        }
        return n;

    }
    public int convert(char c) {
        if (c >= '0' && c <= '9')
            return c - '0';
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 36;
        }
        return -1;
    }
    public String base10ToBase62(long n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            sb.insert(0, elements.charAt((int)(n % 62)));
            n /= 62;
        }
        while (sb.length() < 7) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        URLService service = new URLService();
        String s = service.longToShort("https://medium.com/@sandeep4.verma/system-design-scalable-url-shortener-service-like-tinyurl-106f30f23a82");
        String s1 = service.longToShort("https://www.geeksforgeeks.org/system-design-url-shortening-service/");

        System.out.println("Long.toHexString(100000000000L) = " + Long.toHexString(100000000000L));
        System.out.println("Integer.toBinaryString(1073741824) = " + Integer.toBinaryString(1073741824));
        System.out.println("Integer.parseInt(\"1111111\", 2) = " + Integer.parseInt("111111", 2));
        System.out.println("Long.parseLong(\"000000000000000000000000000000111111\", 2) = " + Long.parseLong("111111", 2));
        System.out.println("Long.parseLong(\"111111111111111111111111111111111111\", 2) = " + Long.parseLong("111111111111111111111111111111111111111111111111", 2));
        System.out.println("Long.toHexString(67645734912L) = " + Long.toHexString(67645734912L));
        System.out.println("s = " + s);
        System.out.println("s1 = " + s1);
        System.out.println("Long.MAX_VALUE = " + Long.MAX_VALUE);
        System.out.println("Long.toBinaryString(Integer.parseInt(\"000000000000003f\", 16)) = " + Long.toBinaryString(Long.parseLong("000000000000003f", 16)));
        System.out.println("Long.toBinaryString(Integer.parseInt(\"0000000000000fc0\", 16)) = " + Long.toBinaryString(Long.parseLong("fc0000000", 16)));
        System.out.println("Integer.parseInt(\"0x000000000000003fL\", 16) = " + Integer.parseInt("000000000000003f", 16));
        System.out.println("Integer.parseInt(\"0000000000000fc0\", 16) = " + Integer.parseInt("0000000000000fc0", 16));
        System.out.println("(Long.parseLong(\"0x0000000fc0000000L\", 16)) = " + (Long.parseLong("0000000fc0000000", 16)));
        System.out.println("(0x0000000fc0000000L& 1234 ) = " +( (0x0000000fc0000000L & 12343423424234L) >> 30));
        System.out.println("Long.parseLong(\"0000000fc0000000\", 16) = " + Long.parseLong("0000000fc0000000", 16));
        System.out.println("Long.toBinaryString(Long.parseLong(\"0000000fc0000000\", 16)) = " + Long.toBinaryString(Long.parseLong("0000000fc0000000", 16)));
        System.out.println("Long.toBinaryString(Long.parseLong(\"000000003f000000\", 16)) = " + Long.toBinaryString(Long.parseLong("000000003f000000", 16)));
        System.out.println("Long.toBinaryString(Long.parseLong(\"000000000000003f\", 16)) = " + Long.toBinaryString(Long.parseLong("000000000000003f", 16)));
        System.out.println("Integer.toBinaryString(1234567) = " + Integer.toBinaryString(1234567));
        System.out.println("(64L * 64L * 64L * 64L * 64L * 64L) = " + (64L * 64L * 64L * 64L * 64L * 64L));

        System.out.println("(0x0000000fc0000000L & 12343423424234L) = " + ((0x000000003f000000L & (64L * 64L * 64L * 64L ) >>24)));

        System.out.println("getBase64String((64L * 64L * 64L * 64L )) = " + getBase64String(RandomGenerator.getDefault().nextLong()));

        System.out.println("getBase64String((64L * 64L * 64L * 64L )) = " + getBase64String(68719476735L));
    }

    private static String getBase64String(long hostCounter) {
        final long BOTTOM_SIX  = 0x000000000000003fL;
        final long SECOND_SIX  = 0x0000000000000fc0L;
        final long THIRD_SIX   = 0x000000000003f000L;
        final long FOURTH_SIX  = 0x0000000000fc0000L;
        final long FIFTH_SIX   = 0x000000003f000000L;
        final long SIXTH_SIX   = 0x0000000fc0000000L;

        char chars[] = new char[6];
        System.out.println("ModBase64.getChar((int) ((SIXTH_SIX & hostCounter) >> 30)); = " + ModBase64.getChar((int) ((SIXTH_SIX & hostCounter) >> 30)));
        ;
        chars[0] = ModBase64.getChar((int) ((SIXTH_SIX & hostCounter) >> 30));
        chars[1] = ModBase64.getChar((int) ((FIFTH_SIX & hostCounter) >> 24));
        chars[2] = ModBase64.getChar((int) ((FOURTH_SIX & hostCounter) >> 18));
        chars[3] = ModBase64.getChar((int) ((THIRD_SIX & hostCounter) >> 12));
        chars[4] = ModBase64.getChar((int) ((SECOND_SIX & hostCounter) >> 6));
        chars[5] = ModBase64.getChar((int) ((BOTTOM_SIX & hostCounter) >> 0));

        return new String(chars);
    }

    static class ModBase64 {
        private static final char[] base64 = new char[64];

        // initialize the base64 mapping.
        static {
            int i = 0;
            for (char j = '0'; j <= '9'; j++) {
                base64[i++] = (char) j;
            }
            base64[i++] = 45;// '-'
            base64[i++] = '_';
            for (char j = 'A'; j <= 'Z'; j++) {
                base64[i++] = (char) j;
            }
            for (char j = 'a'; j <= 'z'; j++) {
                base64[i++] = (char) j;
            }
            assert i == 64;
        }

        /**
         * Retrieve a base64 representation of the specified integer.
         *
         * @param i
         * @return a modBase64 char
         *
         * @throws IllegalArgumentException if i is outside the range 0-63;
         */
        static char getChar(int i) {
            if (i < 0 || i > 63) {
                throw new IllegalArgumentException("Number is outside the range 0-63.");
            }
            return base64[i];
        }

        /**
         * Given the following character in modbase64, return the integer which it encodes.
         *
         * @param ch A character in the range '0'..'9', '-','_','A'..'Z','a..z'     *
         * @return an integer in the range 0-64
         * @throws IllegalArgumentException If ch is not in the range specified above.
         */
        static int getInt(char ch) {
            int num = -1;
            if (ch >= '0' && ch <= '9') {
                num = ch - '0';
            }
            else if (ch == '-') {
                num = 10;
            }
            else if (ch == '_') {
                num = 11;
            }
            else if (ch >= 'A' && ch <= 'Z') {
                num = 12 + (ch - 'A');
            }
            else if (ch >= 'a' && ch <= 'z') {
                num = 38 + (ch - 'a');
            }
            return num;
        }
    }
}
