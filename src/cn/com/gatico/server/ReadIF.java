package cn.com.gatico.server;

import java.net.Socket;

public interface ReadIF {
    byte [] doRead(Socket socket);
}
