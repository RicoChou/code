public class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 1) return strs[0];
        String prefix = getPrefix(strs[0], strs[1]);
        for (int i = 2; i < strs.length; i++) {
            prefix = getPrefix(prefix, strs[i]);
        }
        return prefix;
    }

    private String getPrefix(String a, String b) {
        int length = a.length() < b.length() ? a.length() : b.length();
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (a.charAt(i) == b.charAt(i))
                prefix.append(a.charAt(i));
            else
                return prefix.toString();
        }

        return prefix.toString();
    }

    private String getPrefix2(String left,String right) {
        int min = Math.min(left.length(), right.length());
        for (int i = 0; i < min; i++) {
            if ( left.charAt(i) != right.charAt(i) )
                return left.substring(0, i);
        }
        return left.substring(0, min);
    }
}

// Horizontal scanning
public class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        return prefix;
    }
}

// Vertical scanning
public class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        for (int i = 0; i < strs[0].length() ; i++){
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j ++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
    }
}
