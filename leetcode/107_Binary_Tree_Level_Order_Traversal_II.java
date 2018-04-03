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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        for (int i = 0; i < getTreeDepth(root); i++) {
          list.add(new ArrayList<Integer>());
        }
        addVal(list, root, 0);
        return list;
    }

    public void addVal(List<List<Integer>> list, TreeNode node, int depth) {
      if (node == null) {
        return;
      }
      list.get(list.size() - depth - 1).add(node.val);
      addVal(list, node.left, depth + 1);
      addVal(list, node.right, depth + 1);
    }

    public int getTreeDepth(TreeNode node) {
      if (node == null) {
        return 0;
      }
      int leftDepth  = getTreeDepth(node.left);
      int rightDepth = getTreeDepth(node.right);
      int depth = leftDepth > rightDepth ? leftDepth + 1: rightDepth + 1;
      return depth;
    }
}

/******************************************************************************/
