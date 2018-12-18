package com.manage.applepestmanagement.arithmetic.day12month18;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Swang
 * Date: 2018-12-17
 * Time: 15:04
 */
public class TwoSum {


    public int[] nums = {2, 5, 8, 8, 6, 8, 6, 1, 2, 7, 8, 9, 4, 3, 5};

    @Test
    public void testSum(){
        System.out.println(Arrays.toString(getSum(nums, 7)));
    }

    /**
     * int[] nums = {2, 5, 8, 8, 6, 8, 6, 1, 2, 7, 8, 9, 4, 3, 5}, target = 7;
     * because nums[0] + nums[1] = 7;
     * return int[]{0, 1};
     * @param nums
     * @param target
     * @return
     */
    public int[] getSum(int[] nums, int target){
        int[] result = new int[2];
        int len = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            if (map.containsKey(nums[i])) {
                result[1] = i;
                result[0] = map.get(nums[i]);
                break;
            } else {
                map.put(target - nums[i], i);
            }
        }
        return result;
    }
}
