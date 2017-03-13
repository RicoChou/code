/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// time complexity is O(n), but string concatenation (+) is costly
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

// more elegant one
public class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> answer = new ArrayList<String>();
        if (root != null) searchBT(root, "", answer);
        return answer;
    }
    private void searchBT(TreeNode root, String path, List<String> answer) {
        if (root.left == null && root.right == null) answer.add(path + root.val);
        if (root.left != null) searchBT(root.left, path + root.val + "->", answer);
        if (root.right != null) searchBT(root.right, path + root.val + "->", answer);
    }
}


/**
 * The time complexity for the problem should be O(n),
 * since we are basically visiting each node in the tree.
 * Yet an interviewer might ask you for further optimization
 * when he or she saw a string concatenation.
 * A string concatenation is just too costly.
 *
 * A StringBuilder can be used although a bit tricky since it is not immutable like string is.
 * When using StringBuilder, We can just keep track of the length
 * of the StringBuilder before we append anything to it before recursion
 * and afterwards set the length back. Another trick is when to append the "->",
 * since we don't need the last arrow at the end of the string,
 * we only append it before recurse to the next level of the tree. Hope the solution helps!
 */
 public class Solution {
     public List<String> binaryTreePaths(TreeNode root) {
         List<String> list = new ArrayList<>();
         StringBuilder sb = new StringBuilder();
         if (root != null) traversalBT(list, root, new StringBuilder());
         return list;
     }

     private void traversalBT(List<String> list, TreeNode root, StringBuilder sb) {
         int len = sb.length();
         if (root.left == null && root.right == null) list.add(sb.append(root.val).toString());
         sb.append(root.val).append("->");
         if (root.left != null) traversalBT(list, root.left, sb);
         if (root.right != null) traversalBT(list, root.right, sb);
         sb.setLength(len);
     }
 }
