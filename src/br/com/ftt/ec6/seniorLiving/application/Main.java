package br.com.ftt.ec6.seniorLiving.application;
	
import java.io.IOException;

import br.com.SeniorLiving.controllers.Controller;
import br.com.SeniorLiving.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			LoginController loginController = new LoginController();
			
			FXMLLoader loader = loginController.getFXMLLoader();
			AnchorPane scrollPane = loader.load();
		
			primaryStage.setScene(new Scene(scrollPane));
			primaryStage.setTitle("Senior Living");
			primaryStage.setMaxHeight(500);
			primaryStage.setMaxWidth(800);
			
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/logos.png");
			primaryStage.getIcons().add(anotherIcon);
            Controller.goToNextScene(null, false, primaryStage, false);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}	
}
