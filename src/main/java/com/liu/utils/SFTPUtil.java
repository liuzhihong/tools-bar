package com.liu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {

	private static Logger logger = LoggerFactory.getLogger(SFTPUtil.class);

	public static String OPERATE_UPLOAD = "upload";
	public static String OPERATE_DOWNLOAD = "download";
	
	private String host;// 服务器连接ip
	private int port;// 端口号
	private String username;// 用户名
	private String password;// 密码

	private ChannelSftp sftp = null;
	private Session sshSession = null;

	public SFTPUtil(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * 通过SFTP连接服务器
	 */
	public void connect() {
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			sshSession = jsch.getSession(username, host, port);
			if (logger.isInfoEnabled()) {
				logger.info("Session created.");
			}
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			if (logger.isInfoEnabled()) {
				logger.info("Session connected.");
			}
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			if (logger.isInfoEnabled()) {
				logger.info("Opening Channel.");
			}
			sftp = (ChannelSftp) channel;
			if (logger.isInfoEnabled()) {
				logger.info("Connected to " + host + ".");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接
	 */
	public void disconnect() {
		if (this.sftp != null) {
			if (this.sftp.isConnected()) {
				this.sftp.disconnect();
				if (logger.isInfoEnabled()) {
					logger.info("sftp is closed already");
				}
			}
		}
		if (this.sshSession != null) {
			if (this.sshSession.isConnected()) {
				this.sshSession.disconnect();
				if (logger.isInfoEnabled()) {
					logger.info("sshSession is closed already");
				}
			}
		}
	}

	/**
	 * 下载单个文件
	 * 
	 * @param remotPath：远程下载目录(以路径符号结束)
	 * @param remoteFileName：下载文件名
	 * @param localPath：本地保存目录(以路径符号结束)
	 * @param localFileName：保存文件名
	 * @return
	 */
	public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
		FileOutputStream fieloutput = null;
		try {
			File file = new File(localPath + localFileName);
			fieloutput = new FileOutputStream(file);
			sftp.get(remotePath + remoteFileName, fieloutput);
			if (logger.isInfoEnabled()) {
				logger.info("===DownloadFile:" + remoteFileName + " success from sftp.");
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			if (null != fieloutput) {
				try {
					fieloutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 上传单个文件
	 * 
	 * @param remotePath：远程保存目录
	 * @param remoteFileName：保存文件名
	 * @param localPath：本地上传目录(以路径符号结束)
	 * @param localFileName：上传的文件名
	 * @return
	 */
	public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
		FileInputStream in = null;
		String tempRemoteFileName = remoteFileName + ".temp" ;
		try {
			createDir(remotePath);
			File file = new File(localPath + localFileName);
			in = new FileInputStream(file);
			sftp.put(in, tempRemoteFileName);
			sftp.rename(remotePath + tempRemoteFileName, remotePath + remoteFileName);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 创建目录
	 * 
	 * @param createpath
	 * @return
	 */
	public boolean createDir(String createpath) {
		try {
			if (isDirExist(createpath)) {
				this.sftp.cd(createpath);
				return true;
			}
			String pathArry[] = createpath.split("/");
			StringBuffer filePath = new StringBuffer("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path + "/");
				if (isDirExist(filePath.toString())) {
					sftp.cd(filePath.toString());
				} else {
					// 建立目录
					sftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					sftp.cd(filePath.toString());
				}

			}
			this.sftp.cd(createpath);
			return true;
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断目录是否存在
	 * 
	 * @param directory
	 * @return
	 */
	public boolean isDirExist(String directory) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}
	
	public boolean operateSFTP(String remotePath, String remoteFileName, String localPath, String localFileName, String operateType){
		SFTPUtil sftp = null;
		try {
			sftp = new SFTPUtil(this.host, this.port, this.username, this.password);
			sftp.connect();
			if(OPERATE_UPLOAD.equals(operateType)){
				return sftp.uploadFile(remotePath, remoteFileName, localPath, localFileName);
			}else if(OPERATE_DOWNLOAD.equals(operateType)){
				return sftp.downloadFile(remotePath, remoteFileName, localPath, localFileName);
			}else{
				logger.info("操作类型不在已知的范围内");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sftp.disconnect();
		}
		return false;
	}
	
}
