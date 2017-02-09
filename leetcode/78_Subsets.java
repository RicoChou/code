// Iteratively O(n*2^n)
public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		result.add(new ArrayList<Integer>());

		for (int i : nums) {
			List<List<Integer>> temp = new ArrayList<>();
			for (List<Integer> elem : result) {
				List<Integer> clone = new ArrayList<>(elem);
				clone.add(i);
				temp.add(clone);
			}
			result.addAll(temp);
		}
		return result;
    }
}

// Bit Manipulation O(n*2^n)
public class Solution {
    public List<List<Integer>> subsets(int[] nums) {
		int totalAmount = 1 << nums.length;
		List<List<Integer>> result = new ArrayList<>(totalAmount);

		for (int i = 0; i < totalAmount; i++) {
			List<Integer> elem = new ArrayList<>();
			for (int j = 0; j < nums.length; j++) {
				if ((i & (1 << j)) != 0) {
					elem.add(nums[j]);
				}
			}
			result.add(elem);
		}
		return result;
    }
}
