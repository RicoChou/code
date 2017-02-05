public class Solution {
    public void sortColors(int[] nums) {
        int[] buckets = new int[3];
        int i, k, j = 0;
        for (i = 0; i < nums.length; i++)
            buckets[nums[i]]++;
        for (i = 0; i < 3; i++)
            for(k = 0;k < buckets[i]; k++)
                nums[j++] = i;
    }
}
