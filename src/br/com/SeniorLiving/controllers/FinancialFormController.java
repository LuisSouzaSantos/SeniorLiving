package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.controllers.util.Alerts;
import br.com.SeniorLiving.controllers.util.Constraints;
import br.com.SeniorLiving.controllers.util.Utils;
import br.com.SeniorLiving.db.DbException;
import br.com.SeniorLiving.model.exceptions.ValidationException;
import br.com.SeniorLiving.model.services.FinancialService;
import br.com.ftt.ec6.seniorLiving.model.entities.Financial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FinancialFormController implements Initializable {

	private Financial entity;

	private FinancialService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtDescription;

	@FXML
	private TextField txtAmount;
	
	@FXML
	private TextField txtQnt;
	
	@FXML
	private TextField txtVencimento;
	
	@FXML
	private TextField txtValorTotal;
	
	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	private ObservableList<Financial> obsList;

	public void setFinancial(Financial entity) {
		this.entity = entity;
	}
	
	public void setServices(FinancialService service) {
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

	private Financial getFormData() {
		Financial obj = new Financial();

		ValidationException exception = new ValidationException("Validation error");

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtDescription.getText() == null || txtDescription.getText().trim().contentEquals("")) {
			exception.addError("description", "Field can't be empty");
		}
		obj.setDescription(txtDescription.getText());		
		
		
		if (txtAmount.getText() == null || txtAmount.getText().trim().contentEquals("")) {
			exception.addError("amount", "Field can't be empty");
		}
		obj.setAmount(Utils.tryParseToDouble(txtAmount.getText()));
		
		
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
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtDescription, 100);
		Constraints.setTextFieldDouble(txtAmount);
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtDescription.setText(entity.getDescription());
		txtAmount.setText(String.format("%.2f", entity.getAmount()));
		
	}

	public void loadAssociateObjects() {
		if (service == null) {
			throw new IllegalStateException("Financial was null");
		}
		List<Financial> list = service.findALL();
		obsList = FXCollections.observableArrayList(list);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

	}
}
