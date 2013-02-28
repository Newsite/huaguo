/**
 * Copyright(C) 2011-2013 Touco WuXi Technology LTD. All Rights Reserved.  
 * 版权所有(C) 2011-2013 无锡途拓科技有限公司
 * 公司名称：无锡途拓科技有限公司 
 * 
 * 网址:http://www.touco.cn
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2011-12-27上午11:29:06
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.common;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 史中营 文件上传辅助工具类
 * 
 */

public class FileUploadUtil {
	private static String SEPARATOR = "/";

	/**
	 * 单文件上传
	 * 
	 * @param request
	 * @param originalFileName
	 * @param bytes
	 * @param typeVal
	 * @return
	 * @throws Exception
	 */
	public String fileUpload(HttpServletRequest request,
			String originalFileName, byte[] bytes, int typeVal)
			throws Exception {
		return fileUpload(request, originalFileName, bytes, typeVal, true);
	}

	/**
	 * 单文件上传
	 * 
	 * @param request
	 * @param originalFileName
	 * @param bytes
	 * @param typeVal
	 * @param isRename
	 *            是否需要改名
	 * @return
	 * @throws Exception
	 */
	public String fileUpload(HttpServletRequest request,
			String originalFileName, byte[] bytes, int typeVal, boolean isRename)
			throws Exception {

		if (StringUtils.isEmpty(originalFileName) || "" == originalFileName) {
			return "";
		}

		String newFileName = originalFileName;
		if (isRename) {
			newFileName = getNewFileName(originalFileName);
		}

		// 图片保存完整路径
		String savePath = genrateFileFloderPath(typeVal);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

		FTPFileOperator fileOperator = new FTPFileOperator();
		fileOperator.singleUpload(savePath, inputStream, newFileName);
		fileOperator.logout();

		return savePath + SEPARATOR + newFileName;
	}

	/**
	 * 单文件上传
	 * 
	 * @param cust
	 * @param originalFileName
	 * @param bytes
	 * @param typeVal
	 * @return
	 * @throws Exception
	 */
	public String fileUpload(String realPath, String originalFileName,
			byte[] bytes, int typeVal) throws Exception {
		return fileUpload(realPath, originalFileName, bytes, typeVal, true);
	}

	/**
	 * 单文件上传
	 * 
	 * @param cust
	 * @param originalFileName
	 * @param bytes
	 * @param typeVal
	 * @param isRename
	 *            是否需要改名
	 * @return
	 * @throws Exception
	 */
	public String fileUpload(String realPath, String originalFileName, byte[] bytes, int typeVal, boolean isRename) throws Exception {

		if (StringUtils.isEmpty(originalFileName) || "" == originalFileName) {
			return "";
		}

		String newFileName = originalFileName;
		if (isRename) {
			newFileName = getNewFileName(originalFileName);
		}
		String savePath = genrateFileFloderPath(typeVal);

		File imgFile = new File(realPath + savePath);
		if (!imgFile.exists()) {
			imgFile.mkdirs();
		}
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		
		FileOutputStream fs = new FileOutputStream(realPath + savePath  + "/" + newFileName);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = inputStream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		inputStream.close();
		// 图片保存完整路径
	//	ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		// FTPFileOperator fileOperator = new FTPFileOperator();
		// fileOperator.singleUpload(savePath, inputStream, newFileName);
		// fileOperator.logout();
		return savePath + SEPARATOR + newFileName;
	}

	/**
	 * 多文件上传
	 * 
	 * @param request
	 * @param fileList
	 * @param typeVal
	 * @return
	 * @throws Exception
	 */
	public List<String> mutilFileUpload(HttpServletRequest request,
			LinkedList<MultipartFile> fileList, int typeVal) throws Exception {
		return mutilFileUpload(request, fileList, typeVal, true);
	}

	/**
	 * 多文件上传
	 * 
	 * @param request
	 * @param fileList
	 * @param typeVal
	 * @param isRename
	 * @return
	 * @throws Exception
	 */
	public List<String> mutilFileUpload(HttpServletRequest request,
			LinkedList<MultipartFile> fileList, int typeVal, boolean isRename)
			throws Exception {

		if (fileList == null) {
			return null;
		}

		// 图片保存完整路径
		String savePath = genrateFileFloderPath(typeVal);

		Map<String, InputStream> upLoadMap = new HashMap<String, InputStream>();

		LinkedList<String> filePathList = new LinkedList<String>();

		for (MultipartFile file : fileList) {
			String newFileName = file.getOriginalFilename();
			if (isRename) {
				newFileName = getNewFileName(file.getOriginalFilename());
			}
			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					file.getBytes());

			upLoadMap.put(newFileName, inputStream);
			filePathList.add(savePath + SEPARATOR + newFileName + ","
					+ file.getOriginalFilename());
		}

		FTPFileOperator fileOperator = new FTPFileOperator();
		fileOperator.mutliUpload(savePath, upLoadMap);
		fileOperator.logout();

