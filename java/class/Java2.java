import java.util.*;
import java.net.*;
import java.io.*;

public class Java2 {
        public static void main(String[] args) {    
            Socket socket = null;
            OutputStream os = null;
            try {
                //1.创建Socket对象，指明服务器端的ip和端口号
                InetAddress inet = InetAddress.getByName("127.0.0.1");
                socket = new Socket(inet, 8899);
                //2.获取一个输出流，用于输出数据
                os = socket.getOutputStream();
                //3.写出数据的操作
                os.write("你好，我是客户端mm".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //4.资源的关闭
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
    
                }
            }
        }
    
    }
