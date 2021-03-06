# java note 4 常见类

## String

### 特点

- 被final修饰,不可被继承
- 实现了Serializable接口,表示字符串是支持序列化的
- 实现了Comparable接口：表示String可以比较大小
- 内部定义了final char[] value用于存储字符串数据
- String具有不可变性:当对字符串进行修改时，需要重新在常量池中指定新的内存区域

### 初始化

- `String s = "XXX"`即字面量定义
  - 此时字符串值声明在常量池中
  - 字符串常量池中不会存储相同内容的字符串
- `String s = new String()`
  - 参数可以是字符串"XXX",或另一个String类,或char[]数组
  - 此时地址值是new出的String类的地址值

注意:

- 只要操作中有一个是变量(而不是常量)，结果就在堆中
- 如果调用intern()方法，返回值在常量池中

### 常用方法

- int length()：返回字符串的长度
- char charAt(int index)：返回某索引处的字符
- boolean isEmpty()：判断是否是空字符串
- String toLowerCase()：将所有字符转换为小写
- String toUpperCase()：将所有字符转换为大写
- String trim()：返回字符串的副本，忽略前导空白和尾部空白
- boolean equals(Object obj)：比较字符串的内容是否相同
- boolean equalsIgnoreCase(String anotherString)：与equals方法类似，忽略大小写
- String concat(String str)：将指定字符串连接到此字符串的结尾，等价于用“+”
- int compareTo(String anotherString)：比较两个字符串的大小
- String substring(int beginIndex)：返回一个新的字符串，它是此字符串的从beginIndex开始截取到最后的一个子字符串。
- String substring(int beginIndex, int endIndex) ：返回一个新字符串，它是此字符串从beginIndex开始截取到endIndex(不包含)的一个子字符串。
- String replace(char oldChar, char newChar)：用 newChar 替换此字符串中出现的所有 oldChar
- String replace(CharSequence target, CharSequence replacement)：使用指定的字面值替换序列替换此字符串所有匹配字面值目标序列的子字符串。
- String replaceAll(String regex, String replacement)：使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串。
- String replaceFirst(String regex, String replacement)：使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串。
- boolean matches(String regex)：告知此字符串是否匹配给定的正则表达式。
- String[] split(String regex)：根据给定正则表达式的匹配拆分此字符串。
- String[] split(String regex, int limit)：根据匹配给定的正则表达式来拆分此字符串，最多不超过limit个，如果超过了，剩下的全部都放到最后一个元素中。
- boolean endsWith(String suffix)：测试此字符串是否以指定的后缀结束
- boolean startsWith(String prefix)：测试此字符串是否以指定的前缀开始
- boolean startsWith(String prefix, int toffset)：测试此字符串从指定索引开始的子字符串是否以指定前缀开始
- boolean contains(CharSequence s)：当且仅当此字符串包含指定的 char 值序列时，返回 true
- int indexOf(String str)：返回指定子字符串在此字符串中第一次出现处的索引
- int indexOf(String str, int fromIndex)：返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始
- int lastIndexOf(String str)：返回指定子字符串在此字符串中最右边出现处的索引
- int lastIndexOf(String str, int fromIndex)：返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索

### 转换

与基本数据类型，包装类的转换

- String->基本数据类型、包装类：调用包装类的静态方法：parseXxx(str)
- 基本数据类型、包装类->String:调用String重载的valueOf(xxx)

与char[]的转换

- String->char[]:调用String的toCharArray()
- char[]->String:调用String的构造器

与byte[]的转换

- 编码：String->byte[]:调用String的getBytes()
- 解码：byte[]->String:调用String的构造器
- 参数可加字符编码类型如"gbk"等

### Stringbuffer和StringBuilder

- String:不可变的字符序列,效率低
- StringBuffer:可变的字符序列；线程安全的
- StringBuilder:可变的字符序列；jdk5.0新增，线程不安全的，效率比StringBuffer高

比较:

- 底层都是使用char[]存储,但StringBuffer和StringBuilder底层创建了一个默认长度是16的数组
- StringBuffer和StringBuilder如果要添加的数据超出数组长度，则数组扩容为原来容量的2倍 + 2，同时将原有数组中的元素复制到新的数组中。

方法:

- String的方法
- StringBuilder(int capacity)
- StringBuffer append(xxx)：字符串拼接
- StringBuffer delete(int start,i nt end)：删除指定位置的内容
- StringBuffer replace(int start, int end, String str)：把[start,end)位置替换为str
- StringBuffer insert(int offset, CharSequence s)：在指定位置插入xxx
- StringBuffer reverse()：把当前字符序列逆转
- void setCharAt(int n ,char ch): 修改索引为n位置的字符

## 日期时间

### (java.util.)Date

- Date()：创建一个对应当前时间的Date对象
- Date(long date): 创建指定毫秒数的Date对象
- toString():显示当前的年、月、日、时、分、秒、时区
- long getTime():获取当前Date对象对应的毫秒数。（时间戳）

### (java.sql.)Date

数据库中的日期类,继承于java.util.Date

- java.sql.Date(long date)
- 调用Date的getTime()方法做参数可以将Date转换为java.sql.Date

### 时间

- long System.currentTimeMillis()返回当前时间距离1970-01-01的毫秒数

### (java.text.)SimpleDateFormat

实例化

- SimpleDateFormat(String pattern):填入格式,如"YYYY-MM-dd hh:mm:ss"

