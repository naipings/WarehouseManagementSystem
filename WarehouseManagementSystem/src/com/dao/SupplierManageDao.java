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

        try{
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, name);

            num = preSql.executeUpdate(); //更新数据，返回值放到num里面
            return num;

        } catch (SQLException e) {
            return 3;
        }

    }

}
