package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.security.auth.login.LoginException;

import com.jfoenix.controls.JFXSnackbar;

import br.com.SeniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.impl.LoginServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	
	@FXML private AnchorPane rootPane;
	private JFXSnackbar snackbar;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		snackbar = new JFXSnackbar(rootPane);
		//snackbar.show("Carregando...", 5000);
	}
	
	public void performLogin() throws LoginException, IOException {
		try {
			if(txtEmail == null || txtEmail.getText().trim().isEmpty()) { infoBox("Email não pode estar em branco"); } 
			
			if(txtPassword == null || txtPassword.getText().trim().isEmpty()) { infoBox("Senha não pode estar em branco"); } 
			
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
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
			newStage.getIcons().add(anotherIcon);
			
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
	
	public JFXSnackbar showSnackBar(String message,AnchorPane pane){
        JFXSnackbar jfxSnackbar=new JFXSnackbar(pane);
     //   jfxSnackbar.setAlignment(Pos.BOTTOM_RIGHT);
      //  jfxSnackbar.setPrefHeight(40);
        jfxSnackbar.setPrefWidth(pane.getWidth()-40);
      //  jfxSnackbar.show(message,2000);

        return jfxSnackbar;
    }
}
