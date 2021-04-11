package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ElderlyListController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/ElderlyList.fxml";

	@FXML
	private TableView<Elderly> tableViewElderly;

	@FXML
	private TableColumn<Elderly, String> tableColumnName;
	
	@FXML
	private TableColumn<Elderly, String> tableColumnState;
	
	@FXML
	private TableColumn<Elderly, String> tableColumnNacionality;
	
	@FXML
	private Button btNew;
	
	@FXML
	private Button btSearch;
	
	@FXML
	private TextField textSearch;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Nome"));

		tableColumnState.setCellValueFactory(new PropertyValueFactory<>("Estado Civil"));

		tableColumnNacionality.setCellValueFactory(new PropertyValueFactory<>("Nacionalidade"));
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewElderly.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void initializeNodes() {
	}


	@Override
	public FXMLLoader getFXMLLoader() {
	return new FXMLLoader(getClass().getResource(UI_PATH));

}
}