package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.utils.Alerts;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AcommodationListController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/AcommodationList.fxml";
	
	@FXML
	private TableView<Role> tableViewAccomodation;

	@FXML
	private TableColumn<Role, String> tableColumnName;
	
	@FXML
	private Button btNew;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Nome"));
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAccomodation.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void initializeNodes() {
	}


	@FXML
	private void onBtNewAction() {
		//Stage parentStage = Utils.currentStage(event);
	//	Role obj = new Role();
		//createDialogForm("/br/com/SeniorLiving/gui/Accomodation.fxml", parentStage);
	}
	
	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Senior Living");
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

	@Override
	public FXMLLoader getFXMLLoader() {
	return new FXMLLoader(getClass().getResource(UI_PATH));

}
}
