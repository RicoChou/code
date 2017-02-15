/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode currNode = head;
        ListNode prevNode = head;
        while (currNode != null && currNode.next != null) {
            if (currNode.next == head)
                return true;
            currNode = currNode.next;
            prevNode.next = head;
            prevNode = currNode;
        }
        return false;
    }
}

// better solution which costs time <= n, use fast and slow walker
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }
}
