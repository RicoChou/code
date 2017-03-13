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
    List<String> list = new ArrayList<>();
    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) return list;
        dfs(root, "");
        return list;
    }

    void dfs(TreeNode root, String str) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            str = str + root.val;
            list.add(str);
            return;
        } else {
            str = str + root.val + "->";
        }
        dfs(root.left, str);
        dfs(root.right, str);
        return;
    }
}
