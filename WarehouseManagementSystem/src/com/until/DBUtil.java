package src.com.until;

import java.sql.*;

//数据库与驱动连接
public class DBUtil {

    public static Connection conn = null;

    public DBUtil(String account, String password, String database) { //三个参数分别是：数据库账号，数据库密码，数据库名字
        //连接驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("加载驱动成功");
        } catch (Exception e) {
            System.out.println("加载驱动失败");
        }

        //连接数据库
        try {
            String url = "jdbc:mysql://localhost:3306/"+database+"?characterEncoding=utf-8&useSSL=false";
            conn = DriverManager.getConnection(url, account, password);
            System.out.println("连接数据库成功");
        }catch (SQLException e1) {
            System.out.println("——————————————————————————");
            System.out.println("连接数据库失败");
            e1.printStackTrace(); //打印报错信息
            System.out.println("——————————————————————————");
        }
    }

    //检测连接是否关闭，并自动关闭连接
    //用一次调用该函数关闭一次，防止参数表中相应变量的数据积累而造成卡顿效果
    public static void CloseDb(ResultSet rs, PreparedStatement stm) { //第一个参数是读取数据库的接口，第二个参数是预处理的接口
        if (rs != null) { //如果rs不为空（表示里面有东西），就尝试关闭
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace(); //如果无法关闭，打印错误信息
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
