// my solution
public class Solution {
    private int index = Integer.MAX_VALUE;
    private class Node {
        int num = 0;
        int pos = -1;
    }

    public int firstUniqChar(String s) {
        Node[] array = new Node[26];
        for (int j = 0; j < 26; j++) {
            array[j] = new Node();
        }
        for (int i = 0; i < s.length(); i++) {
            int bucket = s.charAt(i) - 'a';
            array[bucket].num++;
            array[bucket].pos = i;
        }
        for (int k = 0; k < 26; k++) {
            if (array[k].num == 1) {
                index = Math.min(index, array[k].pos);
            }
        }
        return index == Integer.MAX_VALUE ? -1 : index;
    }
}

// more concise solution, O(n)
public class Solution {
    public int firstUniqChar(String s) {
        int[] bucket = new int[26];
        int i;
        for (i = 0; i < s.length(); i++)
            bucket[s.charAt(i) - 'a']++;
        for (i = 0; i < s.length(); i++)
            if (bucket[s.charAt(i) - 'a'] == 1)
                return i;
        return -1;
    }
}

// other solution, O(n^2)
public class Solution {
    public int firstUniqChar(String s) {
        for (int i = 0; i < s.length(); i++)
            if (s.indexOf(s.charAt(i)) == s.lastIndexOf(s.charAt(i)))
                return i;
        return -1;
    }
}
