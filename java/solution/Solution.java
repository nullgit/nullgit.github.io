package solution;

import java.util.*;

class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0]) {
                    return 1;
                } else if (o1[0] < o2[0]) {
                    return -1;
                } else {
                    return -Integer.compare(o1[1], o2[1]);
                }
            }
        });

        ArrayList<Integer> subsequence = new ArrayList<>();
        for (int i = 0; i < envelopes.length; ++i) {
            if (subsequence.isEmpty() || envelopes[i][1] > subsequence.get(subsequence.size() - 1)) {
                subsequence.add(envelopes[i][1]);
            } else {
                int index = binarySearchIndex(subsequence, envelopes[i][1]);
                subsequence.set(index, envelopes[i][1]);
            }
        }
        return subsequence.size();
    }

    public int binarySearchIndex(ArrayList<Integer> nums, int target) {
        int left = 0;
        int right = nums.size() - 1;
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