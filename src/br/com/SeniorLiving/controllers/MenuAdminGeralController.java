package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuAdminGeralController extends Controller implements Initializable {
	
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/MenuAdminGeral.fxml";

	@FXML
	private AnchorPane pane;
	
	@FXML
	private AnchorPane containerToScreen;
	
	@FXML 
	private TextField txtVoltar;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	@FXML
	private void roleButtonClick() throws IOException {
		RoleListController roleListController = new RoleListController();
		FXMLLoader loader = roleListController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		containerToScreen.requestFocus();
	}
	
	@FXML
	private void userButtonClick() throws IOException {
		UserController userController = new UserController();
		FXMLLoader loader = userController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
	
	@FXML
	private void restHomeButtonClick() throws IOException {
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//		newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void aboutButtonClick() throws IOException {
		AboutController aboutController = new AboutController();
		FXMLLoader loader = aboutController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
	}
	@FXML
	private void logoutButtonClick(MouseEvent event) throws IOException {
		LoginController loginController = new LoginController();
		FXMLLoader loader = loginController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		
		Scene futureScene = new Scene(anchorPane);
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
		newStage.getIcons().add(anotherIcon);
		loginController.performLogout();
		
		
		Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, true);
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	@FXML
	public void voltarAction(MouseEvent event) {
		try {
			MenuController menuController = new MenuController();
			FXMLLoader loader = menuController.getFXMLLoader();
			AnchorPane anchorPane = loader.load();
			loader.getController();
			Scene futureScene = new Scene(anchorPane);
			
			Stage newStage = new Stage();
			newStage.setScene(futureScene);
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
			newStage.getIcons().add(anotherIcon);
			
			Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, true);
		} catch (Exception e) {
		e.getMessage();
		}			
	}
	
}
	
	
