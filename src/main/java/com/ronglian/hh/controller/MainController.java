package com.ronglian.hh.controller;

import com.ronglian.hh.dao.BlogDao;
import com.ronglian.hh.dao.FriendDao;
import com.ronglian.hh.dao.UserDao;
import com.ronglian.hh.domain.Blog;
import com.ronglian.hh.domain.User;
import com.ronglian.hh.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    protected static Logger logger = LoggerFactory.getLogger(MainController.class);
    UserDao userDao =new UserDao();
    BlogDao blogDao =new BlogDao();
    FriendDao friendDao =new FriendDao();

    @RequestMapping(value="/login")
    public String login(String name, String pwd, HttpServletRequest request){
        logger.info("==login=={},{}",name, pwd);

        User user = (User) request.getSession().getAttribute("loginUser");
        //验证用户是否已登录
        if(user!=null){
            logger.info("user is not null");
            //登录则跳到，用户需要跳转的页面接口
            return "redirect:home.do";
        }
        //未登录，跳转到登录页面
        logger.info("user is null");
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:login";
    }

    /**
     * 登录验证
     * @return
     */
    @RequestMapping(value="/validateLogin.do")
    public String validateLogin(String name, String pwd, HttpServletRequest request, HttpServletResponse response){
        logger.info("validateLogin, name={},pwd={}",name,pwd);

        User user = userDao.findByName(name);
        logger.info("user,="+user);
        Map<String,Object> resultMap = new HashMap<String,Object>();

        if(user==null){
            logger.info("user is null");
            resultMap.put("resultCode", "9902");
            resultMap.put("resultMsg", "账号不存在");
            return StringUtils.responseOut(response, resultMap);
        }

        if(!user.getPwd().equals(pwd)){
            logger.info("pwd is incorrect");
            resultMap.put("resultCode", "9903");
            resultMap.put("resultMsg", "密码错误");
            return StringUtils.responseOut(response, resultMap);
        }

        request.getSession().setAttribute("loginUser", user);
        //更新登录时间
        userDao.setLoginTime(user);
        // 恢复session会话失效时间
        request.getSession().setMaxInactiveInterval(30 * 60);// 30分钟，以秒为单位

        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "验证成功");
        resultMap.put("userId", user.getId());
        logger.info("validate success");
        logger.info("LoginUser="+request.getSession().getAttribute("loginUser"));
        return StringUtils.responseOut(response, resultMap);
    }

    @RequestMapping(value="/queryUser.do")
    public String queryUser(String name, HttpServletResponse response){
        logger.info("==queryUser=="+name);
        User user = userDao.findByName(name);
        Map<String,Object> resultMap = new HashMap<String,Object>();

        if(user!=null){
            logger.info("user is not null");
            resultMap.put("resultCode", "9999");
            resultMap.put("resultMsg", "用户名已被占用");
            return StringUtils.responseOut(response, resultMap);
        }

        resultMap.put("resultCode", "0000");
        resultMap.put("resultMsg", "用户名可用");
        return StringUtils.responseOut(response, resultMap);
    }

    @RequestMapping(value="/home.do")
    public String home(String userId, HttpServletRequest request, String name, String pwd, ModelMap model){
        logger.info("==主页=="+userId);

        User user = null;
        if(!StringUtils.isBlank(userId)){
            user = userDao.findById(userId);
        }else{
            //页面传递的用户ID为空，则获取登录用户
            user = (User)request.getSession().getAttribute("loginUser");
        }

//        logger.info("user=="+user);

        if(user == null){
            logger.info("user is null");
            model.addAttribute("errorMsg","请登录");
            return "error";
        }
        model.addAttribute("User", user);

        //查询用户发布的微博
        List<Blog> blogs = blogDao.queryBlogByUser(user.getId(), "", 2);
        model.addAttribute("Blogs", blogs);

        //查询我关注的人
        List<User> friendsForMy = friendDao.friendsForMy(user.getId(), 3);
        //关注我的人
        List<User> friendsForMe = friendDao.friendsForMe(user.getId(), 3);

        model.addAttribute("friendsForMy", friendsForMy);
        model.addAttribute("friendsForMe", friendsForMe);
        return "home";
    }

    @RequestMapping(value="/regist.do")
    public  String regist(){
        logger.info("== 注册 ==");
        return "regist";
    }

}
