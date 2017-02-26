// use stream
public class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = Arrays.stream(nums2).boxed().collect(Collectors.toSet());
        return Arrays.stream(nums1).distinct().filter(e-> set.contains(e)).toArray();
    }
}

// just use set
public class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> res = new HashSet<>();
        for (int num : nums1)
            set.add(num);
        for (int num : nums2)
            if (set.contains(num))
                res.add(num);

        int[] arr = new int[res.size()];
        int i = 0;
        for (int num : res)
            arr[i++] = num;
        return arr;
    }
}

// use binary search
public class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        Arrays.sort(nums2);
        for (int num : nums1)
            if (binarySearch(nums2, num))
                set.add(num);

        int[] arr = new int[set.size()];
        int i = 0;
        for (int num : set)
            arr[i++] = num;

        return arr;
    }

    private boolean binarySearch(int[] nums, int num) {
        int low = 0;
        int high = nums.length - 1;
        int mid;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (nums[mid] > num)
                high = mid - 1;
            else if (nums[mid] < num)
                low = mid + 1;
            else
                return true;
        }
        return false;
    }
}
