# java note 7 网络编程

## 网络编程

网络编程中有两个主要的问题：

1. 如何准确地定位网络上一台或多台主机；定位主机上的特定的应用
2. 找到主机后如何可靠高效地进行数据传输

网络编程中的两个要素：

1. IP和端口号
2. 提供网络通信协议：TCP/IP参考模型（应用层、传输层、网络层、物理+数据链路层）

### IP地址

Internet上的计算机（通信实体）的唯一标识

分类：

- IPv4(4个字节组成,如192.168.0.1)和IPv6
- 万维网和局域网

域名:如www.baidu.com

本地回路地址：127.0.0.1 对应着：localhost
在Java中用java.net.InetAddress类代表IP

- 实例化：
  - InetAddress InetAddress.getByName(String host)
  - InetAddress InetAddress.getLocalHost()
- 方法:
  - String getHostName()
  - String getHostAddress()

### 端口号

表示正在计算机上运行的网络进程

- 不同的进程有不同的端口号
- 范围：被规定为一个 16 位的整数 0~65535
- 端口号与IP地址的组合得出一个网络套接字：Socket
  - Socket(InetAddress address, int port)
  - InetAddress getInetAddress()
  - int getPort()

### 协议

服务端(TCP)

```java
//1.创建服务器端的ServerSocket，指明自己的端口号
ServerSocket ss = new ServerSocket(8899);
//2.调用accept()表示接收来自于客户端的socket
Socket socket = ss.accept();
//3.获取输入流
InputStream is = socket.getInputStream();
//4.读取输入流中的数据
ByteArrayOutputStream baos = new ByteArrayOutputStream();
byte[] buffer = new byte[5];
int len;
while((len = is.read(buffer)) != -1){
    baos.write(buffer,0,len);
}
System.out.println(baos.toString());
//5.关闭资源
baos.close();
is.close();
socket.close();
ss.close();
```

客户端(TCP)

```java
//1.创建Socket对象，指明服务器端的ip和端口号
InetAddress inet = InetAddress.getByName("127.0.0.1");
Socket socket = new Socket(inet,8899);
//2.获取一个输出流，用于输出数据
OutputStream os = socket.getOutputStream();
//3.写出数据的操作
os.write("你好，我是客户端".getBytes());
//4.资源的关闭
os.close();
socket.close();
```

接收端(UDP)

```java
DatagramSocket socket = new DatagramSocket(9090);
byte[] buffer = new byte[100];
DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
socket.receive(packet);
System.out.println(new String(packet.getData(), 0, packet.getLength()));
socket.close();
```

发送端(UDP)

```java
DatagramSocket socket = new DatagramSocket();
byte[] data = "UDP方式发送".getBytes();
InetAddress inet = InetAddress.getByName("127.0.0.1");
DatagramPacket packet = new DatagramPacket(data, 0, data.length, inet, 9090);
socket.send(packet);
socket.close();
```

### URL

统一资源定位符,格式:协议 主机名 端口号 资源地址 (参数列表),如`http://localhost:8080/examples/beauty.jpg?username=Tom`

```java
URL url = new URL("http://localhost:8080/examples/beauty.jpg");
HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
urlConnection.connect();
InputStream is = urlConnection.getInputStream();
//... 传输操作
urlConnection.disconnect();
//... 关闭资源
```

## 反射

反射(reflection)是被视为动态语言的关键，反射机制允许程序在执行期借助于Reflection API取得任何类的内部信息，并能直接操作任意对象的内部属性及方法。因为加载完类之后，在堆内存的方法区中就产生了一个Class类型的对象（一个类只有一个Class对象），这个对象就包含了完整的类的结构信息。我们可以通过这个对象看到类的结构。

### Class类

程序经过javac.exe命令以后，会生成一个或多个字节码文件(.class结尾)。接着使用java.exe命令对某个字节码文件进行解释运行,将某个字节码文件加载到内存中,此过程就称为类的加载。加载到内存中的类，就称为运行时类.此运行时类就是Class类的一个对象。

获取Class的实例的方式:

