package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuAdminGeralController extends Controller implements Initializable {
	
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/MenuAdminGeral.fxml";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	private void roleButtonClick() throws IOException {
		RoleController menuAdminGeralController = new RoleController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void userButtonClick() throws IOException {
		UserController menuAdminGeralController = new UserController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void restHomeButtonClick() throws IOException {
		RestHomeController menuAdminGeralController = new RestHomeController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void aboutButtonClick() {
		
	}
	
	@FXML
	private void logoutButtonClick() {
		
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}

}
