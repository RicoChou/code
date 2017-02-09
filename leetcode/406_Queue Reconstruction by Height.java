public class Solution {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>{
            public int compare(int[] a, int[]b){
                return a[0] == b[0] ? a[1] - b[1] : b[0] - a[0];
            }
        });

        List<int[]> res = new LinkedList<>();
        for (int[] item : people) {
            res.add(item[1], item);
        }
        return res.toArray();
    }
}
