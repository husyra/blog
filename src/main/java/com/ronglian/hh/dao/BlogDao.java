package com.ronglian.hh.dao;

import com.ronglian.hh.domain.Blog;
import com.ronglian.hh.jdbc.BaseDao;
import com.ronglian.hh.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class BlogDao {

    protected static Logger logger = LoggerFactory.getLogger(BlogDao.class);
    BaseDao baseDao = new BaseDao();

    public List<Blog> queryBlogByUser(String userId, String content, int limit){
        String sql = "select * from blog where owner like ? and content like ? order by pubtime desc";
        if(StringUtils.isBlank(userId)){
            userId = "%";
        }

        if(StringUtils.isBlank(content)){
            content="%";
        }else{
            content ="%"+content+"%";
        }

        if(limit!=0){
            sql = sql+" limit "+ limit;
        }
        List<Blog> list = (List<Blog>)baseDao.selectMore(sql, Blog.class, userId, content);
        return list;
    }

    public int add(Blog blog){
        String id = StringUtils.getSeq("001");
        blog.setId(id);
        String sql = "insert into blog(id, owner, title, content, image, type, pubtime, updatetime, status, hot) values(?, ?, ?, ?, ?, '0', sysdate(), sysdate(), '1', 0)";
        int n= baseDao.executeUpdate(sql, id, blog.getOwner(), blog.getTitle(), blog.getContent(), blog.getImage());
        return n;
    }

    /**
     * 更新微博热度
     */
    public int updateHot(String blogId, String type){
        //先查询当前热度
        String query = "select * from blog where id=? ";
        Blog blog = (Blog) baseDao.selectOne(query, Blog.class, blogId);
        if(blog == null){
            return 0;
        }

        int hot = blog.getHot();
        if("add".equals(type)){
            hot++;
        }else{
            hot--;
        }

        String sql = "update blog set hot=? where id=? ";
        return baseDao.executeUpdate(sql, hot, blogId);
    }

    public int del(String id){
        String sql = "delete from blog where id=? ";
        int n = baseDao.executeUpdate(sql, id);
        return n;
    }
}
