package com.manage.applepestmanagement.arithmetic.d12_18;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: Swang
 * Date: 2018-12-18
 * Time: 16:53
 */
public class PalindromeNumber {

    /**
     * 真垃圾
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }
        int flag = x;
        int result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            x /= 10;
        }
        if (flag == result) {
            return true;
        }
        return false;
    }

    @Test
    public void testP() {
        System.out.println(isPalindrome1(-1112111));
        Assert.assertEquals(true, isPalindrome1(1112111));
    }

    /**
     * 厉害了
     * @param x
     * @return
     */
    public boolean isPalindrome1(int x) {
        if (x<0 || (x!=0 && x%10==0)) return false;
        int rev = 0;
        while (x>rev){
            rev = rev*10 + x%10;
            x = x/10;
        }
        return (x==rev || x==rev/10);
    }
}
