package com.qunar.interview.qunarQ.q1.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Properties;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;



public class EmailUtil {

	private static Properties config = new Properties();
	private static String hostName;
	private static String authenticationName;
	private static String authenticationPwd;
	private static String sentFrom;
	private static String subject;
	private static String dateformat;

	static {

		ClassLoader loader = EmailUtil.class.getClassLoader();
		InputStream in = loader.getResourceAsStream("." + File.separator
				+ "mailcfg.properties");

		try {
			config.load(in);
			hostName = config.getProperty("hostName");
			authenticationName = config.getProperty("authenticationName");
			authenticationPwd = config.getProperty("authenticationPwd");
			sentFrom = config.getProperty("sentFrom");
			subject = config.getProperty("subject");
			dateformat = config.getProperty("dateformat");

		} catch (IOException e) {
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

	}

	public static void sendEmail(String address, String feedback) {
		try {
		Date a = new Date(System.currentTimeMillis());
		DateFormat fmt = new SimpleDateFormat(dateformat);
		String str = "Hi,\n\n"
			+ "New feeback submited : \n\n"
			+ feedback
			+ "\nDate :  " + fmt.format(a) + "\n\nBest Regards";

			// email sending
			SimpleEmail email = new SimpleEmail();
			email.setHostName(hostName);
			email.setAuthentication(authenticationName, authenticationPwd);
			email.setCharset("UTF-8");
			email.addTo(address);
			email.setFrom(sentFrom);// 必须和Authentication使用的用户相同，否则失败
			email.setSubject(subject);
			email.setMsg(str);
			email.send();
			
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
