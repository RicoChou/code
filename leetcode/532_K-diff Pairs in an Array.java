public class Solution {
    public int findPairs(int[] nums, int k) {
        int count = 0;
        boolean flag = true;
        if (k < 0) return 0;
        if (k == 0) {
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i+1] == nums[i]) {
                    if (flag) {
                        count++;
                        flag = false;
                    }
                } else {
                    flag = true;
                }
            }
            return count;
        }
        Set<Integer> set = new HashSet(nums.length);
        for (int i : nums) set.add(i);
        for (int num : set) {
            if (set.contains(num + k)) count++;
            if (set.contains(num - k)) count++;
        }

        return count / 2;
    }

}