package cn.com.gatico;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Window {
    private static JFrame frame;

    public static void main(String[] args) {
        // 创建 JFrame 实例
        frame = new JFrame("登录");
        // Setting the width and height of frame
        frame.setSize(350, 200);
        //获取屏幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(Double.valueOf(screenSize.getWidth() / 2 - frame.getWidth() / 2).intValue(), Double.valueOf(screenSize.getHeight() / 2 - frame.getHeight() / 2).intValue());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            File icon = new File(Window.class.getResource("/").getPath() + "/source/icon.jpg");
            Image image = ImageIO.read(icon);
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    public static void closeThis() {
        frame.dispose();
    }

    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setFont(Font.getFont("微软雅黑"));
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setText("admin");
        userText.setBounds(100, 20, 165, 25);
        userText.setBorder(new MyLineBorder(Color.GREEN, 1, false));
        userText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                userText.setBorder(new MyLineBorder(Color.GREEN, 1, false));
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(Font.getFont("微软雅黑"));
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        passwordText.setBorder(new MyLineBorder(Color.GREEN, 1, false));
        passwordText.setText("admin");
        passwordText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                passwordText.setBorder(new MyLineBorder(Color.GREEN, 1, false));
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new MyButton("登录");
        loginButton.setBounds(100, 80, 80, 25);
        loginButton.addActionListener(e -> {
            String user = userText.getText();
            char[] password = passwordText.getPassword();
            System.out.println(user + "\t" + new String(password));
            if (user.equals("admin")) {
                if (new String(password).equals("admin")) {
                    closeThis();
                } else {
                    passwordText.setBorder(new MyLineBorder(Color.RED, 1, false));
                }
            } else {
                userText.setBorder(new MyLineBorder(Color.RED, 1, false));

            }
        });
        loginButton.setHorizontalAlignment(JButton.CENTER);
        panel.add(loginButton);

    }

}
