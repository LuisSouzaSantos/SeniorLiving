package br.com.ftt.ec6.seniorLiving.entities.support;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.BillingProduct;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class BillingProductForm {

	private Long id;
	private Billing billing;
	private ComboBox<Product> comboBoxProduct;
	private String quantity;
	private String unitaryValue;
	private String amount;
	private TableView<BillingProductForm> billingProductTable;
	private BillingProduct currentBillingProductInDatabase;
	
	private Integer quantidadeIntegerMode;
	private Double unitaryValueDoubleMode;
	private Double amountBigDoubleMode;
	
	private final static String UNITARY_VALUE_MATCHER = "((^R\\$)(\\d{1,})(\\.)(\\d{1,})$)";
	private final static String QUANTITY_VALUE_MACHER = "(^\\d{0,}$)";
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	public BillingProduct getCurrentBillingProductInDatabase() {
		return currentBillingProductInDatabase;
	}

	public void setCurrentBillingProductInDatabase(BillingProduct currentBillingProductInDatabase) {
		this.currentBillingProductInDatabase = currentBillingProductInDatabase;
	}

	public boolean isUnitaryValueValid() {
		if(this.getUnitaryValue() == null) { return false;}
		
		return this.getUnitaryValue().matches(UNITARY_VALUE_MATCHER);
	}
	
	public boolean isQuantityValueValid() {
		if(this.getQuantity() == null) { return false;}
		
		return this.getQuantity().matches(QUANTITY_VALUE_MACHER);
	}
	
	public Integer getQuantidadeIntegerMode() {
		return quantidadeIntegerMode;
	}

	public void setQuantidadeIntegerMode(Integer quantidadeIntegerMode) {
		this.quantidadeIntegerMode = quantidadeIntegerMode;
	}

	public Double getUnitaryValueDoubleMode() {
		return unitaryValueDoubleMode;
	}

	public void setUnitaryValueDoubleMode(Double unitaryValueDoubleMode) {
		this.unitaryValueDoubleMode = unitaryValueDoubleMode;
	}

	public Double getAmountBigDoubleMode() {
		return amountBigDoubleMode;
	}

	public void setAmountBigDoubleMode(Double amountBigDoubleMode) {
		this.amountBigDoubleMode = amountBigDoubleMode;
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
			amountBigDoubleMode =  quantidadeIntegerMode * unitaryValueDoubleMode;
			setAmount("R$"+amountBigDoubleMode);
		}catch(NumberFormatException e) { 
			
		}
		
	}
	
}
