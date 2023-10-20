package src.manage.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

//入库窗格
public class InStockPan extends JPanel {

    final int WIDTH = 730; //设置顶层框架的宽度
    final int HEIGHT = 50; //设置顶层框架的高度

    //表格的数据
    Object columns[] = {"供应商", "商品名字", "入库时间", "商品数量", "商品价格", "商品库存"}; //表格表头信息
    JTable jtable = null; //定义一个表格
    JScrollPane jscrollpane; //滚动条
    public static DefaultTableModel model; //定义表格的控制权，可以用它来控制表格

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
        jpanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); //左对齐
        //设置方框大小和位置
        jpanel1.setBounds(0, 0, WIDTH,50);
        jpanel1.setBackground(Color.red);

        this.add(jpanel1);

        //定义5个按钮（管理员进行增删改查没有时间限制，普通员工只能在商品添加后，三分钟内进行删改操作）
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
        jpanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 35)); //左对齐
        jpanel2.setBounds(0, 60, WIDTH, 100);
        jpanel2.setBackground(Color.green);
        // 添加4个标签
        JLabel jl1 = new JLabel("商品供应商");
        jpanel2.add(jl1);
        JComboBox cmbSupName = new JComboBox<>(); //下拉菜单
        cmbSupName.addItem("--请选择供应商--");
        jpanel2.add(cmbSupName);

        JLabel jl2 = new JLabel("商品名称");
        jpanel2.add(jl2);
        JComboBox cmbStockName = new JComboBox<>(); //下拉菜单
        cmbStockName.addItem("--请选择商品--");
        jpanel2.add(cmbStockName);

        JLabel jl3 = new JLabel("商品数量");
        jpanel2.add(jl3);
        JTextField stockNum = new JTextField(8);
        jpanel2.add(stockNum);

        JLabel jl4 = new JLabel("商品价格");
        jpanel2.add(jl4);
        JTextField stockPrice= new JTextField(8);
        jpanel2.add(stockPrice);

        this.add(jpanel2);
        table();
        this.add(jscrollpane); //把滚动条添加到方框里面
        //实现1个表格




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

}
