package cn.com.gatico;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("测试窗口");
        frame.setSize(400, 250);
        frame.getContentPane().add(new Login(frame).getPanel());
        //frame.setUndecorated(true); // 去掉窗口的装饰
        //frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);//采用指定的窗口装饰风格
        //获取屏幕大小
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //frame.setLocation(Double.valueOf(screenSize.getWidth() / 2 - frame.getWidth() / 2).intValue(), Double.valueOf(screenSize.getHeight() / 2 - frame.getHeight() / 2).intValue());
        frame.setLocationRelativeTo(null);// 设置居中显示
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            File icon = new File(Window.class.getResource("/").getPath() + "/source/icon.jpg");
            Image image = ImageIO.read(icon);
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
