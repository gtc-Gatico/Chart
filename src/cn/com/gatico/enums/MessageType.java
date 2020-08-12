package cn.com.gatico.enums;

public enum  MessageType {
    HEART(0,"心跳"),
    DATA(1,"数据"),

    ;

    private int code;
    private String description;
    MessageType(int code,String description) {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
