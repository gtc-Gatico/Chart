package cn.com.gatico;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class Login {
    private Gson gson = new Gson();
    private JFrame rootFrame;
    private JPanel panel;
    private JLabel lblTitle;
    private JLabel lblUserName;
    private JLabel lblUserPwd;
    private JTextField txtUserName;
    private JPasswordField txtUserPwd;
    private JButton btnLogin;

    public Login(JFrame rootFrame) {
        this.rootFrame = rootFrame;
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 400, 250);
        lblTitle = new JLabel("欢迎登录", JLabel.CENTER);
        lblTitle.setFont(new Font("微软雅黑", 1, 15));
        lblTitle.setBounds(0, 0, 400, 50);
        panel.add(lblTitle);

        lblUserName = new JLabel("用户名：", JLabel.RIGHT);
        lblUserName.setBounds(50, 80, 50, 25);
        panel.add(lblUserName);

        txtUserName = new JTextField();
        txtUserName.setBounds(100, 80, 200, 25);
        panel.add(txtUserName);

        lblUserPwd = new JLabel("密码：", JLabel.RIGHT);
        lblUserPwd.setBounds(50, 110, 50, 25);
        panel.add(lblUserPwd);

        txtUserPwd = new JPasswordField();
        txtUserPwd.setBounds(100, 110, 200, 25);
        panel.add(txtUserPwd);

        btnLogin = new JButton("登录");
        btnLogin.setHorizontalAlignment(SwingConstants.CENTER);
        btnLogin.setBounds(75, 150, 250, 30);
        panel.add(btnLogin);

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                login();
            }
        });
        txtUserName.registerKeyboardAction(e -> txtUserPwd.requestFocus(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
        txtUserPwd.registerKeyboardAction(e -> login(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    Point loc = null;
    Point tmp = null;
    boolean isDragged = false;

    public void setDragable() {
        rootFrame.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                rootFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mousePressed(MouseEvent e) {
                tmp = new Point(e.getX(), e.getY());
                isDragged = true;
                rootFrame.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        rootFrame.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isDragged) {
                    loc = new Point(rootFrame.getLocation().x + e.getX() - tmp.x,
                            rootFrame.getLocation().y + e.getY() - tmp.y);
                    rootFrame.setLocation(loc);
                }
            }
        });
    }  //在初始化该组件的时候调用 setDragable() 就可以使组件具体拖放窗体的功能了。因为可能有背景图，可能会重写paint方法，不能在paint方法中调用setDragable()

    public void login() {
        String user = txtUserName.getText();
        char[] password = txtUserPwd.getPassword();
        try {
            if (user.length() == 0) {
                txtUserName.setBorder(new MyLineBorder(Color.RED, 1, false));
                return;
            }
            if (password.length == 0) {
                txtUserPwd.setBorder(new MyLineBorder(Color.RED, 1, false));
                return;
            }
            URLConnection urlConnection = new URL("http://127.0.0.1:8080/api/getUser?user=" + user).openConnection();
            BufferedReader bf = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String tmp = "";
            while ((tmp = bf.readLine()) != null) {
                stringBuffer.append(tmp);
            }
            JsonObject obj = gson.fromJson(stringBuffer.toString(), JsonObject.class);
            Map map = gson.fromJson(obj.get("resource"), Map.class);
            if (obj != null && user.equals(map.get("name").toString())) {
                txtUserName.setBorder(new MyLineBorder(Color.GREEN, 1, false));
                if (new String(password).equals(map.get("password").toString())) {
                    txtUserPwd.setBorder(new MyLineBorder(Color.GREEN, 1, false));
                    JOptionPane.showConfirmDialog(null, "登录成功,欢迎你 " + user, "提示", JOptionPane.PLAIN_MESSAGE);
                    Index index = new Index();
                    Server.index = index;
                    index.resource = map;
                    Server.serverHost = obj.get("serverHost").getAsString();
                    Server.serverPort = obj.get("serverPort").getAsInt();
                    index.init();
                    rootFrame.dispose();
                } else {
                    txtUserPwd.setBorder(new MyLineBorder(Color.RED, 1, false));
                    JOptionPane.showConfirmDialog(null, "密码错误，请重试", "提示", JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                txtUserName.setBorder(new MyLineBorder(Color.RED, 1, false));
                JOptionPane.showConfirmDialog(null, "用户不存在，请重新输入", "提示", JOptionPane.PLAIN_MESSAGE);
            }


        } catch (ConnectException e) {
            JOptionPane.showConfirmDialog(null, "网络连接失败，请检查网络", "提示", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "未知错误，请重试", "提示", JOptionPane.PLAIN_MESSAGE);

        }

    }
}
