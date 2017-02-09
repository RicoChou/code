public class Solution {
    char[][] keyboard = {{'q','w','e','r','t','y','u','i','o','p'}, {'a','s','d','f','g','h','j','k','l'}, {'z','x','c','v','b','n','m'}};
    public String[] findWords(String[] words) {
        List<String> result = new ArrayList<>();
        for (String item : words) {
            char[] line = getFirstLetterInWhichLine(item.charAt(0));
            if(itemIsInLine(item, line)){
                result.add(item);
            }
        }
        return (String[])result.toArray(new String[result.size()]);
    }

    private char[] getFirstLetterInWhichLine(char c){
        for(char[] chars : keyboard){
            for (char x : chars) {
                if (c == x || c == x - 32) {
                    return chars;
                }
            }
        }
        return null;
    }

    private boolean itemIsInLine(String str, char[] chars){
        for (char c : str.toCharArray()) {
            for (char test : chars) {
                if (c == test || c == test - 32) {
                    break;
                }
                if (test == chars[chars.length - 1])
                return false;
            }
        }
        return true;
    }
}
