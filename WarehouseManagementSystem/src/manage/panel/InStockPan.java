package src.manage.panel;

import src.com.dao.InStockDao;
import src.com.dao.SupplierManageDao;
import src.com.tool.Tool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

//入库窗格
public class InStockPan extends JPanel {

    final int WIDTH = 730; //设置顶层框架的宽度
    final int HEIGHT = 50; //设置顶层框架的高度

    //表格的数据
    Object columns[] = {"订单编号", "供应商", "商品名称", "入库时间", "商品数量", "商品价格", "商品库存"}; //表格表头信息
    JTable jtable = null; //定义一个表格
    JScrollPane jscrollpane; //滚动条
    public static DefaultTableModel model; //定义表格的控制权，可以用它来控制表格

    //为了方便后面调用这些变量，所以这么定义，而不是设置为局部变量（包括：供应商，商品名称，商品数量，商品价格）
    public static JComboBox cmbSupName;
    public static JComboBox cmbStockName;
    public static JTextField stockNumIn;
    public static JTextField stockPriceIn;

    public InStockPan(int x, int y, int width, int height) {//第一，二个参数表示起始位置；第三、四个参数表示方框的宽度与高度
        this.setBounds(x, y, width, height);
        init();
    }

    void init() {
        //设置空布局
        this.setLayout(null);

        //设置方框大小
        this.setBackground(Color.GRAY);

        //产品入库信息：商品名称，商品入库时间，商品入库价格，商品入库数量，商品库存，商品供应商

        //需要三个方框
        JPanel jpanel1 = new JPanel();
        JPanel jpanel2 = new JPanel();
        JPanel jpanel3 = new JPanel();
        //设置方框1位置和大小
        jpanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); //左对齐
        jpanel1.setBounds(0, 0, WIDTH,50);
        jpanel1.setBackground(Color.red);

        this.add(jpanel1);

        //（在方框1中）定义5个按钮（管理员进行增删改查没有时间限制，普通员工只能在商品添加后，三分钟内进行删改操作）
        JButton jb1 = new JButton("保存入库");
        jpanel1.add(jb1);
        JButton jb2 = new JButton("更改商品");
        jpanel1.add(jb2);
        JButton jb3 = new JButton("查找商品");
        jpanel1.add(jb3);
        JButton jb4 = new JButton("删除商品");
        jpanel1.add(jb4);
        JButton jb5 = new JButton("重置界面");
        jpanel1.add(jb5);

