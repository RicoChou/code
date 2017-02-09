public class Solution {
    public int hammingDistance(int x, int y) {
        int value = x^y;
        int result = 0;
        while(value > 0){
            if ((value & 1) != 0) {
                result++;
            }
            value >>= 1;
        }
        return result;
    }
}
