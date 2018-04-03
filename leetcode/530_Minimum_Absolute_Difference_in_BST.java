/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// my solution
public class Solution {
    public int getMinimumDifference(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inOrderTraversal(root, list);
        int minDiff = Math.abs(list.get(0) - list.get(1)), temp;
        for (int i = 1; i < list.size() - 1; i++) {
            temp = Math.abs(list.get(i) - list.get(i + 1));
            if (minDiff > temp) minDiff = temp;
        }
        return minDiff;
    }

    private void inOrderTraversal(TreeNode node, List<Integer> list) {
        if (node == null) return;
        inOrderTraversal(node.left, list);
        list.add(node.val);
        inOrderTraversal(node.right, list);
    }
}

// use global variable
public class Solution {
    private int min = Integer.MAX_VALUE;
    private Integer prev = null;

    public int getMinimumDifference(TreeNode root) {
        if (root == null) return min;
        getMinimumDifference(root.left);
        if (prev != null) {
            min = Math.min(min, Math.abs(root.val - prev));
        }
        prev = root.val;
        getMinimumDifference(root.right);
        return min;
    }
}
