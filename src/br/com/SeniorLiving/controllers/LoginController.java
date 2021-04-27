package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.security.auth.login.LoginException;

import br.com.ftt.ec6.seniorLiving.entities.Action;
import br.com.ftt.ec6.seniorLiving.entities.Audit;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.AuditService;
import br.com.ftt.ec6.seniorLiving.service.impl.AuditServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.LoginServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Login.fxml";
	private final static AuditService audit = AuditServiceImpl.getInstance();
	
	@FXML
	private AnchorPane loginContainerAnchorPane;

	@FXML
	private TextField txtEmail;
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button btLogar;
	@FXML
	private Button btFechar;
	
	@FXML
	private Text loginVersionText;
	@FXML 
	private Text loginErrorEmailMessageText;
	@FXML
	private Text loginErrorPasswordMessageText;
	@FXML
	private Text loginErrorSignInMessageText;
	
	@FXML
	private Pane loginSpinner;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void performLogin() throws LoginException {
		performAudit(LoginAudit.PERFORM_LOGIN);
		try {
			if(validateFields() == false) { return; }
			
			String email = txtEmail.getText();
			String password = txtPassword.getText();
			
			LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
			User userLogged = loginServiceImpl.performLogin(email, password);
			
			setUserLogged(userLogged);
			
			MenuController menuController = new MenuController();
			FXMLLoader loader = menuController.getFXMLLoader();
			AnchorPane anchorPane = loader.load();
			loader.getController();
			Scene futureScene = new Scene(anchorPane);
			
			Stage newStage = Controller.getCurrentStage();		
			newStage.setScene(futureScene);
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
			newStage.getIcons().add(anotherIcon);
			
			Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, false);
		} catch (LoginException e) {
			loginErrorSignInMessageText.setText(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void performLogout() {
		performAudit(LoginAudit.PERFORM_LOGOUT);
		setUserLogged(null);
	}
	
	public static void infoBox(String infoMessage){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(infoMessage);
        //alert.setTitle(title);
        //alert.setHeaderText(headerText);
        alert.showAndWait();
    }

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	} 	

	@FXML
	public void pressedAction(KeyEvent ke) throws LoginException, IOException {
		if(ke.getCode().equals(KeyCode.ENTER)){
			performLogin();
		}
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtEmail, 255);
		Constraints.setTextFieldMaxLength(txtPassword, 255);
		initText();
	}
	
	private void initText() {
		loginVersionText.setText(Controller.VERSION);
		cleanErrorsMessages();
	}
	
	private void cleanErrorsMessages() {
		loginErrorEmailMessageText.setText("");
		loginErrorPasswordMessageText.setText("");
		loginErrorSignInMessageText.setText("");
	}
	
	private boolean validateFields() throws LoginException {
		cleanErrorsMessages();
		
		Map<String, String> fieldsAndMessages = checkFields();
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("EMAIL")) {
				loginErrorEmailMessageText.setText(value);
			}
			
			if(key.equals("PASSWORD")) {
				loginErrorPasswordMessageText.setText(value);
			}
		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields() {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if(txtEmail == null || txtEmail.getText().trim().isEmpty()) { 
			errorsList.put("EMAIL", "Email n�o pode estar em branco");
		} 
		
		if(txtPassword == null || txtPassword.getText().trim().isEmpty()) { 
			errorsList.put("PASSWORD", "Senha n�o pode estar em branco");
		} 
		
		return errorsList;
	}
		
	private void performAudit(LoginAudit loginAudit) {
		switch (loginAudit) {
		case PERFORM_LOGIN:
			audit.addToWaitingAuditList(new Audit(System.currentTimeMillis(), "performLogin()", Action.LOGIN, null, null));
			break;
		case PERFORM_LOGOUT:
			audit.addToWaitingAuditList(new Audit(System.currentTimeMillis(), "performLogout()", Action.LOGOUT, getUserLogged().getEmail(), getRoleActived().getName()));
			break;
		default:
			break;
		}
	}
	
	private enum LoginAudit{
		PERFORM_LOGIN,
		PERFORM_LOGOUT
	}
}
