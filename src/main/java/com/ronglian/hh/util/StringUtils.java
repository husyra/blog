package com.ronglian.hh.util;

import com.alibaba.fastjson.JSON;
import com.ronglian.hh.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class StringUtils {

    protected static Logger logger = LoggerFactory.getLogger(MainController.class);
    public static final String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
    private static int num = 0;

    /**
     * 首字母转大写
     * @param str 需要转换的字符串
     * @return 转换完的字符串
     */
    public static String toFirstUpper(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    /**
     * 判断字符串是否为空
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 获取当前系统时间
     *
     * @param pattern 时间格式
     * @return String
     */
    public static final String getNowDT(String pattern) {

        String date = "";
        if (StringUtils.isBlank(pattern)) {
            // pattern = "yyyy-MM-dd HH:mm:ss";
            pattern = defaultDatePattern;// by ljyan 20140918
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            date = sdf.format(new Date());
        } catch (Exception e) {
            logger.error("获取当前时间失败:{}", e.getMessage());
        }
        return date;
    }

    /**
     * 生成序列号
     */
    public synchronized static String getSeq(String flag){
        if (num >= 999) {
            num = 0;
        }
        num++;

        return flag+getNowDT("yyMMddHHmmss")+String.format("%1$,03d", num);
    }



    /**
     * 输出json串
     * @param response
     * @return
     */
    public static String responseOut(HttpServletResponse response, Map<String,Object> map){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try{
            PrintWriter out = response.getWriter();
            String str = JSON.toJSONString(map);
            out.print(str);
        }catch (IOException e){

        }
        return null;
    }

    public static String responseOut(HttpServletResponse response, String result){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try{
            PrintWriter out = response.getWriter();
            out.print(result);
        }catch (IOException e){

        }
        return null;
    }

}
