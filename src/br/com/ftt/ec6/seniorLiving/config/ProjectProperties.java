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
			//instance.readProperties();
		}
		return instance;
	}
	
//	private void readProperties() {
//		try {
//			File file = Utils.getFile(FILE_PATH);
//			FileInputStream fileInputStream = new FileInputStream(file);
//			config.load(fileInputStream);
//		}catch(IOException e) {
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//	}
	
	public Properties getProperties() {
		return config;
	}

	public String getMailSmtpHost() {
		return "smtp.gmail.com" ;
	}

	public String getMailSmtpSocketFactoryPort() {
		return "587";
	}

	public String getMailSmtpSocketFactoryClass() {
		return "javax.net.ssl.SSLSocketFactory";
	}

	public String getMailSmtpAuth() {
		return "false";
	}

	public Integer getMailSmtpPort() {
		return 587;
	}

	public String getMailSmtpUsername() {
		return "casaDeRepousoParaIdosos2021@gmail.com";
	}

	public String getMailSmtpPassword() {
		return "sadfghjkl0urtghjkl856";
	}

}
