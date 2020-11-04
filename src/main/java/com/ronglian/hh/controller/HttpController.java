package com.ronglian.hh.controller;

import com.ronglian.hh.util.JsonUtils;
import com.ronglian.hh.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value="/http")
public class HttpController {

    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 账号查询
     * @param companyCode
     * @param userCode
     * @param response
     * @return
     */
    @RequestMapping(value = "/tcsw10")
    public String tcsw10(String companyCode, String userCode, HttpServletResponse response){
        logger.info("== tcsw10 =={},{}", companyCode,userCode);
        String result = "";
        if(StringUtils.isBlank(companyCode) || StringUtils.isBlank(userCode)){
            result = tcsw10_fail();
        }else if("123456".equals(userCode) || "00850001".equals(userCode) || "55555".equals(userCode)){
            result = tcsw10_success();
        }else if("654321".equals(userCode)){
            result = tcsw10_success_not();
        }else{
            result = tcsw10_fail();
        }

        return StringUtils.responseOut(response, result);
    }

    /**
     * 欠费查询
     * @param companyCode
     * @param userCode
     * @param response
     * @return
     */
    @RequestMapping(value = "/tcsw02")
    public String tcsw02(String companyCode, String userCode, HttpServletResponse response){
        logger.info("== tcsw02 =={},{}", companyCode,userCode);
        String result = "";
        if(StringUtils.isBlank(companyCode) || StringUtils.isBlank(userCode)){
            result = tcsw02_fail();
        }else if("123456".equals(userCode)) {
            result = tcsw02_success(2);
        }else if("55555".equals(userCode)) {
            result = tcsw02_success_not();
        }else if("00850001".equals(userCode)) {
            result = tcsw02_fail();
        }

        return StringUtils.responseOut(response, result);
    }

    /**
     * 历史查询
     * @param response
     * @return
     */
    @RequestMapping(value = "/tcsw07")
    public String tcsw07(String companyCode, String userCode, String month, HttpServletResponse response){
        logger.info("== tcsw07 ==");

        String result = "";
        if(StringUtils.isBlank(companyCode) || StringUtils.isBlank(userCode)){
            result = tcsw07_fail();
        }else if("123456".equals(userCode)) {
            result = tcsw07_success(month);
        }else if("00850001".equals(userCode)) {
            result = tcsw07_success_not();
        }else{
            result = tcsw07_fail();
        }

        return StringUtils.responseOut(response, result);
    }

    @RequestMapping(value = "/tcsw08", method = RequestMethod.POST)
    public String tcsw08(@RequestBody String str, HttpServletResponse response){
        logger.info("== tcsw08 =="+str);
        Map<String, Object> reqMap = JsonUtils.buildMapFormJson(str);
        String companyCode = String.valueOf(reqMap.get("companyCode"));
        String userCode = String.valueOf(reqMap.get("userCode"));

        String result = "";
        if(StringUtils.isBlank(companyCode) || StringUtils.isBlank(userCode) || "55555".equals(userCode)){
            result = tcsw08_fail();
        }else{
            result = tcsw08_success();
        }
        return StringUtils.responseOut(response, result);
    }

    private String tcsw10_success(){
        return "{ \n" +
                "\"status\": true, \n" +
                "\"message\": \"success.\", \n" +
                "\"data\": { \n" +
                "\"code\": null,\n" +
                "\"userCode\": \"39308\",\n" +
                "\"userName\": \"108办公室\", \n" +
                "\"initCode\": \"015J_105800123\", \n" +
                "\"meterType\": 0, \n" +
                "\"userArea\": \"001058\", \n" +
                "\"areaName\": \"建行1#统建楼（解放路174#）\", \n" +
                "\"proportional\": \"民用;100;2.59\", \n" +
                "\"balanceMoney\": \"20.81\", \n" +
                "\"meterName\": \"机械型水表\", \n" +
                "\"userPhone\": \"13565295188\", \n" +
                "\"address\": \"北基房产花园六期商住办公楼 旌旗\"\n" +
                "} \n" +
                "}";
    }

    private String tcsw10_success_not(){
        return "{ \n" +
                "\"status\": true, \n" +
                "\"message\": \"success.\", \n" +
                "\"data\": { \n" +
                "\"code\": null,\n" +
                "\"userCode\": \"39308\",\n" +
                "\"userName\": \"108办公室\", \n" +
                "\"initCode\": \"015J_105800123\", \n" +
                "\"meterType\": 1, \n" +
                "\"userArea\": \"001058\", \n" +
                "\"areaName\": \"建行1#统建楼（解放路174#）\", \n" +
                "\"proportional\": \"民用;100;2.59\", \n" +
                "\"balanceMoney\": \"20.81\", \n" +
                "\"meterName\": \"机械型水表\", \n" +
                "\"userPhone\": \"13565295188\", \n" +
                "\"address\": \"北基房产花园六期商住办公楼 旌旗\"\n" +
                "} \n" +
                "}";
    }

    private String tcsw10_fail(){
        return "{ \n" +
                "\"status\": false, \n" +
                "\"message\": \"账号不存在\", \n" +
                "\"data\": {}";
    }

