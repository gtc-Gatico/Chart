package cn.com.gatico;

import javax.swing.*;

public class WindowStyle {
    // 1、Metal风格(默认)
    public static String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";

    //2、Windows风格
    public static String WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    //3、Windows Classic风格
    public static String WINDOWS_CLASSIC = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";

    //4、Motif风格
    public static String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

    //5、Mac风格(需要在相关的操作系统上方可实现)
    public static String MAC = "com.sun.java.swing.plaf.mac.MacLookAndFeel";

    //6、GTK风格(需要在相关的操作系统上方可实现)
    public static String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";

    //7、可跨平台的默认风格
    public static String CROSSPLAT = UIManager.getCrossPlatformLookAndFeelClassName();

    //8、当前系统的风格
    public static String CURRENT = UIManager.getSystemLookAndFeelClassName();
}
