package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuAdminLocalController extends Controller implements Initializable {
	
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/MenuAdminLocal.fxml";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		initializeNodes();
	}

	private void initializeNodes() {
	}
	
	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}

	@FXML
	private void acommodationButtonClick() throws IOException {
		AcommodationListController menuAdminGeralController = new AcommodationListController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
        newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void typeButtonClick() throws IOException {
		TypeListController menuAdminGeralController = new TypeListController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
        newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void productButtonClick() throws IOException {
		ProductListController menuAdminGeralController = new ProductListController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
        newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void personButtonClick() throws IOException {
		PersonFormController menuAdminGeralController = new PersonFormController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
        newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void elderlyButtonClick() throws IOException {
		ElderlyController menuAdminGeralController = new ElderlyController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
        newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void reportButtonClick() throws IOException {
		ReportController menuAdminGeralController = new ReportController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
        newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
}
