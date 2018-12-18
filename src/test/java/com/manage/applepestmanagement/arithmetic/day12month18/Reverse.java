package com.manage.applepestmanagement.arithmetic.day12month18;

import org.junit.Test;

/**
 * User: Swang
 * Date: 2018-12-18
 * Time: 15:06
 */
public class Reverse {


    /**
     * reverse工具
     * @param x
     * @return
     */
    private int reverse1(int x) {
        int result = 0;
        while(x != 0) {
            int tall = x % 10;
            int newResult = result * 10 + tall;
            if ((newResult - tall) / 10 != result) {
                return 0;
            }
            result = newResult;
            x /= 10;
        }
        return result;
    }

    /**
     * 转换字符串做法
     * @param x
     * @return
     */
    private int reverse(int x) {
        String str = String.valueOf(x);
        if (x < 0) {
            str = String.valueOf(-x);
        }
        StringBuilder result = new StringBuilder();
        int len = str.length();
        for (int i = len; i > 0; i--) {
            result.append(str.charAt(i-1));
        }
        if (x < 0) {
            return -Integer.parseInt(result.toString());
        }
        return Integer.parseInt(result.toString());
    }

    @Test
    public void testReverse() {
        System.out.println(reverse(-13200));
        System.out.println(reverse1(2233));
    }
}
