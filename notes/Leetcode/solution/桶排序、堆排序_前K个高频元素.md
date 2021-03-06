# 347. 前 K 个高频元素

给定一个非空的整数数组，返回其中出现频率前 k 高的元素。

示例 1:
输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]

示例 2:
输入: nums = [1], k = 1
输出: [1]

提示：
你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
你可以按任意顺序返回答案。

---

桶排序法：桶排序的意思是为每个值设立一个桶，桶内记录这个值出现的次数（或其它属性），然后对桶进行排序。样例:nums = [1,1,1,1,2,2,3,4], k = 2.针对样例来说，我们先通过桶排序得到三个桶 [1,2,3,4]，它们的值分别为 [4,2,1,1]，表示每个数字出现的次数。紧接着，我们对桶的频次进行排序，前 k 大个桶即是前 k 个频繁的数。这里我们可以使用各种排序算法，甚至可以再进行一次桶排序，把每个旧桶根据频次放在不同的新桶内。针对样例来说，因为目前最大的频次是 4，我们建立 [1,2,3,4] 四个新桶，它们分别放入的旧桶为 [[3,4],[2],[],[1]]，表示不同数字出现的频率。最后，我们从后往前遍历，直到找到 k 个旧桶。

时间复杂度：O(n) 空间复杂度：O(n)  

---

```py
class Solution:
    def topKFrequent(self, nums: List[int], k: int) -> List[int]:
        hashmap = dict()
        for num in nums:
            if num in hashmap:
                hashmap[num] += 1
            else:
                hashmap[num] = 1
        nums = [[] for i in range(len(nums))]
        for key, value in hashmap.items():
            nums[value - 1].append(key)
        
        kk = 0
        ans = []
        for i in range(len(nums) - 1, -1, -1):
            if nums[i] != []:
                for num in nums[i]:
                    ans.append(num)
                    kk += 1
            if kk == k:
                return ans
```

---

堆排序法:待更

---