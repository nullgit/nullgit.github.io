# java note3 异常 枚举 注解 泛型

## 异常

### 异常及其分类

程序执行中发生的不正常情况称为“异常”

分类:

- Error:Java虚拟机无法解决的严重问题。如：JVM系统内部错误、资源耗尽等严重情况。比如：StackOverflowError和OOM(OutOfMemoryError)。一般不编写针对性的代码进行处理。
- Exception: 其它因编程错误或偶然的外在因素导致的一般性问题，可以使用针对性的代码进行处理。如空指针访问,试图读取不存在的文件,网络连接中断,数组角标越界等
  - 编译时异常(checked)
  - 运行时异常(unchecked):RunTimeException

### 常见的异常

编译时异常：

- IOException
  - FileNotFoundException
- ClassNotFoundException

运行时异常：

- NullPointerException
- IndexOutOfBoundsException
  - ArrayIndexOutOfBoundsException
  - StringIndexOutOfBoundsException
- ClassCastException
- NumberFormatException
- InputMismatchException
- ArithmeticException
- ...

### 异常处理机制1 try-catch-finally

```java
try{
    //可能出现异常的代码
} catch (异常类型1 e) {
    //处理异常的方式1
} catch (异常类型2 e) {
    //处理异常的方式2
}
...
finally {
    //一定会执行的代码
}
```

注意:

- catch中的异常类型如果满足子父类关系，则要求子类一定声明在父类的上面。
- 常用的异常对象处理的方式:1.String getMessage() 2.e.printStackTrace()
- 使用try-catch-finally处理编译时异常,使得程序在编译时就不再报错,但是运行时仍可能报错,相当于我们将一个编译时可能出现的异常，延迟到运行时出现。
- 由于运行时异常比较常见，所以通常不针对运行时异常处理,针对于编译时异常才一定要考虑异常处理。

关于finally:

- finally是可选的
- finally中声明的是一定会被执行的代码,即使catch中又出现异常,try中有return语句,catch中有return语句等情况
- 像数据库连接、输入输出流、网络编程Socket等资源，JVM是不能自动的回收的，我们需要自己手动的进行资源的释放。此时的资源释放，就需要声明在finally中。

### 异常处理机制2 throws+异常类

`throws 异常类`写在方法的声明处,一旦当方法体执行时,出现异常,就将异常抛给了方法的调用者.异常代码后续的代码，将不再执行！

- 如果父类中被重写的方法没有throws方式处理异常，则子类重写的方法也不能使用throws,必须使用try-catch-finally方式处理.如果父类throws一个异常类,那么子类throws的异常类不能比父类抛出的大
- 执行的方法a中，先后又调用了另外的几个方法，且这几个方法是递进关系执行的。我们建议这几个方法使用throws的方式进行处理,而执行的方法a可以考虑使用try-catch-finally方式进行处理。

### 手动抛出异常

- 自动生成异常
- 手动抛出异常`throw 异常类`

### 用户自定义异常类

```java
public class MyException extends Exception{  //继承Exception或RunTimeException
    static final long serialVersionUID = -7034897193246939L;  //id

    public MyException(){  //空参构造器
    }

    public MyException(String msg){
    super(msg);
    }
}
```

## 枚举类

当需要定义一组常量时，建议使用枚举类

### 自定义枚举类

1. 对象如果有实例变量，应该声明为private final，并在构造器中初始化
2. 私有化类的构造器
3. 在类的内部创建枚举类的实例，声明为：public static final

```java
class Season{
    private final String SEASONNAME;//季节的名称
    private final String SEASONDESC;//季节的描述
 
    private Season(String seasonName,String seasonDesc){
        this.SEASONNAME = seasonName;
        this.SEASONDESC = seasonDesc;
    }
 
    public static final Season SPRING = new Season("春天", "春暖花开");
    public static final Season SUMMER = new Season("夏天", "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天", "秋高气爽");
    public static final Season WINTER = new Season("冬天", "白雪皑皑");
 
    //按需求提供get()方法,toString()方法等
}

Season summer = Season.SUMMER;
```

### enum定义枚举类

1. 提供当前枚举类的对象(必须声明在第一行)，多个对象之间用","隔开，末尾对象";"结束
2. 声明Season对象的属性:private final修饰
3. 私有化类的构造器,并给对象属性赋值

```java
enum Season1 {
    SPRING("春天","春暖花开"),
    SUMMER("夏天","夏日炎炎"),
    AUTUMN("秋天","秋高气爽"),
    WINTER("冬天","冰天雪地");

    private final String seasonName;
    private final String seasonDesc;

    private Season1(String seasonName,String seasonDesc){
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    //按需求提供get()方法,toString()方法等
}
```

- 使用 enum 定义的枚举类默认继承了 java.lang.Enum类，因此不能再继承其他类
- 列出的实例系统会自动添加 public static final 修饰,不能再添加修饰
- JDK 1.5 中可以在 switch 中使用Enum定义的枚举类的对象作为表达式, case 子句可以直接使用枚举值的名字, 无需添加枚举类作为限定。
- 枚举类可以实现一个或多个接口,若每个枚举值在调用实现的接口方法呈现相同的行为方式，则只要统一实现该方法即可。若需要每个枚举值在调用实现的接口方法呈现出不同的行为方式,则可以让每个枚举值分别来实现该方法

方法:

- String toString():返回枚举类对象的名称
- values():返回所有的枚举类对象构成的数组
- valueOf(String objName):返回枚举类中对象名是objName的对象,如果没有objName的枚举类对象，则抛IllegalArgumentException异常

## 注解(JDK5.0)

