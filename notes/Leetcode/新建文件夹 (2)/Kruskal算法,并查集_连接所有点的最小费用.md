# 1584. 连接所有点的最小费用

给你一个points 数组，表示 2D 平面上的一些点，其中 points[i] = [xi, yi] 。
连接点 [xi, yi] 和点 [xj, yj] 的费用为它们之间的 曼哈顿距离 ：|xi - xj| + |yi - yj| ，其中 |val| 表示 val 的绝对值。
请你返回将所有点连接的最小总费用。只有任意两点之间 有且仅有 一条简单路径时，才认为所有点都已连接。

示例 1：
输入：points = `[[0,0],[2,2],[3,10],[5,2],[7,0]]`
输出：20

解释：
我们可以按照上图所示连接所有点得到最小总费用，总费用为 20 。
注意到任意两个点之间只有唯一一条路径互相到达。

示例 2：
输入：points = `[[3,12],[-2,5],[-4,1]]`
输出：18

示例 3：
输入：points = `[[0,0],[1,1],[1,0],[-1,1]]`
输出：4

示例 4：
输入：points = `[[-1000000,-1000000],[1000000,1000000]]`
输出：4000000

示例 5：
输入：points = `[[0,0]]`
输出：0

提示：
1 <= points.length <= 1000
-106 <= xi, yi <= 106
所有点 (xi, yi) 两两不同。

---

Kruskal算法+并查集：Kruskal算法是生成最小生成树的算法,简而言之就是每次都从图中选取权重最小的,含有原本与图不连通节点的边,直到所有的节点都连接.本题首先求出连接所有的节点的边和距离(权重),然后按权重进行排序,依次添加这些边,直到所有节点都连接.其中用并查集检查两个节点是否连通.
时间复杂度：O($n^2logn$) 空间复杂度：O($n^2$)  

---

```cpp
class UnionFind {
    vector<int> id;

public:
    UnionFind(int len) {
        id = vector<int>(len);
        for (int i = 0; i < len; ++i) {
            id[i] = i;
        }
    }

    int find(int c) {
        while (c != id[c]) {
            id[c] = id[id[c]];
            c = id[c];
        }
        return c;
    }

    bool connect(int x, int y) {
        int findx = find(x);
        int findy = find(y);
        if (findx == findy) {
            return true;
        }
        id[findx] = findy;
        return false;
    }
};

struct Edge {
    int d, x, y;
    Edge(int _d, int _x, int _y) {
        d = _d;
        x = _x;
        y = _y;
    }
};

class Solution {
public:
    int minCostConnectPoints(vector<vector<int>>& points) {
        // 求出所有的边
        vector<Edge> edges;
        for (int i = 0; i < points.size(); ++i) {
            for (int j = i + 1; j < points.size(); ++j) {
                Edge edge = Edge(distance(points, i, j), i, j);
                edges.push_back(edge);
            }
        }
        // 边按权重进行排序
        sort(edges.begin(), edges.end(), compare);

        UnionFind uf = UnionFind(points.size());
        int ans = 0;
        int num = 0;
        for (int i = 0; i < edges.size(); ++i) {
            // 每次选择权重最小且含有与原图不连接节点的边
            if (!uf.connect(edges[i].x, edges[i].y)) {
                ans += edges[i].d;
                ++num;
            }
            if (num == points.size() - 1) {
                break;
            }
        }
        return ans;
    }

    int distance(vector<vector<int>>& points, int x, int y) {
        return abs(points[x][0] - points[y][0]) + abs(points[x][1] - points[y][1]);
    }

    static bool compare(Edge& e1, Edge& e2) {
        return e1.d < e2.d;
    }
};
```