格式化

- String format(Date date):将日期转化为特定格式的字符串

解析

- Date parse(String date):将格式化字符串转化为日期
- 如果date的格式不正确,会抛出ParseException异常

### (java.util.)Calendar

实例化

- GregorianCalendar Calendar.getInstance();
- Calendar是一个抽象类,不能直接使用构造器,其静态方法getInstance()返回的是其子类GregorianCalendar的对象

方法

- int get(Calendar.DAY_OF_MONTH):取得日历对象的日期是那个月的第几天
- int set(Calendar.DAY_OF_MONTH, 22):修改成当月的第几天
- void add(Calendar.DAY_OF_MONTH, -3):增删天数
- Date getTime():日历类->日期类
- void setTime(Date):日期类->日历类
- 获取月份时,0是一月,1是一月,以此类推;获取星期时,1是星期日,2是星期一,以此类推

### (java.time.)LocalDate LocalTime LocalDateTime(JDK8新增)

- LocalDateTime LocalDateTime.now():返回当前时间
- LocalDateTime LocalDateTime.of(year, month, day, hour, minute, second):设定指定的时间
- int getDayOfMonth():getXxx()返回指定的属性
- LocalDateTime withDayOfMonth(int):withXxx(int)修改指定属性
- LocalDateTime plusMonths(int):plusXxx(int),minusXxx(int)修改指定属性

- 特性:不可变性

### Instant(JDK8新增)

- Instant Instant.now():获取本初子午线对应的标准时间
- OffsetDateTime atOffset(ZoneOffset.ofHours(8)):添加时间的偏移量
- long .toEpochMilli():获取自1970年1月1日0时0分0秒（UTC）开始的毫秒数
- Instant Instant.ofEpochMilli(long):通过给定的毫秒数，获取Instant实例

### DateTimeFormatter(JDK8新增)

实例化

- DateTimeFormatter DateTimeFormatter.ISO_LOCAL_DATE_TIME:使用预定义的标准格式
- DateTimeFormatter DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG):使用本地化相关格式
- DateTimeFormatter DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"):使用自定义格式

格式化

- String format(LocalDateTime):日期->字符串

解析:

- TemporalAccessor formatter.parse(String):字符串->日期

## 比较器

实现了Comparable或Comparator接口即可以使用比较运算符或sort方法比较大小

### Comparable

自然排序

- 重写compareTo(obj)的规则：如果当前对象this大于形参对象obj，则返回正整数;小于，返回负整数;等于，返回零。

```java
//按商品的价格从小到大排序,价格相等则按名称从小到大排序
@Override
public int compareTo(Object o) {
    if(o instanceof Goods){
        Goods goods = (Goods)o;
        if (!this.price.equals(goods.price)) {
            return this.price.compareTo(good.price);
        }else{
           return this.name.compareTo(goods.name);
        }
    }
    throw new RuntimeException("传入的数据类型不一致！");
}
```

### Comparator

定制排序:当元素的类型没有实现Comparable接口而又不方便修改代码，或者实现的Comparable接口的排序规则不适合当前的操作，那么可以考虑使用Comparator的对象来排序.规则与Comparable相同

```java
Arrays.sort(arr, new Comparator() {
    @Override
    public int compare(Object o1, Object o2) {
        ...
    }
});
```

## 其他

### System

- void exit(int status):退出程序。其中status的值为0代表正常退出，非零代表异常退出。使用该方法可以在图形界面编程中实现程序的退出功能
- void gc()：请求系统进行垃圾回收。至于系统是否立刻回收，则取决于系统中垃圾回收算法的实现以及系统执行时的情况。
- String getProperty(String key)：获得系统中的属性。系统中常见的属性如：
  - java.version
  - java.home
  - os.name
  - os.version
  - user.home
  - user.name
  - user.dir

### Math

- abs 绝对值
- acos,asin,atan,cos,sin,tan 三角函数
- sqrt 平方根
- pow(double a,double b) a的b次幂
- log 自然对数
- exp e为底指数
- max(double a,double b) 最大值
- min(double a,double b) 最小站
- random() 返回0.0到1.0的随机数
- long round(double a) double型数据a转换为long型（四舍五入）
- toDegrees(double angrad) 弧度—>角度
- toRadians(double angdeg) 角度—>弧度

### (java.math.)BigInteger BigDecimal

BigInteger可以表示不可变的任意精度的整数

- BigInteger(String val)：根据字符串构建BigInteger对象
- BigInteger abs()：绝对值
- BigInteger add(BigInteger val)：加
- BigInteger subtract(BigInteger val)：减
- BigInteger multiply(BigInteger val)：乘
- BigInteger divide(BigInteger val)：除,整数相除只保留整数部分。
- BigInteger remainder(BigInteger val)：取模
- BigInteger[] divideAndRemainder(BigInteger val)：返回相除的结果和余数
- BigInteger pow(int exponent)：幂运算

BigDecimal类支持不可变的、任意精度的有符号十进制定点数

- BigDecimal(double val)
- BigDecimal(String val)
- BigDecimal add(BigDecimal augend)
- BigDecimal subtract(BigDecimal subtrahend)
- BigDecimal multiply(BigDecimal multiplicand)
- BigDecimal divide(BigDecimal divisor, int scale, int roundingMode): scale指返回的精度, roundingMode指截断的模式,如BigDecimal.ROUND_HALF_UP等
