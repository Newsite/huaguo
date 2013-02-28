/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.ccmp.common.FTPFileUtil.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-3-6下午2:30:32
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.common;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

public class FTPFileUtil {

	/**
	 * 判断是否存在文件目录
	 * 
	 * @param client
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	public static boolean existsFtpDir(FTPClient ftpClient,String dir)
			throws Exception {
		if(dir.equals(ftpClient.currentDirectory())){
			return true;
		}
		
		FTPFile[] ftpFiles = ftpClient.list();
		for (FTPFile ftpFile : ftpFiles) {
			if ((ftpFile.getType() == FTPFile.TYPE_DIRECTORY)
					&& (dir.equals(ftpFile.getName()))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否存在文件或文件夹
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @param encoding
	 * @return
	 */
	public static boolean exists(FTPClient ftpClient,String path) {
		try {
			if (path.indexOf(".") != -1) {// 文件
				int index = path.lastIndexOf("/");
				String p = path.substring(0, index);
				String f = path.substring(index + 1);
				ftpClient.changeDirectory(p);
				FTPFile[] ftpFiles = ftpClient.list();
				for (FTPFile ftpFile : ftpFiles) {
					if ((ftpFile.getType() == FTPFile.TYPE_FILE)
							&& (ftpFile.getName().equals(f))) {
						return true;
					}
				}
				return false;
			} else {// 文件夹，如果不存在的话，会进入exception
				ftpClient.changeDirectory(path);
			}
		} catch (Exception e) {//不存在文件夹
			return false;
		}

		return true;
	}
	
	/**
	 * 获取一个新的文件路径
	 * 
	 * @param parentPath
	 * @param childPath
	 * @return
	 */
	public static String getNewFilePath(String parentPath,String childPath){
		String path = "";
		if(!parentPath.endsWith("/")){
			parentPath += "/";
		}
		
		path = parentPath + childPath;	
		
		return path;
	}
	
	/**
	 * 获取一个新的文件路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getNewFilePath(String filePath){
		if(!filePath.startsWith("/")){
			filePath = "/" + filePath;
		}
		
		return filePath;
	}
}
