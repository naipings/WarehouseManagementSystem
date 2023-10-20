package src.com.dao;

import src.com.until.DBUtil;

import java.sql.*;

public class LoginDao {
    //处理登录事件的类

    static Connection con = DBUtil.conn; //将DBUtil类中连接的conn传过来

    //登录状态（成功则返回true，失败则返回false）
    public static boolean loginStar(String account, String password) { //提供两个参数，第一个账号，第二个密码，采用预处理的方法
        PreparedStatement preSql; //预处理语句
        ResultSet rs; //定义一个结果集（存放结果）
        String sqlStr = "select * from users where account = ? and password = ?";  //?表示从外部输入的

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, account); //相当于把上面第一个问号设置成账号
            preSql.setString(2, password); //把上面第二个问号设置成密码

            rs = preSql.executeQuery(); //查询，返回值放到rs里面
            if ( rs.next() ) { //rs是表头，看他下一个是否有东西，如果里面有东西执行了，就返回true
                return true;
            } else { //如果查询失败了
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }


//-----------------------------------------------------
    //和前面那个函数类似，登录成功过后，检验账号权限
    public static int loginPow(String account, String password) { //提供两个参数，第一个账号，第二个密码，采用预处理的方法
        PreparedStatement preSql; //预处理语句
        ResultSet rs; //定义一个结果集（存放结果）
        String sqlStr = "select * from users where account = ? and password = ?";  //?表示从外部输入的

        try {
            preSql = con.prepareStatement(sqlStr);
            preSql.setString(1, account); //相当于把上面第一个问号设置成账号
            preSql.setString(2, password); //把上面第二个问号设置成密码

            rs = preSql.executeQuery(); //查询，返回值放到rs里面
            if ( rs.next() ) { //rs是表头，看他下一个是否有东西，如果里面有东西执行了，就返回true
                if ( rs.getString("pow").equals("2") ) {
                    //拿获取到的数值与2进行比较，如果比较成功了（相等的），他就是管理员
                    return  2;
                } else {
                    //如果和2等不上，那么他就是普通用户
                    return 1;
                }

            } else { //如果查询失败了
                //里面是空数据，啥也没有
                return 3;
            }
        } catch (SQLException e) {
            return 3;
        }
    }

}
