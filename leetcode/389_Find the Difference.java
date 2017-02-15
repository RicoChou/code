public class Solution {
    public char findTheDifference(String s, String t) {
        char res = t.charAt(0);
        for (int i = 1; i < t.length(); i++)
            res ^= s.charAt(i - 1) ^ t.charAt(i);
        return res;
    }
}
