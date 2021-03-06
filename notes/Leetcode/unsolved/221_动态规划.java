public class Solution1 {
    public static void main(String[] args) {
        char[][] a = { { '1' } };
        
        // int[] a = { 10, 15, 20 , 25};
        System.out.println(maximalSquare(a));
    }

    public static int minPathSum(int[][] grid) {
        int[] row = new int[grid[0].length];
        for (int i = 0; i < grid[0].length; ++i) {
            row[i] = grid[0][i];
        }
        for (int i = 1; i < grid[0].length; ++i) {
            row[i] = grid[0][i] + row[i - 1];
        }
        for (int i = 1; i < grid.length; ++i) {
            row[0] = row[0] + grid[i][0];
            for (int j = 1; j < grid[0].length; ++j) {
                row[j] = Math.min(row[j - 1], row[j]) + grid[i][j];
            }
        }
        return row[grid[0].length - 1];
    }

    public static int maximalSquare(char[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix[0].length; ++i) {
            dp[0][i] = matrix[0][i] - '0';
        }

        for (int i = 1; i < matrix.length; ++i) {
            dp[i][0] = matrix[i][0] - '0';
            for (int j = 1; j < matrix[0].length; ++j) {
                if (matrix[i][j] == '0') {
                    dp[i][j] = 0;
                }
                else {
                    if (matrix[i - 1][j] == matrix[i][j - 1] 
                    && matrix[i - 1][j] == matrix[i - 1][j - 1]) {
                        if (dp[i - 1][j] == dp[i][j - 1]
                            && dp[i - 1][j] == dp[i - 1][j - 1]) {
                            dp[i][j] = dp[i - 1][j] + 1;
                        }
                        else {
                            dp[i][j] = 2;
                        }
                    }
                    else {
                        dp[i][j] = 1;
                    }
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < dp.length; ++i) {
            for (int j = 0; j < dp[0].length; ++j) {
                if (dp[i][j] > ans) {
                    ans = dp[i][j];
                }
            }
        }
        return ans * ans;
    }
}