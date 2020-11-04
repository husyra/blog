package com.ronglian.hh.dao;

import com.ronglian.hh.domain.User;
import com.ronglian.hh.jdbc.BaseDao;
import com.ronglian.hh.util.StringUtils;
import java.util.List;

public class UserDao extends BaseDao {

    /**
     * 根据用户ID，查询用户信息
     * @param userId
     * @return
     */
    public User findById(String userId){
        String sql = "select * from user where id = ?";
        User user = (User)selectOne(sql, User.class, userId);
        return user;
    }

    /**
     * 根据用户名和密码查询用户信息
     * @param name
     * @return
     */
    public User findByName(String name){
        String sql = "select * from user where name = ?";
        User user = (User)selectOne(sql, User.class, name);
        return user;
    }

    public List<User> userList(String name){
        String sql = "select * from user where name like ? order by updatetime desc";
        if(StringUtils.isBlank(name)){
            name = "%";
        }else{
            name = "%"+name+"%";
        }
        List<User> userList = selectMore(sql, User.class, name);
        return userList;
    }

    /**
     * 新增用户信息
     * @param user
     * @return
     */
    public int addUser(User user){

        User u = findByName(user.getName());
        if(u!=null){
            return 0;
        }

        //获取唯一id
        String id = StringUtils.getUUID();
        user.setId(id);

        String sql = "INSERT INTO USER (id, NAME, pwd, sex, status, photo, birthday, phone, address, email, regtime, updatetime) VALUES (?, ?, ?, ?,  '1', ?, ?, ?, ?, ?, NOW(), NOW());";
        int n = executeUpdate(sql, id, user.getName(), user.getPwd(), user.getSex(), user.getPhoto(), user.getBirthday(), user.getPhone(), user.getAddress(), user.getEmail());

        return n;
    }

    public void setLoginTime(User user){
        String sql = "update user set logintime= SYSDATE() where id=?";
        int n = executeUpdate(sql, user.getId());
        return;
    }

    public int setUserData(String userId, String dataStr){
        String sql = "update user set dataStr=? where id=?";
        int n = executeUpdate(sql, dataStr, userId);
        return n;
    }
}
