public class Solution {
    public int  thirdMax(int[] nums) {
        Integer x1 = null;
        Integer x2 = null;
        Integer x3 = null;
        for (Integer num : nums) {
            if (num.equals(x1) || num.equals(x2) || num.equals(x3)) {
                continue;
            }
            if (x1 == null || num > x1) {
                x3 = x2;
                x2 = x1;
                x1 = num;
            } else if (x2 == null || num > x2) {
                x3 = x2;
                x2 = num;
            } else if (x3 == null || num > x3) {
                x3 = num;
            }
        }
        return x3 == null ? x1 : x3;
    }
}
