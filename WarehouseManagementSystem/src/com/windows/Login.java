package src.com.windows;

import src.com.dao.LoginDao;
import src.com.dao.SupplierManageDao;
import src.com.style.Style;
import src.com.tool.Tool;
import src.manage.panel.InStockPan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//登录界面
public class Login {
    final int WIDTH = 500; //设置顶层框架的宽度
    final int HEIGHT = 250; //设置顶层框架的高度
    String title;
    JFrame jframe = new JFrame(); //窗口

    FlowLayout flowLayout; //定义一个布局（流布局）

    //将这两个文本框（账号，密码用的）设置成全局静态变量（便于反复使用）
    public static JTextField jtextfield;
    public static JPasswordField jpasswordfield;

    Login(String title) {
        this.title = title;
        init();
        jframe.setVisible(true); //设置当前窗口是否可显示
        jframe.setResizable(false); //设置窗口的大小不可变
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //设置默认关闭方式
        jframe.validate(); //让组件生效
    }
    private void init() {
        //设置标题
        jframe.setTitle(title);

        //设置当前窗口大小及位置居中
        //因为有多个窗口需要使用这部分代码，故将其移入一个工具类里面
        Tool.setWindowPosCenter(WIDTH, HEIGHT, jframe);

        //流布局
        flowLayout = new FlowLayout(flowLayout.CENTER); //flowLayout.LEFT表示左对齐；flowLayout.CENTER是居中对齐
        jframe.setLayout(null); //先将窗口布局设置为空布局
        Style style = new Style();
        // 添加背景图片
        ImageIcon img = new ImageIcon("src/img/Login.png"); //将图片读取到img变量里面
        JLabel bgimg = new JLabel(img); //用img变量初始化标签bgimg，所用标签bgimg一显示就是这个图片了
        bgimg.setBounds(0, 0, img.getIconWidth(), img.getIconHeight()); //设置背景图片，设置背景位置

        // 定义两个方框（用于装标题及账号密码）
        //  1.标题
        JPanel jpanel1 = new JPanel();
        jpanel1.setLayout(flowLayout); //设置一个布局为流布局。流布局相当于一个窗口，它会自动为我们调整大小和位置，前提是依次向后排列
        jpanel1.setBounds(0, 0, 500, 45);
        //jpanel1.setBackground(Color.cyan); //调试用
        //   添加标题
        JLabel jlabel1 = new JLabel("仓库管理系统登录");
        jlabel1.setFont(style.title);
        jpanel1.add(jlabel1); //把标签1添加进方框1里面
        jpanel1.setOpaque(false); //设置透明（将方框设置为透明）

        //  2.账号密码
        JPanel jpanel2 = new JPanel();
        jpanel2.setLayout(flowLayout); //设置一个布局为流布局。流布局相当于一个窗口，它会自动为我们调整大小和位置，前提是依次向后排列
        jpanel2.setBounds(125, 45, 240, 230);
        //jpanel2.setBackground(Color.black); //调试用
        jpanel2.setOpaque(false); //设置透明（将方框设置为透明）
        //  2.1.1添加账号
        JLabel jlabel2 = new JLabel("账号");
        jlabel2.setFont(style.account);
        jpanel2.add(jlabel2); //把标签2添加进方框2里面
        //  2.1.2添加账号文本框
        jtextfield = new JTextField(18); // 18表示宽度为18
        jtextfield.setFont(style.accounttext);
        jpanel2.add(jtextfield); //把文本框添加进方框2里面

        //  2.2.1添加密码
        JLabel jlabel3 = new JLabel("密码");
        jlabel3.setFont(style.account);
        jpanel2.add(jlabel3); //把标签3添加进方框2里面
        //  2.2.2添加密码文本框
        jpasswordfield = new JPasswordField(18); // 18表示宽度为18
        jpasswordfield.setFont(style.accounttext);
        jpanel2.add(jpasswordfield); //把文本框添加进方框2里面

        // 登录按钮
        JButton jbutton = new JButton("安全登录");
        jbutton.setFont(style.ok);
        jbutton.setPreferredSize(new Dimension(209, 35)); //设置按钮宽高
        jbutton.setBackground(Color.gray); //设置背景颜色
        jbutton.setForeground(new Color(255, 215, 0)); //设置字体颜色

        jpanel2.add(jbutton); //把登录按钮添加进方框2里面

        jframe.add(jpanel1);
        jframe.add(jpanel2);
        jframe.add(bgimg); //图片需要放到最下面加载（不然会覆盖文字）


        //以下是监听事件（监听是否点击登录按钮）
        jbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("您点击了登录按钮"); //Debug
                //将账号和密码获取，并做相应的提示
                //将账号和密码更数据库进行匹对，同时再匹对权限，实现跳转不同的界面

                String account = jtextfield.getText(); //文本框获取里面内容的方法。获取账号
                //获取密码
                char[] str = jpasswordfield.getPassword();
                String password = new String(str); //把获取的字符数组转换成字符串

                //Debug
                //System.out.println(account);
                //.out.println(password);

                boolean star = LoginDao.loginStar(account, password); //将获取的账号密码传过去，如果账号密码正确就会返回true
                if ( star == true ) {
                    System.out.println("登录成功");
                    //登陆成功之后，还需要判断权限在哪个等级，根据等级进行跳转不同的界面
                    int pow = LoginDao.loginPow(account, password);
                    if ( pow == 2 ) { //如果返回的是2，则表示这个人是管理员
                        JOptionPane.showMessageDialog(null, "登录管理员界面", "登录消息", JOptionPane.WARNING_MESSAGE);
                    } else if ( pow == 1 ) { //如果返回的是1，则表示这个人是普通员工
                        JOptionPane.showMessageDialog(null, "登录普通员工界面", "登录消息", JOptionPane.WARNING_MESSAGE);
                    } else { //报错
                        JOptionPane.showMessageDialog(null, "系统错误", "登录消息", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    //登陆失败，则用信息框进行提示
                    JOptionPane.showMessageDialog(null, "账号或者密码错误！", "登录消息", JOptionPane.WARNING_MESSAGE);
                }

            }

        });


    }

}