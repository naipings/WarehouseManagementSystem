package src.com.dao;

import src.com.until.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//入库刀，用于入库界面的数据管理
public class InStockDao {

    //

    static Connection con = DBUtil.conn;

    //商品入库
    public static int writeStock(String supName, String sunName, String num, String price) { //需要四个参数(见入库界面)：商品供应商、商品名称、商品数量、商品价格
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
//        int num1 = Integer.parseInt(num); //将参数传入的字符串类型的num，转换成int类型
        int num1 = 0; //定义一个结果集（存放结果）
        String sqlStr = "insert into instock(supname, stockname, intime, num, price) values(?, ?, now(), ?, ?)"; //?表示从外部输入的

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, supName);
            preSql.setString(2, sunName);
            preSql.setString(3, num);
            preSql.setString(4, price);


            num1 = preSql.executeUpdate(); //更新数据，返回值放到num里面
            return num1;

        } catch (SQLException e) {
            return 3;
        }
    }


}
