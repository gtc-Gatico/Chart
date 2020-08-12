package cn.com.gatico;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    public TextPanel() {
        this.setLayout(null);
    }

    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;

    }

    @Override
    protected void printComponent(Graphics g) {
   /*     try {
            g.drawImage(ImageIO.read(new File(getClass().getResource("/").getPath() + message.getImg())), 0, 0, 50, 50, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        this.setBackground(Color.RED);
        g.drawString(message.getMsg(), 0, 0);
        super.printComponent(g);
    }
}
