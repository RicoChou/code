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
        }

        return count;
    }

}

public class Solution {
    public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0)   return 0;
        
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (k == 0) {
                //count how many elements in the array that appear more than twice.
                if (entry.getValue() >= 2) {
                    count++;
                } 
            } else {
                if (map.containsKey(entry.getKey() + k)) {
                    count++;
                }
            }
        }
        
        return count;
    }
}