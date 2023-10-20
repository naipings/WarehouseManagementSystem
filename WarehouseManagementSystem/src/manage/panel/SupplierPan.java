package src.manage.panel;

import src.com.dao.SupplierManageDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//添加供应商窗格
public class SupplierPan extends JPanel {

    final int WIDTH = 730; //设置顶层框架的宽度
    final int HEIGHT = 50; //设置顶层框架的高度

    //为了方便后面调用这些变量，所以这么定义，而不是设置为局部变量
    public static JTextField jt1;
    public JComboBox cmb1;

    //对"添加旗下子产品"按钮（jb3）添加监听事件时，计数器
    static int num = 1;

    public SupplierPan(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        init();
    }

    void init() {
        //设置空布局
        this.setLayout(null);

        //设置方框大小
        this.setBackground(Color.magenta);

        //需要三个方框
        JPanel jpanel1 = new JPanel();
        JPanel jpanel2 = new JPanel();
        JPanel jpanel3 = new JPanel();
        //设置方框1位置和大小
        jpanel1.setLayout(new FlowLayout(FlowLayout.CENTER,10,10)); //方框1采用流布局，居中对齐
        jpanel1.setBounds(0, 0, WIDTH, 50);
        jpanel1.setBackground(Color.orange);

        this.add(jpanel1);

        //（在方框1中）添加标签，文本框及按钮
        JLabel jl1 = new JLabel("供应商");
        jt1 = new JTextField(12);
        JButton jb1 = new JButton("添加供应商");
        JButton jb2 = new JButton("删除供应商");

        jpanel1.add(jl1);
        jpanel1.add(jt1);
        jpanel1.add(jb1);
        jpanel1.add(jb2);

        //设置方框2，总体宽度是一样的
        jpanel2.setLayout(new FlowLayout(FlowLayout.CENTER,40,10)); //方框2采用流布局，居中对齐
        jpanel2.setBounds(0, 60, WIDTH, 470);
        jpanel2.setBackground(Color.pink);

        this.add(jpanel2);

        //在方框2中添加：
        JLabel jl2 = new JLabel("供应商"); //标签
        cmb1 = new JComboBox<>(); //下拉菜单
        cmb1.addItem("--请选择供应商--");
        JButton jb3 = new JButton("添加旗下子产品"); //按钮
        JButton jb4 = new JButton("保存数据"); //按钮
        JButton jb5 = new JButton("重置"); //按钮

        jpanel2.add(jl2);
        jpanel2.add(cmb1);
        jpanel2.add(jb3);
        jpanel2.add(jb4);
        jpanel2.add(jb5);

        //设置方框3（方框3放入方框2中）
        jpanel3.setLayout(new FlowLayout(FlowLayout.CENTER)); //方框3采用流布局，居中对齐
        jpanel3.setPreferredSize(new Dimension(200, 420)); //必须在流布局中使用（流布局不能设置组件的大小及位置，如果想在流布局中设置组件的大小及位置，就必须这么使用）
        jpanel3.setBackground(Color.gray);

        //在方框3中添加：
        JLabel jl3 = new JLabel("产品名称"); //标签
        JTextField jt2 = new JTextField(12); //文本框
        jt2.setName("sun"); //给jt2取个名字，sun表示“孩子”的意思

        jpanel3.add(jl3);
        jpanel3.add(jt2);

        jpanel2.add(jpanel3);

        //对"添加旗下子产品"按钮（jb3）添加监听事件
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //最多添加5次就不能再添加了
                if ( num < 5 ) {
                    JLabel jl3 = new JLabel("产品名称"); //标签
                    JTextField jt2 = new JTextField(12); //文本框
                    jt2.setName("sun"); //表示“孩子”的意思

                    jpanel3.add(jl3);
                    jpanel3.add(jt2);

                    myUpdateUI();
                    num++;
                } else {
                    //超过5个，就跳出一个弹窗
                    JOptionPane.showMessageDialog(null, "最多只能添加5个", "消息", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //实现通过按钮添加供应商
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //将（添加的供应商的）数据写入到数据库中
                //先判断文本框里面是否为空
                if ( jt1.getText().equals("") ) {
                    JOptionPane.showMessageDialog(null, "请添加供应商", "消息", JOptionPane.WARNING_MESSAGE);
                } else {
                    //star用于接收writeSup()函数的返回值，进而判断是否成功添加供应商
                    int star = SupplierManageDao.writeSup(jt1.getText());
                    if ( star == 0 ) {
                        JOptionPane.showMessageDialog(null, "供应商添加失败", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                    else if ( star == 1 ) { //如果输入的值成功插入（表明成功更改数据了），此时返回值应为1
                        JOptionPane.showMessageDialog(null, "供应商添加成功", "消息", JOptionPane.WARNING_MESSAGE);

                        //刷新下拉框（让“请选择供应商”下拉框，每次添加成功结束后，自动刷新）
                        SupplierManageDao.readSup(cmb1);

                    }
                    else if ( star == 3 ) { //报错
                        JOptionPane.showMessageDialog(null, "供应商名字重复，请重新输入", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        //添加”删除供应商“按钮的监听
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //将（添加的供应商的）数据写入到数据库中
                //先判断文本框里面是否为空
                if ( jt1.getText().equals("") ) {
                    JOptionPane.showMessageDialog(null, "请输入要删除的供应商", "消息", JOptionPane.WARNING_MESSAGE);
                } else {
                    //star用于接收writeSup()函数的返回值，进而判断是否成功添加供应商
                    int star = SupplierManageDao.deleteSup(jt1.getText());
                    if ( star == 0 ) {
                        JOptionPane.showMessageDialog(null, "供应商删除失败，请检查名字", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                    else if ( star == 1 ) { //如果输入的值成功插入（表明成功更改数据了），此时返回值应为1
                        JOptionPane.showMessageDialog(null, "供应商删除成功", "消息", JOptionPane.WARNING_MESSAGE);

                        //刷新下拉框（让“请选择供应商”下拉框，每次添加成功结束后，自动刷新）
                        SupplierManageDao.readSup(cmb1);

                    }
                    else if ( star == 3 ) { //报错
                        JOptionPane.showMessageDialog(null, "报错，请检查输入内容", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        //获取方框3（"产品名称"）里面所有东西，
        //把下拉框选择的数据给它 和方框3里面文本框里面的内容获取到，写入数据库。
        //添加“保存数据”按钮的监听
        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = 0; //用于后面接收writeSupSun()返回的值，进而判断是否成功添加供应商的子产品
                Component[] temp = jpanel3.getComponents(); //getComponents()会返回方框3里面的所有组件

                for ( int i=0; i<temp.length; i++ ) {
//                    System.out.println(temp[i].getName());
                    if ( temp[i].getName()!=null && temp[i].getName().equals("sun") ) {
                        //如果组件i的名字等于“sun”，表明这个组件是文本框
                        JTextField TEMP1 = (JTextField)temp[i]; //由于文本框是TextField类型的对象，故我们使用类型转换
                        String text = TEMP1.getText(); //把文本框的内容存过来
//                        System.out.println(cmb1.getSelectedIndex());
                        if ( cmb1.getSelectedIndex() == 0 ) {
                            JOptionPane.showMessageDialog(null, "请选择供应商", "消息", JOptionPane.WARNING_MESSAGE);
                        } else {
                            //获取项目名字（供应商名字）
//                            System.out.println(cmb1.getSelectedItem());
                            String sup = (String)cmb1.getSelectedItem();

                            //注：sup获取到供应商名字；text获取到子产品名字

                            a = SupplierManageDao.writeSupSun(sup, text);
                        }
                    }
                }

                //后面高级版本进行改进，让它自动报错（自动报哪行有问题）
                if ( a == 3 ) {
                    JOptionPane.showMessageDialog(null, "请检查产品名称是否重复", "消息", JOptionPane.WARNING_MESSAGE);
                }
                else if ( a == 0 ) {
                    JOptionPane.showMessageDialog(null, "添加失败！", "消息", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "添加产品成功！", "消息", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        //重置按钮功能：将下方菜单清空，只保留一个
        //给”重置“按钮添加监听事件
        jb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jpanel3.removeAll();

                JLabel jl3 = new JLabel("产品名称"); //标签
                JTextField jt2 = new JTextField(12); //文本框
                jt2.setName("sun"); //表示“孩子”的意思

                jpanel3.add(jl3);
                jpanel3.add(jt2);

                num = 1;

                myUpdateUI();
            }
        });


        //“请选择供应商”下拉框，每次添加结束后，自动刷新
        //点击保存数据按钮，将数据写入数据库
        //点击重置按钮，将子产品方框（如果有多个）变成1个，并清空里面的内容






    }

    //更新界面
    private void myUpdateUI() {
        SwingUtilities.updateComponentTreeUI(this); //添加或删除组件后，更新窗口
    }

}
