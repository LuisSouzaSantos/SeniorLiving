package br.com.SeniorLiving.controllers;

import java.awt.Button;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.exception.UserFormException;
import br.com.ftt.ec6.seniorLiving.service.RoleService;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.service.impl.RoleServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserFormController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/UserForm.fxml";
	private final UserService userService =  UserServiceImpl.getInstance();
	private final RoleService roleService = RoleServiceImpl.getInstance();
	
	@FXML
	private TableView<User> userTable;
	
	@FXML
	private TextField formUserEmailField;
	@FXML
	private TextField formUserNicknameField;
	@FXML
	private TextField formUserPasswordField;
	@FXML
	private TextField formUserPasswordConfirmationField;
	@FXML
	private CheckBox formUserAdminGeralCheckBox;
	@FXML
	private CheckBox formUserAdminLocalCheckBox;
	
	@FXML
	private Text formUserErrorNicknameMessageText;
	@FXML
	private Text formUserErrorPasswordMessageText;
	@FXML
	private Text formUserErrorPasswordConfirmationMessageText;
	@FXML
	private Text formUserErrorEmailMessageText;
	@FXML
	private Text formUserErrorRoleMessageText;
	@FXML
	private Text formUserErrorMessageText;
	
	@FXML
	private Button newUserButton;
	
	private Stage me;

    public UserFormController() {
    }
	
    @Override
    public void initialize(URL url, ResourceBundle db) {
    	initializeNodes();
    }
    
	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	private void initializeNodes() {
		cleanErrorMessages();
	}
	
	private void cleanErrorMessages() {
		formUserErrorNicknameMessageText.setText("");
		formUserErrorPasswordMessageText.setText("");
		formUserErrorPasswordConfirmationMessageText.setText("");
		formUserErrorEmailMessageText.setText("");
		formUserErrorRoleMessageText.setText("");
		formUserErrorMessageText.setText("");
	}
	
	@FXML
	private void createNewUserButtonAction() {
		try {
			if(isValidFields() == false) { return; }
			
			String[] roleNameArray = new String[2]; 
			
			if(formUserAdminGeralCheckBox.isSelected()) {
				roleNameArray[0] = "ADMIN_GERAL";
			}
			
			if(formUserAdminLocalCheckBox.isSelected()) {
				roleNameArray[1] = "ADMIN_LOCAL";
			}
			
			List<Role> roleList =  roleService.findRoleListByRoleNameArray(roleNameArray);
			
			userService.save(formUserEmailField.getText(), formUserNicknameField.getText(), formUserPasswordField.getText(), formUserPasswordConfirmationField.getText(), roleList);
			clearForm();
		}catch(UserException ex) {
			formUserErrorMessageText.setText(ex.getMessage());
		}
	}
	
	@FXML
	private void closeUserFormButtonAction() {
		clearForm();
		closeMe();
	}
	
	private boolean isValidFields(){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields();
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("EMAIL")) {
				formUserErrorEmailMessageText.setText(value);
			}
			
			if(key.equals("NICKNAME")) {
				formUserErrorNicknameMessageText.setText(value);
			}
			
			if(key.equals("PASSWORD")) {
				formUserErrorPasswordMessageText.setText(value);
			}
			
			if(key.equals("PASSWORD_CONFIRMATION")) {
				formUserErrorPasswordConfirmationMessageText.setText(value);
			}
			
			if(key.equals("ROLE")) {
				formUserErrorRoleMessageText.setText(value);
			}
		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields() {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if(formUserEmailField == null || formUserEmailField.getText().trim().isEmpty()) { 
			errorsList.put("EMAIL", "Email não pode estar em branco.");
		} 
		
		if(formUserNicknameField == null || formUserNicknameField.getText().trim().isEmpty()) { 
			errorsList.put("NICKNAME", "Apelido não pode estar em branco.");
		}
		
		if(formUserPasswordField == null || formUserPasswordField.getText().trim().isEmpty()) { 
			errorsList.put("PASSWORD", "Senha não pode estar em branco.");
		}
		
		if(formUserPasswordConfirmationField == null || formUserPasswordConfirmationField.getText().trim().isEmpty()) { 
			errorsList.put("PASSWORD_CONFIRMATION", "Confirmação da senha não pode estar em branco.");
		}
		
		if((formUserAdminGeralCheckBox.isSelected() == false) && (formUserAdminLocalCheckBox.isSelected() == false)) {
			errorsList.put("ROLE", "Ao menos uma role deve ser selecionada.");
		}
		
		return errorsList;
	}
	
	private void clearForm() {
		formUserEmailField.setText("");
		formUserNicknameField.setText("");
		formUserPasswordField.setText("");
		formUserPasswordConfirmationField.setText("");
		formUserAdminGeralCheckBox.disarm();
		formUserAdminLocalCheckBox.disarm();
	}
	
	private void closeMe() {
		this.me.close();
	}
	
	public void setStageMe(Stage me) {
		this.me = me;
	}
	
}
