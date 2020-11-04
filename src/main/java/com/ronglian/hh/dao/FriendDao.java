package com.ronglian.hh.dao;

import com.ronglian.hh.domain.Friend;
import com.ronglian.hh.domain.User;
import com.ronglian.hh.jdbc.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class FriendDao extends BaseDao {
    protected static Logger logger = LoggerFactory.getLogger(FriendDao.class);
    BaseDao baseDao = new BaseDao();

    /**
     * 添加好友
     */
    public int addFriend(Friend friend){
        String sql = "insert into friend(owner, friend, addTime, updateTime) values(?, ?, sysdate(), sysdate())";
        return baseDao.executeUpdate(sql, friend.getOwner(), friend.getFriend());
    }
    /**
     * 我关注的朋友
     * @return
     */
    public List<User> friendsForMy(String userId, int limit){
        String sql = "select u.* from user u INNER JOIN friend f on u.id=f.friend where f.owner= ?  order by f.addTime desc";

        if(limit!=0){
            sql = sql + " limit "+limit;
        }

        return (List<User>)baseDao.selectMore(sql, User.class, userId);
    }

    /**
     * 关注我的朋友
     */
    public List<User> friendsForMe(String userId, int limit){
        String sql = "select u.* from user u INNER JOIN friend f on u.id=f.owner where f.friend= ? order by f.addTime desc";

        if(limit!=0){
            sql = sql + " limit "+limit;
        }

        return (List<User>)baseDao.selectMore(sql, User.class, userId);
    }

}
