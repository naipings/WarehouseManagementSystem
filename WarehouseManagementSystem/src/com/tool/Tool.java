package src.com.tool;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

//工具类
public class Tool {

    //设置当前窗口大小及位置居中
    public static void setWindowPosCenter(int WIDTH, int HEIGHT, JFrame jframe) {
        //设置当前窗口大小
        Toolkit kit = Toolkit.getDefaultToolkit(); //获取对象(电脑屏幕)大小  //设置窗口位置
        Dimension screenSize = kit.getScreenSize(); //把获取的数据放到screenSize里面
        //然后，从screenSize中，获取屏幕高度和宽度
        int width = screenSize.width;
        int height = screenSize.height;
        //设置窗口大小并居中
        int x = (width-WIDTH)/2;
        int y = (height-HEIGHT)/2;
        jframe.setBounds(x, y, WIDTH, HEIGHT);
    }

    //添加表格的模块化工具
    public static int addDataTable(ResultSet rs, DefaultTableModel model, int index) {  //定义表格的控制权，可以用它来控制表格
        //三个参数：存储数据的rs；表格的控制权，可以用它来控制表格；下标（用于规定表格宽度）
        int count = 0; //用于判断data[]里面是否有东西
        model.setNumRows(0); //为避免重复添加，故每次添加前，将里面的内容清空
        String[] data = new String[index];
        try {
            while ( rs.next() ) {
                count++;
                for ( int i=0; i<data.length; i++ ) {
                    data[i] = rs.getString(i+1);

                }
                model.addRow(data); //将data的数据添加到一行当中
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }



    }
}
