/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    private int tiltSum = 0;

    public int findTilt(TreeNode root) {
        DFS(root);
        return tiltSum;
    }

    public int DFS(TreeNode node) {
        if (node == null) return 0;

        int sumL = DFS(node.left);
        int sumR = DFS(node.right);

        int tilt = Math.abs(sumL - sumR);

        tiltSum += tilt;

        return node.val + sumL + sumR;
    }
}