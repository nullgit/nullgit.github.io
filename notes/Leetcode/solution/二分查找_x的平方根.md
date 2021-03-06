# 69. x 的平方根

实现 int sqrt(int x) 函数.计算并返回 x 的平方根，其中 x 是非负整数。由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。

示例 1:  
输入: 4  
输出: 2  

示例 2:  
输入: 8  
输出: 2  
说明: 8 的平方根是 2.82842...,由于返回类型是整数，小数部分将被舍去。

---

二分法：每次取半，判断mid的平方与x的大小。  
时间复杂度：O(log(n)) 空间复杂度：O(1)  

---

C++  

```cpp
class Solution {
public:
    int mySqrt(int x) {
        int l = 1, r = x;
        int mid;
        while (r >= l) {
            mid = l + (r - l) / 2;
            long long res = mid * mid;
            if ((long long)(mid) * mid <= x) {
                l = mid + 1;
            }
            else {
                r = mid - 1;
            }
        }
        return r;
    }
};
```

或下面的的方法：

```cpp
class Solution {
public:
    int mySqrt(int x) {
        int l = 1, r = x;
        int mid;
        while (r >= l) {
            mid = l + (r - l) / 2;
            int xDivideMid = x / mid;
            if (mid < xDivideMid) {
                l = mid + 1;
            }
            else if (mid > xDivideMid) {
                r = mid - 1;
            }
            else {
                return mid;
            }
        }
        return r;
    }
};
```

---

牛顿法:本题可以看作是求$f(x)=x^2-a$的零点,牛顿法求零点的迭代公式为$x_{n+1}=x_n-f(x_n)/f^{'}(x_n)$,所以本题的迭代公式为`x = (x + a / x) / 2`  
时间复杂度：O(log(n)) 空间复杂度：O(1)

---

```cpp
class Solution {
public:
    int mySqrt(int a) {
        if (a == 0) {
            return 0;
        }

        double x = a;
        while (true) {
            x = (x + a / x) / 2;
            if (abs(x * x - a) < 1) {
                break;
            }
        }
        return int(x);
    }
};
```