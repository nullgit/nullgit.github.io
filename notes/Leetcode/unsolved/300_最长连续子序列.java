import java.util.*;
//300

class Solution1 {
    public int lengthOfLIS(int[] nums) {
        int ans = 0;
        int[] dp = new int[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            dp[i] = 1;
            for (int j = 0; j < i; ++j) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}

class Solution2 {
    public int lengthOfLIS(int[] nums) {
        ArrayList<Integer> subsequence = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            if (subsequence.isEmpty() || nums[i] > subsequence.get(subsequence.size() - 1)) {
                subsequence.add(nums[i]);
            } else {
                int index = binarySearchIndex(subsequence, nums[i]);
                subsequence.set(index, nums[i]);
            }
        }
        return subsequence.size();
    }

    public int binarySearchIndex(ArrayList<Integer> nums, int target) {
        int left = 0;
        int right = nums.size();
        int mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums.get(mid) > target) {
                right = mid - 1;
            } else if (nums.get(mid) < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}