```java
//方式一：调用运行时类的属性：.class
Class clazz1 = Person.class;

//方式二：通过运行时类的对象,调用getClass()
Person p1 = new Person();
Class clazz2 = p1.getClass();

//方式三：调用Class的静态方法：forName(String classPath) (常用)
Class clazz3 = Class.forName("package.Person");
//如clazz3 = Class.forName("java.lang.String");

//方式四：使用类的加载器：ClassLoader
ClassLoader classLoader = SomeClass.class.getClassLoader();
Class clazz4 = classLoader.loadClass("package.Person");
```

类的加载器:

```java
//对于自定义类，使用系统类加载器进行加载
ClassLoader classLoader1 = SomeClass.class.getClassLoader();
//调用系统类加载器的getParent()：获取扩展类加载器
//负责将jar包装入工作库
ClassLoader classLoader2 = classLoader1.getParent();
//调用扩展类加载器的getParent()：无法获取引导类加载器
//引导类加载器主要负责加载java的核心类库，无法加载自定义类。
ClassLoader classLoader3 = classLoader2.getParent();//null
```

类,接口,数组,枚举类,注解,基本数据类型,void,Class都可以是Class的实例,万事万物皆对象:

- Class c1 = Object.class;
- Class c2 = Comparable.class;
- Class c3 = String[].class;
- Class c4 = int[][].class;
- Class c5 = ElementType.class;
- Class c6 = Override.class;
- Class c7 = int.class;
- Class c8 = void.class;
- Class c9 = Class.class;

### 运行时类的完整结构

- 属性
  - Field[] getFields():获取当前运行时类及其父类中声明为public访问权限的属性
  - Field[] getDeclaredFields():获取当前运行时类中声明的所有属性。（不包含父类中声明的属性）
    - int getModifiers();属性的修饰符
    - Class getType();属性类型
    - String getName();属性名
- 方法
- 构造器
- 父类(带泛型的)
- 实现的接口
- 所在的包
- 注解
- ...

### 调用运行时类的指定结构

```java
Class clazz = Person.class;

//创建对象,默认使用的是空参构造器
Person p = clazz.getDeclaredConstructor().newInstance();

//调用构造器(可以是私有的)创建对象
Constructor constructor = clazz.getConstructor(String.class, int.class);  //参数列表为参数的类型
constructor.setAccessible(true);  //获得调用私有结构的权限
Person p = (Person) constructor.newInstance("Tom", 12);

//调用属性
Field age = clazz.getDeclaredField("age");  //属性名
age.setAccessible(true);
age.set(p, 18);  //对象 属性值
int getAge = age.get(p);  //对象

//调用方法
Method show = clazz.getDeclaredMethod("getDescription", String.class);  //方法名 形参列表
show.setAccessible(true);
String description = getDescription.invoke(p, "China");  //对象(如果是静态方法,这里是对应的类) 参数列表
```

当需要使用动态性时,通过反射的方法调用构造器,属性和方法

### 动态代理 AOP

略

## JDK8新特性

### Lambda表达式

Lambda表达式的本质：函数式接口的实例

原来的写法:

```java
new Comparator<Integer> comparator = new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
};
```

Lambda表达式写法:`(o1,o2) -> Integer.compare(o1,o2);`

- -> :lambda操作符 或 箭头操作符
- 左边：lambda形参列表 （接口中的抽象方法的形参列表）
- 右边：lambda体 （重写的抽象方法的方法体）

规则:

- 左边：
  - lambda形参列表的参数类型在可以类型推断的情况下可以省略(类型推断)
  - 如果lambda形参列表只有一个参数，()也可以省略
- 右边：
  - lambda体应该使用一对{}包裹,但如果lambda体只有一条执行语句（可能是return语句），省略这一对{}和return关键字

### 函数式接口

只声明了一个抽象方法的接口,可以在一个接口上使用 @FunctionalInterface 注解

java内置的4大核心函数式接口:
| 接口                     | 抽象方法          |
| ------------------------ | ----------------- |
| 消费型接口 Consumer<T>   | void accept(T t)  |
| 供给型接口 Supplier<T>   | T get()           |
| 函数型接口 Function<T,R> | R apply(T t)      |
| 断定型接口 Predicate<T>  | boolean test(T t) |

