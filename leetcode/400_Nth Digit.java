public class Solution {
    public int findNthDigit(int n) {
        int len = 1;
        // must be long, or it will exceed
        long count = 9;
        int realNum = 1;

        while (n > len * count) {
            n -= len * count;
            len += 1;
            count *= 10;
            realNum *= 10;
        }
        // here got the real num in the sequence
        realNum += (n - 1) / len;

        // pos is the position counted from rigth
        int pos = len - (n - 1) % len;
        while (pos-- > 1)
            realNum /= 10;
        return realNum % 10;
    }
}