        //添加4个标签，2个文本框，2个下拉菜单
        // 先设置方框二的属性
        jpanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20)); //左对齐
        jpanel2.setBounds(0, 60, WIDTH, 100);
        jpanel2.setBackground(Color.green);
        // 添加4个标签
        JLabel jl1 = new JLabel("商品供应商");
        jpanel2.add(jl1);
        cmbSupName = new JComboBox<>(); //下拉菜单
        cmbSupName.addItem("--请选择供应商--");
        jpanel2.add(cmbSupName);

        JLabel jl2 = new JLabel("商品名称");
        jpanel2.add(jl2);
        cmbStockName = new JComboBox<>(); //下拉菜单
        cmbStockName.addItem("--请选择商品--");
        jpanel2.add(cmbStockName);

        JLabel jl3 = new JLabel("商品数量");
        jpanel2.add(jl3);
        stockNumIn = new JTextField(8);
        jpanel2.add(stockNumIn);

        JLabel jl4 = new JLabel("商品价格");
        jpanel2.add(jl4);
        stockPriceIn = new JTextField(10);
        jpanel2.add(stockPriceIn);

        JLabel jl5 = new JLabel("订单编号");
        jpanel2.add(jl5);
        JTextField stockID = new JTextField(10);
        jpanel2.add(stockID);

        this.add(jpanel2);

        //实现1个表格
        table();
        this.add(jscrollpane); //把滚动条添加到方框里面

        //因为进入系统，默认是商品入库界面，在进入该界面时，就直接先读取 “请选择供应商”下拉框的 已添加的公司，
        //进而避免需要再点击一下“商品入库”按钮，才能显示供应商内容
        SupplierManageDao.readSup(InStockPan.cmbSupName); //用于显示 商品入库界面的 “请选择供应商”下拉框的 已添加的公司

        //“保存入库”按钮添加监听事件
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //将数据获取，写入到数据库中

                if ( cmbSupName.getSelectedIndex() == 0 ) { //表示没有获取到
                    JOptionPane.showMessageDialog(null, "请选择供应商", "消息", JOptionPane.WARNING_MESSAGE);
                } else if ( cmbStockName.getSelectedIndex() == 0 ) {
                    JOptionPane.showMessageDialog(null, "请选择产品", "消息", JOptionPane.WARNING_MESSAGE);
                } else if ( stockNumIn.getText().equals("") ) {
                    JOptionPane.showMessageDialog(null, "请输入产品入库数量", "消息", JOptionPane.WARNING_MESSAGE);
                } else if ( stockPriceIn.getText().equals("") ) {
                    JOptionPane.showMessageDialog(null, "请输入产品入库价格", "消息", JOptionPane.WARNING_MESSAGE);
                } else {
                    String sup = (String) cmbSupName.getSelectedItem();
                    String sun = (String) cmbStockName.getSelectedItem();
                    String num = stockNumIn.getText();
                    String pri = stockPriceIn.getText();
                    int a = InStockDao.writeStock(sup, sun, num, pri);
                    if ( a ==0 ) {
                        JOptionPane.showMessageDialog(null, "添加失败！", "消息", JOptionPane.WARNING_MESSAGE);
                    } else if ( a ==3 ) {
                        JOptionPane.showMessageDialog(null, "请检查是否规范输入数量或价格", "消息", JOptionPane.WARNING_MESSAGE);
                    } else if ( a == 1 ) {
                        JOptionPane.showMessageDialog(null, "添加成功！", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                }

            }
        });

        //“请选择供应商”下拉框的监听
        cmbSupName.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                SupplierManageDao.readSun(cmbStockName, (String)cmbSupName.getSelectedItem()); //用于显示 商品入库界面的 “请选择商品”下拉框的 已添加的子产品
            }
        });

        //”查找商品“按钮添加监听事件
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //两种查询方法：查找全部，查找单个
                String IDNum = stockID.getText();
                ResultSet rs;
                if ( IDNum.equals("") ) { //如果订单编号为空
                    //则查找全部
                    rs = InStockDao.findStockAllData();
                    //然后更新表格窗格：传递一个存储数据的rs 和一个表格 还需要一个表格的宽度
                    int a = Tool.addDataTable(rs, model, 7);
                    if ( a == 0 ) {
                        JOptionPane.showMessageDialog(null, "未查到相关数据", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                }
                else { //如果订单编号不为空
                    //则查找单个
                    rs = InStockDao.findStockOneData(IDNum);
                    updateTextBox(IDNum); //文本框加载相关数据
                    //然后更新表格窗格：传递一个存储数据的rs 和一个表格 还需要一个表格的宽度
                    int a = Tool.addDataTable(rs, model, 7);
                    if ( a == 0 ) {
                        JOptionPane.showMessageDialog(null, "未查到相关数据", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        //”删除商品“按钮添加监听事件
        //鼠标选择哪个商品，就删除哪个商品
        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //直接输入ID进行删除
                String IDNum = stockID.getText();
                if ( IDNum.equals("") ) {
                    JOptionPane.showMessageDialog(null, "请输入删除编号", "消息", JOptionPane.WARNING_MESSAGE);
                } else {
                    int a = InStockDao.deleteStockData(IDNum);
                    if ( a == 0 ) {
                        JOptionPane.showMessageDialog(null, "请检查输入编号是否存在", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                    if ( a == 1 ) {
                        JOptionPane.showMessageDialog(null, "删除成功", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                    if ( a == 3 ) {
                        JOptionPane.showMessageDialog(null, "请检查输入编号是否为数字", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                    if ( a == 4 ) {
                        JOptionPane.showMessageDialog(null, "商品库存不足，不能删除当前订单", "消息", JOptionPane.WARNING_MESSAGE);
                    }
                }

            }
        });

        //”更改商品“按钮添加监听事件
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supName=null, sunName=null, stockNum=null, price=null, IDNum=null; //供应商名称，商品名称，商品数量，商品价格
                if ( stockID.getText().equals("") ) {
                    JOptionPane.showMessageDialog(null, "订单编号不能为空！", "消息", JOptionPane.WARNING_MESSAGE);
                } else {
                    if ( cmbSupName.getSelectedIndex() == 0 ) {
                        JOptionPane.showMessageDialog(null, "请选择供应商", "消息", JOptionPane.WARNING_MESSAGE);
                    } else if ( cmbStockName.getSelectedIndex() == 0 ) {
                        JOptionPane.showMessageDialog(null, "请选择商品", "消息", JOptionPane.WARNING_MESSAGE);
                    } else if ( stockNumIn.getText().equals("") ) {
                        JOptionPane.showMessageDialog(null, "商品数量不能为空", "消息", JOptionPane.WARNING_MESSAGE);
                    } else if ( stockPriceIn.getText().equals("") ) {
                        JOptionPane.showMessageDialog(null, "商品价格不能为空", "消息", JOptionPane.WARNING_MESSAGE);
                    } else {
                        //写入（进行更改）
                        supName = (String)cmbSupName.getSelectedItem();
                        sunName = (String)cmbStockName.getSelectedItem();
                        stockNum = stockNumIn.getText();
                        price = stockPriceIn.getText();
                        //将四个值传入数据库
                        IDNum = stockID.getText();
                        int a = InStockDao.changeStockData(supName, sunName, stockNum, price, IDNum);
                        if ( a == 0 ) {
                            JOptionPane.showMessageDialog(null, "数据未变动", "消息", JOptionPane.WARNING_MESSAGE);
                        } else if ( a == 1 ) {
                            JOptionPane.showMessageDialog(null, "更改成功", "消息", JOptionPane.WARNING_MESSAGE);
                        } else if ( a == 3 ) {
                            JOptionPane.showMessageDialog(null, "请检查输入格式", "消息", JOptionPane.WARNING_MESSAGE);
                        }

                    }
                }

            }
        });

    }

    void table() {
        jtable = getTable(); //初始化表格
        jscrollpane = new JScrollPane(jtable); //添加一个浏览窗格（即：把表格放到滚动条里面）
        //jscrollpane.setPreferredSize(new Dimension(WIDTH-30,250)); //给窗格（也就是滚动条）设置大小。但是流布局才有用
        jscrollpane.setBounds(0, 170, WIDTH-30, 360); //给窗格（也就是滚动条）设置大小。空布局有用
        jtable.setPreferredSize(new Dimension(WIDTH-30, 10000)); //给表格设置大小。高度设置为10000是为了让表格无限大，防止表格显示一半就不显示了
        jscrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//将滑动组件显示在窗口中
    }

    JTable getTable() {
        if ( jtable == null ) { //如果表格为空，则创建表格
            jtable = new JTable(); //创建表格
        }

        model = new DefaultTableModel() {
            //添加一些对表格的控制，例如：设置表格不可动、不可编辑等
            public boolean isCellEditable(int row, int col) { //行列数，并让表格不可编辑
                return false; //返回false就表示表格不可编辑
            }
        };

        model.setColumnIdentifiers(columns); //将 表格表头信息 放入 表格中
        jtable.setModel(model); //将jtable设置为表格的模式。上面都是规矩，这里是实现。
        jtable.getTableHeader().setReorderingAllowed(false); //让表格不可拖动
        jtable.getTableHeader().setResizingAllowed(false); //让表格不可拖动

        return jtable;
    }

    //查找单个商品时，在文本框上显示相关数据
    private void updateTextBox(String IDNum) {
        ResultSet rs1 = InStockDao.findStockOneData(IDNum);
        try {
            if ( rs1.next() ) {
                String sup = rs1.getString("supname");
                String sun = rs1.getString("stockname");
                String number = rs1.getString("num");
                String pri = rs1.getString("price");

                //遍历两个下拉框，便于在下拉框中显示：对应的供应商名称，商品名称
                for ( int i=0; i<cmbSupName.getItemCount(); i++ ) {
                    String a = (String)cmbSupName.getItemAt(i);
                    if ( a.equals(sup) ) {
                        cmbSupName.setSelectedIndex(i);
                        cmbSupName.repaint();
                    }
                }
                for ( int i=0; i<cmbStockName.getItemCount(); i++ ) {
                    String a = (String)cmbStockName.getItemAt(i);
                    if ( a.equals(sun) ) {
                        cmbStockName.setSelectedIndex(i);
                        cmbStockName.repaint();
                    }
                }
                //在文本框中显示：商品数量，价格
                stockNumIn.setText(number);
                stockPriceIn.setText(pri);
                //更新窗格
                myUpdateUI();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    //更新界面
    private void myUpdateUI() {
        SwingUtilities.updateComponentTreeUI(this); //添加或删除组件后，更新窗口
    }

}
