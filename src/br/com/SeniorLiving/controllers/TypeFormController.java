package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.service.TypeService;
import br.com.ftt.ec6.seniorLiving.service.impl.TypeServiceImpl;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TypeFormController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/TypeForm.fxml";
	private final TypeService typeService = TypeServiceImpl.getInstance();
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_CREATE = {"NAME"};
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_UPDATE = {"NAME"};
	
	
	@FXML
	private TextField formTypeNameField;
	
	@FXML
	private Text formTypeErrorNameMessageText;
	@FXML
	private Text formTypeErrorMessageText;
	
	@FXML
	private Button typeFormCreateButton;
	@FXML
	private Button typeFormUpdateButton;
	@FXML
	private Button typeFormCancelButton;
	
	@FXML
	private Pane typeFormProgressIndicator;
	
	private Stage me;
	private boolean isCreatedForm;
	private Type type;
	private TypeController father;
	
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
	private void createNewTypeButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_CREATE)) == false) { return; }
		
		String name = formTypeNameField.getText();
		RestHome restHome = getRestHomeActived();
			
		Task<Type> newTypeTask = createNewTypeTask(name, restHome);
			
		new Thread(newTypeTask).start();
	}
	
	private Task<Type> createNewTypeTask(String name, RestHome restHome){
		Task<Type> task = new Task<Type>() {
			@Override
			protected Type call() throws Exception {
				initProgressIndicator();
				return typeService.save(name, restHome);
			}
		};
		
		task.setOnSucceeded(e -> {
			Type typeCreated = task.getValue();
			closeMe();
			father.addNewTypeOnTable(typeCreated);
			stopProgressIndicator();
			Thread.interrupted();
		});
		
		task.setOnFailed(e -> {
			if(task != null && task.getException() != null && task.getException().getMessage() != null) {
				formTypeErrorMessageText.setText(task.getException().getMessage());
			}
			stopProgressIndicator();
			Thread.interrupted();
		});
		
		return task;
	}
	
	@FXML
	private void updateTypeButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_UPDATE)) == false) { return; }
			
		String name = formTypeNameField.getText();
			
		Task<Type> typeTask = updateAccommodationTask(name);
			
		new Thread(typeTask).start();
	}
	
	private Task<Type> updateAccommodationTask(String name) {
		Task<Type> task = new Task<Type>() {
			@Override
			protected Type call() throws Exception {
				initProgressIndicator();
				
				type.setName(name);
				
				return typeService.update(type);
			}
		};
		
		task.setOnSucceeded(e -> {
			Type typeUpdated = task.getValue();
			closeMe();
			father.updateTypeOnTable(typeUpdated);
			stopProgressIndicator();
			Thread.interrupted();
		});
		
		task.setOnFailed(e -> {
			if(task != null && task.getException() != null && task.getException().getMessage() != null) {
				formTypeErrorMessageText.setText(task.getException().getMessage());
			}
			stopProgressIndicator();
			Thread.interrupted();
		});
		
		return task;
	}
	
	
	@FXML
	private void closeTypeFormButtonAction() {
		clearForm();
		closeMe();
	}
	
	private void initializeNodes(){
		cleanErrorMessages();
		clearForm();
		buttonFormAvailable();
		checkIfIsUpdateForm();
	}
	
	private void clearForm() {
		formTypeNameField.setText("");
	}
	
	private void cleanErrorMessages() {
		formTypeErrorNameMessageText.setText("");
		formTypeErrorMessageText.setText("");
	}
	
	private boolean isValidFields(List<String> fieldsToBeValidate){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields(fieldsToBeValidate);
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("NAME")) {
				formTypeErrorNameMessageText.setText(value);
			}

		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields(List<String> fieldsToBeValidate) {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if((fieldsToBeValidate.contains("NAME")) && (formTypeNameField == null || formTypeNameField.getText().trim().isEmpty())) { 
			errorsList.put("NAME", "Nome não pode estar em branco.");
		} 
		
		return errorsList;
	}
	
	private void buttonFormAvailable() {
		if(this.isCreatedForm) {
			this.typeFormCreateButton.setVisible(true);
			this.typeFormUpdateButton.setVisible(false);
		}else {
			this.typeFormCreateButton.setVisible(false);
			this.typeFormUpdateButton.setVisible(true);
		}
	}
	
	private void checkIfIsUpdateForm() {
		if(this.isCreatedForm) { return; }
		
		if(this.type == null) { return; }
		
		formTypeNameField.setText(this.type.getName());
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public TypeController getFather() {
		return father;
	}

	public void setFather(TypeController father) {
		this.father = father;
	}
	
	private void initProgressIndicator() {
		typeFormProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		typeFormProgressIndicator.setVisible(false);
	}

}
