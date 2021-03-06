# java note 6 多线程 IO流

## 多线程

### 程序、进程、线程

- 程序:一段静态的代码
- 进程:正在运行的一个程序
- 线程:一个程序内部的一条执行路径,JVM中每个线程有自己的栈和程序计数器,多个线程共享堆和方法区

并行与并发:

- 并行：多个CPU同时执行多个任务
- 并发：一个CPU(采用时间片)同时执行多个任务

守护线程:

- 守护线程是用来服务用户线程的，通过在start()方法前调用
- thread.setDaemon(true)可以把一个用户线程变成一个守护线程。
- Java垃圾回收就是一个典型的守护线程。
- 若JVM中都是守护线程，当前JVM将退出。

### 线程的创建和使用

#### 方式1:继承Thread类

- 创建继承于Thread的类
- 重写Thread类的run(),将此线程执行的操作声明在run()中
- 创建Thread类的子类的对象
- 通过此对象调用start()

```java
class MyThread extends Thread {
    @Override
    public void run() {
        ...
    }
}

MyThread t1 = new MyThread();
MyThread t2 = new MyThread();
t1.start();
t2.start();
```

注意:

- 当调用start()时:1.启动当前线程2.调用当前线程的run().不能直接调用run(),因为这样不能创建多线程
- 一个对象只能启动一个线程,否则抛出IllegalThreadStateException异常;启动多线程需要多个线程对象

#### 方式2:实现Runnable接口

- 创建一个实现了Runnable接口的类
- 实现run()方法
- 创建实现类的对象
- 将此对象作为参数传递到Thread类的构造器中,创建Thread类的对象
- 通过Thread类的对象调用start()

```java
class MThread implements Runnable{
    @Override
    public void run() {
        ...
    }
}

MThread mThread = new MThread();
Thread t1 = new Thread(mThread);
Thread t2 = new Thread(mThread);
t1.start();
t2.start();
```

注意:

- 开发中,优先选择实现Runnable接口的方式,原因:1. 没有类单继承性的局限性 2. 实现的方式更适合来处理多个线程有共享数据的情况。
- Thread也实现了Runnable接口

#### 方式3:实现Callable接口(JDK5.0新增)

- 创建一个实现Callable的实现类
- 实现call方法
- 创建Callable接口实现类的对象
- 将此Callable接口实现类的对象作为参数传递到FutureTask构造器中，创建FutureTask的对象
- 将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
- FutureTask的对象get()方法的返回值即为call()的返回值。

```java
class MyThread implements Callable{
    @Override
    public Object call() throws Exception {
        ...
        return res;
    }
}

MyThread myThread = new myThread();
FutureTask futureTask = new FutureTask(myThread);
new Thread(futureTask).start();
try {
    Object res = futureTask.get();
} catch (InterruptedException e) {
    e.printStackTrace();
} catch (ExecutionException e) {
    e.printStackTrace();
}
```

- 使用该方法可以有返回值,可以throws异常,且支持泛型

#### 方法四:创建线程池(JDK5.0新增)

提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池中。可以避免频繁创建销毁、实现重复利用,也便于线程管理。

```java
//1. 提供指定线程数量的线程池
ExecutorService service = Executors.newFixedThreadPool(10);

//实际上返回的是ThreadPoolExecutor类
ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;

//可以设置线程池的属性,如:
//service1.setCorePoolSize(15);//核心池的大小
//service1.setKeepAliveTime(10);//线程没有任务时最多保持多长时间后会终止

//2.执行指定的线程的操作。需要提供实现Runnable接口或Callable接口实现类的对象
service.execute(new myThread());//用于Runnable实现类
//service.submit(Callable callable);//用于Callable实现类

//3.关闭连接池
service.shutdown();
```

### Thread中的其他API

- currentThread():静态方法，返回执行当前代码的线程
- getName():获取当前线程的名字(或在创建对象时传入String参数)
- setName():设置当前线程的名字
- yield():释放当前cpu的执行权
- join():在线程a中调用线程b的join(),此时线程a就进入阻塞状态，直到线程b完全执行完以后，线程a才结束阻塞状态。
- sleep(long millitime):让当前线程“睡眠”指定的millitime毫秒。在指定的millitime毫秒时间内，当前线程是阻塞状态
- isAlive():判断当前线程是否激活/存活

