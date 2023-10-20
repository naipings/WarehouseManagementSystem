package src.manage.panel;

import javax.swing.*;
import java.awt.*;

//入库窗格
public class InStockPan extends JPanel {

    public InStockPan(int x, int y, int width, int height) {//第一，二个参数表示起始位置；第三、四个参数表示方框的宽度与高度
        this.setBounds(x, y, width, height);
        init();
    }

    void init() {
        //设置空布局
        this.setLayout(null);

        //设置方框大小
        this.setBackground(Color.GRAY);

    }

}
