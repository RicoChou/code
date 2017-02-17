/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
         int aSize = 0;
         int bSize = 0;
         int diff;
         ListNode aWalker = headA;
         ListNode bWalker = headB;
         while (aWalker != null) {
             aWalker = aWalker.next;
             aSize++;
         }
         while (bWalker != null) {
             bWalker = bWalker.next;
             bSize++;
         }
         aWalker = headA;
         bWalker = headB;
         if (aSize >= bSize) {
             diff = aSize - bSize;
             while (diff-- > 0)
                aWalker = aWalker.next;
             while (aWalker != null) {
                 if (aWalker == bWalker) {
                     return aWalker;
                 }
                 aWalker = aWalker.next;
                 bWalker = bWalker.next;
             }
             return null;
         } else {
             diff = bSize - aSize;
             while (diff-- > 0)
                bWalker = bWalker.next;
             while (bWalker != null) {
                 if (aWalker == bWalker) {
                     return aWalker;
                 }
                 aWalker = aWalker.next;
                 bWalker = bWalker.next;
             }
             return null;
         }
    }
}
