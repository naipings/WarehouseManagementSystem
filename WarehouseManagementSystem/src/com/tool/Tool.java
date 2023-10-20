package src.com.tool;

import javax.swing.*;
import java.awt.*;

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
}
