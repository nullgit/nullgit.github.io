# java note1 基础

## java语言概述

### 历史

- 是SUN(Stanford University Network，斯坦福大学网络公司 ) 1995年推出的一门高级编程语言。
- 重要的版本变更:2014年，发布JDK8.0，是继JDK5.0以来变化最大的版本

### 术语

- Java SE(Java Standard Edition)标准版
- Java EE(Java Enterprise Edition)企业版
- Java ME(Java Micro Edition)小型版
- JVM (Java Virtual Machine)Java虚拟机
- GC(Garbage Collection)垃圾收集机制
- JDK(Java Development Kit)Java开发工具包
- JRE(Java Runtime Environment)Java运行环境
- JDK = JRE + 开发工具集（例如Javac编译工具等）
- JRE = JVM + Java SE标准类库

应用:

- 企业级应用,Android平台应用,大数据平台开发

### 特性

- 面向对象:两个基本概念：类、对象,三大特性：封装、继承、多态
- 健壮性:吸收了C/C++语言的优点，但去掉了其影响程序健壮性的部分（如指针、内存的申请与释放等）,提供了一个相对安全的内存管理和访问机制
- 跨平台性:JVM实现一次编写,到处运行

### 步骤

- 编写:1. 将Java代码编写到扩展名为.java的文件中。
- 编译:编译通过javac命令对该java文件进行编译,生成class文件。如`javac HelloWorld.java`
- 运行:通过java命令对生成的class文件进行运行。如`java HelloWorld`

### 第一个HelloWorld程序

```java
//HelloWorld.java源文件
public class HelloWorld{
    public static void main(String[] args) {
        System.out.println(“Hello World!”);
    }
}
```

注意:

- 在一个Java源文件中可以声明多个class,但最多只有一个类声明为public的,且该类的类名就是源文件名.
- 程序的入口就是main方法,格式是固定的,即`public static void main(String[] args)`
- 编译后会生成一个或多个class文件,运行的时候运行与源文件名相同的.
- 输出语句`System.out.println()`和`System.out.print()`
- 单行注释:`//` 多行注释`/* */` 文档注释`/** */`(可以被javadoc解析,有@Description,@author,@date,@param,@return@version等参数)

## 变量和运算符

### 关键字和保留字

- 关键字:被Java语言赋予了特殊含义，用做专门用途的字符串（单词）关键字中所有字母都为小写
- 保留字:现有Java版本尚未使用，但以后版本可能会作为关键字使用。

### 标识符

- 标识符：对各种变量、方法和类等要素命名时使用的字符序列

合法标识符规则：

- 由26个英文字母大小写，0-9 ，_或 $ 组成
- 数字不可以开头。
- 不可以使用关键字和保留字，但能包含关键字和保留字。
- Java中严格区分大小写，长度无限制。
- 标识符不能包含空格。

Java中的名称命名规范：

- 包名：多单词组成时所有字母都小写：xxxyyyzzz
- 类名、接口名：多单词组成时，所有单词的首字母大写：XxxYyyZzz
- 变量名、方法名：多单词组成时，第一个单词首字母小写，第二个单词开始每个单词首字母大写：xxxYyyZzz
- 常量名：所有字母都大写。多单词时每个单词用下划线连接：XXX_YYY_ZZZ

### 变量

- 变量是程序中最基本的存储单元。包含变量类型、变量名和存储的值

注意:

- Java中每个变量必须先声明，后使用
- 变量的作用域：其定义所在的一对{ }内,变量只有在其作用域内才有效
- 同一个作用域内，不能定义重名的变量
- 声明变量,如`int var;`
- 变量赋值,如`var = 10;`
- 声明和赋值变量,如`int var = 10;`

分类:

- 基本数据类型:
  - 数值型
    - 整数类型:byte(1字节),short(2字节),int(4字节),long(8字节)
    - 浮点类型:float(4字节),double(8字节)
  - 字符型:char(2字节)
  - 布尔类型:boolean
- 引用数据类型
  - 类
  - 接口
  - 数组

### 数值型

- 声明long型常量须后加‘l’或‘L’
- Java程序中变量通常声明为int型，除非不足以表示较大的数，才使用long
- 声明float型常量，须后加‘f’或‘F’。
- Java 的浮点型常量默认为double型

### 字符类型

- Java中的所有字符都使用Unicode编码，故一个字符可以存储一个字母，一个汉字，或其他书面语的一个字符。
- 字符型变量的三种表现形式：
  - 字符常量是用单引号(‘ ’)括起来的单个字符,如：`char ch = 'a'`
  - Java中还允许使用转义字符‘\’来将其后的字符转变为特殊字符型常量。`char ch = '\n'`
  - 直接使用 Unicode 值来表示字符型常量：'\uXXXX'。其中，XXXX代表一个十六进制整数。如：`\u000a` 表示 `\n`。
- 了解:ASCII码一共规定了128个字符的编码,Unicode将世界上所有的符号都纳入其中,UTF-8 是在互联网上使用最广的一种 Unicode 的实现方式。

### 布尔类型

- 只有true,false两个值,用于条件判断

### 类型转换

自动类型转换:

