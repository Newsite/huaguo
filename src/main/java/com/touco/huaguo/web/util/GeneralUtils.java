/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.huaguo.web.util.GeneralUtil.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-8-30下午4:22:35
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.web.util;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class GeneralUtils {

	public static String getShowTime(Date recordDate) {
		String result = "";
		Date now = new Date();
		long nowLong = now.getTime();
		long recordLong = recordDate.getTime();
		long temp = Math.abs(nowLong - recordLong);
		if (temp < 60000 * 60)// 1个小时以内
		{
			result = "刚刚";
		} else if (temp >= 3600000 && temp < 3600000 * 2)// 一小时内
		{
			result = "1小时前";
		} else if (temp >= 3600000 * 2 && temp < 3600000 * 3)// 一天内
		{
			result = "2小时前";
		} else if (temp >= 3600000 * 3 && temp < 3600000 * 4) {
			result = "3小时前";
		} else if (temp >= 3600000 * 4 && temp < 3600000 * 6) {
			result = "半天前";
		} else if (temp >= 3600000 * 6 && temp < 3600000 * 24) {
			result = "24小时以内";
		} else if (temp >= 3600000 * 24 && temp < 3600000 * 24 * 2) {
			result = "1天前";
		} else if (temp >= 3600000 * 24 * 2 && temp < 3600000 * 24 * 3) {
			result = "2天前";
		} else if (temp >= 3600000 * 24 * 3 && temp < 3600000 * 24 * 4) {
			result = "3天前";
		} else if (temp >= 3600000 * 24 * 4 && temp < 3600000 * 24 * 7) {
			result = "1星期以内";
		} else if (temp >= 3600000 * 24 * 7 && temp < 3600000 * 24 * 14) {
			result = "2星期以内";
		} else if (temp >= 3600000 * 24 * 14 && temp < 3600000 * 24 * 30) {
			result = "1月以内";
		} else if (temp >= 3600000 * 24 * 30 && temp < 3600000 * 24 * 30 * 2) {
			result = "2月以内";
		} else if (temp >= 3600000 * 24 * 30 * 3
				&& temp < 3600000 * 24 * 30 * 4) {
			result = "3月以内";
		} else if (temp >= 3600000 * 24 * 30 * 4 && temp < 3600000 * 24 * 365) {
			result = "1年以内";
		} else if (temp >= 3600000 * 24 * 365 && temp < 3600000 * 24 * 365 * 2) {
			result = "2年以内";
		} else if (temp >= 3600000 * 24 * 365 * 2
				&& temp < 3600000 * 24 * 365 * 3) {
			result = "3年以内";
		} else {
			result = "3年以上";
		}
		return result;
	}

}
