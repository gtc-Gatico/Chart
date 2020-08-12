package cn.com.gatico;

import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.ByteBuffer;

public class Chat {
    public String userName = "";
    public Long userId = 0L;

    public void init() {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("与" + userName + "聊天中");
        jFrame.setSize(500, 500);
        ImageIcon icon = new ImageIcon(Chat.class.getResource("/").getPath() + "/source/icon.jpg");
        Image image = icon.getImage();
        jFrame.setIconImage(image);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);// 设置居中显示
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //添加主布局页面
        JPanel main = new JPanel();
        main.setLayout(null);
        main.setLocation(0, 0);
        main.setSize(jFrame.getSize());
        jFrame.setContentPane(main);
        //添加信息框
        JPanel showMsg = new JPanel();
        showMsg.setLayout(null);
        showMsg.setBackground(Color.CYAN);
        showMsg.setBounds(0, 0, 400, 300);
        main.add(showMsg);
        TextPanel textPanel = new TextPanel();
        textPanel.setMessage(new Message("你好", "/source/icon.jpg"));
        textPanel.setSize(400, 50);
        textPanel.setLocation(0, 0);
        showMsg.add(textPanel);
        //添加发送信息框
        JPanel sendMsg = new JPanel();
        sendMsg.setBackground(Color.WHITE);
        sendMsg.setLayout(null);
        sendMsg.setBounds(0, 300, 400, 200);
        main.add(sendMsg);

        //文本框
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(0, 0, 400, (200 - 25 * 2 - 10));
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBorder(new LineBorder(Color.green));
        JTextArea sendTextMsg = new JTextArea("6666666666");
        sendTextMsg.setColumns(200);
        sendTextMsg.setLineWrap(true);
        sendTextMsg.setWrapStyleWord(true);
        jScrollPane.getViewport().add(sendTextMsg);
        sendMsg.add(jScrollPane);

        //添加发送按钮
        JButton jButton = new JButton("发送");
        jButton.setBounds((400 - 65 - 5), (200 - 25 * 2 - 10), 65, 25);
        sendMsg.add(jButton);
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //发送
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("userId", userId);
                jsonObject.addProperty("messageType", "txt");
                jsonObject.addProperty("message", sendTextMsg.getText());
                byte[] bytes = jsonObject.toString().getBytes();
                ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + 9);
                byteBuffer.put((byte) 2);
                byteBuffer.putLong(bytes.length);
                byteBuffer.put(bytes);
                Server.write(byteBuffer);
            }
        });
        //添加广告栏
        JPanel ad = new JPanel();
        ad.setBackground(Color.GREEN);
        ad.setBounds(400, 0, 100, 500);
        main.add(ad);

        jFrame.setVisible(true);

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void onMessage(ByteBuffer byteBuffer) {

    }

    public static void main(String[] args) {
        new Chat().init();
    }


}
