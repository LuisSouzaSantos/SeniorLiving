package gui;

import java.net.URL;
import java.util.ResourceBundle;
import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.User;
import model.exceptions.ValidationException;
import model.services.UserService;

public class SignUPController implements Initializable {


	private User entity;
	
	private UserService service;

	@FXML
    private TextField id;
	
	@FXML
    private TextField fullname;

    @FXML
    private PasswordField password;

    @FXML
    private TextField email;


    @FXML
    private Button signup;

    @FXML
    private Button login;
    
    @Override
    public void initialize(URL url, ResourceBundle db) {
		initializeNodes();
    }
    private void initializeNodes() {
		Constraints.setTextFieldInteger(id);
		Constraints.setTextFieldMaxLength(fullname, 30);
		
	}
    
    public void setUser(User entity) {
		this.entity = entity;
	}
	
	public void setUserSevice(UserService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		//dataChangeListeners.add(listener);
	}
	
    @FXML
	 public void save(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.save(entity);
			//notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (ValidationException  e) {
			//setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
    }
    
    public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		id.setText(String.valueOf(entity.getId()));
		fullname.setText(entity.getFull_name());
	}
    
    private User getFormData() {
    	User obj = new User();
		
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setId(Utils.tryParseToInt(id.getText()));
		
		if(fullname.getText() == null || fullname.getText().trim().contentEquals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setFull_name(fullname.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}
	
	    
	 	 
   
}
