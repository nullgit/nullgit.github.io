输入:
1. 导包`import java.util.Scanner;`
2. 实例化`Scanner scanner = new Scanner(System.in);`
3. 调用相关方法`int num = scanner.nextint();`

随机数 Math.random() [0.0, 1.0)

# System

currentTimeMillis() 返回当前时间距离1970-01-01的毫秒数
out.println(Object x) 输出对象的地址值
out.println(char[] x) 输出char数组的全部值

# String

charAt(int index)
equals(String s) 判断字符串是否相等

# Arrays

boolean equals(int[] a,int[] b) 判断两个数组是否相等String toString(int[] a) 输出数组信息。
void fill(int[] a,int val) 将指定值填充到数组之中。
void sort(int[] a) 对数组进行排序。
int binarySearch(int[] a,int key) 对排序后的数组进行二分法检索指定的值。
