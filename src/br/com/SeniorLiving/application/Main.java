package br.com.SeniorLiving.application;
	
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
	public void start(Stage primeStage) {
		try {
			stage = primaryStage;
			LoginController loginController = new LoginController();
			
			FXMLLoader loader = loginController.getFXMLLoader();
			AnchorPane scrollPane = loader.load();
		
			primeStage.setScene(new Scene(scrollPane));
			primeStage.setTitle("Senior Living");
			primaryStage.setMaxHeight(500);
			primaryStage.setMaxWidth(800);
			primaryStage.setResizable(false);
			
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
			primeStage.getIcons().add(anotherIcon);
            Controller.goToNextScene(null, false, primeStage, false);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}	
}