### 方法引用 构造器引用

方法引用，本质上就是Lambda表达式

使用格式：类(或对象)::方法名

- 对象::非静态方法
- 类::静态方法
- 类::非静态方法

要求：

- 接口中的抽象方法的形参列表和返回值类型与方法引用的方法的形参列表和返回值类型相同（对应类型1和类型2）
- 当函数式接口方法的第一个参数是需要引用方法的调用者，并且第二个参数是需要引用方法的参数(或无参数)时(对应类型3)

```java
//对象::实例方法
//Consumer中的void accept(T t)
//PrintStream中的void println(T t)

//Lambda表达式写法
Consumer<String> consumer = str -> System.out.println(str);

//方法引用写法
Consumer<String> consumer = System.out::println;

//调用
consumer.accept("beijing");

//类::静态方法
//Comparator中的int compare(T t1,T t2)
//Integer中的int compare(T t1,T t2)
Comparator<Integer> comparator = Integer::compare;

//类 :: 实例方法
//Comparator中的int comapre(T t1,T t2)
//String中的int t1.compareTo(t2)
Comparator<String> comparator = String::compareTo;
```

构造器引用

```java
//返回对应对象
Supplier<Employee> supplier = Employee::new;
Employee employee = supplier.get();

//返回对应长度的数组
Function<Integer, int[]> function = int[]::new;
int[] arr = function.apply(10);
```

### Stream API

使用Stream API 能对集合数据进行操作，类似于使用 SQL 执行的数据库查询。

- Stream关注的是对数据的运算，与CPU相关;集合关注的是数据的存储，与内存相关
- Stream 自己不存储元素,且Stream不会改变源对象,它们返回的是一个持有结果的新Stream
- Stream 执行流程:Stream的实例化,一系列的中间操作(过滤、映射、...),终止操作
- Stream 操作是延迟执行的,执行终止操作才会就执行中间操作链并产生结果,且之后不会再被使用

实例化

```java
//通过集合
List<Employee> employees = ...;
//default Stream<E> stream() : 返回一个顺序流
Stream<Employee> stream = employees.stream();
//default Stream<E> parallelStream() : 返回一个并行流
Stream<Employee> parallelStream = employees.parallelStream();

//通过数组
//调用Arrays类的static <T> Stream<T> stream(T[] array): 返回一个流
IntStream stream = Arrays.stream(arr);
//自定义对象数组
Stream<Employee> stream1 = Arrays.stream(arr);

//通过Stream.of(T...values)
Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

//创建无限流
//迭代
//public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
//遍历前10个偶数
Stream.iterate(0, t -> t + 2).limit(10).forEach(System.out::println);
//生成
//public static<T> Stream<T> generate(Supplier<T> s)
//生成10个随机数
Stream.generate(Math::random).limit(10).forEach(System.out::println);
```

中间操作

- 筛选与切片
  - filter(Predicate p):从流中筛选出某些元素
  - limit(n):截断流，使元素不超过给定数量
  - skip(n):跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。
  - distinct():去重，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
- 映射
  - map(Function f):将元素转换成其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素。
  - flatMap(Function f):将流中的每个值都换成另一个流，然后把所有流连接成一个流。
- 排序
  - sorted():自然排序
  - sorted(Comparator com):定制排序

终止操作

- 匹配与查找
  - allMatch(Predicate p)——检查是否匹配所有元素。
  - anyMatch(Predicate p)——检查是否至少匹配一个元素。
  - noneMatch(Predicate p)——检查是否没有匹配的元素。
  - Optional findFirst()返回第一个元素
  - Optional findAny()返回当前流中的任意元素
  - long count()返回流中元素的总个数
  - Optional max(Comparator c):返回流中最大元素
  - Optional min(Comparator c):返回流中最小值
  - forEach(Consumer c):内部迭代
- 归约
  - T reduce(T identity, BinaryOperator):可以将流中元素反复结合起来，得到一个值。返回 T
  - Optional<T> reduce(BinaryOperator)
- 收集
  - Collection collect(Collector c):将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
