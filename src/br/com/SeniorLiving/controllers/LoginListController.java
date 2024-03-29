package br.com.SeniorLiving.controllers;

import java.io.IOException;

import br.com.SeniorLiving.model.services.UserService;
import br.com.ftt.ec6.seniorLiving.utils.Alerts;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginListController{
	
	@FXML
	private Button btLogin;
	
    @FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		//User obj = new User();
		createDialogForm("/br/com/SeniorLiving/gui/Loginn.fxml", parentStage);
	}
	
    private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			LoginController controller = loader.getController();
			//controller.setUser(obj);
			controller.setUserSevice(new UserService());
			//controller.subscribeDataChangeListener(this);
			//controller.updateFormData();

			Stage dialogStage = new Stage();
			//dialogStage.setTitle("Enter Login data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);

			dialogStage.initOwner(parentStage);
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
            dialogStage.getIcons().add(anotherIcon);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}