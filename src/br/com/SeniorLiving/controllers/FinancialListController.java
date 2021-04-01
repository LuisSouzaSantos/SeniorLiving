package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.db.DbIntegrityException;
import br.com.SeniorLiving.model.services.FinancialService;
import br.com.ftt.ec6.seniorLiving.entities.Financial;
import br.com.ftt.ec6.seniorLiving.utils.Alerts;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FinancialListController implements Initializable, DataChangeListener {

	private FinancialService service;

	@FXML
	private TableView<Financial> tableViewFinancial;

	@FXML
	private TableColumn<Financial, Integer> tableColumnId;

	@FXML
	private TableColumn<Financial, String> tableColumnName;

	@FXML
	private TableColumn<Financial, Financial> tableColumnEDIT;
	
	@FXML
	private TableColumn<Financial, Financial> tableColumnREMOVE;

	@FXML
	private Button btNew;

	@FXML
	private TextField textSearch;

	@FXML
	private Button btSearch;
	
	@FXML
	private Button btReport;
	
	private ObservableList<Financial> obsList;

	public void setFinancialService(FinancialService service) {
		this.service = service;
	}

	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Financial obj = new Financial();
		createDialogForm(obj, "/br/com/SeniorLiving/gui/FinancialControlForm.fxml", parentStage);
	}

	@FXML
	private void onBtNewReport() {		
	
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewFinancial.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Financial> list = service.findALL();
		obsList = FXCollections.observableArrayList(list);
		tableViewFinancial.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Financial obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			FinancialFormController controller = loader.getController();
			controller.setFinancial(obj);
			//controller.setFinancialService(new FinancialService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Financial data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
			dialogStage.getIcons().add(anotherIcon);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Financial, Financial>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Financial obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/br/com/SeniorLiving/gui/FinancialControlForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Financial, Financial>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Financial obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	protected void removeEntity(Financial obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try  {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removint object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	
	}

}
