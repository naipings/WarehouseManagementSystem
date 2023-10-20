package src.com.windows;

import src.com.until.DBUtil;

public class WarehouseSystem {

    public static void main(String[] args) {

        //打开登陆界面，连接数据库
        //Login login = new Login("仓库管理系统");
        ManagerWindows a = new ManagerWindows();
        DBUtil dbutil = new DBUtil("root","20030504", "warehouse_management");


    }
}