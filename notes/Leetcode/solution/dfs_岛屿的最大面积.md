# 695. 岛屿的最大面积

给定一个包含了一些 0 和 1 的非空二维数组 grid 。
一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 0 。)

示例 1:
`[[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]`
对于上面这个给定矩阵应返回 6。注意答案不应该是 11 ，因为岛屿只能包含水平或垂直的四个方向的 1 。

示例 2:
`[[0,0,0,0,0,0,0,0]]`
对于上面这个给定的矩阵, 返回 0。

---

深度优先搜索法：下面分别是用栈实现和用递归实现。
时间复杂度：O(m\*n) 空间复杂度：O(m\*n)  

---

```py
class Solution:
    def maxAreaOfIsland(self, grid: List[List[int]]) -> int:
        ans = 0
        # 用栈实现
        stack = []
        # 使用了一个数组储存方向，每个元素分别是上下左右四个方向之一
        directions = [[1, 0],[0, -1],[-1, 0],[0, 1]]
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                # 每当找到一个岛屿，通过深度优先搜索遍历这个岛屿
                if grid[i][j] == 1:
                    stack.append([i, j])
                tmparea = 0
                while not len(stack) == 0:
                    index = stack.pop()
                    if grid[index[0]][index[1]] == 0:
                        continue
                    # 每遍历到一块陆地，当前面积加1，并将它清为0
                    grid[index[0]][index[1]] = 0
                    tmparea += 1
                    # 上下左右四个方向搜索
                    for direction in directions:
                        around = [direction[k] + index[k] for k in range(2)]
                        if around[0] >= 0 and around[0] < len(grid) \
                        and around[1] >= 0 and around[1] < len(grid[0]) \
                        and grid[around[0]][around[1]] == 1:
                            stack.append(around)
                ans = max(ans, tmparea)
        return ans
```

---

```py
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

    # 用递归实现
    def dfs(self, grid: List[List[int]], i: int, j: int):
        # 这里的实现是先进入递归函数，再判断是否符合边界的条件
        if i >= 0 and i < len(grid) and j >= 0 and j < len(grid[0]) and grid[i][j] == 1:
            self.currArea += 1
            grid[i][j] = 0
            for direction in self.directions:
                self.dfs(grid, i + direction[0], j + direction[1])
```
