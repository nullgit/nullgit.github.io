# 417. 太平洋大西洋水流问题

给定一个 m x n 的非负整数矩阵来表示一片大陆上各个单元格的高度。“太平洋”处于大陆的左边界和上边界，而“大西洋”处于大陆的右边界和下边界。
规定水流只能按照上、下、左、右四个方向流动，且只能从高到低或者在同等高度上流动。
请找出那些水流既可以流动到“太平洋”，又能流动到“大西洋”的陆地单元的坐标。

提示：
输出坐标的顺序不重要
m 和 n 都小于150

示例：
给定下面的 5x5 矩阵:

```md
  太平洋 ~   ~   ~   ~   ~ 
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * 大西洋
```

返回:

`[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]]` (上图中带括号的单元).

---

深度优先搜索法：虽然题目要求的是满足向下流能到达两个大洋的位置，如果我们对所有的位置进行搜索，那么在不剪枝的情况下复杂度会很高。因此我们可以反过来想，从两个大洋开始向上流，这样我们只需要对矩形四条边进行搜索。搜索完成后，只需遍历一遍矩阵，满足条件的位置即为两个大洋向上流都能到达的位置。
时间复杂度：O(n) 空间复杂度：O(n)  

---

```java
class Solution {
    int[] directions = { 1, 0, -1, 0, 1 };

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        if (matrix.length == 0 || matrix[0].length == 0) {
            return ans;
        }
        int[][] reached1 = new int[matrix.length][matrix[0].length];
        int[][] reached2 = new int[matrix.length][matrix[0].length];
        // 对上下左右四条边进行搜索
        for (int i = 0; i < matrix[0].length; ++i) {
            if (reached1[0][i] == 0) {
                reached1[0][i] = 1;
                dfs(matrix, 0, i, reached1);
            }
            if (reached2[matrix.length - 1][i] == 0) {
                reached2[matrix.length - 1][i] = 1;
                dfs(matrix, matrix.length - 1, i, reached2);
            }
        }
        for (int i = 0; i < matrix.length; ++i) {
            if (reached1[i][0] == 0) {
                reached1[i][0] = 1;
                dfs(matrix, i, 0, reached1);
            }
            if (reached2[i][matrix[0].length - 1] == 0) {
                reached2[i][matrix[0].length - 1] = 1;
                dfs(matrix, i, matrix[0].length - 1, reached2);
            }
        }
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                if (reached1[i][j] == 1 && reached2[i][j] == 1) {
                    List<Integer> index = new ArrayList<Integer>();
                    index.add(i);
                    index.add(j);
                    ans.add(index);
                }
            }
        }
        return ans;
    }

    public void dfs(int[][] matrix, int i, int j, int[][] reached) {
        for (int k = 0; k < 4; ++k) {
            int curi = i + directions[k];
            int curj = j + directions[k + 1];
            // 如果相邻位置可到达,且未被确定为搜索过,且比当前位置高,则继续搜索
            if (canReached(matrix, curi, curj) && reached[curi][curj] == 0 
            && matrix[curi][curj] >= matrix[i][j]) {
                reached[curi][curj] = 1;
                dfs(matrix, curi, curj, reached);
            }
        }
    }

    public boolean canReached(int[][] matrix, int i, int j) {
        return i < matrix.length && i >= 0 && j < matrix[0].length && j >= 0;
    }
}
```
