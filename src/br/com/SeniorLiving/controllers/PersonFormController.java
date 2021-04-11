package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.bind.ValidationException;

import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.db.DbException;
import br.com.SeniorLiving.model.services.DepartmentService;
import br.com.ftt.ec6.seniorLiving.entities.Department;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.service.PersonService;
import br.com.ftt.ec6.seniorLiving.utils.Alerts;
import br.com.ftt.ec6.seniorLiving.utils.Constraints;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class PersonFormController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Person.fxml";

	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtEstadoCivil;
	
	@FXML
	private TextField dpBirthDate;
	
	@FXML
	private TextField txtRg;
	
	@FXML
	private TextField txtPerfil;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private TextField txtNacionalidade;
	
	@FXML
	private TextField txtTelefone;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private TextField txtRua;
	
	@FXML
	private TextField txtNumero;
	
	@FXML
	private TextField txtEstado;
	
	@FXML
	private TextField txtCep;
	
	@FXML
	private TextField txtReferencia;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
			
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
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