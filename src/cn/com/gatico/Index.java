package cn.com.gatico;

import cn.com.gatico.protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Index implements ClientRead {
    Map map = new HashMap();
    Map resource = null;
    final Logger logger = Logger.getGlobal();
    private JPanel main;
    private JLabel labImage;
    private JLabel labName;
    private JLabel labStatus;
    private JLabel labSignature;
    private JList listUsers;
    private JScrollPane sp;
    private UserItem selectUserItem;

    public void init() {
        Server.connect();
        JFrame jFrame = new JFrame();
        jFrame.setTitle("XXX聊天系统【在线】");
        jFrame.setBounds(0, 0, 200, 400);
        ImageIcon icon = new ImageIcon(Index.class.getResource("/").getPath() + "/source/icon.jpg");
        Image image = icon.getImage();
        jFrame.setIconImage(image);
        jFrame.setResizable(false);
//        jFrame.setLocationRelativeTo(null);// 设置居中显示
        jFrame.setLocation(new Point(1920 - 500 + 200, 100));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main = new JPanel();
        main.setSize(jFrame.getSize());
        jFrame.setContentPane(main);
        main.setLayout(null);
        image = image.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        labImage = new JLabel(new ImageIcon(image), JLabel.CENTER);
        labImage.setBounds(0, 0, 60, 60);
        main.add(labImage);
        labName = new JLabel("admin", JLabel.CENTER);
        labName.setBounds(60, 10, 40, 20);
        main.add(labName);
        labStatus = new JLabel("[在线]", JLabel.CENTER);
        labStatus.setBounds(110, 10, 40, 20);
        main.add(labStatus);
        labSignature = new JLabel("我是猪猪侠", JLabel.CENTER);
        labSignature.setBounds(70, 30, 60, 20);
        main.add(labSignature);
        DefaultListModel dlm = new DefaultListModel<UserItem>();
        String path = "/source/icon.jpg";
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 1L,"张三", "张三", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 2L,"李四", "李四", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 3L,"王五", "王五", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 4L,"赵六", "赵六", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 5L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 6L,"赵六", "赵六", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 7L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 8L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 9L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 10L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 11L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 12L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 13L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 14L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 15L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 16L,"田七", "田七", "200", "570"));
        dlm.addElement(new UserItem(path, "2018-6-20", "18:17", 17L,"孙八", "孙八", "200", "570"));
        listUsers = new JList(dlm);
        listUsers.setCellRenderer(new UserItemRender());
        listUsers.setFixedCellHeight(50);

        listUsers.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                selectUserItem = ((UserItem) dlm.get(e.getLastIndex()));
            }
        });
        listUsers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    Chat chat = new Chat();
                    chat.setUserName(selectUserItem.getUserName());
                    chat.setUserId(selectUserItem.getUserId());
                    chat.init();
                    map.put(selectUserItem.getUserId(), chat);
                }
            }
        });

        sp = new JScrollPane(listUsers);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setBounds(0, 60, 200, main.getHeight() - 90);
        main.add(sp);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //jFrame.setLocation(Double.valueOf(screenSize.getWidth() / 2 - jFrame.getWidth() / 2).intValue(), Double.valueOf(screenSize.getHeight() / 2 - jFrame.getHeight() / 2).intValue());

        jFrame.setVisible(true);
        /*jFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                sp.setBounds(0, 60, 200, main.getHeight() - 60);
                listUsers.setBounds(sp.getBounds());
            }
        });
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                sp.setBounds(0, 60, 200, main.getHeight() - 60);
                listUsers.setBounds(sp.getBounds());
            }
        });*/

    }

    @Override
    public void onRead(ByteBuffer byteBuffer) {
        byte[] data = byteBuffer.array();
        logger.info("" + data.length);
//        map.get(targetName, chat);
    }
}
