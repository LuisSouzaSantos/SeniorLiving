package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.security.auth.login.LoginException;

import br.com.SeniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.User;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Login.fxml";
	
	@FXML
	private TextField txtEmail;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btLogar;
	@FXML
	private Button btFechar;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	public void performLogin() throws LoginException, IOException {
		try {
			if(txtEmail == null || txtEmail.getText().trim().isEmpty()) { throw new Exception("Email não pode estar em branco"); } 
			
			if(txtPassword == null || txtPassword.getText().trim().isEmpty()) { throw new Exception("Senha não pode estar em branco"); } 
			
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
			
			Stage newStage = new Stage();
			newStage.setScene(futureScene);
			
			Main.changeStage(newStage);
			Main.getCurrentStage().close();
		} catch (Exception e) {
			e.getMessage();
		}
			
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtEmail, 255);
		Constraints.setTextFieldMaxLength(txtPassword, 255);
	}
	
	public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	} 
	

}
