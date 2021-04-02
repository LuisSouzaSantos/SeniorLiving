package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RoleController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/RoleList.fxml";
	
	@FXML
	private TableView<Role> tableViewRole;

	@FXML
	private TableColumn<Role, String> tableColumnRoleName;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tableColumnRoleName.setCellValueFactory(new PropertyValueFactory<>("Nome"));
	//	tableColumnNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
	//	tableColumnInitiaton.setCellValueFactory(new PropertyValueFactory<>("initiation"));
	//	Utils.formatTableColumnDate(tableColumnInitiaton, "dd/MM/yyyy");
	//	tableColumnTermination.setCellValueFactory(new PropertyValueFactory<>("termination"));
	//	Utils.formatTableColumnDate(tableColumnTermination, "dd/MM/yyyy");
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewRole.prefHeightProperty().bind(stage.heightProperty());
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));	
	}

}
