public class Solution {
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int result = 0;
        int index, dist1, dist2;

        for (int house : houses) {
            index = Arrays.binarySearch(heaters, house);
            if (index < 0) {
                index = ~index;
                dist1 = index >= 1 ? house - heaters[index - 1] : Integer.MAX_VALUE;
                dist2 = index < heaters.length ? heaters[index] - house : Integer.MAX_VALUE;
                result = Math.max(result, Math.min(dist1, dist2));
            }
        }

        return result;
    }

}