### 线程的优先级

Thread中关于优先级的常量:

- MAX_PRIORITY：10
- MIN_PRIORITY：1
- NORM_PRIORITY：5(默认优先级)

方法:

- getPriority():获取线程的优先级
- setPriority(int p):设置线程的优先级
- 注意:高优先级的线程要抢占低优先级线程cpu的执行权,高优先级的线程较大概率的情况被执行,但并不意味着只有当高优先级的线程执行完以后,低优先级的线程才执行.

### 线程的生命周期

```plantuml
digraph thread_lifecycle {
    
    新建  -> 就绪 [label = "start()"]
    就绪 -> 运行 [label = "获得CPU执行权"]
    运行 -> 就绪 [label = "失去CPU执行权\nyield()"]
    运行 -> 死亡 [label = "执行完run()\n调用了stop()\n出现异常且未处理"]
    运行 -> 阻塞 [label = "sleep()\njoin()\n等待同步锁\nwait()\nsuspend()"]
    阻塞 -> 就绪 [label = "sleep()时间到\njoin()jieshu\n获取了同步锁\nnotify()/notifyAll()\nresume()"]
}
```

### 线程同步

线程安全问题:一个线程操作共享数据的过程中,另一个线程也参与了进来,导致数据计算错误.解决方法:当一个线程在操作共享数据的时候,其他线程不能参与进来,直到这个线程操作完成

同步机制解决线程安全问题:

#### 方式一:同步代码块

```java
synchronized (锁) {
   //需要被同步的代码
}
```

- 任何一个类的对象,都可以充当锁
- 多个线程必须要共用同一把锁
- 可以考虑this关键字或`当前类名.class`作为锁

#### 方法二:同步方法

在方法前加上synchronized关键字

- 同步的普通方法相当于加上当前对象(this)作为锁
- 同步的静态方法相当于加上当前类作为锁

#### 方法三:ReentrantLock类锁(JDK5新增)

- ReentrantLock实例化
- 使用lock()方法加锁
- 使用unlock()方法解锁

```java
ReentrantLock lock = new ReentrantLock();
lock.lock()
//需要被同步的代码
lock.unlock()
```

同步的方式，解决了线程的安全问题。但是操作同步代码时，只能有一个线程参与，其他线程等待。相当于是一个单线程的过程，效率低.

#### 线程安全的懒汉式单例模式

```java
class Singleton {
    private Singleton() {
    
    }
  
    private static Singleton single = null;
  
    public static Singleton getInstance() {
        if (single == null) {  //如果对象已经被创建,直接return,防止线程等待
            synchronized (Singleton.class()) {  //加锁保证线程安全
                if(single == null) {
                    single = new Singleton();
                }
            }
        }
        return single;
    }
}
```

#### 死锁

不同的线程分别占用对方需要的锁不放弃，都在等待对方放弃自己需要的锁，就形成了线程的死锁

锁的释放方式:

- 同步代码块或方法执行结束
- 遇到return,break结束同步代码
- 遇到未处理的异常
- wait()方法

不会释放锁:

- yield()方法
- sleep()方法

### 线程通信

- wait():使当前线程进入阻塞状态，并释放锁。
- notify():唤醒wait中的一个线程。如果有多个线程wait，唤醒优先级高的那个。
- notifyAll():唤醒所有wait中的线程。

