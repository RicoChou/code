public class Solution {
    public boolean checkPerfectNumber(int num) {
        if (num == 1) return false;
        int sum = 0;
        for (int x = 2; x * x <= num; x++) {
            if (num % x == 0) {
                sum += x + num / x;
            }
        }

        sum++;
        return sum == num;
    }
}