注解(Annotation)其实就是代码里的特殊标记, 这些标记可以在编译, 类加载或运行时被读取, 并执行相应的处理。

### 生成文档相关的注解

- @author 标明开发该类模块的作者，多个作者之间使用,分割
- @version 标明该类模块的版本
- @see 参考转向，也就是相关主题
- @since 从哪个版本开始增加的
- @param 对方法中某参数的说明，如果没有参数就不能写
- @return 对方法返回值的说明，如果方法的返回值类型是void就不能写
- @exception 对方法可能抛出的异常进行说明 ，如果方法没有用throws显式抛出的异常就不能写
- 其中@param @return 和 @exception 这三个标记都是只用于方法的。
  - @param的格式要求：@param 形参名 形参类型 形参说明
  - @return 的格式要求：@return 返回值类型 返回值说明
  - @exception的格式要求：@exception 异常类型 异常说明
  - @param和@exception可以并列多个

### 在编译时进行格式检查的注解(JDK内置的三个基本注解)

- @Override: 限定重写父类方法,
- @Deprecated: 用于表示所修饰的元素(类, 方法等)已过时,通常是因为所修饰的结构危险或存在更好的选择
- @SuppressWarnings: 抑制编译器警告,如`@SuppressWarnings("unused")`

### 实现替代配置文件功能的注解

通常在框架中使用

### 自定义注解

参照@SuppressWarnings定义

- 注解声明为：@interface
- 内部定义成员，通常使用value表示
- 可以指定成员的默认值，使用default定义

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE}) //{}内为参数
public @interface MyAnnotation {
    String value() default "hello";  //成员变量以无参数方法的形式来声明
}
```

- 如果自定义注解没有成员，表明是一个标识作用。
- 如果注解有成员，在使用注解时，需要指明成员的值。
- 自定义注解必须配上注解的信息处理流程(使用**反射**,且注解的生命周期要声明为RUNTIME)才有意义。
- 自定义注解通过都会指明两个元注解：Retention、Target

### 元注解

对注解进行解释说明的注解

- Retention：指定所修饰的 Annotation 的生命周期：SOURCE\CLASS（默认行为）\RUNTIME.只有声明为RUNTIME生命周期的注解，才能通过反射获取。
- Target:用于指定被修饰的 Annotation 能用于修饰哪些程序元素:TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE
- Documented:表示所修饰的注解在被javadoc解析时，保留下来。
- Inherited:被它修饰的 Annotation 将具有继承性。

### JDK8新特性

可重复注解：

```java
@Repeatable(MyAnnotations.class)//在MyAnnotation上声明@Repeatable，成员值为MyAnnotations.class
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
public @interface MyAnnotation {
    String value() default "hello";
}

// MyAnnotations元注解与MyAnnotations相同
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
public @interface MyAnnotations {
    MyAnnotation[] value();//注解数组
}

//使用
//jdk 8之前的写法：
//@MyAnnotations({@MyAnnotation(value="hi"),@MyAnnotation(value="hi")})
@MyAnnotation(value="hi")
@MyAnnotation(value="abc")
```

类型注解：

- ElementType.TYPE_PARAMETER 表示该注解能写在类型变量的声明语句中（如：泛型声明）。
- ElementType.TYPE_USE 表示该注解能写在使用类型的任何语句中。

## 泛型(JDK5)

泛型就是允许在定义类、接口时通过一个标识表示类中某个属性的类型或者是某个方法的返回值及参数类型,这个类型参数将在使用时确定.目的是使类型统一一致.

注意:

- 实例化后，操作原来泛型位置的结构必须与指定的泛型类型一致。
- 泛型的类型必须是类。需要用到基本数据类型的位置，拿包装类替换
- 如果实例化时，没有指明泛型的类型。默认类型为Object类型

### 指定泛型

例子如:`Map<String, Integer> map = new HashMap<String,Integer>();`

- 泛型数组`E[] elements = (E[])new Object[capacity];`
- jdk7新特性：类型推断`Map<String, Integer> map = new HashMap<>();`

### 自定义泛型

泛型类

- 泛型类的构造函数不能加上泛型
- 泛型不同的引用不能相互赋值
- 异常类不能是泛型的
- 父类有泛型，子类可以选择保留泛型也可以选择指定泛型类型：

```java
class A<T1, T2> {}

class B<T2, T3> extends A <Integer, T2> {} //T1指定了泛型,T2保留了泛型,T3是自定义的泛型
```

泛型方法

```java
public class DAO {
    public <E> E get(int id, E e) {
    ...
    }
}
```

- 自定义了泛型的方法才叫泛型方法,而不是使用了类的泛型的方法
- 在静态方法中不能使用类的泛型,因为泛型在创建对象的时候才能确定类型
- 但是泛型方法可以使用自定义的泛型

### 泛型的继承性

- 如果类A是类B的父类，G\<A>和G\<B>二者不具备子父类关系，二者是并列关系。
  - 二者共同的父类是G<?>(?是通配符)
  - 但A<G>是B<G>的父类

通配符泛型的方法:

- 写入：对于List<?>不能向其内部添加数据(除了null),因为不确定类型
- 读取：允许读取数据，读取的数据类型为Object。

注意：

- 不能用在泛型方法声明上，返回值类型前面<>不能使用?，如public static <?> void test(ArrayList<?> list){}
- 不能用在泛型类的声明上，如class GenericTypeClass<?>{}
- 不能用于创建对象，如new ArrayList<?>()

有限制条件的通配符的使用：

- G<? extends A> 只允许泛型为A或A的子类调用
- G<? super A> 只允许泛型为A或A的父类调用
