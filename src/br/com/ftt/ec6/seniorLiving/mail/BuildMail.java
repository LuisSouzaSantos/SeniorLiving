package br.com.ftt.ec6.seniorLiving.mail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.utils.Utils;

public class BuildMail {
	
	private static final String FILE_PATH_INIT = "Mail/newBillingBody.html";
	private static final String FILE_PATH_END = "Mail/newBillingFooter.html";
	
	private static final String REST_HOME_NAME = "{{REST_HOME_NAME}}";
	private static final String REST_HOME_ADDRESS = "{{REST_HOME_ADDRESS}}";
	private static final String ELDERLY_NAME = "{{ELDERLY_NAME}}";
	private static final String ELDERLY_AGE = "{{ELDERLY_AGE}}";
	private static final String ELDERLY_ACCOMMODATION = "{{ELDERLY_ACCOMMODATION}}";
	private static final String ELDERLY_MONTHLY_PAYMENT = "{{ELDERLY_MONTHLY_PAYMENT}}";
	private static final String BILLING_DATE = "{{BILLING_DATE}}";
	private static final String BILLING_DATE_DUE = "{{BILLING_DATE_DUE}}";
	
	private static final String PRODUCT_ITEM_STRUCTURE = "<tr>"
			+ "                                <td class=\"no\">{{PRODUCT_NUMBER}}</td>\r\n"
			+ "                                <td class=\"desc\">{{PRODUCT_NAME}}</td>\r\n"
			+ "                                <td class=\"unit\">{{PRODUCT_UNITARY_VALUE}}</td>\r\n"
			+ "                                <td class=\"qty\">{{PRODUCT_QUANTITY}}</td>\r\n"
			+ "                                <td class=\"total\">{{PRODUCT_AMOUNT}}</td>\r\n"
			+ "                            </tr>";

	public void buildElderlyBilling(Billing billing) {
		System.out.println("ENTROU NO BILLING");
		try {
			File file = Utils.getFile(FILE_PATH_INIT);
			System.out.println("Encontrou o arquivo");
			List<String> fileLine  = Files.readAllLines(file.toPath());
			String htmlInit = "";
			
			for (String line : fileLine) {
				htmlInit+=line;
			}
			
			
			System.out.println("-------------------");
			System.out.println("Vai buildar o email");
			
			RestHome elderlyResthome = billing.getElderly().getRestHome();
			Accommodation elderlyAccommodation = billing.getElderly().getAccommodation();
			
			String emailFormated = htmlInit.replace(REST_HOME_NAME, elderlyResthome.getSocialReason())
											.replace(REST_HOME_ADDRESS, elderlyResthome.getAddressStreet()+","+elderlyResthome.getAddressNumber()+","+elderlyResthome.getAddressState())
											.replace(ELDERLY_NAME, billing.getElderly().getName())
											.replace(ELDERLY_AGE, billing.getElderly().getBirthDate().format(DateTimeFormatter.ofPattern("dd-MMM-yy")).toString())
											.replace(ELDERLY_ACCOMMODATION, elderlyAccommodation.getName())
											.replace(ELDERLY_MONTHLY_PAYMENT, "R$"+billing.getElderly().getMonthlyPayment())
											.replace(BILLING_DATE, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy")).toString())
											.replace(BILLING_DATE_DUE, billing.getMonth().format(DateTimeFormatter.ofPattern("dd-MMM-yy")).toString());
			
			
			System.out.println("Email foi buildado");
			
			Integer index = 1;
			Double amountProduct = 0.0;
			for (BillingProduct billingProduct : billing.getBillingProductList()) {
				String content = PRODUCT_ITEM_STRUCTURE;
				String contentFormat = content.replace("{{PRODUCT_NUMBER}}", index.toString())
												.replace("{{PRODUCT_NAME}}", billingProduct.getProduct().getName())
												.replace("{{PRODUCT_UNITARY_VALUE}}", "R$"+billingProduct.getUnitaryValue().toString())
												.replace("{{PRODUCT_QUANTITY}}", billingProduct.getQuantity().toString())
												.replace("{{PRODUCT_AMOUNT}}", "R$"+billingProduct.getAmount().toString());
				
				amountProduct+=Double.parseDouble(billingProduct.getAmount().toString());
				index++;				
				emailFormated+=contentFormat;
			}
			
			File fileEnd = Utils.getFile(FILE_PATH_END);
			List<String> fileLineEnd  = Files.readAllLines(fileEnd.toPath());
			String htmlEnd = "";
			
			for (String line : fileLineEnd) {
				htmlEnd+=line;
			}
			
			Double amount = 0.0;
			amount+=Double.parseDouble(billing.getElderly().getMonthlyPayment().toString());
			amount+=amountProduct;
			String htmlEndFormat = htmlEnd.replace("{{ELDERLY_MONTHLY_PAYMENT}}", billing.getElderly().getMonthlyPayment().toString())
											.replace("{{PRODUCTS_ITEM_AMOUNT}}", amountProduct.toString())
											.replace("{{AMOUNT}}", amount.toString());
			
			
			emailFormated+=htmlEndFormat;
			
			System.out.println("Vai enviar o email");
			Mail mail = new Mail();
			mail.sendEmail("luis345souza@gmail.com", billing.getElderly().getCurator().getEmail(), "Faturamento "+billing.getElderly().getName(), emailFormated);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
