package com.ronglian.hh.controller;

import com.ronglian.hh.dao.BlogDao;
import com.ronglian.hh.dao.CommDao;
import com.ronglian.hh.domain.Comment;
import com.ronglian.hh.domain.CommentUser;
import com.ronglian.hh.util.StringUtils;
import com.ronglian.hh.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/comm")
public class CommController {
    protected static Logger logger = LoggerFactory.getLogger(CommController.class);
    CommDao commDao = new CommDao();
    BlogDao blogDao = new BlogDao();

    @RequestMapping(value="/add.do", method = RequestMethod.POST)
    public String add(Comment comm, HttpServletResponse response){
        logger.info("=add="+comm);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(comm == null){
            resultMap.put("resultCode", "9001");
            resultMap.put("resultMsg", "发布失败");
            return StringUtils.responseOut(response, resultMap);
        }

        if(StringUtils.isBlank(comm.getOwner())){
            resultMap.put("resultCode", "9001");
            resultMap.put("resultMsg", "请先登录");
            return StringUtils.responseOut(response, resultMap);
        }

        if(StringUtils.isBlank(comm.getObject())){
            resultMap.put("resultCode", "9001");
            resultMap.put("resultMsg", "数据有误");
            return StringUtils.responseOut(response, resultMap);
        }
        if(StringUtils.isBlank(comm.getContent())){
            resultMap.put("resultCode", "9001");
            resultMap.put("resultMsg", "内容为空");
            return StringUtils.responseOut(response, resultMap);
        }

        int n = commDao.add(comm);
        if(n<1){
            logger.info("fail");
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "发布失败");
            return StringUtils.responseOut(response, resultMap);
        }

        if("0".equals(comm.getParent())){
            //一级评论对应微博增加热度
            n = blogDao.updateHot(comm.getObject(), "add");
        }else{
            //子评论对应父评论增加热度
            n = commDao.updateHot(comm.getParent(), "add");
        }

        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "发布成功");
        return StringUtils.responseOut(response, resultMap);
    }

    /**
     * 评论列表，json格式接口
     */
    @RequestMapping(value="/listJson", method = RequestMethod.POST)
    public String list(String blogId, String userId, int limit, HttpServletResponse response){

        Map<String,Object> resultMap = new HashMap<>();
        List<CommentUser> comments = commDao.queryCommUsers(userId, blogId, "", limit);
        logger.info("comments=="+comments);
        if(comments == null){
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "查询失败");
            return StringUtils.responseOut(response, resultMap);
        }

        //树形结构数据处理
        List<CommentUser> resultComments = Utils.commentTrees(comments);

        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "成功");
        resultMap.put("comments", resultComments);
        return StringUtils.responseOut(response, resultMap);
    }

    /**
     * 删除评论
     */
    @RequestMapping(value="/del.do")
    public String del(HttpServletResponse response, String commId){
        logger.info("=del="+commId);

        Map<String,Object> resultMap = new HashMap<>();
        Comment comm = commDao.queryOne(commId);
        if(comm == null){
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "记录不存在");
            return StringUtils.responseOut(response, resultMap);
        }

        int n = commDao.del(comm.getId());

        if(n<1){
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "删除失败");
            return StringUtils.responseOut(response, resultMap);
        }

        if("0".equals(comm.getParent())){
            //一级评论对应微博减少热度
            n = blogDao.updateHot(comm.getObject(), "dec");
        }else{
            //二级评论对应父评论减少热度
            n = commDao.updateHot(comm.getParent(), "desc");
        }

        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "成功");
        return StringUtils.responseOut(response, resultMap);
    }

}
