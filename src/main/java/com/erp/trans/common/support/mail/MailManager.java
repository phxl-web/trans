package com.erp.trans.common.support.mail;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.erp.trans.common.exception.BaseException;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.JSONUtils;

/**
 * 邮件发送管理端
 *
 * @version	1.0
 * @since	JDK 1.6
 */
public class MailManager {
	public Logger logger = LoggerFactory.getLogger(MailManager.class);
	
	@Resource
	private JavaMailSender	mailSender;
	
	/**
	 * 发送邮件
	 *
	 * @date	2016年9月18日 下午3:57:34
	 * @param	mail
	 * @return	void
	 * @throws	PhxlException 
	 */
	public void send(Mail mail) throws BaseException {
		long currentTime = System.currentTimeMillis();
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			//--------[发件人]------------
			mail.setFrom(((JavaMailSenderImpl)mailSender).getUsername());
			//验证基本的Mail信息
			validateOfMail(mail);
			if(mail.getFromNickname()!=null){
				helper.setFrom(mail.getFrom(), mail.getFromNickname());//发件人及其昵称
			}else{
				helper.setFrom(mail.getFrom());//发件人
			}
			//--------[收件人]------------
			String[] multiTo = mail.getTo();
			Map<String, String> toNicknames = mail.getToNicknames();
			if (ArrayUtils.isNotEmpty(multiTo)) {
				InternetAddress[] toAddresses = perfectAddresses(multiTo, toNicknames);//生成友好的邮件地址列表
				if(ArrayUtils.isNotEmpty(toAddresses)){
					message.addRecipients(Message.RecipientType.TO, toAddresses);
				}
			}
			//--------[抄送人]------------
			String[] multiCc = mail.getCc();
			if (ArrayUtils.isNotEmpty(multiCc)) {
				InternetAddress[] toAddresses = perfectAddresses(multiCc, toNicknames);//生成友好的邮件地址列表
				if(ArrayUtils.isNotEmpty(toAddresses)){
					message.addRecipients(Message.RecipientType.CC, toAddresses);
				}
			}
			//--------[密送人]------------
			String[] multiBcc = mail.getBcc();
			if (ArrayUtils.isNotEmpty(multiBcc)) {
				InternetAddress[] toAddresses = perfectAddresses(multiBcc, toNicknames);//生成友好的邮件地址列表
				if(ArrayUtils.isNotEmpty(toAddresses)){
					message.addRecipients(Message.RecipientType.BCC, toAddresses);
				}
			}
			//--------[邮件主题]----------
			helper.setSubject(mail.getSubject());
			//--------[邮件内容]----------
			if(mail.isHtmlCtt()){
				helper.setText(mail.getContent(), true);
			}else{
				helper.setText(mail.getContent());
			}
			//--------[被回复人]----------
			if(StringUtils.isNotEmpty(mail.getReplyTo())){
				if(MapUtils.isNotEmpty(toNicknames) && toNicknames.get(mail.getReplyTo())!=null){
					helper.setReplyTo(mail.getReplyTo(), toNicknames.get(mail.getReplyTo()));
				}else{
					helper.setReplyTo(mail.getReplyTo());
				}
			}
			//--------[发送时间]----------
			helper.setSentDate(new Date());
			
			//--------[邮件内容:内嵌资源]----------
			if (MapUtils.isNotEmpty(mail.getNestedSources())) {
				Set<Entry<String, String>> entryList = mail.getNestedSources().entrySet();
				for (Entry<String, String> entry: entryList) {
					logger.debug("[邮件内容:内嵌资源]"+entry.getKey()+"="+entry.getValue());
					if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
						helper.addInline(entry.getKey(), new File(entry.getValue()));//添加内嵌图片、音频等资源
					}
				}
			}
			//--------[邮件附件]----------
			if (ArrayUtils.isNotEmpty(mail.getAttachments())) {
				for (String path : mail.getAttachments()) {
					File file = new File(path);
					if (file.exists() && file.isFile()) {
						helper.addAttachment(MimeUtility.encodeWord(file.getName(), "gbk", "B"), file);//添加附件，使用MimeUtility.encodeWord()来解决附件名称的中文问题
					}
				}
			}
			//发送邮件
			mailSender.send(message);
			if (logger.isDebugEnabled()) {
				logger.debug("邮件发送成功(消耗"+(System.currentTimeMillis()-currentTime)+"毫秒). mail=" + JSONUtils.toJsonLoosely(mail));
			}
		} catch(Exception e){
			logger.error("邮件发送失败! mail="+JSONUtils.toJsonLoosely(mail), e);
			String errorMsg = "邮件发送异常: " + e.getMessage();
			throw new BaseException(errorMsg.length()>200 ? errorMsg.substring(0, 200) : errorMsg);
		}
	}

	/**
	 * 生成友好的邮件地址列表
	 *
	 * @date	2016年9月19日 上午9:28:07
	 * @param 	multiTo
	 * @param 	toNicknames
	 * @throws 	UnsupportedEncodingException
	 * @throws 	AddressException
	 * @return	InternetAddress[]
	 */
	private InternetAddress[] perfectAddresses(String[] multiTo, Map<String, String> toNicknames)
			throws UnsupportedEncodingException, AddressException {
		InternetAddress[] toAddresses = null;
		if(ArrayUtils.isNotEmpty(multiTo)){
			for(String to: multiTo){
				if (toAddresses==null) {
					toAddresses = new InternetAddress[1];
				}else{
					toAddresses = Arrays.copyOf(toAddresses, toAddresses.length+1);
				}
				if(MapUtils.isNotEmpty(toNicknames) && toNicknames.get(to)!=null){
					toAddresses[toAddresses.length-1] = new InternetAddress(to, toNicknames.get(to));
				}else{
					toAddresses[toAddresses.length-1] = new InternetAddress(to);
					//message.addRecipients(Message.RecipientType.TO, to);
				}
			}
		}
		return toAddresses;
	}
	
	/**
	 * 验证基本的Mail信息
	 *
	 * @date	2016年9月19日 下午4:06:01
	 * @param mail
	 * @throws ValidationException
	 * @return	void
	 */
	private void validateOfMail(Mail mail) throws ValidationException{
		if(StringUtils.isEmpty(mail.getFrom())){
			throw new ValidationException("发件人，不能为空");
		}
		if(ArrayUtils.isEmpty(mail.getTo()) && ArrayUtils.isEmpty(mail.getCc())
				&& ArrayUtils.isEmpty(mail.getBcc()) && StringUtils.isBlank(mail.getReplyTo())){
			throw new ValidationException("收件人，不能为空");
		}
		if(StringUtils.isBlank(mail.getSubject())){
			throw new ValidationException("邮件主题，不能为空");
		}
	}
	
	/**
	 * 邮件信息实体Bean
	 * @date: 2016年9月18日 下午2:47:33
	 *
	 * @version 1.0
	 * @since JDK 1.6
	 */
	public static class Mail implements Serializable {
		/**发件人*/
		private String from;
		
		/**收件人列表*/
		private String[] to;
		
		/**抄送人列表*/
		private String[] cc;

		/**密送人列表*/
		private String[] bcc;
		
		/**收件人（被回复人）*/
		private String replyTo;
		
		/**邮件标题*/
		private String subject;
		
		/**邮件内容*/
		private String content;
		
		/**邮件内容是否网页格式:默认普通文本*/
		private boolean isHtmlCtt = false;
		
		/**发件人别名*/
		private String fromNickname;
		
		/**接收人别名*/
		private Map<String, String> toNicknames;
		
		/**邮件内容:内嵌资源路径映射(Map:[cid:url])*/
		private Map<String, String> nestedSources;
		
		/**邮件附件List*/
		private String[] attachments;

		public Mail() {
			super();
		}

		public String getFrom() {
			return from;
		}

		/**
		 * SET: 发件人
		 */
		public void setFrom(String from) {
			this.from = from;
		}

		public String[] getTo() {
			return to;
		}

		/**
		 * SET: 收件人列表
		 */
		public void setTo(String[] to) {
			this.to = to;
		}

		public String getReplyTo() {
			return replyTo;
		}

		/**
		 * SET: 被回复人
		 */
		public void setReplyTo(String replyTo) {
			this.replyTo = replyTo;
		}

		public String[] getCc() {
			return cc;
		}

		/**
		 * SET: 抄送人列表
		 */
		public void setCc(String[] cc) {
			this.cc = cc;
		}

		public String[] getBcc() {
			return bcc;
		}

		/**
		 * SET: 密送人列表
		 */
		public void setBcc(String[] bcc) {
			this.bcc = bcc;
		}

		public String getSubject() {
			return subject;
		}

		/**
		 * SET: 邮件标题
		 */
		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getContent() {
			return content;
		}

		/**
		 * SET: 邮件内容
		 */
		public void setContent(String content) {
			this.content = content;
		}

		public boolean isHtmlCtt() {
			return isHtmlCtt;
		}

		/**
		 * SET: 邮件内容是否网页格（默认普通文本）
		 */
		public void setHtmlCtt(boolean isHtmlCtt) {
			this.isHtmlCtt = isHtmlCtt;
		}

		public String getFromNickname() {
			return fromNickname;
		}

		/**
		 * SET: 发件人别名
		 */
		public void setFromNickname(String fromNickname) {
			this.fromNickname = fromNickname;
		}

		public Map<String, String> getToNicknames() {
			return toNicknames;
		}

		/**
		 * SET: 接收人别名
		 */
		public void setToNicknames(Map<String, String> toNicknames) {
			this.toNicknames = toNicknames;
		}

		public Map<String, String> getNestedSources() {
			return nestedSources;
		}

		/**
		 * SET: 邮件内容的内嵌资源路径映射(Map:[cid:url])
		 */
		public void setNestedSources(Map<String, String> nestedSources) {
			this.nestedSources = nestedSources;
		}

		public String[] getAttachments() {
			return attachments;
		}

		/**
		 * SET: 邮件附件列表
		 */
		public void setAttachments(String[] attachments) {
			this.attachments = attachments;
		}
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}