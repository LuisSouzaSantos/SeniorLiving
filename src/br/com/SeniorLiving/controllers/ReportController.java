package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.application.Main;
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

public class ReportController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Report.fxml";

	@FXML
	private TextField txtElderly;
	
	@FXML
	private Button btReport;
	
	@FXML
	private TableView<Role> tableViewReport;

	@FXML
	private TableColumn<Role, String> tableColumnService;

	@FXML
	private TableColumn<Role, String> tableColumnValues;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableColumnService.setCellValueFactory(new PropertyValueFactory<>("Servi�o"));
		tableColumnValues.setCellValueFactory(new PropertyValueFactory<>("Valor"));
		
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewReport.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void initializeNodes() {
	}


	@Override
	public FXMLLoader getFXMLLoader() {
	return new FXMLLoader(getClass().getResource(UI_PATH));

}
}