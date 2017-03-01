// count every edge
public class Solution {
    public int islandPerimeter(int[][] grid) {
        int res = 0;
        int[][] dir = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int width = grid[0].length;
        int height = grid.length;

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (grid[y][x] == 1)
                    for (int i = 0; i < 4; i++)
                        if ((y + dir[i][1]) < 0 || (y + dir[i][1]) > height - 1
                         || (x + dir[i][0]) < 0 || (x + dir[i][0]) > width - 1
                         || grid[y + dir[i][1]][x + dir[i][0]] == 0)
                            res++;
        return res;
    }
}

// remove the internal edge, only down and right
public class Solution {
    public int islandPerimeter(int[][] grid) {
        int island = 0, neighbour = 0;
        for (int y = 0; y < grid.length; y++)
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 1) {
                    island++;
                    if (y + 1 < grid.length && grid[y+1][x] == 1) neighbour++;
                    if (x + 1 < grid[y].length && grid[y][x+1] == 1) neighbour++;
                }
            }
        return island * 4 - neighbour * 2;
    }
}
