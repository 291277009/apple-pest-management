package com.manage.applepestmanagement.arithmetic.d12_18;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * User: Swang
 * Date: 2018-12-18
 * Time: 10:59
 */
public class LongestSubstring {

    @Test
    public void TestSubstring() {
        String s = "abcabcbbcsh";
        System.out.println(lengthOfLongestSubstring(s));
        System.out.println(lengthOfLongestSubstring1(s));
    }

    /**
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     *
     * Input: "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     *
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     * @param s
     * @return
     */
    private int lengthOfLongestSubstring1(String s) {
        if (s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); ++i) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - j + 1);
        }
        return max;
    }

    private int lengthOfLongestSubstring(String s) {
        HashSet<Character> characters = new HashSet<>();
        HashMap<Character, Integer> characterPos =  new HashMap<>();

        int start = 0;
        int end = 0;
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            end  = i;
            //character already seen
            if (characters.contains(s.charAt(i))) {
                //check if character is seen after start
                if (characterPos.get(s.charAt(i)) >= start) {
                    if (end - start > max) {
                        max = end - start;
                    }
                    start = characterPos.get(s.charAt(i)) + 1;
                    //set new location
                    characterPos.replace(s.charAt(i), i);
                } else {
                    //it was seen before start no need to worry
                    characterPos.replace(s.charAt(i), i);
                }
            }
            else {
                //new character never before seen
                characters.add(s.charAt(i));
                characterPos.put(s.charAt(i),i);
            }
        }
        if (s.length() != 0) {
            end = end + 1;
        }
        if (end - start > max) {
            max = end - start;
        }

        return max;
    }

}
