/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// For every node, length of longest path which pass it = maxHeight of its left subtree + maxHeight of its right subtree.
public class Solution {
    private int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        maxHeight(root);
        return max;
    }

    private int maxHeight(TreeNode root) {
        if (root == null) return 0;

        int left = maxHeight(root.left);
        int right = maxHeight(root.right);

        max = Math.max(max, left + right);

        return Math.max(left, right) + 1;
    }
}