```java
synchronized (lock) {
    lock.notify();
    ...
    try {
        lock.wait();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

- wait()，notify()，notifyAll()三个方法必须使用在同步代码块或同步方法中。
- 三个方法的调用者必须是同步代码块或同步方法中的同步监视器,否则会出现IllegalMonitorStateException异常
- 三个方法定义在java.lang.Object类中.

生产者消费者问题

## IO流

### File类

File类声明在java.io包下,File类的一个对象，代表一个文件或一个文件目录(文件夹).File类中涉及到关于文件或文件目录的创建、删除、重命名、修改时间、文件大小等方法,但并未涉及到写入或读取文件内容的操作。如果需要读取或写入文件内容，需要使用IO流来完成。

方法:

- 实例化:
  - File(String filePath)
  - File(String parentPath,String childPath)
  - File(File parentFile,String childPath)
    - 以上的路径可以是相对路径或绝对路径;分隔符在windows下用\\\\表示,在unix下为/,分隔符可用File.separator代替
- 查询:
  - String getAbsolutePath()：获取绝对路径
  - String getPath() ：获取路径
  - String getName() ：获取名称
  - String getParent()：获取上层文件目录路径。查询不到则返回null
  - long length() ：获取文件长度（即：字节数）,不能获取目录的长度。
  - long lastModified() ：获取最后一次修改的时间戳
  - String[] list() ：获取指定目录下的所有文件或者文件目录的名称数组,用于文件夹
  - File[] listFiles() ：获取指定目录下的所有文件或者文件目录的File数组,用于文件夹
  - boolean isDirectory()：返回是否是文件目录
  - boolean isFile() ：返回是否是文件
  - boolean exists() ：返回是否存在
  - boolean canRead() ：返回是否可读
  - boolean canWrite() ：返回是否可写
  - boolean isHidden() ：返回是否隐藏
- 移动
  - boolean renameTo(File dest):把文件重命名(移动)为指定的文件路径,返回是否成功.如`file1.renameTo(file2)`要想保证成功,需要file1在硬盘中是存在的，且file2不能在硬盘中存在。
- 创建
  - boolean createNewFile() ：创建文件。若文件存在，则不创建，返回false
  - boolean mkdir() ：创建文件目录。若目录存在则不创建了。如果此文件目录的上层目录不存在，也不创建
  - boolean mkdirs() ：创建文件目录。若目录存在则不创建了。如果上层文件目录不存在，一并创建
- 删除
  - boolean delete()：删除文件或者文件夹,注意被删除的文件目录下不能有子目录或文件,且Java中删除的东西不走回收站

### 流

Java程序中，对于数据的输入/输出操作以“流(stream)” 的方式进行。

流的分类:

- 操作数据单位
  - 字节流
  - 字符流:用来处理文本文件,不能使用字符流来处理图片等字节数据
- 数据的流向
  - 输入流
  - 输出流
- 流的角色
  - 节点流:直接从数据源或目的地读写数据
  - 处理流:“连接”在已存在的流（节点流或处理流）之上，通过对数据的处理为程序提供更为强大的读写功能。

流的体系结构:

| 抽象基类     | 节点流（或文件流）                            | 缓冲流（处理流的一种）                                     |
| ------------ | --------------------------------------------- | ---------------------------------------------------------- |
| InputStream  | FileInputStream   (read(byte[] buffer))       | BufferedInputStream (read(byte[] buffer))                  |
| OutputStream | FileOutputStream  (write(byte[] buffer,0,len) | BufferedOutputStream (write(byte[] buffer,0,len) / flush() |
| Reader       | FileReader (read(char[] cbuf))                | BufferedReader (read(char[] cbuf) / readLine())            |
| Writer       | FileWriter (write(char[] cbuf,0,len)          | BufferedWriter (write(char[] cbuf,0,len) / flush()         |

### 输入流:FileReader

从文件中读出数据到内存中

```java
FileReader fr = null;
try {
    //1.File类的实例化
    File file = new File("hello.txt");

    //2.FileReader流的实例化
    fr = new FileReader(file);

    int data;
    //3.读/写的操作
    while((data = fr.read()) != -1){
        System.out.print((char)data);
    }
} catch (IOException e) {
    e.printStackTrace();
} finally {
    //4.资源的关闭
    if(fr != null){
        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

jdk7新特性:try-with-resources自动关闭资源

```java
File file = new File("./solution/tmp.txt");
try(FileReader reader = new FileReader(file);) {
    int ch;
    while ((ch = reader.read()) != -1) {
        System.out.print((char) ch);
    }
} catch (IOException e) {
    e.getStackTrace();
}
```

- 读入的文件一定要存在，否则就会报FileNotFoundException

方法:

- int read():返回读入的一个字符。如果达到文件末尾，返回-1
- int read(char[] cbuf):将读入的字符写入cbuf中,返回每次读入cbuf数组中的字符的个数。如果达到文件末尾，返回-1
- int read(char[] cbuf, int off, int len):指定放入数组的起始位置和长度
- void close() throws IOException:关闭此输入流并释放与该流关联的所有系统资源。

### 输出流:FileWriter

从内存中写出数据到硬盘的文件里。

- File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建此文件。
- File对应的硬盘中的文件如果存在：默认情况会对原有文件进行覆盖,指定append参数为true后,不会对原有文件覆盖，而是在原有文件基础上添加内容

方法:

- void write(int c);
- void write(char[] cbuf);
- void write(char[] buff, int off, int len);
- void close();

### 字节流:FileInputStream/FileOutputStream

与FileReader/FileWriter类似,不同在于输入/输出的是字节流,用byte[]存储

### 缓存流:BufferedInputStream/BufferedOutputStream/BufferedReader/BufferedWriter

缓冲流作用：内部提供了一个缓冲区,加快流的读取、写入的速度

使用:构造器参数中传入字节流即可,如`BufferedInputStream(FileInputStream)`;方法调用基本一样

注意:

- 关闭外层流的同时，内层流也会自动的进行关闭。关于内层流的关闭，我们可以省略.
- 缓存流的输出流有flush()方法刷新缓存区
- BufferedReader提供String readLine()方法读取文本文件的一行(不包含换行符)

### 转换流:InputStreamReader/OutputStreamWriter

- InputStreamReader(InputStream in)：将InputStream转换为Reader,输入字节流转换为输入字符流
- OutputStreamWriter(OutputStream out)：将Writer转换为OutputStream,输出字符流转换为输出字节流
- 可以加上第二个字符串参数指明字符集类型

### 标准输入输出流:System.in/System.out

- InputStream System.in:从键盘中读入字节流
- PrintStream System.out:输出到控制台

### 打印流:PrintStream/PrintWriter

实现将基本数据类型的数据格式转化为字符串输出

- PrintStream(OutputStream, true):创建打印字节流,设置为自动刷新模式(写入换行符或字节 '\n' 时都会刷新输出缓冲区)
  - 提供多种print(),println()方法
- System.setOut(PrintStream)把标准输出流(控制台输出)改成文件

### 数据流:DataInputStream/DataOutputStream

用于读取或写出基本数据类型的变量或字符串

- DataOutputStream(OutputStream)
  - 提供了多种输出基本数据类型的方法,如writeInt(),writeUTF()等
- DataInputStream(InputStream)
  - 提供了多种读取基本数据类型的方法,将write改为read即可,注意读取的类型要与原来输出的类型一致

### 对象流:ObjectInputStream/ObjectOutputStream

可以把Java中的对象写入到数据源中，也能把对象从数据源中还原回来。

- 序列化过程：对象序列化机制允许把内存中的Java对象转换成平台无关的二进制流,从而将java对象保存到磁盘中或通过网络传输出去
- 反序列化：将磁盘文件中的对象还原为内存中的一个java对象

```java
oos = new ObjectOutputStream(new FileOutputStream("object.dat"));
oos.writeObject(new String("我爱北京天安门"));
oos.flush();//刷新
ois = new ObjectInputStream(new FileInputStream("object.dat"));
Object obj = ois.readObject();
String str = (String) obj;
```

对象流要求对象是是可序列化的，可序列化的类需要满足相应的要求。

```java
public class Person implements Serializable{  //需要实现Serializable接口
    public static final long serialVersionUID = 475463534532L;  //提供序列版本id
    ...//如果有其他属性,需要保证其内部所有属性也必须是可序列化的
    //补充：ObjectOutputStream和ObjectInputStream不能序列化static和transient修饰的成员变量
}
```

### 随机存取文件流:RandomAccessFile

- RandomAccessFile直接继承于java.lang.Object类，实现了DataInput和DataOutput接口
- RandomAccessFile既可以作为一个输入流，又可以作为一个输出流
- 如果RandomAccessFile作为输出流时，写出到的文件如果不存在，则在执行过程中自动创建。
- 如果写出到的文件存在，则会对原有文件内容进行覆盖。（从头覆盖）

方法

- RandomAccessFile(String File, String mode);
  - mode:"r": 以只读方式打开;"rw"：打开以便读取和写入
- long getFilePointer()：获取文件记录指针的当前位置
- void seek(long pos)：将文件记录指针定位到pos位置
- read/write方法与其他流类似

### NIO.2

Non-Blocking IO 非阻塞式IO

jdk 7.0 时，引入了 Path、Paths、Files三个类。此三个类声明在：java.nio.file包下。Path可以看做是java.io.File类的升级版本。也可以表示文件或文件目录，与平台无关.

方法略
