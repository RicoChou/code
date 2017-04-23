public class Solution {
    public String[] findRelativeRanks(int[] nums) {
        String[] medals = {"Gold Medal", "Silver Medal", "Bronze Medal"};
        List<Pair> list = new ArrayList<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            list.add(new Pair(nums[i], i));
        }
        Collections.sort(list, (o1, o2) -> {
            if (o1.score > o2.score)
                return -1;
            else if (o1.score < o2.score)
                return 1;
            return 0;
        });
        String[] res = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i < 3)
                res[list.get(i).postion] = medals[i];
            else
                res[list.get(i).postion] = String.valueOf(i + 1);
        }
        return res;
    }

    class Pair {
        public int score;
        public int postion;
        Pair(int score, int postion) {
            this.score = score;
            this.postion = postion;
        }
    }
}

public class Solution {
    public String[] findRelativeRanks(int[] nums) {
        String[] medals = {"Gold Medal", "Silver Medal", "Bronze Medal"};
        String[] res = new String[nums.length];
        int[][] pairs = new int[nums.length][2];

        for (int i = 0; i < nums.length; i++) {
            pairs[i][0] = nums[i];
            pairs[i][1] = i;
        }
        Arrays.sort(pairs, (o1, o2) -> (o2[0] - o1[0]));

        for (int i = 0; i < nums.length; i++) {
            if (i < 3)
                res[pairs[i][1]] = medals[i];
            else
                res[pairs[i][1]] = String.valueOf(i + 1);
        }
        return res;
    }
}