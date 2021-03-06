# java note 5 集合

面向对象语言对事物的体现都是以对象的形式，为了方便对多个对象的操作，就要对对象进行存储。另一方面，使用Array存储对象方面具有一些弊端，而Java 集合就像一种容器，可以动态地把多个对象的引用放入容器中。Java 集合类可以用于存储数量不等的多个对象，还可用于保存具有映射关系的关联数组。

框架:

- Collection接口：单列数据，定义了存取一组对象的方法的集合
  - List：元素有序、可重复的集合(动态数组)
    - ArrayList
    - LinkedList
    - Vector
  - Set：元素无序、不可重复的集合
    - HashSet
      - LinkedHashSet
    - TreeSet
- Map接口：双列数据，保存具有映射关系"key-value对"的集合
  - HashMap
    - LinkedHashMap
  - TreeMap
  - Hashtable、
    - Properties

## Collection接口

### Collection方法

- 增
  - add(Object obj)
  - addAll(Collection coll)
- 查
  - int size()
  - boolean isEmpty()
  - boolean contains(Object obj)：通过equals方法判断是否相同
  - boolean containsAll(Collection c)：也是调用元素的equals方法来比较的,判断集合中是否包含c中的全部数据
  - boolean equals(Object obj):判断两个集合是否相等
  - hashCode()获取集合对象的哈希值
- 删
  - void clear()
  - boolean remove(Object obj) ：通过元素的equals方法判断是否是要删除的那个元素。只会删除找到的第一个元素
  - boolean removeAll(Collection coll)：取当前集合的差集
- 改
  - boolean retainAll(Collection c)：把交集的结果存在当前集合中
- 转换
  - Object[] toArray()
- 遍历
  - Iterator iterator()：返回迭代器对象，用于集合遍历

将数组转换为集合:`<Serializable> List<Serializable> Arrays.asList(Serializable... a)`

### Iterator接口

集合元素的遍历操作，使用迭代器Iterator接口

方法:

- boolean hasNext():判断接下来是否有元素
- Object next():指针移动到下一元素并返回
- void remove():删除集合中迭代器所指向的元素

```java
while(iterator.hasNext()) {
    System.out.println(iterator.next());
}
```

注意:

- 集合对象每次调用iterator()方法都得到一个全新的迭代器对象，默认游标都在集合的第一个元素之前
- 不能删除同一个位置的元素两次

### foreach循环(JDK5新增)

用于遍历集合、数组,底层仍是用迭代器实现

```java
//内部仍然调用的是迭代器。
for(Object obj : collection) {
    System.out.println(obj);
}
```

### List子接口

- ArrayList：List接口的主要实现类；线程不安全的，效率高；底层使用Object[] elementData存储
- LinkedList：对于频繁的插入、删除操作，使用此类效率比ArrayList高；底层使用双向链表存储
- Vector：作为List接口的古老实现类；线程安全的，效率低；底层使用Object[] elementData存储

#### ArrayList

- 底层创建了长度是10的Object[]数组elementData
- 如果添加导致底层elementData数组容量不够，则扩容。默认情况下，扩容为原来的容量的1.5倍，同时需要将原有数组中的数据复制到新的数组中。
- 建议开发中使用带参的构造器
- JDK8中第一次调用add()时，底层才创建了长度为10的数组，并将数据添加到elementData中

#### LinkedList

- 底层使用双向链表实现
- 内部声明了Node类型的first和last属性，默认值为null

#### Vector

略

#### List新增方法

- void add(int index, Object ele):在index位置插入ele元素
- boolean addAll(int index, Collection eles):从index位置开始将eles中的所有元素添加进来
- Object get(int index):获取指定index位置的元素
- int indexOf(Object obj):返回obj在集合中首次出现的位置
- int lastIndexOf(Object obj):返回obj在当前集合中末次出现的位置
- Object remove(int index):移除指定index位置的元素，并返回此元素
- Object set(int index, Object ele):设置指定index位置的元素为ele,并返回被替换的元素
- List subList(int fromIndex, int toIndex):返回从fromIndex到toIndex位置的子集合

### Set子接口

- HashSet：作为Set接口的主要实现类；线程不安全的；可以存储null值
- LinkedHashSet：作为HashSet的子类；遍历其内部数据时，可以按照添加的顺序遍历。对于频繁的遍历操作，LinkedHashSet效率高于HashSet.
- TreeSet：可以按照添加对象的指定属性，进行排序。

#### Set特性

- 无序性：不等于随机性。存储的数据在底层数组中并非按照数组索引的顺序添加，而是根据数据的哈希值决定的。
- 不可重复性：保证添加的元素按照equals()判断时，不能返回true.即：相同的元素只能添加一个。

以HashSet为例说明:向HashSet中添加元素a,首先调用元素a所在类的hashCode()方法，计算元素a的哈希值，然后此哈希值接着通过与运算计算出在HashSet底层数组中的存放位置（即为：索引位置），判断数组此位置上是否已经有元素：

