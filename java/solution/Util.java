package solution;

import java.io.FileInputStream;
import java.util.*;

public class Util {
    public static void printListList(List<List<Integer>> ans) {
        for (int i = 0; i < ans.size(); ++i) {
            for (int j = 0; j < ans.get(i).size(); ++j) {
                System.out.print(ans.get(i).get(j));
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public static void printList(List<Integer> ans) {
        for (int i = 0; i < ans.size(); ++i) {
            System.out.print(ans.get(i));
            System.out.print(' ');
        }
    }

    public static void printIntInt(int[][] a) {
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[i].length; ++j) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printInt(int[] a) {
        System.out.println(Arrays.toString(a));
    }

    public int binaryInsert(List<Integer> window, int x) {
        int l = 0;
        int r = window.size() - 1;
        int mid;
        while (r >= l) {
            mid = (l + r) / 2;
            if (window.get(mid) > x) {
                r = mid - 1;
            } else if (window.get(mid) < x) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return l;
    }
    
    public static int[] Int() {
        ArrayList<Integer> ans = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream("./solution/tmp.txt"));) {
            while (scanner.hasNextInt()) {
                ans.add(scanner.nextInt());
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        int[] nums = new int[ans.size()];
        for (int i = 0; i < ans.size(); ++i) {
            nums[i] = ans.get(i);
        }
        return nums;
    }

    public static int[][] IntInt() {
        ArrayList<Integer> ans = new ArrayList<>();
        int[][] nums = null;
        try (Scanner scanner = new Scanner(new FileInputStream("./solution/tmp.txt"));) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            while (ans.size() < m * n) {
                ans.add(scanner.nextInt());
            }
            nums = new int[m][n];
            int index = 0;
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j, ++index) {
                    nums[i][j] = ans.get(index);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return nums;
    }
}

class UnionFind0 {
    int[] id;
    int countConnected;

    UnionFind(int n) {
        id = new int[n];
        for (int i = 0; i < n; ++i) {
            id[i] = i;
        }
        countConnected = n;
    }

    int find(int x) {
        while (id[x] != x) {
            id[x] = id[id[x]];
            x = id[x];
        }
        return x;
    }

    boolean connect(int x, int y) {
        int findx = find(x);
        int findy = find(y);
        if (findx == findy) {
            return true;
        }
        id[findx] = findy;
        --countConnected;
        return false;
    }

    int getCountConnected() {
        return countConnected;
    }
}