/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.waboo.common.RandomUtil.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-2-10下午3:45:16
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.common;

import java.util.Random;

import org.apache.log4j.Logger;

public class RandomUtil {

	private static Logger logger = Logger.getLogger(Md5Util.class);	
	private static char ch[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
		'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
		'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
		'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
		'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
		'z', '0', '1' }; // 最后又重复两个0和1，因为需要凑足数组长度为64
	
	private static char ch2[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
		'9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1',
		'9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1',
		'9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1',
		'9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1',
		'2', '3', '4' }; // 最后又重复两个0和1，因为需要凑足数组长度为64
	
	private static Random random = new Random();

	public static String getRandomString(int length) {
		String strRandom = "";
		try {
			logger.info(RandomUtil.class.getName() + ": 产生随机码；Length = " + length);
			if (length > 0) {
				int index = 0;
				char[] temp = new char[length];
				int num = random.nextInt();
				for (int i = 0; i < length % 5; i++) {
					temp[index++] = ch[num & 63]; // 取后面六位，记得对应的二进制是以补码形式存在的。
					num >>= 6; // 63的二进制为:111111
								// 为什么要右移6位？因为数组里面一共有64个有效字符。为什么要除5取余？因为一个int型要用4个字节表示，也就是32位。
				}
				for (int i = 0; i < length / 5; i++) {
					num = random.nextInt();
					for (int j = 0; j < 5; j++) {
						temp[index++] = ch[num & 63];
						num >>= 6;
					}
				}
								
				strRandom = new String(temp, 0, length);
				
				logger.info(RandomUtil.class.getName() + ": 产生随机码；随机码为  ： " + strRandom);
			}
		} catch (Exception e) {
			logger.error(RandomUtil.class.getName() + ":" + e.getMessage());
		}		
		
		return strRandom;
	}
	
	/**
	 * 生成纯数字的随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString2(int length) {
		String strRandom = "";
		try {
			logger.info(RandomUtil.class.getName() + ": 产生随机码；Length = " + length);
			if (length > 0) {
				int index = 0;
				char[] temp = new char[length];
				int num = random.nextInt();
				for (int i = 0; i < length % 5; i++) {
					temp[index++] = ch2[num & 63]; // 取后面六位，记得对应的二进制是以补码形式存在的。
					num >>= 6; // 63的二进制为:111111
								// 为什么要右移6位？因为数组里面一共有64个有效字符。为什么要除5取余？因为一个int型要用4个字节表示，也就是32位。
				}
				for (int i = 0; i < length / 5; i++) {
					num = random.nextInt();
					for (int j = 0; j < 5; j++) {
						temp[index++] = ch2[num & 63];
						num >>= 6;
					}
				}
								
				strRandom = new String(temp, 0, length);
				
				logger.info(RandomUtil.class.getName() + ": 产生随机码；随机码为  ： " + strRandom);
			}
		} catch (Exception e) {
			logger.error(RandomUtil.class.getName() + ":" + e.getMessage());
		}		
		
		return strRandom;
	}
}
