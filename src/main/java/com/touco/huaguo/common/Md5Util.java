/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.waboo.common.Md5Util.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-2-10下午3:45:16
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class Md5Util {
	private static Logger logger = Logger.getLogger(Md5Util.class);	
	
	public static String md5(String str) {

		//logger.info(Md5Util.class.getName() + ": 用MD5加密");
		String resultHash = "";
		MessageDigest  md = null;
        try {
        	byte[] bytes =str.getBytes();
            md = MessageDigest.getInstance("MD5");
            
            byte[] result = new byte[md.getDigestLength()];
            md.reset();
            md.update(bytes);
            result = md.digest();
            
            StringBuffer buf = new StringBuffer(result.length * 2);
            for (int i = 0; i < result.length; i++) {
                int intVal = result[i] & 0xff;
                if (intVal < 0x10) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(intVal));
            }

            resultHash = buf.toString();

        } catch (NoSuchAlgorithmException e) {
        	logger.error(Md5Util.class.getName() + ":" + e.getMessage());
        }
        
        return resultHash;      
	}	
}