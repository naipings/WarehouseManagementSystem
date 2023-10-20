package src.com.dao;

import src.com.until.DBUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//用于完成对供应商的数据管理
public class SupplierManageDao {

    //添加供应商

    //删除供应商

    //添加供应商旗下子产品

    static Connection con = DBUtil.conn;

    public static int writeSup(String name) { //通过int返回值确定状态，0、3表示失败，1表示成功
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
        int num; //定义一个结果集（存放结果）
        String sqlStr = "insert into supplier(`name`) values(?)"; //?表示从外部输入的

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, name);

            num = preSql.executeUpdate(); //更新数据，返回值放到num里面
            return num;

        } catch (SQLException e) {
            return 3;
        }

    }

    //读取全部供应商
    public static void readSup(JComboBox cmb1) {
        //移除传递过来的所有项目
        cmb1.removeAllItems(); //移除下拉框里面所有的东西
        cmb1.addItem("--请选择供应商--");

        int star = 0; //定义一个判断数据状态的变量。0表示没有数据 1表示有数据

        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
        ResultSet rs = null; //定义一个结果集（存放结果）
        String sqlStr = "select * from supplier";

        try {
            preSql = con.prepareStatement(sqlStr);
            rs = preSql.executeQuery();

            while (rs.next()) {
                //进入循环表明：下拉框里面还有数据
                if (star == 0) {
                    star++;
                }

                String tempName = rs.getString("name"); //获取数据库相应字段的标签名
                cmb1.addItem(tempName);
            }

            cmb1.repaint();

        } catch (SQLException e) {

        }

    }

    //删除供应商
    public static int deleteSup(String name) { //返回0表示删除失败 1表示删除成功
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
        int num; //定义一个结果集（存放结果）
        String sqlStr = "delete from supplier where `name`=?"; //?表示从外部输入的

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, name);

            num = preSql.executeUpdate(); //更新数据，返回值放到num里面
            return num;

        } catch (SQLException e) {
            return 3;
        }
    }

    //将供应商和子产品都写入数据库（相当于同时写入父亲和孩子的数据）
    public static int writeSupSun(String subName, String sunName) { //通过int返回值确定状态，0、3表示失败，1表示成功
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
        int num; //定义一个结果集（存放结果）
        String sqlStr = "insert into product (`name`, `supname`) VALUES(?, ?)"; //?表示从外部输入的

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, sunName); //即：第一个问号 用孩子的数据替代
            preSql.setString(2, subName); //第二个问号的内容 用父亲的数据替代

            num = preSql.executeUpdate(); //更新数据，返回值放到num里面
            return num;

        } catch (SQLException e) {
            return 3;
        }
    }

}
