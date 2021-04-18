package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class UserController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/UserList.fxml";

    
    @Override
    public void initialize(URL url, ResourceBundle db) {
    	initializeNodes();
    }

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	private void initializeNodes() {
	}
    
//    private void initializeNodes() {
//		Constraints.setTextFieldInteger(id);
//		Constraints.setTextFieldMaxLength(fullname, 30);
//		Constraints.setTextFieldMaxLength(email, 30);
//		Constraints.setTextFieldMaxLength(password, 30);		
//	}
//    
//    public void setUser(User entity) {
//		this.entity = entity;
//	}
//	
//	public void setUserSevice(UserService service) {
//		this.service = service;
//	}
//	
//	public void subscribeDataChangeListener(DataChangeListener listener) {
//		dataChangeListeners.add(listener);
//	}
//	
//    @FXML
//	 public void onBtSaveAction(ActionEvent event) {
//		if (entity == null) {
//			throw new IllegalStateException("Entity was null");
//		}
//		if (service == null) {
//			throw new IllegalStateException("Service was null");
//		}
//		try {
//			entity = getFormData();
//			service.saveOrUpdate(entity);
//			notifyDataChangeListeners();
//			Utils.currentStage(event).close();
//		}
//		catch (ValidationException  e) {
//			setErrorMessages(e.getErrors());
//		}
//		catch (DbException e) {
//			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
//		}
//    }
//    
//    private void notifyDataChangeListeners() {
//		for (DataChangeListener listener : dataChangeListeners) {
//			listener.onDataChanged();
//		}
//    }
//		
//    public void updateFormData() {
//		if (entity == null) {
//			throw new IllegalStateException("Entity was null");
//		}
//		id.setText(String.valueOf(entity.getId()));
//		fullname.setText(entity.getFull_name());
//		email.setText(entity.getEmail_id());
//		password.setText(entity.getPassword());
//	}
//    
//    private void setErrorMessages(Map<String, String> errors) {
//		Set<String> fields = errors.keySet();
//		
//		if (fields.contains("fullname")) {
//			labelErrorName.setText(errors.get("fullname"));
//		}
//		if (fields.contains("email")) {
//			labelErrorEmail.setText(errors.get("email"));
//		}
//		if (fields.contains("password")) {
//			labelErrorPassword.setText(errors.get("password"));
//		}		
//		
//	}
//    
//    @FXML
//	public void onBtCancelAction(ActionEvent event) {
//		Utils.currentStage(event).close();
//	}
//    
//    private User getFormData() {
//    	User obj = new User();
//		
//		ValidationException exception = new ValidationException("Validation error");
//		
//		obj.setId(Utils.tryParseToInt(id.getText()));
//		
//		if(fullname.getText() == null || fullname.getText().trim().contentEquals("")) {
//			exception.addError("fullname", "Field can't be empty");
//		}
//		obj.setFull_name(fullname.getText());
//		
//		if(email.getText() == null || email.getText().trim().contentEquals("")) {
//			exception.addError("email", "Field can't be empty");
//		}
//		obj.setEmail_id(email.getText());
//		
//		if(password.getText() == null || password.getText().trim().contentEquals("")) {
//			exception.addError("password", "Field can't be empty");
//		}
//		obj.setPassword(password.getText());
//		
//		if (exception.getErrors().size() > 0) {
//			throw exception;
//		}
//		
//		return obj;
//	}   
}
