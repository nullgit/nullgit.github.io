# 51. N 皇后

n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。

示例 1：
输入：n = 4
输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
解释：如上图所示，4 皇后问题存在两个不同的解法。

示例 2：
输入：n = 1
输出：[["Q"]]

提示：
1 <= n <= 9
皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。

---

回溯法:对于每行遍历选取坐标,然后递归下一行.
时间复杂度：O(n!) 空间复杂度：O(n)  

---

```java
import java.util.*;

class Solution {
    int n = 0;
    List<List<Integer>> coords = new ArrayList<List<Integer>>();
    List<Integer> coord = new ArrayList<Integer>();
    List<List<String>> ans = new ArrayList<List<String>>();

    public List<List<String>> solveNQueens(int n) {
        this.n = n;
        backtrack(0);
        return ans;
    }
    
    public void backtrack(int i) {
        if (i == n) {  //终止条件是已经选择了n个坐标
            List<String> coordscopy = new ArrayList<String>();
            for (int k = 0; k < n; ++k) {
                char[] row = new char[n];
                Arrays.fill(row, '.');
                row[coords.get(k).get(1)] = 'Q';
                coordscopy.add(new String(row));
            }
            ans.add(coordscopy);
        }

        for (int j = 0; j < n; ++j) {
            coord = new ArrayList<Integer>();
            coord.add(i);
            coord.add(j);
            if (!clash()) {  //未冲突则加入当前坐标
                List<Integer> coordcopy = new ArrayList<Integer>(coord);
                coords.add(coordcopy);
                backtrack(i + 1);
                coords.remove(coords.size() - 1);  //恢复原来状态
            }
        }
    }

    //用于检查皇后之间是否冲突
    public boolean clash() {
        int i = coord.get(0);
        int j = coord.get(1);
        for (int k = 0; k < coords.size(); ++k) {
            int x = coords.get(k).get(0);
            int y = coords.get(k).get(1);
            if (i == x || j == y || i - j == x - y || i + j == x + y) {
                return true;
            }
        }
        return false;
    }
}
```
