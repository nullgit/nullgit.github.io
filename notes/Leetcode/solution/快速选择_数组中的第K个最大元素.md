# 215. 数组中的第K个最大元素

在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

示例 1:
输入: [3,2,1,5,6,4] 和 k = 2
输出: 5

示例 2:
输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
输出: 4

说明:
你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。

---

快速选择法：快速选择一般用于求解k-th Element问题,实现上与快速排序类似,不过只需要找到第k大的枢即可,不需要左右再进行排序.与快速排序一样,快速选择首先要打乱数组,否则最坏情况下时间复杂度为O($n^2$)
时间复杂度：O(n) 空间复杂度：O(1)  

---

```py
class Solution:
    def findKthLargest(self, nums: List[int], k: int) -> int:
        l = 0
        r = len(nums) - 1
        indexOfKthLargest = len(nums) - k
        while True:
            index = self.quickSelect(nums, l, r)
            if index == indexOfKthLargest:
                return nums[index]
            elif index < indexOfKthLargest:
                l = index + 1
            else:
                r = index - 1

    def quickSelect(self, nums: List[int], l: int, r: int) -> int:
        if r - l < 1:
            return l
        random.seed(time)
        pivot = random.randint(l, r)
        [nums[l], nums[pivot]] = [nums[pivot], nums[l]]
        pivot = l
        l += 1
        while l < r:
            while l < r and nums[l] <= nums[pivot]:
                l += 1
            while l < r and nums[r] > nums[pivot]:
                r -= 1
            if l < r:
                [nums[l], nums[r]] = [nums[r], nums[l]]
        if nums[l] > nums[pivot]:
            [nums[l - 1], nums[pivot]] = [nums[pivot], nums[l - 1]]
            return l - 1
        else:
            [nums[l], nums[pivot]] = [nums[pivot], nums[l]]
            return l
```
