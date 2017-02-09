public class Solution {
    public boolean wordPattern(String pattern, String str) {
        String[] list = str.split(" ");
        if (list.length != pattern.length()) {
            return false;
        }
        for (int i = 0; i < pattern.length() - 1; i++ ) {
            for (int j = i + 1; j < pattern.length() ; j++) {
                if (pattern.charAt(i) == pattern.charAt(j)) {
                    if (!list[i].equals(list[j])) {
                        return false;
                    }
                }
                if (pattern.charAt(i) != pattern.charAt(j)) {
                    if (list[i].equals(list[j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
