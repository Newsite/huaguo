/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.ccmp.common.PropertiesUtil.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-3-7上午10:42:35
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取properties文件
 * 
 * @author 史中营
 *
 */
public class PropertiesUtil {

	/**
	 * 
	 * @param propertiesConfigName
	 * @return Properties 对象
	 */
	public static Properties getProperties(String propertiesConfigName) throws IOException{
		Properties properties = new Properties();		
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesConfigName);
		properties.load(inputStream);
		
		return properties;
	}
}