		return filePathList;
	}

	/**
	 * 生成大小不同的图片
	 * 
	 * @param multipartRequest
	 * @param file
	 * @param typeVal
	 *            类型 区分不同的文件夹
	 * @param types
	 *            图片大小类型
	 * @param widths
	 *            宽度
	 * @param heights
	 *            高度
	 * @return
	 * @throws Exception
	 */
	public String uploadFileWithMagickImage(String realPath,
			MultipartFile file, int width, int height) {
		if (file == null) {
			return "";
		}

		String target = "";
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					file.getBytes());
			BufferedImage oldimg = ImageIO.read(inputStream);

			BufferedImage newimg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = newimg.createGraphics();
			// g.setBackground(Color.WHITE);// 设置背景色
			g.clearRect(0, 0, width, height);

			AffineTransform at = AffineTransform.getScaleInstance(
					(double) width / oldimg.getWidth(), (double) height
							/ oldimg.getHeight());
			g.drawRenderedImage(oldimg, at);

			String newFileName = getNewFileName(file.getOriginalFilename());

			String currentDate = new SimpleDateFormat("yyyyMMdd")
					.format(new Date());
			File imgFile = new File(realPath + Constants.TEMP_DIR + currentDate);
			if (!imgFile.exists()) {
				imgFile.mkdirs();
			}

			target = Constants.TEMP_DIR + currentDate + "/" + newFileName;
			imgFile = new File(realPath + target);
			ImageIO.write(newimg, "png", imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return target;
	}

	/**
	 * 生成大小不同的图片
	 * 
	 * @param multipartRequest
	 * @param file
	 * @param typeVal
	 *            类型 区分不同的文件夹
	 * @param types
	 *            图片大小类型
	 * @param widths
	 *            宽度
	 * @param heights
	 *            高度
	 * @return
	 * @throws Exception
	 */
	public String uploadFileWithMagickImage2(String realPath,
			MultipartFile file, int width, int height) {
		if (file == null) {
			return "";
		}

		String target = "";
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					file.getBytes());
			BufferedImage oldimg = ImageIO.read(inputStream);

			BufferedImage newimg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = newimg.createGraphics();
			// g.setBackground(Color.WHITE);// 设置背景色
			g.clearRect(0, 0, width, height);

			AffineTransform at = AffineTransform.getScaleInstance(
					(double) width / oldimg.getWidth(), (double) height
							/ oldimg.getHeight());
			g.drawRenderedImage(oldimg, at);

			String newFileName = getNewFileName(file.getOriginalFilename());

			String currentDate = new SimpleDateFormat("yyyyMMdd")
					.format(new Date());
			File imgFile = new File(realPath + Constants.MERCHANT_DIR
					+ currentDate);
			if (!imgFile.exists()) {
				imgFile.mkdirs();
			}

			target = Constants.MERCHANT_DIR + currentDate + "/" + newFileName;
			imgFile = new File(realPath + target);
			ImageIO.write(newimg, "png", imgFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return target;
	}

	/**
	 * 根据原始文件名，得到新的文件名
	 * 
	 * @param originalFileName
	 * @return
	 */
	private String getNewFileName(String originalFileName) {
		String newFileName = new SimpleDateFormat("yyyyMMddhhmmss")
				.format(new Date())
				+ "_"
				+ RandomUtil.getRandomString(8)
				+ originalFileName.substring(originalFileName.lastIndexOf("."));
		return newFileName;
	}

	/**
	 * 根据不同类型生成不同的目录
	 * 
	 * @param typeVal
	 * @return
	 */
	private static String genrateFileFloderPath(int typeVal) {
		String floderName = "";
		switch (typeVal) {
		case 1:
			floderName = "static/huaguo/merchant";// 餐厅图片
			break;
		case 2:
			floderName = "static/huaguo/headimage";// 用户头像
			break;
		case 3:
			floderName = "static/huaguo/merchantimage";// 餐厅动态图片
			break;
		default:
			floderName = "other";
			break;
		}
		return floderName;
	}

	/**
	 * 多文件删除
	 * 
	 * @param filePathList
	 */
	public Boolean mutliDelete(List<String> filePathList) {
		Boolean result = Boolean.TRUE;
		try {
			FTPFileOperator fileOperator = new FTPFileOperator();
			fileOperator.mutliDelete(filePathList);
		} catch (Exception e) {
			result = Boolean.FALSE;
		}
		return result;
	}

	public String saveFile(String realPath, String originalFileName,
			InputStream stream) {
		String target = "";
		try {

			String newFileName = getNewFileName(originalFileName);
			String currentDate = new SimpleDateFormat("yyyyMMdd")
					.format(new Date());
			File imgFile = new File(realPath + Constants.TEMP_DIR + currentDate);
			if (!imgFile.exists()) {
				imgFile.mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(realPath
					+ Constants.TEMP_DIR + currentDate + "/" + newFileName);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			stream.close();
			target = Constants.TEMP_DIR + currentDate + "/" + newFileName;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return target;
	}

	public String saveFile2(String realPath, String originalFileName,
			InputStream stream) {
		String target = "";
		try {

			String newFileName = getNewFileName(originalFileName);
			String currentDate = new SimpleDateFormat("yyyyMMdd")
					.format(new Date());
			File imgFile = new File(realPath + Constants.MERCHANT_DIR
					+ currentDate);
			if (!imgFile.exists()) {
				imgFile.mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(realPath
					+ Constants.MERCHANT_DIR + currentDate + "/" + newFileName);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			stream.close();
			target = Constants.MERCHANT_DIR + currentDate + "/" + newFileName;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return target;
	}

}
