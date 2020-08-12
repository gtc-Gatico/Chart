package cn.com.gatico;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    static public Index index = null;

    static final Logger logger = Logger.getGlobal();
    static Socket socket = null;
    static InputStream inputStream = null;
    static OutputStream outputStream = null;
    static int serverPort = 0;
    static String serverHost = "";
    static boolean heartFlag = true;

    public static void reConnect() {
        try {
            socket.close();
            socket = new Socket();
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress(serverHost, serverPort));
            logger.info("已经重新连接");
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            read();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void connect() {
        try {
            socket = new Socket();
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress(serverHost, serverPort));
            logger.info("已经连接");
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            heart();
            read();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void write(ByteBuffer data) {
        if (socket != null && socket.isConnected()) {
            try {
                logger.info(socket.toString());
                outputStream.write(data.array());
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "连接服务器失败");
                reConnect();
            }
        } else {
            reConnect();
        }
    }

    public static void onRead(byte[] data) {
        logger.info("读取数据");
        if (data != null) {
            ByteBuffer resData = ByteBuffer.wrap(data);
            logger.info("返回数据：" + String.valueOf(resData.get()));
            index.onRead(resData);
        }
        logger.info("读取完成");
    }

    public static void read() {
        new Thread(() -> {
            if (socket != null && socket.isConnected()) {
                try {
                    logger.info(socket.toString());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte d = -1;
                    while (socket.isConnected()) {
                        d = (byte) inputStream.read();
                        if (d != -1) {
                            byteArrayOutputStream.write(d);
                        } else {
                            onRead(byteArrayOutputStream.toByteArray());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.log(Level.WARNING, "连接服务器失败");
                    Thread.interrupted();
                    reConnect();
                }
            }
        }).start();
    }

    public static void heart() {
        new Thread(() -> {
            while (heartFlag) {
                logger.info("发送心跳");
                ByteBuffer byteBuffer = ByteBuffer.allocate(1 + 8 + 8);
                byteBuffer.put((byte) 1);
                byteBuffer.putLong(8);
                Long userId = Long.valueOf(index.resource.get("userId").toString());
                byteBuffer.putLong(userId);
                write(byteBuffer);
                logger.info("发送完毕");
                try {
                    Thread.sleep(10000L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
