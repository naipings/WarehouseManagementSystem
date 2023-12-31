package src.com.dao;

import src.com.until.DBUtil;
import src.manage.panel.InStockPan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//入库刀，用于入库界面的数据管理
public class InStockDao {

    static Connection con = DBUtil.conn;

    //商品入库
    public static int writeStock(String supName, String sunName, String num, String price) { //需要四个参数(见入库界面)：商品供应商、商品名称、商品数量、商品价格
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
//        int num1 = Integer.parseInt(num); //将参数传入的字符串类型的num，转换成int类型
        int num1 = 0; //返回不同num1的值，表示不同状态
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

    //查找单个商品
    public static ResultSet findStockOneData(String IDNum) {
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
        String sqlStr = "select instock.ID,instock.supname,instock.stockname,instock.intime,instock.num,instock.price,product.stock from instock, product where product.supname=instock.supname and product.`name`=instock.stockname and id=?"; //?表示从外部输入的
        ResultSet rs = null; //定义一个结果集（存放结果）
        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, IDNum);
            rs = preSql.executeQuery();
            return rs;

        } catch (SQLException e) {
            return rs;
        }
    }

    //查找所有商品
    public static ResultSet findStockAllData() {
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
        String sqlStr = "select instock.ID,instock.supname,instock.stockname,instock.intime,instock.num,instock.price,product.stock from instock, product where product.supname=instock.supname and product.`name`=instock.stockname";
        ResultSet rs = null; //定义一个结果集（存放结果）
        try {
            preSql = con.prepareStatement(sqlStr);
            rs = preSql.executeQuery();
            return rs;

        } catch (SQLException e) {
            return rs;
        }
    }

    //根据ID删除商品
    public static int deleteStockData(String IDNum) {
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
        int num = 0; //返回不同num的值，表示不同状态
        String sqlStr = "delete from instock where ID=?"; //?表示从外部输入的

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, IDNum);
            num = preSql.executeUpdate(); //更新数据，返回值放到num里面
            return num;

        } catch (SQLException e) {
            if ( e.getMessage().equals("仓库库存不足，不能删除此订单号")) {
                return 4;
            } else {
                return 3;
            }
        }
    }

    //更改商品（将数据传入数据库）
    public static int changeStockData(String supName, String sunName, String stockNum, String price, String IDNum) {
        //采用预处理的方式
        PreparedStatement preSql; //预处理语句
        int num = 0; //返回不同num的值，表示不同状态
        String sqlStr = "UPDATE instock set supname=?, stockname=?, num=?, price=? where id=?"; //?表示从外部输入的

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, supName);
            preSql.setString(2, sunName);
            preSql.setString(3, stockNum);
            preSql.setString(4, price);
            preSql.setString(5, IDNum);
            num = preSql.executeUpdate(); //更新数据，返回值放到num里面
            return num;

        } catch (SQLException e) {
            return 3;
        }
    }


}
