package com.touco.huaguo.common;

import org.apache.log4j.Logger;

/**
 * 
 * @author 史中营
 * 
 */
public class ExceptionUtil {
	private static Logger log = Logger.getLogger(ExceptionUtil.class);

	/**
	 * 格式化异常信息输出
	 * 
	 * @author 史中营
	 */
	public static void exceptionParse(Exception e) {
		boolean flag = true;
		for (StackTraceElement ste : e.getStackTrace()) {
			if (ste.getClassName().indexOf(Constants.ERROR_FILTER) != -1) {
				flag = false;
				log.error("\r\n[Exception] " + ste.getClassName() + "的" + ste.getMethodName() + "方法在" + ste.getLineNumber() + "行发生错误：" + e.getMessage());
			}
		}
		if (flag) {
			log.error("\r\n[Exception] " + e.getMessage());
		}
		e.printStackTrace();
	}
}
