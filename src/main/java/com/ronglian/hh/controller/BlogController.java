package com.ronglian.hh.controller;

import com.ronglian.hh.dao.BlogDao;
import com.ronglian.hh.dao.UserDao;
import com.ronglian.hh.domain.Blog;
import com.ronglian.hh.domain.Comment;
import com.ronglian.hh.domain.User;
import com.ronglian.hh.util.StringUtils;
import com.ronglian.hh.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/blog")
public class BlogController {

    protected static Logger logger = LoggerFactory.getLogger(BlogController.class);
    BlogDao blogDao = new BlogDao();
    UserDao userDao = new UserDao();

    @RequestMapping(value="/add.do", method = RequestMethod.POST)
    public String add(Blog blog, HttpServletResponse response){
        logger.info("==blog.add=={}",blog);

        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(blog==null){
            logger.info("发布失败");
            resultMap.put("resultCode", "9001");
            resultMap.put("resultMsg", "发布失败");
            return StringUtils.responseOut(response, resultMap);
        }

        if(StringUtils.isBlank(blog.getOwner())){
            logger.info("请先登录");
            resultMap.put("resultCode", "9001");
            resultMap.put("resultMsg", "请先登录");
            return StringUtils.responseOut(response, resultMap);
        }

        if(StringUtils.isBlank(blog.getContent())){
            logger.info("发布内容为空");
            resultMap.put("resultCode", "9001");
            resultMap.put("resultMsg", "发布内容为空");
            return StringUtils.responseOut(response, resultMap);
        }

        int n = blogDao.add(blog);
        if(n<1){
            logger.info("发布失败");
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "发布失败");
            return StringUtils.responseOut(response, resultMap);
        }

        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "发布成功");
        return StringUtils.responseOut(response, resultMap);
    }

    @RequestMapping(value="/del.do")
    public String del(HttpServletResponse response, String blogId){
        logger.info("=del="+blogId);

        Map<String,Object> resultMap = new HashMap<>();
        int n = blogDao.del(blogId);

        if(n<1){
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "删除失败");
            return StringUtils.responseOut(response, resultMap);
        }

        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "成功");
        return StringUtils.responseOut(response, resultMap);
    }

    @RequestMapping(value="/list.do")
    public String list(String userId, String search, HttpServletRequest request, ModelMap model,
                       @RequestParam(value = "pageNo", defaultValue = "1") String pageNo, @RequestParam(value = "pageSize", defaultValue = "3") String pageSize){
        //不传userId查询所有用户的微博
        List<Blog> blogList = blogDao.queryBlogByUser(userId, search, 0);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("RESULT_LIST", blogList);
        Utils.pageable(map, pageNo, pageSize);

        model.addAttribute("blogList", map.get("RESULT_LIST"));
        model.addAttribute("userId", userId);
        model.addAttribute("search", search);
        model.addAllAttributes(map);

        if(!StringUtils.isBlank(userId)){
            User user = userDao.findById(userId);
            model.put("User", user);
        }

        return "blogList";
    }

}
