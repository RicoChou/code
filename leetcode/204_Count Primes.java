public class Solution {
    public int countPrimes(int n) {
        int count = 0;
        int[] array = new int[n];
        for (int i = 2; i * i < n; i++)
            if (array[i] == 0)
                for (int j = i * i; j < n; j += i)
                    array[j] = 1;

        for (int i = 2; i < n; i++)
            if (array[i] == 0) count++;
        return count;
    }
}
