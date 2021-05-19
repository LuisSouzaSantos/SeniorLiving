package br.com.ftt.ec6.seniorLiving.entities.support;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class BillingProductForm {

	private Billing billing;
	private ComboBox<Product> comboBoxProduct;
	private String quantity;
	private String unitaryValue;
	private String amount;
	private TableView<BillingProductForm> billingProductTable;
	
	private Integer quantidadeIntegerMode;
	private Double unitaryValueDoubleMode;
	private Double amountDoubleMode;
	boolean quantityCorrectPatter = false;
	boolean unitaryValueCorrectPatter = false;
	
	private final static String UNITARY_VALUE_MATCHER = "((^R\\$)(\\d{1,})(\\.)(\\d{1,})$)";
	private final static String QUANTITY_VALUE_MACHER = "(^\\d{0,}$)";
	
	public Billing getBilling() {
		return billing;
	}
	
	public void setBilling(Billing billing) {
		this.billing = billing;
	}
	
	public ComboBox<Product> getComboBoxProduct() {
		return comboBoxProduct;
	}
	
	public void setComboBoxProduct(ComboBox<Product> comboBoxProduct) {
		this.comboBoxProduct = comboBoxProduct;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
		checkIfIsPossibleGenerateAmount();
		billingProductTable.refresh();
	}
	
	public String getUnitaryValue() {
		return unitaryValue;
	}
	
	public void setUnitaryValue(String unitaryValue) {
		this.unitaryValue = unitaryValue;
		checkIfIsPossibleGenerateAmount();
		billingProductTable.refresh();
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public TableView<BillingProductForm> getBillingProductTable() {
		return billingProductTable;
	}

	public void setBillingProductTable(TableView<BillingProductForm> billingProductTable) {
		this.billingProductTable = billingProductTable;
	}
	
	public boolean isUnitaryValueValid() {
		return this.getUnitaryValue().matches(UNITARY_VALUE_MATCHER);
	}
	
	public boolean isQuantityValueValid() {
		return this.getQuantity().matches(QUANTITY_VALUE_MACHER);
	}

	public void checkIfIsPossibleGenerateAmount() {
		if(isUnitaryValueValid() == false) {
			this.unitaryValue = "Valor Inválido";
			return;
		}
		
		if(isQuantityValueValid() == false) {
			this.quantity = "Valor Inválido";
			return;
		}
		
		try {
			quantidadeIntegerMode = Integer.parseInt(this.getQuantity().trim());
			unitaryValueDoubleMode = Double.parseDouble(this.getUnitaryValue().replace("R$", "").trim());
			amountDoubleMode =  quantidadeIntegerMode * unitaryValueDoubleMode;
			setAmount("R$"+amountDoubleMode);
		}catch(NumberFormatException e) { 
			
		}
		
	}
	
}
