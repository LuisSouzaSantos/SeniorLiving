package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.controllers.util.Alerts;
import br.com.SeniorLiving.controllers.util.Constraints;
import br.com.SeniorLiving.controllers.util.Utils;
import br.com.SeniorLiving.db.DbException;
import br.com.SeniorLiving.model.entities.Agenda;
import br.com.SeniorLiving.model.exceptions.ValidationException;
import br.com.SeniorLiving.model.services.AgendaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AgendaFormController implements Initializable {

	private Agenda entity;

	private AgendaService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField id;

	@FXML
	private TextField fullname;

	@FXML
	private TextArea notes;

	@FXML
	private DatePicker initiation;

	@FXML
	private DatePicker termination;

//	@FXML
	//private Label labelErrorName;

	
	//private ComboBox<Agenda> comboBoxPaciente;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	private ObservableList<Agenda> obsList;
		
		public void setAgenda(Agenda entity) {
		this.entity = entity;
	}
		
		public void setAgendaSevice(AgendaService service) {
			this.service = service;
		}

	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Agenda getFormData() {
		Agenda obj = new Agenda();

		ValidationException exception = new ValidationException("Validation error");

		obj.setId(Utils.tryParseToInt(id.getText()));

		if (fullname.getText() == null || fullname.getText().trim().contentEquals("")) {
			exception.addError("fullname", "Field can't be empty");
		}
		obj.setFullname(fullname.getText());
		
		if (notes.getText() == null || notes.getText().trim().contentEquals("")) {
			exception.addError("notes", "Field can't be empty");
		}
		obj.setNotes(notes.getText());
		
		if (initiation.getValue() == null) {
			exception.addError("initiation", "Field can't be empty");
		}
		else {
			Instant instant  = Instant.from(initiation.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setInitiation(Date.from(instant));
		}		
	
		if (termination.getValue() == null) {
			exception.addError("termination", "Field can't be empty");
		}
		else {
			Instant instant  = Instant.from(termination.getValue().atStartOfDay(ZoneId.systemDefault()));
			obj.setTermination(Date.from(instant));
		}
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle db) {
		initializeNodes();

	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(id);
		Constraints.setTextFieldMaxLength(fullname, 30);
		Constraints.setTextAreaMaxLength(notes, 5000);
		Utils.formatDatePicker(initiation, "dd/MM/yyyy");
		Utils.formatDatePicker(termination, "dd/MM/yyyy");

	//	initializeComboBoxDepartment();
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		id.setText(String.valueOf(entity.getId()));
		fullname.setText(entity.getFullname());
		notes.setText(entity.getNotes());
		if (entity.getInitiation() != null) {
			initiation.setValue(LocalDate.ofInstant(entity.getInitiation().toInstant(), ZoneId.systemDefault()));
		}	
		if (entity.getTermination() != null) {
			termination.setValue(LocalDate.ofInstant(entity.getTermination().toInstant(), ZoneId.systemDefault()));
		}	
	}

	public void loadAssociateObjects() {
		if (service == null) {
			throw new IllegalStateException("Agenda was null");
		}
		List<Agenda> list = service.findALL();
		obsList = FXCollections.observableArrayList(list);
	//	comboBoxAgenda.setItems(obsList);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

	//	labelErrorName.setText((fields.contains("fullname") ? errors.get("fullname") : ""));
	//	labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
		//labelErrorBirthDate.setText((fields.contains("birthDate") ? errors.get("birthDate") : ""));
	//	labelErrorBaseSalary.setText((fields.contains("baseSalary") ? errors.get("baseSalary") : ""));
	}

	
}

