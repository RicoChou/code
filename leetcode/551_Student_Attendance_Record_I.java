public class Solution {
    public boolean checkRecord(String s) {
        int countA = 0;
        int countL = 0;
        for (char c : s.toCharArray()) {
            if (c == 'L') {
                countL++;
                if (countL > 2) return false;
            } else {
                if (c == 'A') countA++;
                if (countA > 1) return false;
                countL = 0;
            }
        }
        return true;
    }

    public boolean checkRecord2(String s) {
        return !s.matches(".*(A.*A|LLL).*");
    }

    public boolean checkRecord3(String s) {
        return !s.matches(".*LLL.*|.*A.*A.*");
    }
}

