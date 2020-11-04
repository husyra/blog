package com.ronglian.hh.controller;

import com.ronglian.hh.dao.BlogDao;
import com.ronglian.hh.dao.CommDao;
import com.ronglian.hh.dao.FriendDao;
import com.ronglian.hh.dao.UserDao;
import com.ronglian.hh.domain.Blog;
import com.ronglian.hh.domain.CommentUser;
import com.ronglian.hh.domain.User;
import com.ronglian.hh.util.StringUtils;
import com.ronglian.hh.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/user")
public class UserController {

    protected static Logger logger = LoggerFactory.getLogger(UserController.class);
    UserDao userDao = new UserDao();
    BlogDao blogDao = new BlogDao();
    FriendDao friendDao = new FriendDao();
    CommDao commDao = new CommDao();

    @RequestMapping(value="/add.do")
    public String userAdd(User user, ModelMap model){
        logger.info("== userAdd =="+user);

        int n= userDao.addUser(user);
        if(n<1){
            logger.info("用户名不能重复");
            model.addAttribute("errorMsg", "注册失败");
            return "regist";
        }

        logger.info("注册成功");
        model.addAttribute("name", user.getName());
        //重定向到首页
        return "redirect:/";
    }

    @RequestMapping(value="/getUserJson")
    public String queryUser(HttpServletResponse response, String userId){
        User user = userDao.findById(userId);
        Map<String,Object> resultMap = new HashMap<>();
        if(user == null){
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "查询失败");
            return StringUtils.responseOut(response, resultMap);
        }
        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "查询成功");
        resultMap.put("User", user);
        return StringUtils.responseOut(response, resultMap);
    }

    @RequestMapping(value="/list.do")
    public ModelAndView list(String name, @RequestParam(value = "pageNo", defaultValue = "1") String pageNo,
                             @RequestParam(value = "pageSize", defaultValue = "10") String pageSize){
        List<User> userList = userDao.userList(name);
        Map<String, Object> map = new HashMap<String,Object>();
        logger.info("userList=="+userList);
        map.put("RESULT_LIST", userList);
        //分页
        Utils.pageable(map, pageNo, pageSize);
        map.put("name", name);
        map.put("userList", map.get("RESULT_LIST"));
        return new ModelAndView("userList", map);
    }

    @RequestMapping(value="/game.do")
    public String game(HttpServletRequest request, ModelMap model) {
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            //为保证实时取数据，查询用户信息
            user = userDao.findById(user.getId());
            model.addAttribute("User", user);
        }
        //用户未登录，提示用户无法保存游戏记录
        return "2048";
    }

    @RequestMapping(value ="saveGame.do")
    public String saveGame(String userId, String data, HttpServletResponse response){
        logger.info("=saveGame=");
        int a= userDao.setUserData(userId, data);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "保存成功");

        if(a<=0){
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "保存失败");
        }

        return StringUtils.responseOut(response, resultMap);
    }

    /**
     * 用户信息管理
     */
    @RequestMapping(value="detail.do")
    public String detail(String userId, ModelMap model, HttpServletRequest request){
        User user = null;
        if(!StringUtils.isBlank(userId)){
            user = userDao.findById(userId);
        }else{
            //页面传递的用户ID为空，则获取登录用户
            user = (User)request.getSession().getAttribute("loginUser");
        }

        if(user == null){
            logger.info("user is null");
            model.addAttribute("errorMsg","请登录");
            return "error";
        }
        model.addAttribute("User", user);

        List<User> fensi= friendDao.friendsForMe(userId, 0);
        List<User> guanzhu = friendDao.friendsForMy(userId, 0);
        List<Blog> blogs = blogDao.queryBlogByUser(userId, "", 0);
        List<CommentUser> comments = commDao.queryCommUsers(userId, "", "", 0);

        model.put("fensi", fensi);
        model.put("guanzhu", guanzhu);
        model.put("blogs", blogs);
        model.put("comments", comments);

        return "userDetail";
    }

    @RequestMapping(value="/test.do")
    public ModelAndView test(String name){
        System.out.println("name="+name);
        return new ModelAndView("test");
    }

}
