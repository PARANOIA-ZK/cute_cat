package com.paranoia.leetcode.easy;

import lombok.Data;

/**
 * @author ZHANGKAI
 * @date 2019/9/10
 * @description https://leetcode-cn.com/problems/merge-two-sorted-lists/
 */
public class 合并两个有序列表 {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
        l1.next.next.next = new ListNode(5);
        l1.next.next.next.next = new ListNode(10);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
        l2.next.next.next = new ListNode(6);
        l2.next.next.next.next = new ListNode(11);
        l2.next.next.next.next.next = new ListNode(12);

        ListNode l11 = null;
        ListNode l12 = new ListNode(0);

        ListNode l111 = new ListNode(1);
        ListNode l122 = null;

        ListNode l33 = new ListNode(5);
        ListNode l333 = new ListNode(1);
        l333.next = new ListNode(2);
        l333.next.next = new ListNode(4);

        ListNode l44 = new ListNode(-9);
        l44.next = new ListNode(3);
        l44.next.next = new ListNode(9);
        ListNode l444 = new ListNode(5);
        l444.next = new ListNode(7);

        ListNode listNode = mergeTwoLists(l1, l2);
        ListNode listNode1 = mergeTwoLists(l11, l12);
        ListNode listNode2 = mergeTwoLists(l111, l122);
        ListNode listNode3 = mergeTwoLists(l33, l333);
        ListNode listNode4 = mergeTwoLists(l44, l444);

        for (ListNode x = listNode; x != null; x = x.next) {
            System.out.println("x.val = " + x.val);
        }
        System.out.println("``````````````````````````````````````````````````````````");
        for (ListNode x = listNode1; x != null; x = x.next) {
            System.out.println("x.val = " + x.val);
        }
        System.out.println("``````````````````````````````````````````````````````````");
        for (ListNode x = listNode2; x != null; x = x.next) {
            System.out.println("x.val = " + x.val);
        }
        System.out.println("``````````````````````````````````````````````````````````");
        for (ListNode x = listNode3; x != null; x = x.next) {
            System.out.println("x.val = " + x.val);
        }
        System.out.println("``````````````````````````````````````````````````````````");
        for (ListNode x = listNode4; x != null; x = x.next) {
            System.out.println("x.val = " + x.val);
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // maintain an unchanging reference to node ahead of the return node.
        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        // exactly one of l1 and l2 can be non-null at this point, so connect
        // the non-null list to the end of the merged list.
        prev.next = l1 == null ? l2 : l1;

        return prehead.next;

    }
}

@Data
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
