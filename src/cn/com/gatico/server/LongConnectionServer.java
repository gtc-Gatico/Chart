package cn.com.gatico.server;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LongConnectionServer implements ReadIF{
    private boolean async = false;
    private Map client = new HashMap();

    public void init(int port) {
        try {
            ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
            if (async) {
                new Thread(() -> {
                    try {
                        while(true){
                            Socket accept = serverSocket.accept();
                            client.put(accept.toString(), accept);
                            new Thread(() -> {
                                doRead(accept);
                            }).start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                while(true) {
                    Socket accept = serverSocket.accept();
                    client.put(accept.toString(), accept);
                    new Thread(() -> {
                        doRead(accept);
                    }).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] doRead(Socket socket) {
        return new byte[0];
    }
}
