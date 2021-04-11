package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import br.com.SeniorLiving.application.Main;
import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.db.DbException;
import br.com.ftt.ec6.seniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.utils.Alerts;
import br.com.ftt.ec6.seniorLiving.utils.Constraints;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FinancialFormController implements Initializable {

	//private Financial entity;

	//private FinancialService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TableView<Product> tableViewReport;

	@FXML
	private TableColumn<Product, String> tableColumnService;

	@FXML
	private TableColumn<Product, String> tableColumnValues;
	
	@FXML
	private TextField txtElderly;
	
	@FXML
	private Button btReport;


	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Report.fxml";
	
//	private ObservableList<Financial> obsList;

	public FXMLLoader getFXMLLoader() {
	return new FXMLLoader(getClass().getResource(UI_PATH));

}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}


	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle db) {
		initializeNodes();
		tableColumnService.setCellValueFactory(new PropertyValueFactory<>("Servi�o"));
		tableColumnValues.setCellValueFactory(new PropertyValueFactory<>("Valor"));
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewReport.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void initializeNodes() {
	}



	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
	}
}
