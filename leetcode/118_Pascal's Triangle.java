public class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        List<Integer> next;
        if (numRows == 0) return res;

        for (int i = 1; i <= numRows; i++) {
            next = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                if (j == 0 || j == i - 1) next.add(1);
                else next.add(curr.get(j) + curr.get(j - 1));
            }
            res.add(next);
            curr = next;
        }

        return res;
    }
}
