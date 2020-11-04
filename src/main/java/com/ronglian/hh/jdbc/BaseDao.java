package com.ronglian.hh.jdbc;

import com.ronglian.hh.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDao<T> {

    /**
     * 增删改查功能
     * @param sql 数据库语句
     * @param args 可以是多个参数，是对sql语句中的占位符“？”传入的参数
     * @return n 操作所影响行数
     */
    public int executeUpdate(String sql, Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        int n=0;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);

            for(int i=0; i<args.length; i++){
                ps.setObject(i+1, args[i]);
            }
            n = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeAll(conn, ps, null);
        }

        return n;
    }

    /**
     * 查询一条记录
     * @param sql
     * @param c 传入的实体类，由于实体类未知，因此用Class<T>
     * @param args 不定参数，是对sql语句中的占位符“？”传入的参数
     * @return 返回的实体类
     */
    public T selectOne(String sql, Class<T> c, Object... args){
        List<T> list = this.selectMore(sql, c, args);
        return list.isEmpty()? null : list.get(0);
    }

    /**
     * 查询多条记录
     * @param sql
     * @param c 传入的实体类，由于实体类未知，因此用Class<T>
     * @param args 不定参数，是对sql语句中的占位符“？”传入的参数
     * @return 查询结果集
     */
    public List<T> selectMore(String sql, Class<T> c, Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();

        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            for(int i=0;i<args.length; i++){
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();

            //从结果集中，获取数据库表相关信息
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                T obj = c.newInstance();    //创建实体类
                //获得数据库表列数
                for(int i=1; i<=metaData.getColumnCount(); i++){
                    //获取字段名称
                    String colName = metaData.getColumnLabel(i);
                    //拼接字段的setter方法名
                    String setterName = "set"+ StringUtils.toFirstUpper(colName);
                    //获取实体中所有声明（私有+公有）的属性
                    Field field = c.getDeclaredField(colName);
                    //获取实体类中所有声明（私有+公有）的形参为field.getType()类型，方法名为setterName的方法
                    Method setterMethod = c.getDeclaredMethod(setterName, field.getType());
                    // 通过结果集获取字段名为fieldName（与实体中的对应属性名完全相同）的值
                    Object val = rs.getObject(colName);
                    //执行实体对象中的setter方法，传入实参
                    setterMethod.invoke(obj, val);
                }
                list.add(obj);
            }

        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeAll(conn, ps, rs);
        }

        return list;
    }

    /**
     * 查询总记录数
     * @param sql
     * @param args 不定参数，需要对sql语句中的占位符“？”传入的参数数组
     * @return 总记录数
     */
    public int selectCount(String sql, Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int n = 0;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            for(int i=0; i<args.length; i++){
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();
            if(rs.next()){
                n = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeAll(conn, ps ,rs);
        }

        return n;
    }

}
