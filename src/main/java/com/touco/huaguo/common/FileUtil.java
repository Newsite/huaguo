/**
 * Copyright(C) 2011-2019 Touco WuXi Technology LTD. All Rights Reserved.   
 * 版权所有(C) 2011-2019 无锡途拓科技有限公司
 * 公司名称：无锡途拓软件科技有限公司 
 * 公司地址：中国，江苏省无锡市滨湖区蠡园开发区530大厦B座5楼
 * 网址:http://www.touco.cn
 * <p>
 * 文件名：com.touco.ccmp.common.FileUtil.java
 * <p>
 * 作者: 史中营
 * <p>
 * 创建时间: 2012-2-10下午3:41:47
 * <p>
 * 部门: 产品部
 * <p>
 */
package com.touco.huaguo.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;

public class FileUtil {

	/**
     * 把一个文件转化为字节
     * @param file
     * @return   byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) throws Exception
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }
    
    /**
     * 把一个文件转化为字节
     * @param file
     * @return   byte[]
     * @throws Exception
     */
    public static byte[] getByte(InputStream is) throws Exception
    {
    	ByteArrayOutputStream os = new ByteArrayOutputStream();
        if(is!=null)
        {
        	byte[] buffer = new byte[8*1024];
        	int len = 0;
        	while((len = is.read(buffer)) != -1){
        		os.write(buffer,0,len);
        	}
            
            os.close();
            is.close();
        }
        return os.toByteArray();
    }
    
    /**
     * copy文件
     * 
     * @param filePath
     * @param targetFilePath
     * @param fileName
     */
    public static void copyFile(String filePath,String targetFilePath,String fileName) {        
        try {
            byte[] bytes = FileUtil.getByte(new File(filePath));
            
            if (bytes.length != 0) {
    			File file2 = new File(targetFilePath);
    			if (!file2.exists()) {
    				file2.mkdirs();
    			}
    			
    			FileCopyUtils.copy(bytes, new File(targetFilePath + fileName));
    		}                       
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
    }
    
    /**
     * copy文件
     * 
     * @param filePath
     * @param targetFilePath
     */
    public static void copyFile(String filePath,String targetFilePath) {        
        try {
        	if(StringUtils.isEmpty(filePath)){
        		return;
        	}
        	
        	File file = new File(filePath);
            byte[] bytes = FileUtil.getByte(file);
            
            if (bytes.length != 0) {
    			File file2 = new File(targetFilePath);
    			if (!file2.exists()) {
    				file2.mkdirs();
    			}
    			
    			FileCopyUtils.copy(bytes, new File(targetFilePath + file.getName()));
    		}                       
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
    }
    
    /**
     * copy文件
     * 
     * @param bytes
     * @param targetFilePath
     * @param fileName
     * @return
     */
    public static File copyFile(byte[] bytes,String targetFilePath,String fileName) {
    	File file = null;
        try {
            if (bytes.length != 0) {
    			File file2 = new File(targetFilePath);
    			if (!file2.exists()) {
    				file2.mkdirs();
    			}
    			file = new File(targetFilePath + fileName);
    			FileCopyUtils.copy(bytes, file);
    		}                       
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
        
        return file;
    }    
    
    /**
     * 读sql文件
     * 
     * @param filepath
     * @return
     */
    public static List<String> readSqlFile(String filepath){
    	List<String> sqlList = new ArrayList<String>();
    	File file = new File(filepath);
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
    		StringBuffer contents = new StringBuffer();
    		String text = null;
    		// repeat until all lines is read
    		while ((text = reader.readLine()) != null){
    			if(StringUtils.isNotBlank(text)){
    				contents.append(text);
    				if(text.endsWith(";")){    					
    					sqlList.add(contents.toString());
    					contents.setLength(0);    					
    				}
    			}
    		}
		} catch (FileNotFoundException e) {
			 e.printStackTrace();
		} catch (IOException e) {
			 e.printStackTrace();
		} finally{
			try {
				 if (reader != null){
					 reader.close();
				 }
			} catch (Exception e) {
				 e.printStackTrace();
			}
		}
    	
    	return sqlList;

    }
    /**
     * 删除文件及目录
     * 
     * @param filepath
     * @throws IOException
     */
	public static Boolean delFile(String filepath) throws IOException {
		Boolean result = Boolean.FALSE;
		File f = new File(filepath);// 定义文件路径
		if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
			if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
				result = f.delete();
			}
			else {// 若有则把文件放进数组，并判断是否有下级目录
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						delFile(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
					}
					result = delFile[j].delete();// 删除文件
				}
			}
		}
		else if(f.isFile()){
			result = f.delete();
		}
		
		return result;
	}
}
