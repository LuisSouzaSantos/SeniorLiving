package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.controllers.util.Alerts;
import br.com.SeniorLiving.controllers.util.Utils;
import br.com.SeniorLiving.db.DbIntegrityException;
import br.com.SeniorLiving.model.entities.Agenda;
import br.com.SeniorLiving.model.services.AgendaService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgendaListController implements Initializable, DataChangeListener {

	private AgendaService service;

	@FXML
	private TableView<Agenda> tableViewAgenda;

	@FXML
	private TableColumn<Agenda, Integer> tableColumnId;

	@FXML
	private TableColumn<Agenda, String> tableColumnFullname;
	
	
//	private TableColumn<Agenda, String> tableColumnNotes;
	
	
//	private TableColumn<Agenda, Date> tableColumnInitiaton;

	
//	private TableColumn<Agenda, Date> tableColumnTermination;

	@FXML
	private TableColumn<Agenda, Agenda> tableColumnEDIT;
	
	@FXML
	private TableColumn<Agenda, Agenda> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<Agenda> obsList;

	public void setAgendaService(AgendaService service) {
		this.service = service;
	}

	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Agenda obj = new Agenda();
		createDialogForm(obj, "/br/com/SeniorLiving/gui/AgendaForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnFullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
	//	tableColumnNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
	//	tableColumnInitiaton.setCellValueFactory(new PropertyValueFactory<>("initiation"));
	//	Utils.formatTableColumnDate(tableColumnInitiaton, "dd/MM/yyyy");
	//	tableColumnTermination.setCellValueFactory(new PropertyValueFactory<>("termination"));
	//	Utils.formatTableColumnDate(tableColumnTermination, "dd/MM/yyyy");
				
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewAgenda.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Agenda> list = service.findALL();
		obsList = FXCollections.observableArrayList(list);
		tableViewAgenda.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Agenda obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			AgendaFormController controller = loader.getController();
			
			controller.setAgenda(obj);
			controller.setAgendaSevice(new AgendaService());
			controller.loadAssociateObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Agenda");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Agenda, Agenda>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Agenda obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/br/com/SeniorLiving/gui/AgendaForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Agenda, Agenda>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Agenda obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				
				//Image img = new Image(getClass().getResourceAsStream("/br/com/SeniorLiving/images/round_delete_black_18dp"));
		       // ImageView imgView = new ImageView(img);
		       // button.setGraphic(imgView);
		        
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	protected void removeEntity(Agenda obj) {
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
