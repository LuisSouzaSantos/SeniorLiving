package br.com.SeniorLiving.application;
	
import java.io.IOException;

import br.com.SeniorLiving.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	private static Scene mainScene;
	private static Stage lastStage;
	private static Stage currentStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			LoginController loginController = new LoginController();
			
			FXMLLoader loader = loginController.getFXMLLoader();
			AnchorPane scrollPane = loader.load();
			
			mainScene = new Scene(scrollPane);
			currentStage = primaryStage;
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Senior Living");
			
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
            primaryStage.getIcons().add(anotherIcon);
            
            setCurrentStage(primaryStage);
			primaryStage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void changeStage(Stage futureStage) {
		Stage currentStage = getCurrentStage();
		
		if(currentStage != null) {
			setLastStage(currentStage);
		}
		
		setCurrentStage(futureStage);
		getLasStage().close();
		getCurrentStage().showAndWait();;
	}
	
	public static void setLastStage(Stage stage) {
		lastStage = stage;
	}
	
	public static void setCurrentStage(Stage stage) {
		currentStage = stage;
	}
	
	public static Stage getLasStage() {
		return lastStage;
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}
	
	public static Stage getCurrentStage() {
		return currentStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
