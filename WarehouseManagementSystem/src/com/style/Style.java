package src.com.style;

import java.awt.Font;

//存放样式
public class Style {

    //字体样式
    public static Font title; //定义一个标题（登陆界面的）
    public static Font account; //账号的样式
    public static Font accounttext; //登录文本框的样式
    public static Font ok; //登录按钮的字体样式

    public Style() {
        //初始化
        //第一个参数是字体类型，第二个参数是是否加粗，第三个参数是字号
        title = new Font("华文新魏", Font.BOLD, 28); //标题字体
        account = new Font("华文行楷", Font.BOLD, 18); //标签
        accounttext = new Font("宋体", Font.PLAIN, 18); //账号框
        ok = new Font("宋体", Font.BOLD, 18); //登录按钮


    }
}
