package br.com.ftt.ec6.seniorLiving.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import br.com.ftt.ec6.seniorLiving.utils.Utils;

public class ProjectProperties {
	
	private static Properties config = new Properties();
	private static final String FILE_PATH = "config.properties";
	
	private static ProjectProperties instance;
	
	private ProjectProperties() {}
	
	public static ProjectProperties getInstance() {
		if(instance == null) {
			instance = new ProjectProperties();
			instance.readProperties();
		}
		return instance;
	}
	
	private void readProperties() {
		try {
			File file = Utils.getFile(FILE_PATH);
			FileInputStream fileInputStream = new FileInputStream(file);
			config.load(fileInputStream);
		}catch(IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public Properties getProperties() {
		return config;
	}

	public String getMailSmtpHost() {
		return config.getProperty("mail.smtp.host") ;
	}

	public String getMailSmtpSocketFactoryPort() {
		return config.getProperty("mail.smtp.socketFactory.port");
	}

	public String getMailSmtpSocketFactoryClass() {
		return config.getProperty("mail.smtp.socketFactory.class");
	}

	public String getMailSmtpAuth() {
		return config.getProperty("mail.smtp.auth");
	}

	public Integer getMailSmtpPort() {
		return Integer.parseInt(config.getProperty("mail.smtp.port"));
	}

	public String getMailSmtpUsername() {
		return config.getProperty("mail.smtp.username");
	}

	public String getMailSmtpPassword() {
		return config.getProperty("mail.smtp.password");
	}

}
