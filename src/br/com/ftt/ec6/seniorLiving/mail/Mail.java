package br.com.ftt.ec6.seniorLiving.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.ftt.ec6.seniorLiving.config.ProjectProperties;

public class Mail {
	
	private Properties getPropertiesToEmail() {
		return ProjectProperties.getInstance().getProperties();
	}
	
	private Session openEmailSession() {
		Session session = Session.getDefaultInstance(getPropertiesToEmail(),
	    	      new javax.mail.Authenticator() {
	    	           protected PasswordAuthentication getPasswordAuthentication()
	    	           {
	    	                 return new PasswordAuthentication( ProjectProperties.getInstance().getMailSmtpUsername(), ProjectProperties.getInstance().getMailSmtpPassword());
	    	           }
		});
		return session;
	}
	
	public void sendEmail(String from, String to, String subject, String content) {
		Session session = openEmailSession();
		
		try {

  	      Message message = new MimeMessage(session);
  	      message.setFrom(new InternetAddress(from));

  	      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

  	      message.setSubject(subject);
  	      message.setContent(content, "text/html");
  	      
  	      Transport.send(message);

  	      System.out.println("Email sent to:"+to);

  	     } catch (MessagingException e) {
  	        throw new RuntimeException(e);
  	     }
	}
    
}
