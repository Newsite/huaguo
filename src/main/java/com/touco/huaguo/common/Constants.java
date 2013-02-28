/**
 * Copyright(C) 2011-2013 Touco WuXi Technology LTD. All Rights Reserved.  
 * 版权所有(C) 2011-2013 无锡途拓科技有限公司
 * 公司名称：无锡途拓科技有限公司 
 * 
 * 网址:http://www.touco.cn
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2011-12-13上午11:09:48
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.common;


public class Constants {
	/**
	 * The name of the ResourceBundle used in this application
	 */
	public static final String CLASSPATH = "classpath:";
	
	public final static String FTP_DEFAULT_ENCODING = "UTF-8";// FTP 默认编码
	public final static int FTP_DEFAULT_PORT = 21;// FTP 默认编码
	public final static String FTP_CONFIG_FILE = "ftpconfig.properties";// FTP
																		// 配置文件
	public final static String DS_CONFIG_FILE = "dsconfig.properties";// 数据库
																		// 配置文件
	public final static String FILE_SYS_DOMAIN = "fileSysDomain";// 文件系统域名

	public final static String ORDER_BY = " order by ";
	public final static String BLANK_SPACE = " ";
	public final static String PAGE_TOTAL = "total";
	public final static String PAGE_ROWS = "rows";
	public final static String PAGE_ORDER_ASC = "asc";
	public final static String PAGE_ORDER_DESC = "desc";
	public final static String CRITERIA_LIKE = "like";
	public final static String CRITERIA_START_LIKE = "startlike";
	public final static String CRITERIA_END_LIKE = "endlike";
	public final static String CRITERIA_EQUALS = "equals";
	public final static String CRITERIA_ISNULL = "isnull";
	public final static String RELEVANCE_NULL = "ISNULL";
	public final static String CRITERIA_IN = "in";
	public final static String CRITERIA_GT = "gt";// >
	public final static String CRITERIA_GE = "ge";// >=
	public final static String CRITERIA_LT = "lt";// <
	public final static String CRITERIA_LE = "le";// <=
	public final static String CRITERIA_BETWEEN = "between";
	public final static String CRITERIA_OR = "or";// >
	public final static String CRITERIA_OR_AND = "or_and";// >
	
	
	public final static String INSIDE_USER = "0";// 站内注册用户
	public final static String OUTSIDE_USER = "1";// 外部注册用户
	public final static String MESSAGE_NOT_READED = "0";// 未读私信
	public final static String MESSAGE_READED = "1";// 已读私信
	public final static String MESSAGE_NOTIFICATIONS = "0";//0 -通知
	public final static String MESSAGE_PRIVATELETTER = "1";// 私信
	
	public final static String MERCHANT_OWNER_SAY = "0";// 店长说
	public final static String MERCHANT_COMMENT_OWNER_SAY = "xxx新发布了一条掌柜说";
	public final static String MERCHANT_GROUP_BUY = "1";// 团购优惠
	public final static String MERCHANT_COMMENT_GROUP_BUY = "xxx新发布了一条团购优惠";
	public final static String MERCHANT_PROMOTION = "2";// 促销活动
	public final static String MERCHANT_COMMENT_PROMOTION = "xxx新发布了一条促销活动";
	public final static String MERCHANT_NORMAL_SAY = "3";// 普通评论
	public final static String MERCHANT_COMMENT_LIKE_DYNAMIC = "我很喜欢这家餐厅！";
	public final static String MERCHANT_COMMENT_NORMAL_DYNAMIC = "这几天餐厅有新动向了，赶紧来店里看看吧！";
	public final static String MERCHANT_STATUS_VERIFY = "0";// 餐厅状态，审核中
	public final static String MERCHANT_STATUS_PASS = "1";// 餐厅状态，已通过
	public final static String MERCHANT_STATUS_NOT_PASS = "2";// 餐厅状态，未通过
	public final static String MERCHANT_STATUS_NO_VERIFY = "3";// 餐厅状态，未提交审核

	public final static String USER_SESSION_INFO = "userSessionInfo";
	public final static String CUSTOMER_ACCOUNT_SESSION_INFO = "customerAccountSessionInfo";
	public final static String ADMIN_SESSION_INFO = "adminSessionInfo";
	public final static String USER_CITY_SESSION_INFO = "userCitySessionInfo";
	public final static String USER_DEFAULT_CITY = "无锡";

	public final static String RESULT_SUCCESS = "SUCCESS";
	public final static String RESULT_FAILURE = "FAILURE";
	public final static String RESULT_NOT_LOGIN = "NOTLOGIN";
	public final static String RESULT_SAME_USER = "SAMEUSER";
	public final static String RESULT_ERROR = "ERROR";
	public final static String RESULT_SAME = "SAME";
	
	public final static String MERCHANT_SESSION_INFO = "sessionScope";
	public final static String MERCHANT_REQUEST_SESSION_INFO = "requestScope";
	
	public final static String UNAME_SESSION = "unameSession";
	public final static String UPWD_SESSION = "upwdSession";
	
	public final static String UNICKNAME_SESSION = "uNickNameSession";
	public final static String PROFILE_ImageUrl_SESSION = "profileImageUrl";
	public final static String PRIVATE_LETTER_COUNT = "messageCount";
	public final static String REG_TYPE = "regType";
	
	public final static String COOKIE_UNAME = "uname";
	public final static String COOKIE_UPWD = "upwd";

	public final static String COOKIE_SERVER_UNAME = "server_uname";
	public final static String COOKIE_SERVER_UPWD = "server_upwd";

	public final static String TEMP_DIR = "static/temp/";
	
	public final static String MERCHANT_DIR = "static/merchantimage/";

	public final static String DEL_TAG = "1";
	public final static String NO_DEL_TAG = "0";

	public final static String ERROR_FILTER = "com.touco.huaguo";
//	public final static String DEFAULT_FILE_SYS = "http://files.huaguo.cn/";
	public final static String DEFAULT_FILE_SYS = "http://218.90.189.138:8009/";

	public final static String MERCHANT_FILE = "static/huaguo/merchant";
	
	public final static String RECOMMEND_STATUS ="1";
	public final static String UN_RECOMMEND_STATUS ="0";
	
	public final static String DEFAULT_MALE ="m" ;
	public final static String DEFAULT_FEMALE ="f" ;
	public final static String DEFAULT_GENDER ="n" ;
	
	//0--餐厅信息审核  1-- 动态审核 2--掌柜审核 3 评价 4 掌柜收到评价
	public final static String CATEGORY_MERCHANT_VERIFY ="0" ;
	public final static String CATEGORY_EVENT_VERIFY ="1" ;
	public final static String CATEGORY_OWNER_VERIFY ="2" ;
	public final static String CATEGORY_EVALUATE ="3" ;
	public final static String CATEGORY_OWNER_EVALUATE ="4" ;
	public final static String CATEGORY_MERCHANT_OWNER_VERIFY ="5" ;
}
