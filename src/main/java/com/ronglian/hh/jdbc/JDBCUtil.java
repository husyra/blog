package com.ronglian.hh.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        init();
        //加载驱动
       try{
           Class.forName(driver);
       }catch (ClassNotFoundException e){
           e.printStackTrace();
       }
    }

    /**
     * 初始化数据库连接数据
     */
    public static void init(){
        Properties prop = new Properties();
        InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
        try{
            prop.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
        driver = prop.getProperty("jdbc.driver");
        url = prop.getProperty("jdbc.url");
        username = prop.getProperty("jdbc.username");
        password = prop.getProperty("jdbc.password");
    }

    /**
     * 获取数据库连接
     * return Connection
     */
    public static Connection getConnection(){
        Connection conn = null;
       try{
           conn = DriverManager.getConnection(url, username, password);
       }catch (SQLException e){
           e.printStackTrace();
       }
       return conn;
    }

    /**
     * 关闭资源
     * @param rs 结果集
     * @param st 语句对象
     * @param conn 数据库连接
     */
    public static void closeAll(Connection conn, Statement st, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
