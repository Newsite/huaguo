package com.touco.huaguo.common;

/**
 * 
 * @author 史中营
 * 
 */
public class StringUtil {
	/**
	 * 截取定长的字符串，超长结尾以...代替
	 * 
	 * @author 史中营
	 * @param str
	 * @param len
	 * @return
	 */
	public static String getCutString(String str, int len) {
		return getCutString(str, len, true);
	}

	/**
	 * 截取定长的字符串，dots判断超长是否结尾以...代替
	 * 
	 * @author 史中营
	 * @param str
	 * @param len
	 * @param dots
	 * @return
	 */
	public static String getCutString(String str, int len, boolean dots) {
		if (str.getBytes().length > len) {
			String s = "";

			for (int i = 0; i < len; i++) {
				s += str.substring(i, i + 1);
				if (s.getBytes().length >= (dots ? (len - 3) : len)) {
					break;
				}
			}
			if (dots) {
				s += "...";
			}
			return s;
		}
		return str;
	}
}
