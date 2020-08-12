package cn.com.gatico.protocol;

import java.nio.ByteBuffer;

public class Protocol {
    byte type;
    long length;
    byte[] data;

    public byte getType() {
        return type;
    }


    public long getLength() {
        return length;
    }


    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ByteBuffer toBytes() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.data.length + 9);
        byteBuffer.put((byte) 2);
        byteBuffer.putLong(this.data.length);
        byteBuffer.put(this.data);
        return byteBuffer;
    }
}
