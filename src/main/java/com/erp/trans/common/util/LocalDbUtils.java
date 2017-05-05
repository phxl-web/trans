package com.erp.trans.common.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作本地工具类
 * @date	2017年1月11日 上午10:25:05
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public class LocalDbUtils {
	private final static Logger logger = LoggerFactory.getLogger(LocalDbUtils.class);
	
	/**
	 * 关闭数据库资源
	 * @author	黄文君
	 * @date	2017年1月9日 下午5:51:08
	 * @param	connection
	 * @param	statement
	 * @param	resultSet
	 * @return	void
	 */
	public static void closeQuietly(Connection connection, Statement statement, ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
				if (logger.isTraceEnabled()) {
					logger.trace(resultSet.getClass().getSimpleName()+"正常关闭。");
				}
			} catch (SQLException e) {
				logger.error("jdbc关闭数据库连接时抛出的错误: ", e);
			}
		}
		if (statement != null) {
			try {
				statement.close();
				if (logger.isTraceEnabled()) {
					logger.trace(statement.getClass().getSimpleName()+"正常关闭。");
				}
			} catch (SQLException e) {
				logger.error("jdbc释放数据库资源时抛出的错误: ", e);
			}
		}
		if (connection != null) {
			try {
				connection.close();
				if (logger.isTraceEnabled()) {
					logger.trace(connection.getClass().getSimpleName()+"正常关闭。");
				}
			} catch (SQLException e) {
				logger.error("jdbc释放数据库资源时抛出的错误: ", e);
			}
		}
	}
	
	/**
	 * 关闭数据库资源
	 * @author	黄文君
	 * @date	2017年1月10日 下午1:35:56
	 * @param	closeable
	 * @return	void
	 */
	public static void closeQuietly(AutoCloseable... closeable) {
		if(closeable!=null && closeable.length>0){
			//排序
			Arrays.sort(closeable, new Comparator<AutoCloseable>(){
				@Override
				public int compare(AutoCloseable c1, AutoCloseable c2) {
					//c1排序号
					int c1Sort = 0;
					if(c1 instanceof ResultSet){
						c1Sort = 0;
					}else if(c1 instanceof Statement){
						c1Sort = 1;
					}else if(c1 instanceof Connection){
						c1Sort = 2;
					}
					//c2排序号
					int c2Sort = 0;
					if(c2 instanceof ResultSet){
						c2Sort = 0;
					}else if(c2 instanceof Statement){
						c2Sort = 1;
					}else if(c2 instanceof Connection){
						c2Sort = 2;
					}
					return c1Sort - c2Sort;
				}
			});
			
			//资源陆续关闭
			for(int i=0, size=closeable.length; i<size; i++){
				AutoCloseable c = closeable[i];
				if(c != null) {
					try {
						c.close();
						if (logger.isTraceEnabled()) {
							logger.trace(c.getClass().getSimpleName()+"正常关闭。");
						}
					} catch (Exception e) {
						logger.error("jdbc释放数据库资源时抛出的错误: ", e);
					}
				}
			}
		}
	}

}
