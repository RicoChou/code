public class Solution {
    public String reverseVowels(String s) {
        char[] chars = s.toCharArray();
        int[] records = new int[chars.length];
        int count = 0;

        for (int i = 0; i < chars.length; i++)
            if (chars[i] == 'a' || chars[i] == 'e' || chars[i] == 'i' || chars[i] == 'o' || chars[i] == 'u' ||
                chars[i] == 'A' || chars[i] == 'E' || chars[i] == 'I' || chars[i] == 'O' || chars[i] == 'U')
                records[count++] = i;

        for (int j = count - 1; j >= 0; j--)
            chars[records[j]] = s.charAt(records[count - 1 - j]);
        return new String(chars);
    }
}

public class Solution {
    public String reverseVowels(String s) {
        if(s == null || s.length()==0) return s;
        String vowels = "aeiouAEIOU";
        char[] chars = s.toCharArray();
        int start = 0;
        int end = s.length()-1;
        while(start<end){
            
            while(start<end && !vowels.contains(chars[start]+"")){
                start++;
            }
            
            while(start<end && !vowels.contains(chars[end]+"")){
                end--;
            }
            
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            
            start++;
            end--;
        }
        return new String(chars);
}