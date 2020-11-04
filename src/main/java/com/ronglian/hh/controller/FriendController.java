package com.ronglian.hh.controller;

import com.ronglian.hh.dao.FriendDao;
import com.ronglian.hh.domain.Friend;
import com.ronglian.hh.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping(value="/friend")
public class FriendController {

    protected static Logger logger = LoggerFactory.getLogger(FriendController.class);
    FriendDao friendDao = new FriendDao();

    @RequestMapping(value="add.do")
    public String addFriend(String owner, String fid, HttpServletResponse response){

        Map<String,Object> map = new HashMap<String,Object>();

        if(StringUtils.isBlank(owner) || StringUtils.isBlank(fid)){
            map.put("resultCode","9999");
            map.put("resultMsg","操作有误");
            return StringUtils.responseOut(response, map);
        }

        Friend friend = new Friend();
        friend.setOwner(owner);
        friend.setFriend(fid);

        int i = friendDao.addFriend(friend);
        if(i>0){
            map.put("resultCode","0000");
            map.put("resultMsg","添加成功");
        }else{
            map.put("resultCode","9999");
            map.put("resultMsg","添加失败");
        }

        return StringUtils.responseOut(response, map);
    }

}
