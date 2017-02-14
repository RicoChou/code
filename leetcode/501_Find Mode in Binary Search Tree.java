/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
public class Solution {

    private int currVal;
    private int count = 0;
    private int maxCount = 0;
    private int modeCount = 0;
    private int[] array;

    public int[] findMode(TreeNode root) {
        traverse(root);
        array = new int[modeCount];
        modeCount = 0;
        count = 0;
        traverse(root);
        return array;
    }

    private void traverse(TreeNode node) {
        if (node == null) return;
        traverse(node.left);
        countVal(node.val);
        traverse(node.right);
    }

    private void countVal(int val) {
        if (val != currVal) {
            currVal = val;
            count = 0;
        }
        count++;
        if (count > maxCount) {
            maxCount = count;
            modeCount = 1;
        } else if (count == maxCount) {
            if (array != null) {
                array[modeCount] = currVal;
            }
            modeCount++;
        }
    }
}
