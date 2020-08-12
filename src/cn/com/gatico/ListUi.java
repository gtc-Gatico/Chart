package cn.com.gatico;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicListUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ListUi extends BasicListUI {


    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }

    @Override
    protected void paintCell(Graphics g, int row, Rectangle rowBounds, ListCellRenderer cellRenderer, ListModel dataModel, ListSelectionModel selModel, int leadIndex) {
        try {
            UserItem userItem = (UserItem) dataModel.getElementAt(row);
            g.drawImage(ImageIO.read(new File(getClass().getResource("/").getPath() + userItem.getImg())), 0, 0, 50, 50, null);
            g.drawString(userItem.getTitle(), 60, 10);
            g.drawString(userItem.getUserName() + ":" + userItem.getMsg(), 60, 30);
            g.drawString(userItem.getNum(), 170, 10);
            g.drawString(userItem.getTime(), 170, 30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
