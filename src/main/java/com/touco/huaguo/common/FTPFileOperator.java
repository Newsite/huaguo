/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.ccmp.common.FTPFileOperator.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-3-5上午10:15:35
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.common;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * ftp文件操作
 * 
 * @author Administrator
 * 
 */
public class FTPFileOperator {
	private static Logger logger = Logger.getLogger(FTPFileOperator.class);

	private static FTPClient ftpClient = null;

	/**
	 * 获取类的实例化
	 * 
	 * @return
	 */
	public FTPFileOperator() {
		try {
			Properties properties = PropertiesUtil.getProperties(Constants.FTP_CONFIG_FILE);
			ftpClient = new FTPClient();
			ftpClient.setType(FTPClient.TYPE_AUTO);
			// ftpClient.setType(FTPClient.TYPE_BINARY);

			String encoding = properties.getProperty("ftp.encoding");
			if (StringUtils.isNotBlank(encoding)) {
				ftpClient.setCharset(encoding);
			} else {
				ftpClient.setCharset(Constants.FTP_DEFAULT_ENCODING);
			}

			String port = properties.getProperty("ftp.port");
			String url = properties.getProperty("ftp.url");
			if (StringUtils.isNotBlank(port)) {
				ftpClient.connect(url, Integer.parseInt(port));
			} else {
				ftpClient.connect(url, Constants.FTP_DEFAULT_PORT);
			}

			ftpClient.login(properties.getProperty("ftp.username"), properties.getProperty("ftp.pwd"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 退出Ftp服务器
	 */
	public void logout() {
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect(true);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 单文件下载
	 * 
	 * @param targetFile
	 *            服务器端文件具体路径(/static/station/plan/test/test.jpg)
	 * @param dowloadPath
	 *            要下载到本地的文件("E:/")
	 */
	public Boolean singleDownload(String targetFile, String dowloadPath) {
		Boolean result = Boolean.TRUE;
		try {
			String filePath = FTPFileUtil.getNewFilePath(targetFile);

			if (!FTPFileUtil.exists(ftpClient, filePath)) {
				return Boolean.FALSE;
			}

			int index = filePath.lastIndexOf("/");
			String fileName = filePath.substring(index + 1);

			File file = new File(dowloadPath);
			if (!file.exists()) {
				file.mkdirs();
			}

			ftpClient.download(filePath, new File(FTPFileUtil.getNewFilePath(dowloadPath, fileName)));
		} catch (Exception e) {
			result = Boolean.FALSE;
			logger.warn(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 多文件下载
	 * 
	 * @param dowloadMap
	 *            (key是要下载的服务器文件；value是要下载到本地的路径)
	 */
	public Boolean mutilDownload(Map<String, String> dowloadMap) {
		Boolean result = Boolean.TRUE;
		try {
			for (String key : dowloadMap.keySet()) {
				String filePath = FTPFileUtil.getNewFilePath(key);
				if (!FTPFileUtil.exists(ftpClient, filePath)) {
					continue;
				}

				int index = filePath.lastIndexOf("/");
				String fileName = filePath.substring(index + 1);

				File file = new File(dowloadMap.get(key));
				if (!file.exists()) {
					file.mkdirs();
				}

				ftpClient.download(filePath, new File(FTPFileUtil.getNewFilePath(dowloadMap.get(key), fileName)));
			}
		} catch (Exception e) {
			result = Boolean.FALSE;
			logger.warn(e.getMessage(), e);
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 
	 * 下载整个文件夹下面的文件<br>
	 * 目前不用
	 * 
	 * @param targetPath
	 *            具体定位到服务器端那一层目录下面
	 * @param folder
	 *            要下载的文件夹
	 * @throws Exception
	 */
	private void downloadFolder(String targetPath, File folder) throws Exception {
		if (!FTPFileUtil.existsFtpDir(ftpClient, targetPath)) {
			targetPath = ftpClient.currentDirectory();
		}

		ftpClient.changeDirectory(targetPath);
		FTPFile[] ftpFiles = ftpClient.list();
		if (!folder.exists()) {
			folder.mkdirs();
		}

		for (FTPFile ftpFile : ftpFiles) {
			String name = ftpFile.getName();
			if (ftpFile.getType() == FTPFile.TYPE_FILE) {
				// ftpClient.changeDirectory(targetPath);
				ftpClient.download(name, new File(folder, name));
			} else if (ftpFile.getType() == FTPFile.TYPE_DIRECTORY) {
				downloadFolder(FTPFileUtil.getNewFilePath(targetPath, name), new File(folder, name));
			}
		}
	}

	/**
	 * 单文件上传
	 * 
	 * @param targetPath
	 *            上传到服务器端文件具体路径
	 * @param upLoadFile
	 *            要上传的本地的文件
	 */
	public Boolean singleUpload(String targetFath, InputStream fileInputStream, String fileName) {
		Boolean result = Boolean.TRUE;
		try {
			String[] files = targetFath.split("/");
			if (files == null) {
				return Boolean.FALSE;
			}

			for (String file : files) {
				if (!FTPFileUtil.existsFtpDir(ftpClient, file)) {
					ftpClient.createDirectory(file);
				}

				ftpClient.changeDirectory(file);
			}

			ftpClient.upload(fileName, fileInputStream, 0, 0, null);

		} catch (Exception e) {
			result = Boolean.FALSE;
			logger.warn(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 多文件上传
	 * 
	 * @param targetPath
	 *            上传到服务器端文件具体路径
	 * @param upLoadFile
	 *            要上传的本地的文件列表
	 */
	public Boolean mutliUpload(String targetFath, Map<String, InputStream> upLoadMap) {
		Boolean result = Boolean.TRUE;
		try {
			String[] files = targetFath.split("/");
			if (files == null) {
				return Boolean.FALSE;
			}

			for (String file : files) {
				if (!FTPFileUtil.existsFtpDir(ftpClient, file)) {
					ftpClient.createDirectory(file);
				}

				ftpClient.changeDirectory(file);
			}

			for (String key : upLoadMap.keySet()) {
				// ftpClient.upload(upLoadFile);
				ftpClient.upload(key, upLoadMap.get(key), 0, 0, null);
			}
		} catch (Exception e) {
			result = Boolean.FALSE;
			logger.warn(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 上传文件夹下的所有文件<br>
	 * 目前不用
	 * 
	 * @param targetPath
	 *            上传到服务器端文件具体路径
	 * @param folder
	 *            要上载的本地的文件夹
	 */
	private void uploadFolder(String targetPath, File folder) {
		try {
			if (!FTPFileUtil.existsFtpDir(ftpClient, targetPath)) {
				targetPath = ftpClient.currentDirectory();
			}

			ftpClient.changeDirectory(targetPath);

			String[] files = folder.list();
			if (files == null) {
				return;
			}

			for (String file : files) {
				File f = new File(folder, file);
				if (f.isDirectory()) {
					// ftpClient.changeDirectory(targetPath);
					if (!FTPFileUtil.existsFtpDir(ftpClient, f.getName())) {
						ftpClient.createDirectory(f.getName());
					}

					uploadFolder(FTPFileUtil.getNewFilePath(targetPath, f.getName()), f);
				} else if (f.isFile()) {
					ftpClient.upload(f);
				}
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	/**
	 * 单文件删除
	 * 
	 * @param filePath
	 */
	public Boolean singleDelete(String filePath) {
		Boolean result = Boolean.TRUE;
		try {
			filePath = FTPFileUtil.getNewFilePath(filePath);

			if (FTPFileUtil.exists(ftpClient, filePath)) {
				ftpClient.deleteFile(filePath);
			}
		} catch (Exception e) {
			result = Boolean.FALSE;
			logger.warn(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 多文件删除
	 * 
	 * @param filePathList
	 */
	public Boolean mutliDelete(List<String> filePathList) {
		Boolean result = Boolean.TRUE;
		try {
			for (String filePath : filePathList) {
				filePath = FTPFileUtil.getNewFilePath(filePath);
				if (FTPFileUtil.exists(ftpClient, filePath)) {
					ftpClient.deleteFile(filePath);
				}
			}
		} catch (Exception e) {
			result = Boolean.FALSE;
			logger.warn(e.getMessage(), e);
		}
		return result;
	}
}
