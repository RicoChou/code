// O(nlogn)
public class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length())
            return false;

        char[] notes = ransomNote.toCharArray();
        char[] magas = magazine.toCharArray();
        Arrays.sort(notes);
        Arrays.sort(magas);

        int j = 0;
        for (int i = 0; i < notes.length; i++, j++) {
            if (j == magas.length)
                return false;
            while (notes[i] != magas[j]) {
                if (j == magas.length || notes[i] < magas[j])
                    return false;
                if (notes[i] == magas[j])
                    break;
                j++;
                if (j == magas.length)
                    return false;
            }
        }

        return true;
    }
}

// O(n) bucket sort
public class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        char[] notes = ransomNote.toCharArray();
        char[] magas = magazine.toCharArray();
        int[] letters = new int[26];

        for (char c : magas)
            letters[c - 'a']++;

        for (char c : notes) 
            if (--letters[c - 'a'] < 0) return false;

        return true;
    }
}