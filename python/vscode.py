from typing import List
import random
import time


class Solution:
    def maxAreaOfIsland(self, grid: List[List[int]]) -> int:
        ans = 0
        self.directions = [[1, 0], [0, -1], [-1, 0], [0, 1]]
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] == 1:
                    self.currArea = 0
                    self.dfs(grid, i, j)
                    ans = max(self.currArea, ans)
        return ans

    def dfs(self, grid: List[List[int]], i: int, j: int):
        if i >= 0 and i < len(grid) and j >= 0 and j < len(grid[0]) and grid[i][j] == 1:
            self.currArea += 1
            grid[i][j] = 0
            for direction in self.directions:
                self.dfs(grid, i + direction[0], j + direction[1])
