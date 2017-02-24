// Fermat Theory, not AC
public class Solution {
    public int countPrimes(int n) {
        int count = 0;
        for (int i = 2; i < n; i++)
            if (Math.pow((i - 1), (i - 1)) % i == 1) count++;
        return count;
    }
}