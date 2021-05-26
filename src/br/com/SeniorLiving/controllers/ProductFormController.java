package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.service.ProductService;
import br.com.ftt.ec6.seniorLiving.service.impl.ProductServiceImpl;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProductFormController extends Controller implements Initializable {
	
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/ProductForm.fxml";
	private final ProductService productService = ProductServiceImpl.getInstance();
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_CREATE = {"NAME", "DESCRIPTION"};
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_UPDATE = {"NAME", "DESCRIPTION"};
	
	
	@FXML
	private TextField formProductNameField;
	@FXML
	private TextArea formProductDescriptionFieldArea;
	
	@FXML
	private Text formProductErrorNameMessageText;
	@FXML
	private Text formProductErrorDescriptionMessageText;
	@FXML
	private Text formProductErrorMessageText;
	
	@FXML
	private Button productFormCreateButton;
	@FXML
	private Button productFormUpdateButton;
	@FXML
	private Button productFormCancelButton;
	
	@FXML
	private Pane productFormProgressIndicator;
	
	private Stage me;
	private boolean isCreatedForm;
	private Product product;
	private ProductController father;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	public void performReload() {
		initializeNodes();
	}
	
	@FXML
	private void createNewUserButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_CREATE)) == false) { return; }
			
		String name = formProductNameField.getText();
		String description = formProductDescriptionFieldArea.getText();
			
		Task<Product> productTask = createNewProductTask(name, description, getRestHomeActived());
			
		new Thread(productTask).start();
	}
	
	private Task<Product> createNewProductTask(String name, String description, RestHome restHome){
		Task<Product> task = new Task<Product>() {
			@Override
			protected Product call() throws Exception {
				initProgressIndicator();
				return productService.save(name, description, restHome);
			}
		};
		
		task.setOnSucceeded(e -> {
			Product productCreated = task.getValue();
			closeMe();
			father.addNewProductOnTable(productCreated);
			stopProgressIndicator();
		});
		
		task.setOnFailed(e -> {
			if(task != null && task.getException() != null && task.getException().getMessage() != null) {
				formProductErrorMessageText.setText(task.getException().getMessage());
			}
			stopProgressIndicator();
		});
		
		return task;
	}
	
	@FXML
	private void updateProductButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_UPDATE)) == false) { return; }
			
		String name = formProductNameField.getText();
		String description = formProductDescriptionFieldArea.getText();
			
		Task<Product> productTask = updateProductTask(name, description);
		
		new Thread(productTask).start();
	}
	
	private Task<Product> updateProductTask(String name, String description){
		Task<Product> task = new Task<Product>() {
			@Override
			protected Product call() throws Exception {
				initProgressIndicator();
				
				product.setName(name);
				product.setDescription(description);
				
				return productService.update(product);
			}
		};
		
		task.setOnSucceeded(e -> {
			Product productUpdated = task.getValue();
			closeMe();
			father.updateProductOnTable(productUpdated);
			stopProgressIndicator();
		});
		
		task.setOnFailed(e -> {
			if(task != null && task.getException() != null && task.getException().getMessage() != null) {
				formProductErrorMessageText.setText(task.getException().getMessage());
			}
			stopProgressIndicator();
		});
		
		return task;
	}
	
	@FXML
	private void closeProductFormButtonAction() {
		closeMe();
	}
	
	private void initializeNodes(){
		cleanErrorMessages();
		clearForm();
		buttonFormAvailable();
		checkIfIsUpdateForm();
	}
	
	private void clearForm() {
		formProductNameField.setText("");
		formProductDescriptionFieldArea.setText("");
	}
	
	private void cleanErrorMessages() {
		formProductErrorNameMessageText.setText("");
		formProductErrorDescriptionMessageText.setText("");
		formProductErrorMessageText.setText("");
	}
	
	private boolean isValidFields(List<String> fieldsToBeValidate){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields(fieldsToBeValidate);
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("NAME")) {
				formProductErrorNameMessageText.setText(value);
			}
			
			if(key.equals("DESCRIPTION")) {
				formProductErrorDescriptionMessageText.setText(value);
			}

		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields(List<String> fieldsToBeValidate) {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if((fieldsToBeValidate.contains("NAME")) && (formProductNameField == null || formProductNameField.getText().trim().isEmpty())) { 
			errorsList.put("NAME", "Nome não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("DESCRIPTION")) && (formProductDescriptionFieldArea == null || formProductDescriptionFieldArea.getText().trim().isEmpty())) { 
			errorsList.put("DESCRIPTION", "Descrição não pode estar em branco.");
		}
		
		return errorsList;
	}
	
	private void buttonFormAvailable() {
		if(this.isCreatedForm) {
			this.productFormCreateButton.setVisible(true);
			this.productFormUpdateButton.setVisible(false);
		}else {
			this.productFormCreateButton.setVisible(false);
			this.productFormUpdateButton.setVisible(true);
		}
	}
	
	private void checkIfIsUpdateForm() {
		if(this.isCreatedForm) { return; }
		
		if(this.product == null) { return; }
		
		formProductNameField.setText(this.product.getName());
		formProductDescriptionFieldArea.setText(this.product.getDescription());
	}
	
	private void closeMe() {
		this.clearForm();
		this.me.close();
	}
	
	
	public Stage getMe() {
		return me;
	}
	public void setMe(Stage me) {
		this.me = me;
	}
	public boolean isCreatedForm() {
		return isCreatedForm;
	}
	public void setCreatedForm(boolean isCreatedForm) {
		this.isCreatedForm = isCreatedForm;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public ProductController getFather() {
		return father;
	}
	public void setFather(ProductController father) {
		this.father = father;
	}
	
	private void initProgressIndicator() {
		productFormProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		productFormProgressIndicator.setVisible(false);
	}
		
}
