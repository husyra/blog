package com.ronglian.hh.dao;

import com.ronglian.hh.domain.Comment;
import com.ronglian.hh.domain.CommentUser;
import com.ronglian.hh.jdbc.BaseDao;
import com.ronglian.hh.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CommDao{

    protected static Logger logger = LoggerFactory.getLogger(CommDao.class);
    BaseDao baseDao = new BaseDao();

    public int add(Comment comm){
        String id = StringUtils.getSeq("002");
        comm.setId(id);
        String sql = "insert into comment(id, object, parent, owner, content, pubtime, hot) values(?, ?, ?, ?, ?, sysdate(), 0)";
        int n = baseDao.executeUpdate(sql, id, comm.getObject(), comm.getParent(), comm.getOwner(), comm.getContent());
        return n;
    }

    public Comment queryOne(String id){
        String sql = "select * from comment where id=? ";
        Comment comm = (Comment) baseDao.selectOne(sql, Comment.class, id);
        return comm;
    }

    public int del(String id){
        String sql = "delete from comment where id=? ";
        int n = baseDao.executeUpdate(sql, id);
        return n;
    }

    public int updateHot(String commId, String type){
        String query = "select * from comment where id=? ";
        Comment comm = (Comment) baseDao.selectOne(query, Comment.class, commId);
        if(comm == null){
            return 0;
        }
        int hot = comm.getHot();
        if("add".equals(type)){
            hot++;
        }else{
            hot--;
        }
        String sql = "update comment set hot=? where id=? ";
        return baseDao.executeUpdate(sql, hot, commId);
    }

    public List<Comment> queryComments(String blogId, String userId, int limit){
        String sql = "select * from comment where object like ? and owner like ? order by pubtime desc ";
        if(StringUtils.isBlank(blogId)){
            blogId = "%";
        }
        if(StringUtils.isBlank(userId)){
            userId = "%";
        }
        if(limit!=0){
            sql = sql+" limit "+ limit;
        }
        List<Comment> comments = baseDao.selectMore(sql, Comment.class, blogId, userId);
        return comments;
    }

    public List<CommentUser> queryCommUsers(String userId, String blogId, String parent, int limit){
        String sql = "select c.*, u.name as userName, u.photo as userPhoto from comment c " +
                "INNER JOIN user u on c.owner = u.id where c.owner like ? and c.object like ?" +
                " and parent like ? order by c.pubtime desc ";
        if(StringUtils.isBlank(userId)){
            userId = "%";
        }
        if(StringUtils.isBlank(blogId)){
            blogId = "%";
        }
        if(StringUtils.isBlank(parent)){
            parent = "%";
        }
        if(limit!=0){
            sql = sql+" limit "+ limit;
        }
        List<CommentUser> comments = baseDao.selectMore(sql, CommentUser.class, userId, blogId, parent);
        return comments;
    }

}
