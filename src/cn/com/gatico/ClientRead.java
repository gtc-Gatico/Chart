package cn.com.gatico;

import java.nio.ByteBuffer;

public interface ClientRead {
    public void onRead(ByteBuffer byteBuffer);
}
