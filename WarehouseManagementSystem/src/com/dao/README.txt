用于数据库的处理

MVC模式框架DAO：
1）Data Access Object
数据访问对象
2）实际上DAO是一种javaee设计模式
3）DAO层只负责数据的增删改查，不负责业务逻辑
4）如果处理的是t_user 可以叫做UserDao
如果处理的是t_student 可以叫做StudentDao
5)一般情况，一张表对应一个Dao
