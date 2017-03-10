public class Solution {
    public int findComplement(int num) {
        int x = 0;
        int n = num;
        while (num > 0) {
            num >>= 1;
            x = ( x << 1 ) + 1;
        }
        return n ^ x;
    }
}
