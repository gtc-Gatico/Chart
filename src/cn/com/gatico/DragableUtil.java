package cn.com.gatico;

import javax.swing.*;
import java.awt.*;

public class DragableUtil {
    Point loc = null;
    Point tmp = null;
    boolean isDragged = false;
    JComponent jComponent = null;

    public DragableUtil(JComponent jComponent) {
        this.jComponent = jComponent;
    }

    public void setjComponent(JComponent jComponent) {
        this.jComponent = jComponent;
    }

    public static void setDragable(JComponent jComponent) {
        DragableUtil dragableUtil = new DragableUtil(jComponent);
        dragableUtil.dragged();
    }

    public void dragged() {
        jComponent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                isDragged = false;
                jComponent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mousePressed(java.awt.event.MouseEvent e) {
                tmp = new Point(e.getX(), e.getY());
                isDragged = true;
                jComponent.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        jComponent.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (isDragged) {
                    loc = new Point(jComponent.getLocation().x + e.getX() - tmp.x,
                            jComponent.getLocation().y + e.getY() - tmp.y);
                    jComponent.setLocation(loc);
                }
            }
        });
    }
}
