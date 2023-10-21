package src.com.windows;

import src.com.dao.InStockDao;
import src.com.dao.SupplierManageDao;
import src.com.tool.Tool;
import src.manage.panel.InStockPan;
import src.manage.panel.OutStockPan;
import src.manage.panel.SupplierPan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerWindows {

    //动态的按钮监听事件用
    String button[] = {"商品入库", "商品出库", "添加供应商"}; //按钮显示名称
    String buttonName[] = {"stockIn", "stockOut", "supplier"}; //名字，用以区分不同按钮

    //下面这部分内容可以直接从Login.java里面复制过来，再稍作修改即可：
    final int WIDTH = 900; //设置顶层框架的宽度
    final int HEIGHT = 600; //设置顶层框架的高度
    public static JFrame jframe = new JFrame(); //窗口

    public ManagerWindows() {
        init();
        jframe.setVisible(true); //设置当前窗口是否可显示
        jframe.setResizable(false); //设置窗口的大小不可变
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //设置默认关闭方式
        jframe.validate(); //让组件生效
    }

    public void init() {
        //设置标题
        jframe.setTitle("仓库管理系统（管理员）");

        //设置当前窗口大小及位置居中
        Tool.setWindowPosCenter(WIDTH, HEIGHT, jframe);

        jframe.setLayout(null); //先将窗口布局设置为空布局

        //定义两个方框
        JPanel jpanel1 = new JPanel(); //普通方框（面板）
        JLayeredPane jpanel2 = new JLayeredPane(); //分层方框（面板）

        //暂时没有想好是什么布局，暂不设置

        //设置第一个方框位置及大小
        jpanel1.setBounds(5, 5, 150, HEIGHT-10);
        jpanel1.setBackground(Color.black);
        jpanel1.setLayout(new FlowLayout(FlowLayout.CENTER)); //设置为流布局。定义一个FlowLayout类，里面用CENTER（居中）初始化

        // 在第一个方框里面添加两个按钮
        //如果采用静态监听，就加入下面几句用于创建按钮
        //如果采用动态方式，就不需要下面这几句，因为for循环中我们在动态创建按钮
//        JButton inButton = new JButton("商品入库");
//        jpanel1.add(inButton);
//        JButton outButton = new JButton("商品出库");
//        jpanel1.add(outButton);

        // 增加一个菜单栏，放账号管理和增加供应商
        JMenuBar menubar = new JMenuBar(); //创建一个菜单条
        JMenu menu1 = new JMenu("账号管理"); //一个菜单
        JMenu menu2 = new JMenu("仓库管理");
//        JMenuItem item1 = new JMenuItem("查看员工账号");
        JMenuItem item1_1 = new JMenuItem("添加员工账号");
        JMenuItem item1_2 = new JMenuItem("删除员工账号");
        JMenuItem item2_1 = new JMenuItem("更改员工账号信息");
        //  将次一级的菜单加入上一级的菜单中，同时，将菜单放到菜单条
        menu1.add(item1_1);
        menu1.add(item1_2);
        menu2.add(item2_1);
        menubar.add(menu1);
        menubar.add(menu2);
        //  设置菜单条（将菜单条加载到窗口里面）
        jframe.setJMenuBar(menubar);

        //设置第二个方框位置及大小
        //这里采用了层级结构（JLayeredPane），以便后面设置点击不同按钮，显示不同界面
        jpanel2.setBounds(215-50, 5, 680+50, HEIGHT-10);
        //这里（这一层）叫：入库窗格
        InStockPan inpan = new InStockPan(0, 0, 665+50, HEIGHT-10); //这里的位置设置是以jpanel2为参照的，而不是以整个窗口
        jpanel2.add(inpan, (Integer) (JLayeredPane.PALETTE_LAYER));

        //出库窗格
        OutStockPan outpan = new OutStockPan(0, 0, 665+50, HEIGHT-10); //这里的位置设置是以jpanel2为参照的，而不是以整个窗口
        jpanel2.add(outpan, (Integer) (JLayeredPane.PALETTE_LAYER));

        //添加供应商窗格
        SupplierPan supplierpan = new SupplierPan(0, 0, 665+50, HEIGHT-10);
        jpanel2.add(supplierpan, (Integer) (JLayeredPane.PALETTE_LAYER));

        //加载方框
        jframe.add(jpanel1);
        jframe.add(jpanel2);


        //实现点击不同按钮，切换不同窗格（最简朴的方法，后续会进行升级）
        //方法一：静态的（按钮多了比较麻烦）
//        //对入库按钮添加监听事件
//        inButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //将入库窗格设置为顶层
//                jpanel2.moveToFront(inpan);
//            }
//        });
//        //对出库按钮添加监听事件
//        outButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //点击出库按钮，就将出库窗格设置为顶层
//                jpanel2.moveToFront(outpan);
//            }
//        });

        //方法二：动态的
        for ( int i=0; i<button.length; i++ ) { //每有一个按钮，就会有一个按钮名称添加进button数组，进而可以借此动态添加按钮
            JButton bu = new JButton(button[i]);
            jpanel1.add(bu);
            bu.setName(buttonName[i]);
            bu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton jbl = (JButton)e.getSource(); //获取传进来的信息
                    //以后每多一个按钮，只需要在里面增加一个相应的if语句就行
                    if (jbl.getName().equals(buttonName[0])) {
                        //将商品入库那个方框移动到最上面
                        jpanel2.moveToFront(inpan);

                        SupplierManageDao.readSup(InStockPan.cmbSupName); //用于显示 商品入库界面的 “请选择供应商”下拉框的 已添加的公司
                    }

                    if (jbl.getName().equals(buttonName[1])) {
                        //将商品出库那个方框移动到最上面
                        jpanel2.moveToFront(outpan);
                    }

                    if (jbl.getName().equals(buttonName[2])) {
                        //将商品出库那个方框移动到最上面
                        jpanel2.moveToFront(supplierpan);

                        SupplierManageDao.readSup(supplierpan.cmb1); //用于显示 添加供应商界面的 “请选择供应商”下拉框的 已添加的公司
                    }
                }
            });
        }


    }

}
