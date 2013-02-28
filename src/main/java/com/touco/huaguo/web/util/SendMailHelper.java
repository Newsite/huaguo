package com.touco.huaguo.web.util;

import javax.mail.*;
import javax.mail.internet.*;

import com.touco.huaguo.common.Constants;
import com.touco.huaguo.common.PropertiesUtil;

import java.util.*;
import java.io.*;



public class SendMailHelper
{
  // defalt config
  private static String SMTP_HOST_NAME = "smtp.163.com";
  private static String SMTP_AUTH_USER = "";
  private static String SMTP_AUTH_PWD  = "";

  private static String emailMsgTxt      = "text";
  private static String emailSubjectTxt  = "Subject";
  private static String emailFromAddress = "redian23@163.com";

  // Add List of Email address to who email needs to be sent to
  private static String[] emailList = {"baozhenyu@touco.cn"};

  public static void main(String args[]) throws Exception
  {
    SendMailHelper smtpMailSender = new SendMailHelper();
    smtpMailSender.postMail( emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
    System.out.println("Sucessfully Sent mail to All Users");
  }
  
  
  
 public SendMailHelper() 
{
	super();
	try 
	{
		Properties mailpropertie = PropertiesUtil.getProperties(Constants.DS_CONFIG_FILE);
		SMTP_AUTH_USER = mailpropertie.getProperty("mailuser");
		SMTP_AUTH_PWD = mailpropertie.getProperty("mailpassword");
		SMTP_HOST_NAME = mailpropertie.getProperty("mailstmp");
		emailFromAddress = SMTP_AUTH_USER;
	} catch (IOException e) {
		e.printStackTrace();
	}
}



public void postMail( String recipients[ ], String subject,
                            String message , String from) throws Exception
  {
    boolean debug = false;

     //Set the host smtp address
    
    
    
    
    
     Properties props = new Properties();
     props.put("mail.smtp.host", SMTP_HOST_NAME);
     props.put("mail.smtp.auth", "true");

    Authenticator auth = new SMTPAuthenticator();
    Session session = Session.getDefaultInstance(props, auth);

    session.setDebug(debug);

    // create a message
    Message msg = new MimeMessage(session);

    // set the from and to address
    InternetAddress addressFrom = new InternetAddress(emailFromAddress);
    msg.setFrom(addressFrom);

    InternetAddress[] addressTo = new InternetAddress[recipients.length];
    for (int i = 0; i < recipients.length; i++)
    {
        addressTo[i] = new InternetAddress(recipients[i]);
    }
    msg.setRecipients(Message.RecipientType.TO, addressTo);


    // Setting the Subject and Content Type
    msg.setSubject(subject);
    msg.setContent(message, "text/html;charset=GB2312");
    Transport.send(msg);
 }


/**
* SimpleAuthenticator is used to do simple authentication
* when the SMTP server requires it.
*/
private class SMTPAuthenticator extends javax.mail.Authenticator
{

    public PasswordAuthentication getPasswordAuthentication()
    {
        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PWD;
        return new PasswordAuthentication(username, password);
    }
}


public static String getSMTP_HOST_NAME() {
    return SMTP_HOST_NAME;
}

public static void setSMTP_HOST_NAME(String smtp_host_name) {
    SMTP_HOST_NAME = smtp_host_name;
}

public static String getSMTP_AUTH_USER() {
    return SMTP_AUTH_USER;
}

public static void setSMTP_AUTH_USER(String smtp_auth_user) {
    SMTP_AUTH_USER = smtp_auth_user;
}

public static String getSMTP_AUTH_PWD() {
    return SMTP_AUTH_PWD;
}

public static void setSMTP_AUTH_PWD(String smtp_auth_pwd) {
    SMTP_AUTH_PWD = smtp_auth_pwd;
}

public static String getEmailMsgTxt() {
    return emailMsgTxt;
}

public static void setEmailMsgTxt(String emailMsgTxt) {
    SendMailHelper.emailMsgTxt = emailMsgTxt;
}

public static String getEmailSubjectTxt() {
    return emailSubjectTxt;
}

public static void setEmailSubjectTxt(String emailSubjectTxt) {
    SendMailHelper.emailSubjectTxt = emailSubjectTxt;
}

public static String getEmailFromAddress() {
    return emailFromAddress;
}

public static void setEmailFromAddress(String emailFromAddress) {
    SendMailHelper.emailFromAddress = emailFromAddress;
}

public static String[] getEmailList() {
    return emailList;
}

public static void setEmailList(String[] emailList) {
    SendMailHelper.emailList = emailList;
}

}