- 如果此位置上没有其他元素，则元素a添加成功。
- 如果此位置上有其他元素b(或以链表形式存在的多个元素），则比较元素a与元素b的hash值：
  - 如果hash值不相同，则元素a添加成功。
  - 如果hash值相同，进而需要调用元素a所在类的equals()方法：
    - equals()返回true,元素a添加失败
    - equals()返回false,则元素a添加成功。

向Set中添加的数据，其所在的类一定要重写hashCode()和equals(),且要求相等的对象必须具有相等的散列码.对象中用equals() 方法比较的 Field，都应该用来计算 hashCode 值。

#### HashSet

底层使用HashMap实现,数组+链表的结构

- 元素a 与已经存在指定索引位置上数据以链表的方式存储。
  - jdk 7 :元素a放到数组中，指向原来的元素。
  - jdk 8 :原来的元素指向元素a

#### LinkedHashSet

底层使用LinkedHashMap实现,在添加数据的同时，每个数据还维护了两个引用，记录此数据前一个数据和后一个数据。

#### TreeSet

底层使用TreeMap实现,红黑树结构.向TreeSet中添加的数据，要求是相同类的对象。因为TreeSet通过一个类的compareTo()或compare()方法排序.且比较两个对象是否相同的标准为比较器返回0,而不再使用equals().

#### Set无新增方法

## Map接口

Map中的key是无序的、不可重复的，value是可重复的

- HashMap:作为Map的主要实现类；线程不安全的，效率高；可以存储null的key和value
  - LinkedHashMap:保证在遍历map元素时，可以按照添加的顺序实现遍历。对于频繁的遍历操作，此类执行效率高于HashMap。
- TreeMap:会对添加的key-value对进行排序，实现排序遍历。
- Hashtable:作为古老的实现类；线程安全的，效率低；不能存储null的key和value
  - Properties:常用来处理配置文件。key和value都是String类型

### Map方法

- 增/改
  - Object put(Object key,Object value)：将指定key-value添加(或修改)到当前map对象中
  - void putAll(Map m):将m中的所有key-value对存放到当前map中
- 删
  - Object remove(Object key)：移除指定key的key-value对，并返回value
  - void clear()：清空当前map中的所有数据
- 查
  - Object get(Object key)：获取指定key对应的value
  - boolean containsKey(Object key)：是否包含指定的key
  - boolean containsValue(Object value)：是否包含指定的value
  - int size()：返回map中key-value对的个数
  - boolean isEmpty()：判断当前map是否为空
  - boolean equals(Object obj)：判断当前map和参数对象obj是否相等
- 遍历
  - Set keySet()：返回所有key构成的Set集合
  - Collection values()：返回所有value构成的Collection集合
  - Set entrySet()：返回所有key-value对构成的Set集合,entrySet集合中的元素都是Map.Entry
    - entry.getKey():返回entry的key
    - entry.getValue():返回entry的value

### HashMap

jdk7:

在实例化以后，底层创建了长度是16的一维数组Entry[] table(或其他2的幂次值)。map.put(key, value):与set.add(value)方法类似,不同的地方是:计算哈希值的时候使用的是key;对key相同的情况进行的是value替换.
在添加元素当超出临界值(且要存放的位置非空)时，默认的扩容方式是扩容为原来容量的2倍，并将原有的数据经过重新计算哈希值复制过来。

jdk8:

- 实例化后没有立即创建一个长度为16的数组,在加入第一个元素时创建
- 底层的数组是：Node[], 而非Entry[]
- 增加元素时将原来的元素指向新增的元素;而不是让新增的元素放入数组中,新的元素指向原来的元素
- 当数组的某一个索引位置上的元素以链表形式存在的数据个数>8且当前数组的长度>64时，此时此索引位置上的所数据改为使用红黑树存储。但当当前数组的长度<=64时,先扩容.

一些常量:

- DEFAULT_INITIAL_CAPACITY : HashMap的默认容量:16
- DEFAULT_LOAD_FACTOR：HashMap的默认加载因子：0.75
- threshold：扩容的临界值，容量 \* 填充因子：16 * 0.75 = 12
  - 当达到扩容临界值但新增元素所在位置还没有元素时,还不必扩容
- TREEIFY_THRESHOLD：Bucket中链表长度大于该默认值，转化为红黑树:8
- MIN_TREEIFY_CAPACITY：桶中的Node被树化时最小的hash表容量:64

### LinkedHashMap

在原有的HashMap底层结构基础上，添加了一对指针，指向前一个和后一个元素。

### TreeMap

和TreeSet类似,向TreeMap中添加的数据，要求key是相同类的对象;TreeMap通过key的compareTo()或compare()方法排序.

### Properties

常用来处理配置文件

```java
Properties pros = new Properties();
fis = new FileInputStream("xxx.properties");
pros.load(fis);
String name = pros.getProperty("name");
String password = pros.getProperty("password");
System.out.println("name = " + name + ", password = " + password);
//关闭: fis.close();
```

配置文件中的内容:(中文需要用ASCII码存储)

```Properties
name=jack
password=1234
```

## Colletions

操作Collection,Map的工具类

### Collections方法

- reverse(List)：反转 List 中元素的顺序
- shuffle(List)：对 List 集合元素进行随机排序
- sort(List)：根据元素的自然顺序对指定 List 集合元素按升序排序
- sort(List, Comparator)：根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
- swap(List, int, int)：将指定 list 集合中的 i 处元素和 j 处元素进行交换
- Object max(Collection)：根据元素的自然顺序，返回给定集合中的最大元素
- Object max(Collection, Comparator)：根据 Comparator 指定的顺序，返回给定集合中的最大元素
- Object min(Collection)
- Object min(Collection, Comparator)
- int frequency(Collection, Object)：返回指定集合中指定元素的出现次数
- void copy(List dest, List src)：将src中的内容复制到dest中
  - copy前要保证dest的长度与src的长度相同`List dest = Arrays.asList(new Object[list.size()]);`
- boolean replaceAll(List list, Object oldVal, Object newVal)：使用新值替换 List 对象的所有旧值
- Collection<T> synchronizedCollection(Collection<T> c): 将指定集合包装成线程同步的集合，从而可以解决多线程并发访问集合时的线程安全问题
