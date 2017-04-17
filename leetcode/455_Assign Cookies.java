public class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        int j = s.length - 1;
        for (int i = g.length - 1; i >= 0; i--) {
            if (j < 0) {
                return count;
            }
            if (g[i] > s[j]) {
                continue;
            } else {
                count++;
                j--;
            }
        }
        return count;
    }
}

// more simple one
public class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0;
        for(int j=0;i<g.length && j<s.length;j++) {
            if(g[i]<=s[j]) i++;
        }
        return i;
    }
}