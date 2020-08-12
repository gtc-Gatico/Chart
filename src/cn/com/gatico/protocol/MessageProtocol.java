package cn.com.gatico.protocol;

import cn.com.gatico.enums.MessageType;

public class MessageProtocol {

    private MessageType type;

    private long length;

    private byte[] data;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
