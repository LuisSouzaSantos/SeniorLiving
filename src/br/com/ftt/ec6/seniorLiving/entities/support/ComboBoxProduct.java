package br.com.ftt.ec6.seniorLiving.entities.support;

import br.com.ftt.ec6.seniorLiving.entities.Product;
import javafx.scene.control.ComboBox;

public class ComboBoxProduct extends ComboBox<Product> {

	private BillingProductForm billingProductForm;

	public BillingProductForm getBillingProductForm() {
		return billingProductForm;
	}

	public void setBillingProductForm(BillingProductForm billingProductForm) {
		this.billingProductForm = billingProductForm;
	}
	
}
