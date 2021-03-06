package solution;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Sender {
    public static void main(String[] args) {
        //客户端
        try (Socket socket = new Socket(InetAddress.getByName("192.168.1.35"), 7777);
                OutputStream os = socket.getOutputStream();
                FileInputStream fis = new FileInputStream(new File("./solution/tmp.txt"));
             ) {
            // os.write("你好，我是客户端".getBytes());
            byte[] b = new byte[10];
            int len;
            while ((len = fis.read(b)) != -1) {
                os.write(b, 0, len);
            }
            System.out.println("传输完成");
        } catch (Exception e) {
            e.getStackTrace();
        }
        
    }
}