- 数的范围只能从小到大转换:byte,char,short->int->long->float->long
- byte,char,short之间不能相互转化,只能转化为int
- boolean类型不能与其他数据类型运算
- 整型常量默认为int,浮点常量默认为double型

强制类型转换:

- 使用强转符()截断操作,可能有精度损失

### 字符串类型String(类)

- 定义:String str = "abc"
- 当把任何基本数据类型的值和字符串(String)进行连接运算时(+)，基本数据类型的值将自动转化为字符串(String)类型。

### 进制

对于整数，有四种表示方式：

- 二进制(binary)：0,1,以0b或0B开头
- 十进制(decimal)：0-9
- 八进制(octal)：0-7,以数字0开头表示
- 十六进制(hex)：0-9及A-F,以0x或0X开头表示

## 运算符

### 算数运算符

`+ - * / % ++ --`
注意:

- /返回的结果是int型,除非其中一个运算数是double
- mod的正负号与被模数相同
- 自增或自减不改变数据类型

### 赋值运算符

`= += -= *= /= %=`

- 同样不会改变数据类型

### 比较运算符

`== != < > <= >= instanceof`

- 结果是布尔类型
- instanceof用于判断一个对象是否为一个类的实例,如`boolean result = obj instanceof Class`

### 逻辑运算符

`& && | || ! ^`

- &&和||会短路检测,&和|不会

### 位运算符

`<< >> >>> & | ^ ~`

- num1 ^ num2 ^ num2 = num1

### 三元运算符

`<条件> ? <返回值1> : <返回值2>`

- 可以改写为if-else

### 运算符的优先级

- 用()即可

## 流程控制

### 顺序结构

从上到下进行

### 条件结构

- if-else

```java
if(<条件表达式>) {

}
else if {

}
...
else {

}
```

- switch-case

```java
switch(<表达式>) {
  case 常量1: ... break;
  case 常量2: ... break;
  ...
  default: ... break;
}
```

- 表达式中是byte,short,int,char,枚举类型,字符串

### 循环结构

- for

```java
for (<初始化>; <循环条件>; <迭代条件>) {
  <循环体>
}
```

- while

```java
<初始化>
while (<循环条件>) {
  <循环体>
  <迭代条件>
}
```

- do-while

```java
<初始化>
do {
  <循环体>
  <迭代条件>
} while (循环条件)
```

特殊关键字：break(跳出当前循环),continue(跳出当前循环次数)

- 这些关键字后面不能声明语句
- 在循环前可以添加标签,如`label:`,用break <标签>(或continue <标签>)可以跳到添加标签的循环

### 输入

从键盘获取不同类型的数值

1. 导包`import java.util.Scanner;`
2. 实例化`Scanner scanner = new Scanner(System.in);`
3. 调用相关方法`int num = scanner.nextint();`

- 其他方法:next(接收字符串) nextDouble(接受浮点数) nextBoolean(接受布尔型true或false)
- 输入数据类型与要求的类型不匹配时,报InputMismatchException异常

## 数组

- 数组(Array)，是多个相同类型数据按一定顺序排列的集合，并使用一个名字命名，并通过编号的方式对这些数据进行统一管理。

### 一维数组

声明:`type[] var`

初始化:

- 静态初始化(初始化与赋值同时进行):`new int[]{1001, 1002, 1003, 1004};`
- 动态初始化(初始化与赋值分开进行):`new int[4]`
- 初始化后,长度就确定了
- 使用`var1 = var2`指向的是用一个地址
- 类型判断:`int arr[] = {1,2,3,4}`
- 默认初始化值:
  - 整型:0
  - 浮点型:0.0
  - 字符型:ASCII编号为0的字符
  - 布尔型:false
  - 引用数据类型:null

调用:使用角标`var[index]`
获取长度:使用length属性`var.length`

内存解析:

- 栈(局部变量)
- 堆(new)
- 方法区
  - 常量池(字符串等)
  - 静态域(static)

### 二维数组

- 对于二维数组的理解，我们可以看成是一维数组array1又作为另一个一维数组array2的元素而存在。

声明与初始化:

- 静态初始化`int[][] arr = new int [][]{{1,2,3},{4,5},{6,7,8}}`
- 动态初始化`int[][] arr = new int[3][3]`或`int[][] arr = new int[3][]`
- 外层元素初始化为地址或null

调用:`nums[2][3]`

### 十大排序算法

- 选择排序
  - 直接选择排序、堆排序
- 交换排序
  - 冒泡排序、快速排序
- 插入排序
  - 直接插入排序、折半插入排序、Shell排序
- 归并排序
- 桶式排序
- 基数排序

### Arrays工具类

如:

1. boolean equals(int[] a,int[] b) 判断两个数组是否相等。
2. String toString(int[] a) 输出数组信息。
3. void fill(int[] a,int val) 将指定值填充到数组之中。
4. void sort(int[] a) 对数组进行排序。
5. int binarySearch(int[] a,int key) 对排序后的数组进行二分法检索指定的值。

### 数组常见异常

- 数组脚标越界异常(ArrayIndexOutOfBoundsException)
- 空指针异常(NullPointerException)
