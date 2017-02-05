/**
* Definition for singly-linked list.
* public class ListNode {
*     int val;
*     ListNode next;
*     ListNode(int x) { val = x; }
* }
*/
public class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return head;
        }
        if(head.next != null) {
            head = swapHelper(head);
        }
        return head;
    }

    public ListNode swapHelper(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }
        ListNode temp = node.next;
        node.next = swapHelper(node.next.next);
        temp.next = node;
        return temp;
    }
}

/******************************************************************************/

public class Solution {
    public ListNode swapPairs(ListNode head) {
        if(head==null || head.next==null) return head;
        ListNode newHead = head.next;
        ListNode p=head, preHead = new ListNode(0);
        while( p!=null ){
            preHead.next = swap(p);
            preHead = p;
            p = p.next;
        }
        return newHead;
    }
    private ListNode swap(ListNode head){
        if(head==null || head.next==null) return head;
        ListNode nextNode = head.next;
        ListNode nextHead = head.next.next;
        nextNode.next = head;
        head.next = nextHead;
        return nextNode;
    }
}
