package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
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
	
	@FXML
	private TableColumn<Role, String> tableColumnAccomodation;	

	@FXML
	private TableColumn<Role, String> tableColumnValues2; //Segunda Coluna

	@FXML
	private TableColumn<Role, String> tableColumnProduct;
	
	@FXML
	private TableColumn<Role, String> tableColumnQnt;
	
	@FXML
	private TableColumn<Role, String> tableColumnValues3; //Terceira Coluna
	
	@FXML
	private TableColumn<Role, String> tableColumnUnitario;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableColumnService.setCellValueFactory(new PropertyValueFactory<>("Serviço"));
		tableColumnValues.setCellValueFactory(new PropertyValueFactory<>("Valor"));
		tableColumnAccomodation.setCellValueFactory(new PropertyValueFactory<>("Acomodação"));
		tableColumnValues2.setCellValueFactory(new PropertyValueFactory<>("Valor"));
		tableColumnProduct.setCellValueFactory(new PropertyValueFactory<>("Produto"));
		tableColumnQnt.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
		tableColumnValues3.setCellValueFactory(new PropertyValueFactory<>("Valor"));
		tableColumnUnitario.setCellValueFactory(new PropertyValueFactory<>("ValorUnitario"));
				
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