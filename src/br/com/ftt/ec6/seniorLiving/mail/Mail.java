package br.com.ftt.ec6.seniorLiving.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.ftt.ec6.seniorLiving.config.ProjectProperties;

public class Mail {

	

	private Session openEmailSession() {
		
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.googlemail.com");
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.port", "465");
	    props.put("mail.smtp.ssl", "false");
	    props.put("mail.smtp.starttls.enable", true);  
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.fallback", "false");  
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	   
	    
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ProjectProperties.getInstance().getMailSmtpUsername(),
						ProjectProperties.getInstance().getMailSmtpPassword());
			}
		});
		return session;
	}

	public void sendEmail(String from, String to, String subject, String content) {
		//Session session = openEmailSession();

		try {
			HtmlEmail htmlEmail = new HtmlEmail();
			htmlEmail.setHtmlMsg(content);
			htmlEmail.addTo(to);
			htmlEmail.setSubject(subject);
			htmlEmail.setFrom(from);
			htmlEmail.setHostName("smtp.gmail.com");
			htmlEmail.setSmtpPort(587);
			htmlEmail.setTLS(true);          
			htmlEmail.setAuthenticator(new DefaultAuthenticator(ProjectProperties.getInstance().getMailSmtpUsername(), ProjectProperties.getInstance().getMailSmtpPassword()));            
			htmlEmail.send();
			
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(from));
//
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//			message.setSubject(subject);
//			message.setContent(content, "text/html");
//
//			Transport.send(message);
//
//			System.out.println("Email sent to:" + to);

		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
