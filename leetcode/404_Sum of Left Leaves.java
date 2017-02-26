/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

// not good, use global variable
public class Solution {
    private int res = 0;

    public int sumOfLeftLeaves(TreeNode root) {
        traverse(root);
        return res;
    }

    private void traverse(TreeNode root) {
        if (root == null) return;
        if (root.left != null)
            if (root.left.left == null && root.left.right == null)
                res += root.left.val;
        traverse(root.left);
        traverse(root.right);
    }
}

// good, use local variable
public class Solution {

    public int sumOfLeftLeaves(TreeNode root) {
        int res = 0;
        if (root == null) return 0;
        if (root.left != null)
            if (root.left.left == null && root.left.right == null)
                res += root.left.val;
            else
                res += sumOfLeftLeaves(root.left);
        res += sumOfLeftLeaves(root.right);

        return res;
    }
}

public class Solution {

    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) return 0;
        int res = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.empty()) {
            TreeNode node = stack.pop();
            if (node.left != null)
                if (node.left.left == null && node.left.right == null)
                    res += node.left.val;
                else
                    stack.push(node.left);
            if (node.right != null)
                stack.push(node.right);
        }
        return res;
    }
}
