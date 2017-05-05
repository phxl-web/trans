/**
 * Project Name:HSCM File Name:SystemConfig.java Package Name:com.phxl.hscm.common.util Date:2015年6月29日下午5:13:50 Copyright (c) 2015, PHXL All Rights Reserved.
 */

package com.erp.trans.common.util;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;


/**
 * ClassName:SystemConfig <br/>
 * Function: 加载系统Properties配置文件至内存中. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年6月29日 下午5:13:50 <br/>
 * @author shijingbang
 * @version
 * @since JDK 1.6
 * @see
 */
@Component
public class SystemConfig{

	private static final Properties	properties	= new Properties();
	private static final Logger		logger		= LoggerFactory.getLogger(SystemConfig.class);

	@PostConstruct
	public void init() throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
		Resource[] resources = resolver.getResources("classpath:**/spring/*.properties");

		for (Resource resource : resources) {
			if (logger.isDebugEnabled()) {
				logger.debug("加载Properties配置文件{}", resource.getFilename());
			}
			properties.load(resource.getInputStream());
		}
	}

	/**
	 * getProperty:根据key找出value值. <br/>
	 * @Title: getProperty
	 * @Description: TODO
	 * @param key
	 * @return 设定参数
	 * @return String 返回类型
	 * @throws
	 * @author shijingbang
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * getProperty:根据key找出value值.没有找到则返回第二个参数的值 <br/>
	 * @Title: getProperty
	 * @Description: TODO
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return 设定参数
	 * @return String 返回类型
	 * @throws
	 * @author shijingbang
	 */
	public static String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	/**
	 * containsKey:检查是否包含key. <br/>
	 * @Title: containsKey
	 * @Description: TODO
	 * @param key
	 * @return 设定参数
	 * @return boolean 返回类型
	 * @throws
	 * @author shijingbang
	 */
	public static boolean containsKey(Object key) {
		return properties.containsKey(key);
	}

	/**
	 * containsValue:检查系统环境是否包含值. <br/>
	 * @Title: containsValue
	 * @Description: TODO
	 * @param value
	 * @return 设定参数
	 * @return boolean 返回类型
	 * @throws
	 * @author shijingbang
	 */
	public static boolean containsValue(Object value) {
		return properties.containsValue(value);
	}
}
