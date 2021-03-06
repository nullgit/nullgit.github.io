import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Java1 {
    public static void main(String[] args){
            ServerSocket ss = null;
            Socket socket = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                //1.创建服务器端的ServerSocket，指明自己的端口号
                ss = new ServerSocket(8899);
                //2.调用accept()表示接收来自于客户端的socket
                socket = ss.accept();
                //3.获取输入流
                is = socket.getInputStream();
    
                //不建议这样写，可能会有乱码
                //        byte[] buffer = new byte[1024];
                //        int len;
                //        while((len = is.read(buffer)) != -1){
                //            String str = new String(buffer,0,len);
                //            System.out.print(str);
                //        }
                //4.读取输入流中的数据
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[5];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
    
                System.out.println(baos.toString());
    
                System.out.println("收到了来自于：" + socket.getInetAddress().getHostAddress() + "的数据");
    
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (baos != null) {
                    //5.关闭资源
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (is != null) {
                    try {
                        is.close();
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
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
    
            }
    
        }
    
    }
    