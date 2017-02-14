// my solution
public class Solution {
    public String convertToBase7(int num) {
        StringBuilder stb = new StringBuilder();
        if (num < 0) {
            stb.append('-');
            num *= -1;
        }
        stb.append(getChar(num, stb));
        return stb.toString();
    }

    private char getChar(int num, StringBuilder stb){
        if (num < 7) {
            return (char) (num + 48);
        } else {
            stb.append(getChar(num / 7, stb));
            return (char) (num % 7 + 48);
        }
    }
}

// use Java api
public class Solution {
    public String convertToBase7(int num) {
        return Integer.toString(num, 7);
    }
}

// use Java api implementation
public class Solution {
    char[] digits = {'0','1','2','3','4','5','6'};

    public String convertToBase7(int num) {
        // the length of Integer.MAX_VALUE(binary) is 31, so the max length of num(base 7) is ceil(31/3) = 11, then plus '-'
        char[] chars = new char[12];
        int charPos = 11;
        boolean negative = num < 0;
        if (negative) {
            num = -num;
        }
        while(num >= 7) {
            chars[charPos--] = digits[num % 7];
            num /= 7;
        }
        chars[charPos] = num;
        if (negative) {
            chars[--charPos] = '-';
        }
        return new String(chars, charPos, (12 - charPos));
    }
}
