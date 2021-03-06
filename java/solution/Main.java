package solution;

import java.math.BigInteger;
import java.util.*;
// 1:无需package
// 2: 类名必须Main, 不可修改

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //在此输入您的代码...
        int n = scan.nextInt();
        BigInteger ans = BigInteger.valueOf(1);
        for (int i = 1; i <= n; ++i) {
            ans = ans.divide(maxFactor(ans, BigInteger.valueOf(i)));
            ans = ans.multiply(BigInteger.valueOf(i));
        }
        System.out.println(ans);
        scan.close();
    }

    public static BigInteger maxFactor(BigInteger x, BigInteger y) {
        BigInteger a;
        BigInteger b;
        if (x.compareTo(y) > 0) {
            a = x;
            b = y;
        }
        else {
            a = y;
            b = x;   
        }
        while (true) {
            BigInteger mod = a.mod(b);
            if (mod.equals(BigInteger.valueOf(0))) {
                return b;
            }
            else {
                a = b;
                b = mod;
            }
        }
    }
}
