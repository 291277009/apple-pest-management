package com.manage.applepestmanagement.arithmetic;

import org.junit.Test;

/**
 * User: Swang
 * Date: 2018-12-17
 * Time: 15:04
 */
public class AddTwoNumbers {

    @Test
    public void Test() {
        ListNode l1 = new ListNode(2);
        l1.setNext(new ListNode(4));
        l1.next.setNext(new ListNode(3));

        ListNode l2 = new ListNode(5);
        l2.setNext(new ListNode(6));
        l2.next.setNext(new ListNode(4));
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(addTwoNumbers(l1, l2));
    }

    /**
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 0 -> 8
     * Explanation: 342 + 465 = 807.
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(0);
        ListNode ptr = res;
        int carry = 0, val = 0;
        while (l1 != null || l2 != null || carry > 0) {
            if (l1 != null) {
                val += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                val += l2.val;
                l2 = l2.next;
            }
            val += carry;
            ptr.next = new ListNode(val % 10);
            carry = val / 10;
            val = 0;
            ptr = ptr.next;
        }
        return res.next;
    }


}

class ListNode {
    int val;
    ListNode next;

    ListNode (int x) {
        val = x;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        if (null != this.next) {
            return this.next.toString() + String.valueOf(this.val) ;
        }
        return String.valueOf(this.val);
    }
}