    private String tcsw02_success(int n){
        if(n == 1){
            return "{ \n" +
                    "\"status\": true, \n" +
                    "\"message\": \"success.\", \n" +
                    "\"data\": { \n" +
                    "\"balance\": \"0.00\", \n" +
                    "\"meterType\": \"1\", \n" +
                    "\"userName\": \"徐胜军\", \n" +
                    "\"isRemission\":\"0\", \n" +
                    "\"list\": [ \n" +
                    "{ \n" +
                    "\"userName\": \"徐胜军\", \n" +
                    "\"checkId\": \"282988f3cdeb4fc1932f4d91ec896bd6\", \n" +
                    "\"checkerId\": \"0132\", \n" +
                    "\"checkMen\": \"杨小桔\", \n" +
                    "\"meterName\": \"机构水表\", \n" +
                    "\"checkDate\": \"2020-03-21 00:00:00\", \n" +
                    "\"checkMonth\": \"202003\", \n" +
                    "\"kindId\": \"28\", \n" +
                    "\"itemId\": 15, \n" +
                    "\"proportional\": \"居民用水;100;3.40\", \n" +
                    "\"upRead\": 125.0, \n" +
                    "\"currentRead\": 141.0, \n" +
                    "\"addNum\": 0.0, \n" +
                    "\"factNum\": 79.0, \n" +
                    "\"meterNum\": \"000321490w\", \n" +
                    "\"itemPrice\": 2.7, \n" +
                    "\"waterFee\": 43.2, \n" +
                    "\"sewageFee\": 3.2, \n" +
                    "\"resourceFee\": 1.4, \n" +
                    "\"totalWaterFee\": 47.8 \n" +
                    "} \n" +
                    "\n" +
                    "] \n" +
                    "} \n" +
                    "}";
        }

        return "{ \n" +
                "\"status\": true, \n" +
                "\"message\": \"success.\", \n" +
                "\"data\": { \n" +
                "\"balance\": \"0.00\", \n" +
                "\"meterType\": \"1\", \n" +
                "\"userName\": \"徐胜军\", \n" +
                "\"isRemission\":\"0\", \n" +
                "\"list\": [ \n" +
                "{ \n" +
                "\"userName\": \"徐胜军\", \n" +
                "\"checkId\": \"282988f3cdeb4fc1932f4d91ec896bd5\", \n" +
                "\"checkerId\": \"012\", \n" +
                "\"checkMen\": \"杨小桔\", \n" +
                "\"meterName\": \"机构水表\", \n" +
                "\"checkDate\": \"2020-02-21 00:00:00\", \n" +
                "\"checkMonth\": \"202002\", \n" +
                "\"kindId\": \"27\", \n" +
                "\"itemId\": 14, \n" +
                "\"proportional\": \"居民用水;100;3.40\", \n" +
                "\"upRead\": 47.0, \n" +
                "\"currentRead\": 125.0, \n" +
                "\"addNum\": 0.0, \n" +
                "\"factNum\": 78.0, \n" +
                "\"meterNum\": \"0003214901\", \n" +
                "\"itemPrice\": 2.7, \n" +
                "\"waterFee\": 210.6, \n" +
                "\"sewageFee\": 31.2, \n" +
                "\"resourceFee\": 23.4, \n" +
                "\"totalWaterFee\": 265.2 \n" +
                "},\n" +
                "{ \n" +
                "\"userName\": \"徐胜军\", \n" +
                "\"checkId\": \"282988f3cdeb4fc1932f4d91ec896bd6\", \n" +
                "\"checkerId\": \"0132\", \n" +
                "\"checkMen\": \"杨小桔\", \n" +
                "\"meterName\": \"机构水表\", \n" +
                "\"checkDate\": \"2020-03-21 00:00:00\", \n" +
                "\"checkMonth\": \"202003\", \n" +
                "\"kindId\": \"28\", \n" +
                "\"itemId\": 15, \n" +
                "\"proportional\": \"居民用水;100;3.40\", \n" +
                "\"upRead\": 125.0, \n" +
                "\"currentRead\": 141.0, \n" +
                "\"addNum\": 0.0, \n" +
                "\"factNum\": 79.0, \n" +
                "\"meterNum\": \"000321490w\", \n" +
                "\"itemPrice\": 2.7, \n" +
                "\"waterFee\": 43.2, \n" +
                "\"sewageFee\": 3.2, \n" +
                "\"resourceFee\": 1.4, \n" +
                "\"totalWaterFee\": 47.8 \n" +
                "} \n" +
                "\n" +
                "] \n" +
                "} \n" +
                "}";

    }

    private String tcsw02_success_not(){
        return "{ \n" +
                "\"status\": true, \n" +
                "\"message\": \"success.\", \n" +
                "\"data\": { \n" +
                "} \n" +
                "}";
    }

    private String tcsw02_fail(){
        return "{ \n" +
                "\"status\": false, \n" +
                "\"message\": \"失败\", \n" +
                "\"data\": { \n" +
                "\"list\": [] \n" +
                "} \n" +
                "}";
    }

