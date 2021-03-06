# **The Basic Usage of C++ STL**

---

## **Vector&List**

---

the common operator:  
|function|action|
|:---|:---|
|int size()const|...|
|void clear()|...|
|bool empty()|...|
|void push_back()|...|
|void pop_back()|...|
|const object & back()const|return the last element|
|const object & front()const|...|

list:

vector:
|function|action|
|:---|:---|
|vector<T> (int n, const T&value)|n is the number of the elements, and value is the default.|
|vector<T> (Itr first, Itr last)|Initialize the vector with the sequence between Itr first and Itr last.|
|resize(int n)|...|


## **Stack&Queue**

---

Stack:
|function|action|
|:---|:---|
push()|
pop()|
top()|
empty()|

Queue:
|function|action|
|:---|:---|
push()|
pop()|
front()|
back()|
empty()|
size()|
    defination:
    stack<data type, container adapter> 
    container adapter: vector, list, deque

## **Priority queue**

---

|function|action|
|:---|:---|
priority_queue<int, vector<int>, greater<int> > q|
void push(const Object& x)|
const Object &top() const|
void pop()|
bool empty()|
void clear()|

## **String**

---

|function|action|
|:---|:---|
substr(start = 0, len = s.size - start)|
capacity()|
length()|
find(s)|Find the position of the substr s
insert(pos, s)|Insert substr s in front of the position pos
operation: =, +, +=, [], comparison

## **Sort**

---

function|action
:---|:---
sort()|sort the vector or similar container  
stable_sort()|...

There are three parameters: the first two parameters are iterators, expressing the range of sorts; the last one is comparative method(less or greater).

## **Set**

---

function|action
:---|:---
pair<iterator, bool> insert(const object &x)|return an object called pair which has two member functions: first() and second()
int erase(int object &x)|return the number of element which has been erased(0 or 1)
iterator erase(iterator itr)|return the iterator of the next element
iterator erase(iterator start, iterator end)|erase the element(s) between start and end

s.clear()       删除set容器中的所有的元素
 
s.empty() 　　　 判断set容器是否为空
 
s.size()

## **Map**

---

function|action
:---|:---
map<int, int> m|
insert(pair<int, int>())|
pair<int, int> erase(first)|
pair<int, int> find(first)|


## **Find**

---

function|action
:---|:---
iterator find(iterator begin, iterator end, const object&x) const|return the target's position, and if target doesn't exit, return the end.
