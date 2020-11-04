/**
 * @Title: JsonUtils.java
 * @Package com.ronglian.jspt.utils
 * @Description: 
 * @author tolly
 * @date 2013-6-22 下午4:58:52
 * @version V1.0
*/
package com.ronglian.hh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: JsonUtils
 * @Description: JSON工具类
 * @author tolly
 * @date 2013-6-22 下午4:58:52
 * 
 */
public class JsonUtils {
	private static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	
	/**
	 * 从Json构造MAP
	 * @param jsonStr
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> buildMapFormJson(String jsonStr) {
		Map<String, Object> objMap = Maps.newHashMap();
		if (StringUtils.isBlank(jsonStr)) {
			return objMap;
		}
		objMap = jsonBinder.fromJson(jsonStr, HashMap.class);
		return objMap;
	}
	
	/**
	 * json串转化为List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> buildListFormJson(String jsonStr) {
		List<Map<String, Object>> objList = Lists.newArrayList();
		objList = jsonBinder.fromJson(jsonStr, ArrayList.class);
		return objList;
	}

	/**
	 * 对象转化为json串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJosnFromObject(Object object) {
		return jsonBinder.toJson(object);
	}

	/**
	 * 将Map转化为Json
	 *
	 * @param map
	 * @return String
	 */
	public static <T> String mapToJson(Map<String, T> map) {
		String jsonStr = JSON.toJSONString(map);
		return jsonStr;
	}

	public static JSONObject toJsonFromStr(String str) {
		if (org.apache.commons.lang.StringUtils.isBlank(str)) {
			return new JSONObject();
		}
		return JSONObject.parseObject(str, Feature.OrderedField);
	}
}
