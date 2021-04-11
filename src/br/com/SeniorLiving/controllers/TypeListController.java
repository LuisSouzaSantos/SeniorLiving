package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TypeListController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/TypeList.fxml";

	@FXML
	private TableView<Type> tableViewType;

	@FXML
	private TableColumn<Type, String> tableColumnTypeName;
		
	@FXML
	private Button btNew;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableColumnTypeName.setCellValueFactory(new PropertyValueFactory<>("Nome"));
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewType.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void initializeNodes() {
		//Constraints.setTextFieldMaxLength(txtEmail, 255);
		//Constraints.setTextFieldMaxLength(txtPassword, 255);
	}


	@Override
	public FXMLLoader getFXMLLoader() {
	return new FXMLLoader(getClass().getResource(UI_PATH));

}
}
