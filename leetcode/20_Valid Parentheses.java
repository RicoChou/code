public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                case '[':
                case '{': stack.push(s.charAt(i));break;
                case ')': if (stack.isEmpty() || '(' != stack.pop()) return false;break;
                case ']': if (stack.isEmpty() || '[' != stack.pop()) return false;break;
                case '}': if (stack.isEmpty() || '{' != stack.pop()) return false;break;
            }
        }
        return stack.isEmpty();
    }
}

// more elegant
public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray())
            switch (c) {
                case '(': stack.push(')'); break;
                case '[': stack.push(']'); break;
                case '{': stack.push('}'); break;
                case ')':
                case ']':
                case '}': if (stack.isEmpty() || c != stack.pop()) return false; break;
            }
        return stack.isEmpty();
    }
}
