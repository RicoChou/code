public class Solution {
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] res = new int[findNums.length];

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int j = 0; j < findNums.length; j++) {
            if (map.get(findNums[j]) + 1 == nums.length)
                res[j] = -1;
            for (int index = map.get(findNums[j]) + 1; index < nums.length; index++) {
                if (nums[index] > findNums[j]) {
                    res[j] = nums[index];
                    break;
                }
                res[j] = -1;
            }
        }
        return res;
    }
}

// Suppose we have a decreasing sequence followed by a greater number
// For example [5, 4, 3, 2, 1, 6] then the greater number 6 is the next greater element for all previous numbers in the sequence
public class Solution {
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
         Map<Integer, Integer> map = new HashMap<>(); // map from x to next greater element of x
         Stack<Integer> stack = new Stack<>();
         for (int num : nums) {
             while (!stack.isEmpty() && stack.peek() < num)
                 map.put(stack.pop(), num);
             stack.push(num);
         }   
         for (int i = 0; i < findNums.length; i++)
             findNums[i] = map.getOrDefault(findNums[i], -1);
         return findNums;
     }
}