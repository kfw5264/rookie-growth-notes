package com.masq.leetcode;

/**
 * 2. 两数相加 <br/>
 * 两个非空链表，表示两个非负整数，每位数字都是逆序存储的，且只能存储一位数字<br/>
 * 求两束相加，并以相同形式返回一个表示和的链表 <br/>
 *
 * @author masq
 */
public class Question0002 {

    public static void main(String[] args) {
        Solution0002 solution = new Solution0002();
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));

        ListNode l3 = solution.addTwoNumbers(l1, l2);
        while (l3 != null) {
            System.out.println(l3.val);
            l3 = l3.next;
        }
    }
}

class Solution0002 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        // 只要两个其中一个不为0就可以继续相加
        while (l1 != null || l2 != null) {
            int val1 = l1 == null ? 0 : l1.val;
            int val2 = l2 == null ? 0 : l2.val;

            int sum = val1 + val2 + carry;
            carry = sum / 10;
            int val = sum % 10;

            if (head == null) {
                head = tail = new ListNode(val);
            } else {
                tail.next = new ListNode(val);
                tail = tail.next;
            }

            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }

        // 如果循环结束之后carry不是零，则需要将carry进到新的一位
        if (carry > 0) {
            tail.next = new ListNode(carry);
        }


        return head;
    }

}
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
