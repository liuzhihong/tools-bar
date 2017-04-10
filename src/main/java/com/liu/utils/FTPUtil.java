package com.liu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP工具类
 * 
 * @author wujun10
 *
 */
public class FTPUtil {

	private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				logger.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			logger.info("FTP的IP地址可能错误，请正确配置。错误信息：[{}]", e);
		} catch (IOException e) {
			logger.info("FTP的端口错误,请正确配置。错误信息：[{}]", e);
		}
		return ftpClient;
	}

	/**
	 * 去 服务器的FTP路径下上下载文件
	 * 
	 * @param ftpHost
	 * @param ftpPort
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param fileName
	 * @param localPath
	 * @return
	 */
	public static boolean downloadFileFromFTP(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, String ftpPath,
			String fileName, String localPath) {
		boolean flag = false;
		StringBuffer resultBuffer = new StringBuffer();
		InputStream in = null;
		FTPClient ftpClient = null;
		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			in = ftpClient.retrieveFileStream(fileName);
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + ftpPath + "文件。错误信息：[{}]", e);
		} catch (SocketException e) {
			logger.error("连接FTP失败.错误信息：[{}]", e);
		} catch (IOException e) {
			logger.error("文件读取错误。错误信息：[{}]", e);
		}
		if (in != null) {
			FileWriter fileWriter = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data = null;
			try {
				fileWriter = new FileWriter(new File(localPath + File.separator + fileName));
				while ((data = br.readLine()) != null) {
					resultBuffer.append(data + "\n");
				}
				fileWriter.write(resultBuffer.toString());
				fileWriter.flush();
				fileWriter.close();
				flag = true;
			} catch (IOException e) {
				logger.error("文件读取错误。错误信息：[{}]", e);
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.error("in为空，不能读取。");
		}
		return flag;
	}

	/**
	 * 本地上传文件到FTP服务器
	 * 
	 * @param ftpHost
	 * @param ftpPort
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param fileName
	 * @param localPath
	 */
	public static boolean uploadFileToFTP(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, String ftpPath,
			String fileName, String localPath) {
		boolean flag = false;
		FTPClient ftpClient = null;
		logger.info("开始上传文件到FTP.");
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 对远程目录的处理
			String remoteFileName = ftpPath;
			File f = new File(localPath + File.separator + fileName);
			InputStream in = new FileInputStream(f);
			ftpClient.storeFile(remoteFileName, in);
			in.close();
			flag = true;
			logger.info("上传文件" + remoteFileName + "到FTP成功!");
		} catch (Exception e) {
			logger.error("上传文件错误。错误信息：[{}]", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
}
