package cn.com.gatico;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UserItemRender extends DefaultListCellRenderer {
    private UserItem userItem;
    private boolean isSelected;
    private JComponent jlist;
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        jlist = list;
        if (value instanceof UserItem) {
            userItem = (UserItem) value;
        }
        setBackground(Color.WHITE);
        this.isSelected = isSelected;
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(isSelected){
            g.setColor(Color.GREEN);
            g.fillRect(jlist.getX(),jlist.getY(),jlist.getWidth(),jlist.getHeight());
            g.setColor(Color.BLACK);
        }
        try {
            g.drawImage(ImageIO.read(new File(getClass().getResource("/").getPath() + userItem.getImg())), 0, 0, 50, 50, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawString(userItem.getTitle(), 60, 20);
        g.drawString(userItem.getUserName() + ":" + userItem.getMsg(), 50, 40);
        g.drawString(userItem.getNum(), 160, 20);
        g.drawString(userItem.getTime(), 160, 40);
        super.paintComponent(g);
    }
}