    private String tcsw07_success(String month){
        if("01".equals(month)) {
            return "{ \n" +
                    "\"status\": true, \n" +
                    "\"message\": \"success.\", \n" +
                    "\"data\": { \n" +
                    "\"totalNum\": 4, \n" +
                    "\"currentPage\": 1, \n" +
                    "\"totalPageCount\": 10, \n" +
                    "\"items\": [] \n" +
                    "} \n" +
                    "}";
        }else if("02".equals(month)){
            return "{ \n" +
                    "\"status\": true, \n" +
                    "\"message\": \"success.\", \n" +
                    "\"data\": { \n" +
                    "\"totalNum\": 2, \n" +
                    "\"currentPage\": 1, \n" +
                    "\"totalPageCount\": 10, \n" +
                    "\"items\": [ \n" +
                    "{ \n" +
                    "\"code\": \"47706\", \n" +
                    "\"realMoney\": 6.8, \n" +
                    "\"payId\": \"babbe115d39641a281b13f5576cbec62\", \n" +
                    "\"createTime\": \"2019-12-21 19:45:15\", \n" +
                    "\"userName\": \"李霞\", \n" +
                    "\"payType\": \"公众号\" \n" +
                    "},\n" +
                    "{ \n" +
                    "\"code\": \"47706\", \n" +
                    "\"realMoney\": 13.6, \n" +
                    "\"payId\": \"823a1f5283b141a1b31c0b93c9e05a1b\", \n" +
                    "\"createTime\": \"2019-10-16 22:26:49\", \n" +
                    "\"userName\": \"李霞\", \n" +
                    "\"payType\": \"公众号\" \n" +
                    "} \n" +
                    "] \n" +
                    "} \n" +
                    "}";
        }else if("03".equals(month)){
            return "{ \n" +
                    "\"status\": true, \n" +
                    "\"message\": \"success.\", \n" +
                    "\"data\": { \n" +
                    "\"totalNum\": 4, \n" +
                    "\"currentPage\": 1, \n" +
                    "\"totalPageCount\": 10, \n" +
                    "\"items\": [ \n" +
                    "{ \n" +
                    "\"code\": \"47706\", \n" +
                    "\"realMoney\": 6.9, \n" +
                    "\"payId\": \"9196af8559364827b649736c1d4dd308\",\n" +
                    "\"createTime\": \"2020-03-01 16:54:49\",\n" +
                    "\"userName\": \"李霞\", \n" +
                    "\"payType\": \"公众号\" \n" +
                    "},\n" +
                    "{ \n" +
                    "\"code\": \"47706\", \n" +
                    "\"realMoney\": 10.2, \n" +
                    "\"payId\": \"236af2832236454199045621a8dbfd2d\", \n" +
                    "\"createTime\": \"2020-01-19 19:12:11\", \n" +
                    "\"userName\": \"李霞\", \n" +
                    "\"payType\": \"公众号\" \n" +
                    "},\n" +
                    "{ \n" +
                    "\"code\": \"47706\", \n" +
                    "\"realMoney\": 6.8, \n" +
                    "\"payId\": \"babbe115d39641a281b13f5576cbec62\", \n" +
                    "\"createTime\": \"2019-12-21 19:45:15\", \n" +
                    "\"userName\": \"李霞\", \n" +
                    "\"payType\": \"公众号\" \n" +
                    "},\n" +
                    "{ \n" +
                    "\"code\": \"47706\", \n" +
                    "\"realMoney\": 13.6, \n" +
                    "\"payId\": \"823a1f5283b141a1b31c0b93c9e05a1b\", \n" +
                    "\"createTime\": \"2019-10-16 22:26:49\", \n" +
                    "\"userName\": \"李霞\", \n" +
                    "\"payType\": \"公众号\" \n" +
                    "} \n" +
                    "] \n" +
                    "} \n" +
                    "}";
        }
        return tcsw07_fail();
    }

    private String tcsw07_fail(){
        return "{ \n" +
                "\"status\": false, \n" +
                "\"message\": \"无记录\", \n" +
                "\"data\": { \n" +
                "\"totalNum\": 0, \n" +
                "\"currentPage\": 1, \n" +
                "\"totalPageCount\": 0, \n" +
                "\"items\": [] \n" +
                "}";
    }

    private String tcsw07_success_not(){
        return "{ \n" +
                "\"status\": true, \n" +
                "\"message\": \"success.\", \n" +
                "\"data\": { \n" +
                "\"totalNum\": 4, \n" +
                "\"currentPage\": 1, \n" +
                "\"totalPageCount\": 10, \n" +
                "\"items\": [] \n" +
                "} \n" +
                "}";
    }

    private String tcsw08_success(){
        return "{ \n" +
                "\"status\": true, \n" +
                "\"message\": \"success.\", \n" +
                "\"data\": null \n" +
                "}";
    }

    private String tcsw08_fail(){
        return "{ \n" +
                "\"status\": false, \n" +
                "\"message\": \"上账失败，原因自己查\", \n" +
                "\"data\": null \n" +
                "}";
    }
}
