package com.ronglian.hh.tags;

import com.ronglian.hh.jdbc.JDBCUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 功能：页面标签，编号转名称.
 * <p>
 * Title: 页面标签，编号转名称
 * </p>
 * <p>
 * Description: 页面标签，编号转名称。添加属性，对名称按长度进行截断。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: ronglian
 * </p>
 * 
 * @author yxing 修改
 * @version 1.0.0 date：Apr 21, 2010
 */
@SuppressWarnings("serial")
public class IdtoName extends BodyTagSupport {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/* 表名 */
	private String tableName = "";
	
	/* 字段Id */
	private String colId = "";
	
	/* 字段Name */
	private String colName = "";
	
	/* 字段 */
	private String idValue = "";
	
	/* 页面输出对象 */
	private JspWriter out = null;
	
	/* 查询条件 */
	private String parameter1 = "";

	/* 查询条件 */
	private String parameter2 = "";

	/* 查询条件 */
	private String parameter3 = "";

	private String paramevalue1 = "";

	private String paramevalue2 = "";

	private String paramevalue3 = "";

	/* 是否字符串 */
	private String isChar = "";
	
	/* 截断的长度 */
	private String cutLength = "";
	
	/**
	 * 重写 BodyTagSupport 类的 doStartTag 方法 也是标签的入口方法.
	 * 
	 * @throws JspException
	 * @return int
	 */
	@Override
	public synchronized int doStartTag()
		throws JspException {
		try {
			out = pageContext.getOut();
			out.print(getHtml());
		} catch (IOException ex) {
			throw new JspException(ex.toString());
		}
		return EVAL_BODY_BUFFERED;
	}
	
	/*
	 * 获取查询SQL语句.
	 * 
	 * @return sql
	 */
	public String getSql() {
		StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append(getColName());
        sb.append(" from ");
        sb.append(getTableName());
		sb.append(" where ");
		sb.append(getColId());
		sb.append(" = ");
		if ("false".equals(getIsChar())) {
			sb.append(getIdValue());
		} else {
			sb.append("'").append(getIdValue()).append("'");
		}
		if (StringUtils.isNotBlank(getParameter1()) && StringUtils.isNotBlank(getParamevalue1())) {
			sb.append(" and ").append(getParameter1()).append("= '").append(getParamevalue1()).append("' ");
		}
		if (StringUtils.isNotBlank(getParameter2()) && StringUtils.isNotBlank(getParamevalue2())) {
			sb.append(" and ").append(getParameter2()).append("= '").append(getParamevalue2()).append("' ");
		}
		if (StringUtils.isNotBlank(getParameter3()) && StringUtils.isNotBlank(getParamevalue3())) {
			sb.append(" and ").append(getParameter3()).append("= '").append(getParamevalue3()).append("' ");
		}
        return sb.toString();
	}
	
	/**
	 * 根据 getSql() 方法返回 sql 语句，查出相应的结果.
	 * 
	 * @throws JspException
	 * @return ResultSet
	 */
	private String getHtml() throws JspException {
		String sql = getSql();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String html = "";
		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				String idname = rs.getString(1);
				if (null != getCutLength() && !"".equals(getCutLength()) && idname.length() > Integer.valueOf(getCutLength())) {
					idname = "<span title=\"" + idname + "\">" + (idname.substring(0, Integer.valueOf(getCutLength())) + "…") + "</span>";
				}
				html = StringUtils.trimToEmpty(idname);
			}
		} catch (SQLException ex) {
			throw new JspException("编号转名称失败！" , ex);
		} catch (Exception ex) {
			throw new JspException("编号转名称异常！" , ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("编号转名称异常！" , e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error("编号转名称异常！" , e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("编号转名称异常！" , e);
				}
			}
		}
		return html;
	}
	
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * @return the colId
	 */
	public String getColId() {
		return colId;
	}
	
	/**
	 * @param colId the colId to set
	 */
	public void setColId(String colId) {
		this.colId = colId;
	}
	
	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}
	
	/**
	 * @param colName the colName to set
	 */
	public void setColName(String colName) {
		this.colName = colName;
	}
	
	/**
	 * @return the idValue
	 */
	public String getIdValue() {
		return idValue;
	}
	
	/**
	 * @param idValue the idValue to set
	 */
	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public String getParameter1() {
		return parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	public String getParameter2() {
		return parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}

	public String getParameter3() {
		return parameter3;
	}

	public void setParameter3(String parameter3) {
		this.parameter3 = parameter3;
	}

	public String getParamevalue1() {
		return paramevalue1;
	}

	public void setParamevalue1(String paramevalue1) {
		this.paramevalue1 = paramevalue1;
	}

	public String getParamevalue2() {
		return paramevalue2;
	}

	public void setParamevalue2(String paramevalue2) {
		this.paramevalue2 = paramevalue2;
	}

	public String getParamevalue3() {
		return paramevalue3;
	}

	public void setParamevalue3(String paramevalue3) {
		this.paramevalue3 = paramevalue3;
	}

	/**
	 * @return the isChar
	 */
	public String getIsChar() {
		return isChar;
	}
	
	/**
	 * @param isChar the isChar to set
	 */
	public void setIsChar(String isChar) {
		this.isChar = isChar;
	}
	
	/**
	 * @return the cutLength
	 */
	public String getCutLength() {
		return cutLength;
	}
	
	/**
	 * @param cutLength the cutLength to set
	 */
	public void setCutLength(String cutLength) {
		this.cutLength = cutLength;
	}
}
