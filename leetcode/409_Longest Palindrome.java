public class Solution {
    public int longestPalindrome(String s) {
        int[] bucket = new int[52];
        int len = 0;
        int addOne = 0;

        for (char c : s.toCharArray()) {
            if (c >= 'a' && c <= 'z') bucket[c - 'a' + 26]++;
            if (c >= 'A' && c <= 'Z') bucket[c - 'A']++;
        }

        for (int i : bucket) {
            if ((i & 1) == 1) addOne = 1;
            len += i & (Integer.MAX_VALUE - 1);
        }
        return len + addOne;
    }
}