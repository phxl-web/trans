package com.erp.trans.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.erp.trans.common.constant.MimeType;
import com.erp.trans.common.exception.FtpClientException;
import com.erp.trans.common.exception.ValidationException;

/**
 * FTP基本操作工具类
 * 
 * @date	2016年7月9日 下午12:18:58
 * @author	黄文君
 * @version	1.0
 * @since	JDK 1.6
 */
public class FTPUtils {
	public final static Logger logger = LoggerFactory.getLogger(FTPUtils.class);

	/**ftp配置信息*/
	private static FTPConfig ftpConfig;
	
	static {
		SystemConfig systemConfig=new SystemConfig();
		try {
			systemConfig.init();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		ftpConfig = new FTPConfig();
		ftpConfig.setIp(systemConfig.getProperty("ftp.config.ip"));//FTP主机ip
		ftpConfig.setPort(Integer.parseInt(systemConfig.getProperty("ftp.config.port")));//FTP主机端口
		ftpConfig.setUsername(systemConfig.getProperty("ftp.config.username"));//用户名
		ftpConfig.setPassword(systemConfig.getProperty("ftp.config.password"));//密码
		ftpConfig.setControlEncoding(systemConfig.getProperty("ftp.config.controlEncoding"));//FTP服务器文件读写字符集
		ftpConfig.setClientCharset(systemConfig.getProperty("ftp.config.clientSysCharset"));//客户端系统字符集
		ftpConfig.setServerCharset(systemConfig.getProperty("ftp.config.serverSysCharset"));//FTP服务器系统字符集
		
		//数据传输类型:1(ASCII)、2(二进制)
		String transferredFileType = systemConfig.getProperty("ftp.config.transferred.FileType");
		if(StringUtils.isNotBlank(transferredFileType)){
			ftpConfig.setTransferredFileType(Integer.parseInt(transferredFileType.trim()));
		}
		//启用本地被动访问模式: true|false
		String enterLocalPassiveMode = systemConfig.getProperty("ftp.config.enterLocalPassiveMode");
		if(StringUtils.isNotBlank(enterLocalPassiveMode)){
			ftpConfig.setEnterLocalPassiveMode(Boolean.parseBoolean(enterLocalPassiveMode.trim()));
		}
		//启用服务器端被动访问模式: true|false
		String enterRemotePassiveMode = systemConfig.getProperty("ftp.config.enterRemotePassiveMode");
		if(StringUtils.isNotBlank(enterRemotePassiveMode)){
			ftpConfig.setEnterRemotePassiveMode(Boolean.parseBoolean(enterRemotePassiveMode.trim()));
		}
		//服务器活动端口范围
		String activePortRange = systemConfig.getProperty("ftp.config.ActivePortRange");
		if(StringUtils.isNotBlank(activePortRange)){
			String[] range=activePortRange.trim().split("[,~-]+");
			String minPort=range[0];
			String maxPort=range.length>1? range[1] : range[0];
			ftpConfig.setMinActivePort(Integer.parseInt(minPort));
			ftpConfig.setMaxActivePort(Integer.parseInt(maxPort));
		}
		//设置传输超时时间(单位:毫秒)
		String dataTimeout = systemConfig.getProperty("ftp.config.DataTimeout");
		if(StringUtils.isNotBlank(dataTimeout)){
			ftpConfig.setDataTimeout(Integer.parseInt(dataTimeout.trim()));
		}
		//连接超时时间(单位:毫秒)
		String connectTimeout = systemConfig.getProperty("ftp.config.ConnectTimeout");
		if(StringUtils.isNotBlank(connectTimeout)){
			ftpConfig.setConnectTimeout(Integer.parseInt(connectTimeout.trim()));
		}
		//默认连接超时时间(单位:毫秒)
		String defaultTimeout = systemConfig.getProperty("ftp.config.DefaultTimeout");
		if(StringUtils.isNotBlank(defaultTimeout)){
			ftpConfig.setDefaultTimeout(Integer.parseInt(defaultTimeout.trim()));
		}
		
		if(logger.isDebugEnabled()){
			try {
				logger.debug("FTP客户端: 配置初始化完成. \n"+JSONUtils.toPrettyJson(ftpConfig));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * FTP文件上传
	 * @author	黄文君
	 * @date	2016年7月8日 下午10:45:59
	 * @param	ftpClient
	 * @param	directory
	 * @param	fileName
	 * @param	source
	 * @throws	Exception
	 * @return	void
	 */
	public static void upload(String directory, String fileName, InputStream source) throws Exception {
		long startTime = System.currentTimeMillis();
		String tipKey = "FTP文件上传: ";
		if (StringUtils.isBlank(directory)) {
			throw new ValidationException(tipKey + "请指定上传的目录(directory)!");
		}else{
			directory = directory.replaceAll("[\\\\/]+", "/");
		}
		if (StringUtils.isBlank(fileName)) {
			throw new ValidationException(tipKey + "请指定上传文件名称(fileName)!");
		}else{
			fileName = fileName.replaceAll("[\\\\/]+", "/");
		}
		Assert.notNull(source, tipKey + "没有要上传的文件(source)!");
		
		String ftpDirectory = charsetExchange(directory, ftpConfig.getClientCharset(), ftpConfig.getServerCharset());//目录路径
		String ftpFileName = charsetExchange(fileName, ftpConfig.getClientCharset(), ftpConfig.getServerCharset());//文件名

		FTPClient ftpClient = null;
		try {
			ftpClient = createFTPClient();

			// 切换到文件要保存的目录
			if (StringUtils.isNotBlank(ftpDirectory) && !ftpClient.changeWorkingDirectory(ftpDirectory)) {
				makeDirectorys(directory);//创建目录,注意用入参directory
				ftpClient.changeWorkingDirectory(ftpDirectory);
			}
			logger.debug("##打印当前目录: "+ftpClient.printWorkingDirectory());
			
			if(ftpClient.storeFile(ftpFileName, source)){//上传文件
				if(logger.isDebugEnabled()){
					boolean b=ftpDirectory.endsWith("/");
					long time = System.currentTimeMillis() - startTime;
					logger.debug(tipKey + "文件(" + (ftpDirectory.endsWith("/")?directory:(directory+"/")) + fileName + ")上传完成. 消耗时间"+time+"（毫秒）");
				}
			}else{
				processReplyString(ftpClient, null);
				logger.error(tipKey + "文件(" + (ftpDirectory.endsWith("/")?directory:(directory+"/")) + fileName + ")上传失败! "+ftpClient.getReplyString());
			}
		} finally {
			try {
				if (null != source) {
					IOUtils.closeQuietly(source);
				}
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			} finally {
				disconnectFtpServerConnect(ftpClient);
			}
		}
	}
	
	/**
	 * FTP文件下载
	 * @author	黄文君
	 * @date	2016年7月8日 下午10:45:39
	 * 
	 * @param	filePath
	 * @param	destOutput
	 * @param	insertable
	 * @throws	Exception
	 * @return	void
	 */
	public static void download(String filePath, OutputStream destOutput, Insertable insertable) throws Exception {
		long startTime = System.currentTimeMillis();
		String tipKey = "FTP文件下载: ";
		if (StringUtils.isBlank(filePath)) {
			throw new ValidationException(tipKey + "请指定要读取的文件路径(filePath)!");
		}else{
			filePath=filePath.replaceAll("[\\\\/]+", "/");
		}
		
		FTPClient ftpClient = null;
		InputStream source = null;
		try {
			ftpClient = createFTPClient();

			String directory = null;// 文件目录: 如, dir01/dir02/dir03
			String fileName = null;// 文件名: 如, filea.txt
			String fileType = null;// 文件类型: 如, .zip
			String ftpFilePath = charsetExchange(filePath, ftpConfig.getClientCharset(), ftpConfig.getServerCharset());

			if (ftpFilePath.endsWith("/")) {
				throw new ValidationException(tipKey + "未指定文件名!");
			} else if(ftpFilePath.lastIndexOf("/")==-1){
				//directory=".";
				fileName=ftpFilePath;
			} else {
				directory = ftpFilePath.substring(0, ftpFilePath.lastIndexOf("/"));
				fileName = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1);
			}

			if (fileName.lastIndexOf(".") != -1) {//如果有文件类型
				fileType = ftpFilePath.substring(ftpFilePath.lastIndexOf(".") + 1);
			}

			final String ftpDirectory=charsetExchange(directory, ftpConfig.getServerCharset(), ftpConfig.getClientCharset());
			final String ftpFileName=charsetExchange(fileName, ftpConfig.getServerCharset(), ftpConfig.getClientCharset());
			
			if (StringUtils.isNotBlank(directory)&&!ftpClient.changeWorkingDirectory(directory)) {
				throw new FtpClientException(tipKey + "目录(" + ftpDirectory + ")切换失败!"+ftpClient.getReplyString());
			}

			//查找文件
			FTPFile[] fileLst = ftpClient.listFiles(fileName, new FTPFileFilter(){
				@Override
				public boolean accept(FTPFile file) {
					return file.isFile()&&file.getName().equals(ftpFileName);
				}
			});
			if (fileLst == null || fileLst.length == 0) {
				throw new FtpClientException(tipKey + "文件(" + filePath + ")不存在!");
			}

			if (insertable != null) {
				//读取前:代码片段植入
				if (insertable.beforeProcess(ftpClient, ftpDirectory, ftpFileName, fileType)) {
					/**读取文件*/
					source = new BufferedInputStream(ftpClient.retrieveFileStream(fileName));
					/**下载文件*/
					IOUtils.copy(source, destOutput);
					long time = System.currentTimeMillis() - startTime;
					logger.debug(tipKey + "文件(" + filePath + ")下载完成. 消耗时间"+time+"（毫秒）");
					// 读取后:代码片段植入
					insertable.afterProcess(ftpClient, ftpDirectory, ftpFileName, fileType);
				}
			} else {
				/** 读取文件 */
				source = new BufferedInputStream(ftpClient.retrieveFileStream(fileName));
				/** 下载文件 */
				IOUtils.copy(source, destOutput);
				logger.debug(tipKey + "文件(" + filePath + ")读取(下载)完成.");
			}
		} finally {
			try {
				if (null != source) {
					IOUtils.closeQuietly(source);
				}
				if (null != destOutput) {
					IOUtils.closeQuietly(destOutput);
				}
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			} finally {
				disconnectFtpServerConnect(ftpClient);
			}
		}
	}

	/**
	 * FTP文件下载
	 * 
	 * @author 黄文君
	 * @date: 2016年7月8日 下午10:48:57
	 *
	 * @param filePath
	 * @param destOutput
	 * @throws Exception
	 * @return void
	 */
	public static void download(String filePath, OutputStream destOutput) throws Exception {
		download(filePath, destOutput, null);
	}

	/**
	 * 辅助:可植入逻辑片段
	 * 
	 * @date: 2016年7月8日 下午5:51:00
	 * @author 黄文君
	 * @version 1.0
	 * @since JDK 1.6
	 */
	public interface Insertable {
		/**
		 * 读取ftp文件之前处理
		 * @author	黄文君
		 * @date	2016年7月9日 下午12:48:46
		 *
		 * @param	ftpClient
		 * @param	ftpDirectory
		 * @param	ftpFileName
		 * @param	fileType
		 * @return	boolean
		 */
		public boolean beforeProcess(FTPClient ftpClient, String ftpDirectory, String ftpFileName, String fileType) throws Exception;
		
		/**
		 * 读取ftp文件之后处理
		 * @author	黄文君
		 * @date	2016年7月9日 下午12:49:21
		 *
		 * @param	ftpClient
		 * @param	ftpDirectory
		 * @param	ftpFileName
		 * @param	fileType
		 * @return	boolean
		 */
		public void afterProcess(FTPClient ftpClient, String ftpDirectory, String ftpFileName, String fileType) throws Exception;
	}

	/**
	 * FTP文件下载
	 * 
	 * @author 黄文君
	 * @date: 2016年7月8日 下午10:49:19
	 *
	 * @param filePath
	 * @param response
	 * @param newFileName
	 * @throws Exception
	 * @return void
	 */
	public static void download(String filePath, final HttpServletResponse response, final String newFileName) throws Exception {
		final OutputStream destOutput = new BufferedOutputStream(response.getOutputStream());
		download(filePath, destOutput, new Insertable() {
			@Override
			public boolean beforeProcess(FTPClient ftpClient, String directory, String fileName, String fileType) throws Exception {
				String mimeType="";
				if (fileType.matches("pdf|PDF")) {
					mimeType = MimeType.APPLICATION_OF_PDF;
				} else if (fileType.matches("jpg|JPG|png|PNG|jpeg|JPEG|gif|GIF|ico|ICO|icon|ICON|bmp|BMP")) {
					mimeType = MimeType.IMAGE_OF_JPEG;
				} else if(fileType.matches("html|HTML|htm|HTM")){
					mimeType = MimeType.TEXT_OF_HTML;
				} else if(fileType.matches("csv|CSV")){
					mimeType = MimeType.TEXT_OF_CSV;
				} else if(fileType.matches("txt|TXT")){
					mimeType = MimeType.TEXT_OF_PLAIN;
				} else if(fileType.matches("xml|XML")){
					mimeType = MimeType.TEXT_OF_XML;
				} else {
					mimeType = MimeType.APPLICATION_OF_OCTET_STREAM;
				}
				//response.setContentType(mimeType);

				String attachmentName = (StringUtils.isNotBlank(newFileName) ? newFileName : fileName);
				destOutput.flush();
				//response.setHeader("Content-Length", String.valueOf(file.getSize()));
				//response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(attachmentName, "UTF-8"));
				return true;
			}

			@Override
			public void afterProcess(FTPClient ftpClient, String directory, String fileName, String fileType) throws Exception {}
			
		});
	}

	/**
	 * FTP文件删除
	 * @author 黄文君
	 * @date: 2016年7月8日 下午10:49:37
	 *
	 * @param filePath
	 * @throws Exception
	 * @return void
	 */
	public static void deleteFile(String filePath) throws Exception {
		String tipKey = "FTP文件删除: ";
		if (StringUtils.isBlank(filePath)) {
			throw new FtpClientException(tipKey + "请指定文件路径(filePath)!");
		}else{
			filePath=filePath.replaceAll("[\\\\/]+", "/");
		}
		
		FTPClient ftpClient = null;
		String filePath0 = null;
		try {
			ftpClient = createFTPClient();
			filePath0 = charsetExchange(filePath, ftpConfig.getClientCharset(), ftpConfig.getServerCharset());
			if(existFTPFile(ftpClient, filePath0)){
				if(ftpClient.deleteFile(filePath0)){
					logger.debug(tipKey + "文件(" + filePath + ")删除成功");
				}else{
					throw new FtpClientException(tipKey + "文件(" + filePath + ")删除失败!"+ftpClient.getReplyString());
				}
			}else{
				logger.info("文件("+filePath+")不存在");
			}
		} finally {
			disconnectFtpServerConnect(ftpClient);
		}
	}
	
	/**
	 * FTP目录删除
	 * @author 黄文君
	 * @date: 2016年7月9日 下午8:54:07
	 *
	 * @param directory
	 * @throws Exception
	 * @return void
	 */
	public static boolean removeDirectory(String directory) throws Exception {
		String tipKey = "FTP目录删除: ";
		if (StringUtils.isBlank(directory)) {
			throw new ValidationException(tipKey + "请指定目录路径(filePath)!");
		}else{
			directory=directory.replaceAll("[\\\\/]+", "/");
		}
		
		FTPClient ftpClient = null;
		boolean removeFlag=false;//删除结果标识
		try {
			ftpClient = createFTPClient();
			String directory0 = charsetExchange(directory, ftpConfig.getClientCharset(), ftpConfig.getServerCharset());
			
			if(existFTPDirectory(ftpClient, directory0)){
				if(ftpClient.removeDirectory(directory0)){
					logger.debug(tipKey + "目录(" + directory + ")删除成功");
				}else{
					throw new FtpClientException("目录(" + directory + ")删除失败!"+ftpClient.getReplyString());
				}
			}else{
				logger.info("目录("+directory+")不存在");
			}
			
			/*if(directory0.endsWith("/")){
				directory0=directory0.substring(0, directory0.lastIndexOf("/"));
			}
			String directoryName=directory0.substring(directory0.lastIndexOf("/")+1);
			if (StringUtils.isNotBlank(directory0)&&ftpClient.changeWorkingDirectory(directory0)) {//目录存在
				if(ftpClient.changeToParentDirectory()){//上级
					logger.debug("当前目录: "+ftpClient.printWorkingDirectory());
					if (ftpClient.removeDirectory(directoryName)) {
						removeFlag=true;//删除结果标识:成功
					}else{
						throw new FtpClientException("删除目录("+ftpClient.printWorkingDirectory()+")失败!"+ftpClient.getReplyString());
					}
				}else{
					throw new FtpClientException("切换到目录("+directory0+")的上级目录失败!"+ftpClient.getReplyString());
				}
			}else{
				throw new FtpClientException("切换到目录("+directory0+")失败!"+ftpClient.getReplyString());
			}*/
		} finally {
			disconnectFtpServerConnect(ftpClient);
		}
		
		/*if(removeFlag){
			logger.debug(tipKey + "目录(" + directory + ")删除成功");
		}else{
			logger.debug(tipKey + "目录(" + directory + ")删除失败!"+ftpClient.getReplyString());
		}*/
		return removeFlag;
	}
	
	/**
	 * FTP创建目录（并支持多级目录的创建）
	 * @author 黄文君
	 * @date: 2016年7月9日 下午8:58:09
	 *
	 * @param directory
	 * @return void
	 */
	public static void makeDirectorys(String directory) throws Exception {
		String tipKey = "FTP目录创建: ";
		if (StringUtils.isBlank(directory)) {
			throw new ValidationException(tipKey + "请指定目录路径(filePath)!");
		}else{
			directory = directory.replaceAll("[\\\\/]+", "/");
		}
		
		FTPClient ftpClient = null;
		try {
			ftpClient = createFTPClient();

			String ftpDirectory = charsetExchange(directory, ftpConfig.getClientCharset(), ftpConfig.getServerCharset());
			if(ftpDirectory.startsWith("/")){
				ftpClient.changeWorkingDirectory("/");
				ftpDirectory = ftpDirectory.substring(1);
			}
			
			String directorys[] = ftpDirectory.split("[\\\\/]+");
			for(String dir: directorys){
				if(StringUtils.isNotBlank(dir)){
					if(!ftpClient.changeWorkingDirectory(dir)) {//切换当前目录，如果目录还没有，就创建
						if(ftpClient.makeDirectory(dir)){//创建目录
							if(!ftpClient.changeWorkingDirectory(dir)){//切换当前目录
								break;
							}
							logger.debug(tipKey+"当前目录位置path="+ftpClient.printWorkingDirectory());
						}else{
							throw new FtpClientException("创建子目录("+dir+")失败!"+ftpClient.getReplyString());
							
						}
					}else{//如果切换目录成功（目录已经存在），继续建下一个子目录
						continue;
					}
				}
			}
			logger.debug(tipKey+"创建目录("+directory+")成功");
		} finally {
			disconnectFtpServerConnect(ftpClient);
		}
	}
	
	/**
	 * 判断FTP是否存在指定的文件
	 * @author	黄文君
	 * @date	2016年7月12日 上午7:42:05
	 *
	 * @param	ftpClient
	 * @param	filePath
	 * @return	boolean
	 * @throws	IOException 
	 * @throws	ValidationException 
	 */
	public static boolean existFTPFile(FTPClient ftpClient, String filePath) throws IOException, ValidationException{
		if (StringUtils.isBlank(filePath)) {
			throw new ValidationException("请指定文件路径(filePath)!");
		}else{
			filePath=filePath.replaceAll("[\\\\/]+", "/");
		}
		String directory = null;// 文件目录: 如, dir01/dir02/dir03
		String fileName = null;// 文件名: 如, filea.txt
		String filePath0 = charsetExchange(filePath, ftpConfig.getClientCharset(), ftpConfig.getServerCharset());

		if (filePath0.endsWith("/")) {
			throw new ValidationException("未指定文件名!");
		} else if(filePath0.lastIndexOf("/")==-1){
			//directory=".";
			fileName=filePath0;
		} else {
			directory = filePath0.substring(0, filePath0.lastIndexOf("/"));
			fileName = filePath0.substring(filePath0.lastIndexOf("/") + 1);
		}

		if (StringUtils.isNotBlank(directory)&&!ftpClient.changeWorkingDirectory(directory)) {
			logger.error("切换目录("+directory+")失败!"+ftpClient.getReplyString());
			return false;
		}
		
		//System.out.println("当前目录: "+charsetExchange(ftpClient.printWorkingDirectory(), "ISO-8859-1", "UTF-8"));

		//查找文件
		final String finalFileName=fileName;
		FTPFile[] fileLst = ftpClient.listFiles(null, new FTPFileFilter(){
			@Override
			public boolean accept(FTPFile file) {
				return file.isFile()&&file.getName().equals(finalFileName);
			}
		});
		
		if (fileLst != null && fileLst.length > 0) {
			/*for(FTPFile f: fileLst){
				logger.debug(f.getName());
			}*/
			return true;
		}
		return false;
	}
	
	/**
	 * 判断FTP是否存在指定的目录
	 * @author	黄文君
	 * @date	2016年7月12日 上午7:42:44
	 *
	 * @param	ftpClient
	 * @param	filePath
	 * @return	boolean
	 * @throws	IOException
	 * @throws	ValidationException
	 */
	public static boolean existFTPDirectory(FTPClient ftpClient, String directory) throws IOException, ValidationException{
		if (StringUtils.isBlank(directory)) {
			throw new ValidationException("请指定目录(directory)!");
		}else{
			directory=directory.replaceAll("[\\\\/]+", "/");
		}
		if(ftpClient.changeWorkingDirectory(directory)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ftp远程复制文件
	 * @author	黄文君
	 * @date	2017年3月26日 上午8:25:34
	 * 
	 * @param	fromFilePath
	 * @param	toFilePath
	 * @return	void
	 * @throws	Exception 
	 */
	public static void copyFileRemote(String fromFilePath, final String toFilePath) throws Exception{
		LocalAssertUtils.notBlank(fromFilePath, "fomrFilePath，不能为空");
		LocalAssertUtils.notBlank(toFilePath, "toFilePath，不能为空");
		
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		
		download(fromFilePath, bytes, new Insertable() {
			@Override
			public boolean beforeProcess(FTPClient ftpClient, String ftpDirectory, String ftpFileName, String fileType) throws Exception {
				return true;
			}

			@Override
			public void afterProcess(FTPClient ftpClient, String ftpDirectory, String ftpFileName, String fileType) throws Exception {
				String directory = null;// 文件目录: 如, dir01/dir02/dir03
				String fileName = null;// 文件名: 如, filea.txt

				if (toFilePath.endsWith("/")) {
					throw new ValidationException("未指定文件名!");
				} else if(toFilePath.lastIndexOf("/")==-1){
					//directory=".";
					fileName=toFilePath;
				} else {
					directory = toFilePath.substring(0, toFilePath.lastIndexOf("/"));
					fileName = toFilePath.substring(toFilePath.lastIndexOf("/") + 1);
				}
				
				//FTP文件上传
				upload(directory, fileName, new ByteArrayInputStream(bytes.toByteArray()));
			}
		});
	}
	
	/**
	 * ftp远程移动文件
	 * @author	黄文君
	 * @date	2017年3月26日 上午8:25:38
	 * 
	 * @param	fomrFilePath
	 * @param	toFilePath
	 * @return	void
	 */
	public static void moveFileRemote(String fomrFilePath, String toFilePath){
		throw new RuntimeException("该方法功能待实现");
	}

	/**
	 * 建立FTP服务器连接(注意:创建连接后，用完记得释放连接退出)
	 * @link	disconnectFtpServerConnect(FTPClient ftpClient)
	 * @author	黄文君
	 * @date	2016年7月8日 上午11:17:49 <br/>
	 *
	 * @throws	Exception
	 * @return	FTPClient
	 */
	public static FTPClient createFTPClient() throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort());
		ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());

		String tipKey="Ftp客户端连接: ";
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			ftpClient.disconnect();
			throw new FtpClientException(tipKey+"登录失败! ");
		} else {
			logger.debug(tipKey+"登录成功");
		}

		//FTP服务器文件读写字符集
		if(ftpConfig.getControlEncoding()!=null){
			ftpClient.setControlEncoding(ftpConfig.getControlEncoding());
		}else{
			ftpClient.setControlEncoding(ftpConfig.DEFAULT_CONTROL_ENCODING);
		}
		//数据传输类型:二进制、或ASCII
		if (ftpConfig.getTransferredFileType()!=null) {
			ftpClient.setFileType(ftpConfig.getTransferredFileType());
		}else{
			ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
		}
		//服务器端安全认证开关
		ftpClient.setRemoteVerificationEnabled(false);
		
		//启用本地被动访问模式
		if(Boolean.TRUE.equals(ftpConfig.getEnterLocalPassiveMode())){
			ftpClient.enterLocalPassiveMode();
		}
		//启用服务器端被动访问模式
		if(Boolean.TRUE.equals(ftpConfig.getEnterRemotePassiveMode())){
			ftpClient.enterRemotePassiveMode();
		}
		//服务器活动端口范围
		if(ftpConfig.getMinActivePort()!=null){
			ftpClient.setActivePortRange(ftpConfig.getMinActivePort(), ftpConfig.getMaxActivePort());//活动端口范围
		}
		//设置传输超时时间
		if(ftpConfig.getDataTimeout()!=null){
			ftpClient.setDataTimeout(ftpConfig.getDataTimeout());
		}
		//连接超时时间
		if(ftpConfig.getDataTimeout()!=null){
			ftpClient.setConnectTimeout(60000);
		}
		//默认连接超时时间
		if(ftpConfig.getDataTimeout()!=null){
			ftpClient.setDefaultTimeout(60000);
		}
		
		return ftpClient;
	}

	/**
	 * 断开FTP服务器连接
	 * @author	黄文君
	 * @date	2016年7月8日 上午11:17:25
	 *
	 * @param	ftpClient
	 * @return	void
	 */
	public static void disconnectFtpServerConnect(FTPClient ftpClient) {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
				logger.debug("Ftp客户端连接: 退出成功");
			} catch (Exception e) {
				logger.error("Ftp客户端连接: 退出异常!", e);
			}
		}
	}
	
	/**
	 * 字转串转码（转换字符集）
	 * @author	黄文君
	 * @date	2016年7月11日 下午3:14:08
	 *
	 * @param	sourceString
	 * @param	originCharset
	 * @param	destCharset
	 * @throws	UnsupportedEncodingException
	 * @return	String
	 */
	public static String charsetExchange(String sourceString, String originCharset, String destCharset) throws UnsupportedEncodingException{
		if(!originCharset.equalsIgnoreCase(destCharset)){
			return new String(sourceString.getBytes(originCharset), destCharset);
		}
		return sourceString;
	}
	
	/**
	 * 打印FTP服务交互状态行消息
	 * @author	黄文君
	 * @date	2016年7月11日 上午9:49:54
	 *
	 * @param	ftpClient
	 * @throws	FtpClientException
	 * @return	String
	 */
	public static String processReplyString(FTPClient ftpClient, String message, Object... args) throws FtpClientException{
		String replyString = ftpClient.getReplyString();
		if(args!=null){
			int aLength=args.length;
			args=Arrays.copyOf(args, aLength+1);
			args[aLength]=replyString;
		}
		
		if(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
			if(!StringUtils.isEmpty(message)){
				message=MessageFormat.format(message, args);
			}else{
				message="FTP状态行: "+replyString;
			}
			logger.error(message);
			//throw new FtpClientException(message);
		}
		return replyString;
	}
	
	public static void setFtpConfig(FTPConfig ftpConfig) {
		FTPUtils.ftpConfig = ftpConfig;
	}
}