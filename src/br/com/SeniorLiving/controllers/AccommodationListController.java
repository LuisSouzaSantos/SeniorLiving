package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AccommodationListController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/AcommodationList.fxml";
	
	@FXML
	private TableView<Role> tableViewAccomodation;

	@FXML
	private TableColumn<Role, String> tableColumnName;
	
	@FXML
	private TableColumn<Role, String> tableColumnDescription;
	
	@FXML
	private Button btNew;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("Descri��o"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAccomodation.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void initializeNodes() {
	}

	@FXML
	public void onBtNewAction() throws IOException{
		
		//AccommodationController menuAdminGeralController = new AccommodationController();
		//FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		//VBox vBox = loader.load();
		//loader.getController();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
	return new FXMLLoader(getClass().getResource(UI_PATH));

}
}
