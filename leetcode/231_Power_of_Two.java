public class Solution {
    public boolean isPowerOfTwo(int n) {
        if (n <= 0)
            return false;
        return (n&(n-1)) == 0;
    }
}

public class Solution {
    public boolean isPowerOfTwo(int n) {
        if (n <= 0)
            return false;
        // 1073741824 = 2^30
        return 1073741824 % n == 0;
    }
}
