package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.security.auth.login.LoginException;

import br.com.SeniorLiving.application.Main;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private TextField textEmail;
	@FXML
	private PasswordField textPassword;
	@FXML
	private Button btLogar;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	public void performLogin() throws LoginException, IOException {
		String email = textEmail.getText();
		String password = textPassword.getText();
		
		LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
		loginServiceImpl.performLogin(email, password);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/SeniorLiving/gui/MainView.fxml"));
		ScrollPane scrollPane = loader.load();
		MainViewController mainViewController = loader.getController();
		Scene futureScene = new Scene(scrollPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(textEmail, 255);
		Constraints.setTextFieldMaxLength(textPassword, 255);
	}
	
	public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    } 
	

}
