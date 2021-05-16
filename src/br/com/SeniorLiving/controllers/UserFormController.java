package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.service.RoleService;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.service.impl.RoleServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.ServiceProxy;
import br.com.ftt.ec6.seniorLiving.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserFormController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/UserForm.fxml";
	private final UserService userService =  (UserService) ServiceProxy.newInstance(UserServiceImpl.getInstance());
	private final RoleService roleService = (RoleService) ServiceProxy.newInstance(RoleServiceImpl.getInstance());
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_CREATE = {"EMAIL", "NICKNAME", "PASSWORD", "PASSWORD_CONFIRMATION", "ROLE"};
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_UPDATE = {"EMAIL", "NICKNAME", "ROLE"};
	
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
	private Button userFormCreateButton;
	@FXML
	private Button userFormUpdateButton;
	
	private Stage me;
	private boolean isCreatedForm;
	private User user;
	private UserController father;
	
    @Override
    public void initialize(URL url, ResourceBundle db) {
    	initializeNodes();
    }
    
	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	public void performReload() {
		initializeNodes();
	}
	
	private void initializeNodes() {
		cleanErrorMessages();
		clearForm();
		buttonFormAvailable();
		checkIfIsUpdateForm();
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
			if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_CREATE)) == false) { return; }
			
			String[] roleNameArray = new String[2]; 
			
			if(formUserAdminGeralCheckBox.isSelected()) {
				roleNameArray[0] = "ADMIN_GERAL";
			}
			
			if(formUserAdminLocalCheckBox.isSelected()) {
				roleNameArray[1] = "ADMIN_LOCAL";
			}
			
			List<Role> roleList =  roleService.findRoleListByRoleNameArray(roleNameArray);
			
			User user = userService.save(formUserEmailField.getText(), formUserNicknameField.getText(), formUserPasswordField.getText(), formUserPasswordConfirmationField.getText(), roleList);
			closeMe();
			father.addNewUserOnTable(user);
		}catch(UserException ex) {
			formUserErrorMessageText.setText(ex.getMessage());
		}
	}
	
	@FXML
	private void updateUserButtonAction() {
			
		try {
			if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_UPDATE)) == false) { return; }
			
			String[] roleNameArray = new String[2]; 
			
			if(formUserAdminGeralCheckBox.isSelected()) {
				roleNameArray[0] = "ADMIN_GERAL";
			}
			
			if(formUserAdminLocalCheckBox.isSelected()) {
				roleNameArray[1] = "ADMIN_LOCAL";
			}
			
			List<Role> roleList =  roleService.findRoleListByRoleNameArray(roleNameArray);
			this.user.getRoleList().clear();
			
			this.user.setEmail(formUserEmailField.getText());
			this.user.setNickname(formUserNicknameField.getText());
			this.user.setRoleList(roleList);
			
			userService.update(this.user);
			closeMe();
			father.reloadMe();
		}catch(UserException ex) {
			formUserErrorMessageText.setText(ex.getMessage());
		}
	}
	
	@FXML
	private void closeUserFormButtonAction() {
		clearForm();
		closeMe();
	}
	
	private boolean isValidFields(List<String> fieldsToBeValidate){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields(fieldsToBeValidate);
		
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
	
	private Map<String, String> checkFields(List<String> fieldsToBeValidate) {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if((fieldsToBeValidate.contains("EMAIL")) && (formUserEmailField == null || formUserEmailField.getText().trim().isEmpty())) { 
			errorsList.put("EMAIL", "Email n�o pode estar em branco.");
		} 
		
		if((fieldsToBeValidate.contains("NICKNAME")) && (formUserNicknameField == null || formUserNicknameField.getText().trim().isEmpty())) { 
			errorsList.put("NICKNAME", "Apelido n�o pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("PASSWORD")) && ((formUserPasswordField == null) || (formUserPasswordField.getText().trim().isEmpty()))) { 
			errorsList.put("PASSWORD", "Senha n�o pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("PASSWORD_CONFIRMATION")) && ((formUserPasswordConfirmationField == null) || formUserPasswordConfirmationField.getText().trim().isEmpty())) { 
			errorsList.put("PASSWORD_CONFIRMATION", "Confirma��o da senha n�o pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("ROLE")) && ((formUserAdminGeralCheckBox.isSelected() == false) && (formUserAdminLocalCheckBox.isSelected() == false))) {
			errorsList.put("ROLE", "Ao menos uma role deve ser selecionada.");
		}
		
		return errorsList;
	}
	
	private void clearForm() {
		formUserEmailField.setText("");
		formUserNicknameField.setText("");
		formUserPasswordField.setText("");
		formUserPasswordConfirmationField.setText("");
		formUserAdminGeralCheckBox.setSelected(false);;
		formUserAdminLocalCheckBox.setSelected(false);
	}
	
	private void buttonFormAvailable() {
		if(this.isCreatedForm) {
			this.userFormCreateButton.setVisible(true);
			this.userFormUpdateButton.setVisible(false);
		}else {
			this.userFormCreateButton.setVisible(false);
			this.userFormUpdateButton.setVisible(true);
		}
	}
	
	private void checkIfIsUpdateForm() {
		if(this.isCreatedForm) { return; }
		
		if(this.user == null) { return; }
		
		formUserEmailField.setText(this.user.getEmail());
		formUserNicknameField.setText(this.user.getNickname());
		formUserPasswordField.setDisable(true);
		formUserPasswordConfirmationField.setDisable(true);
		
		if(this.user.hasRole("ADMIN_GERAL")) {
			formUserAdminGeralCheckBox.setSelected(true);
		}
		
		if(this.user.hasRole("ADMIN_LOCAL")) {
			formUserAdminLocalCheckBox.setSelected(true);
		}
		
	}
	
	private void closeMe() {
		this.clearForm();
		this.me.close();
	}
	
	public void setStageMe(Stage me) {
		this.me = me;
	}

	public boolean isCreatedForm() {
		return isCreatedForm;
	}

	public void setCreatedForm(boolean isCreatedForm) {
		this.isCreatedForm = isCreatedForm;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserController getFather() {
		return father;
	}

	public void setFather(UserController father) {
		this.father = father;
	}
	
}
