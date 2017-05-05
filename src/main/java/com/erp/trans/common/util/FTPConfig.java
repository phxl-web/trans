package com.erp.trans.common.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * FTP客户端配置项 <br/>
 * @date: 2016年7月8日 上午11:27:57 <br/>
 * @author 黄文君
 * @version 1.0
 * @since JDK 1.6
 */
public class FTPConfig implements Serializable {
	private String ip;
	private int port=21;//默认:21
	private String username;
	private String password;
	
	private String controlEncoding;
	private String clientCharset;
	private String serverCharset;
	
	private Boolean enterLocalPassiveMode;
	
	private Integer transferredFileType;
	
	private Integer dataTimeout;
	
	private Integer connectTimeout;
	
	private Integer defaultTimeout;
	
	private Boolean enterRemotePassiveMode;
	
	private Integer minActivePort;
	
	private Integer maxActivePort;
	
	public final static String DEFAULT_CONTROL_ENCODING="UTF-8";
	public final static String DEFAULT_CLIENT_CHARSET="UTF-8";
	public final static String DEFAULT_SERVER_CHARSET="ISO-8859-1";
	
	public FTPConfig() {
		super();
	}

	public FTPConfig(String ip, int port, String username, String password) {
		super();
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientCharset() {
		//return clientCharset==null ? DEFAULT_CLIENT_CHARSET : clientCharset;
		return clientCharset;
	}

	public void setClientCharset(String clientCharset) {
		//this.clientCharset = StringUtils.isBlank(clientCharset) ? DEFAULT_CLIENT_CHARSET : clientCharset;
		this.clientCharset = clientCharset;
	}

	public String getServerCharset() {
		//return serverCharset==null ? DEFAULT_SERVER_CHARSET : serverCharset;
		return serverCharset;
	}

	public void setServerCharset(String serverCharset) {
		//this.serverCharset = StringUtils.isBlank(serverCharset) ? DEFAULT_SERVER_CHARSET : serverCharset;
		this.serverCharset = serverCharset;
	}

	public String getControlEncoding() {
		//return controlEncoding==null ? DEFAULT_CONTROL_ENCODING : controlEncoding;
		return controlEncoding;
	}

	public void setControlEncoding(String controlEncoding) {
		//this.controlEncoding = StringUtils.isBlank(controlEncoding) ? DEFAULT_CONTROL_ENCODING : controlEncoding;
		this.controlEncoding = controlEncoding;
	}

	public Boolean getEnterLocalPassiveMode() {
		return enterLocalPassiveMode;
	}

	public void setEnterLocalPassiveMode(Boolean enterLocalPassiveMode) {
		this.enterLocalPassiveMode = enterLocalPassiveMode;
	}

	public Integer getTransferredFileType() {
		return transferredFileType;
	}

	public void setTransferredFileType(Integer transferredFileType) {
		this.transferredFileType = transferredFileType;
	}

	public Integer getDataTimeout() {
		return dataTimeout;
	}

	public void setDataTimeout(Integer dataTimeout) {
		this.dataTimeout = dataTimeout;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getDefaultTimeout() {
		return defaultTimeout;
	}

	public void setDefaultTimeout(Integer defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}

	public Boolean getEnterRemotePassiveMode() {
		return enterRemotePassiveMode;
	}

	public void setEnterRemotePassiveMode(Boolean enterRemotePassiveMode) {
		this.enterRemotePassiveMode = enterRemotePassiveMode;
	}

	public Integer getMinActivePort() {
		return minActivePort;
	}

	public void setMinActivePort(Integer minActivePort) {
		this.minActivePort = minActivePort;
	}

	public Integer getMaxActivePort() {
		return maxActivePort;
	}

	public void setMaxActivePort(Integer maxActivePort) {
		this.maxActivePort = maxActivePort;
	}

	@Override
	public String toString() {
		return "FTPConfig [ip=" + ip + ", port=" + port + ", username=" + username + ", password=" + password + ", controlEncoding="
				+ controlEncoding + ", clientSysCharset=" + clientCharset + ", serverSysCharset=" + serverCharset + "]";
	}
}
