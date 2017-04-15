public class Solution {
    public String reverseWords(String s) {
        StringBuilder stb = new StringBuilder();
        int begin = 0;
        for (int end = 0; end < s.length(); end++) {
            if (s.charAt(end) == ' ') {
                reverseWord(s, begin, end, stb);
                stb.append(' ');
                begin = end + 1;
            } else if (end == s.length() - 1) {
                reverseWord(s, begin, end + 1, stb);
            }
        }
        return stb.toString();
    }

    private void reverseWord(String s, int begin, int end, StringBuilder stb) {
        while (--end >= begin)
            stb.append(s.charAt(end));
    }
}

public class Solution {
    public String reverseWords(String s) {
         return Arrays.stream(s.split("\\s+"))
                      .map(StringBuilder::new)
                      .map(StringBuilder::reverse)
                      .collect(Collectors.joining(" "));
     }
}