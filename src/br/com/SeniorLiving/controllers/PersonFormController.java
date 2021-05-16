package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.entities.support.Address;
import br.com.ftt.ec6.seniorLiving.entities.support.State;
import br.com.ftt.ec6.seniorLiving.service.PersonService;
import br.com.ftt.ec6.seniorLiving.service.TypeService;
import br.com.ftt.ec6.seniorLiving.service.impl.PersonServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.ServiceProxy;
import br.com.ftt.ec6.seniorLiving.service.impl.TypeServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.ExternoApi;
import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;
import br.com.ftt.ec6.seniorLiving.utils.SupportProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PersonFormController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/PersonForm.fxml";
	private final static PersonService personService = (PersonService) ServiceProxy.newInstance(PersonServiceImpl.getInstance());
	private final static TypeService typeService = (TypeService) ServiceProxy.newInstance(TypeServiceImpl.getInstance());
	private static List<Type> typeSelectedList = new ArrayList<>();
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_CREATE = {"NAME", "NACIONALITY", "CIVIL_STATE", "JOB", "RG", "CPF", "PHONE", "TYPE", "EMAIL", "CEP", "STREET", "STREET_NUMBER", "STATE", "NEIGHBORHOOD", "BIRTH_DATE"};
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_UPDATE = {"NAME", "NACIONALITY", "CIVIL_STATE", "JOB", "RG", "CPF", "PHONE", "TYPE", "EMAIL", "CEP", "STREET", "STREET_NUMBER", "STATE", "NEIGHBORHOOD", "BIRTH_DATE"};
	
	
	@FXML
	private TextField formPersonNameField;
	@FXML
	private TextField formPersonNacionalityField;
	@FXML
	private ComboBox<MaritalStatus> formPersonCivilStateComboBox;
	@FXML
	private TextField formPersonJobField;
	@FXML
	private TextField formPersonRGField;
	@FXML
	private TextField formPersonCPFField;
	@FXML
	private TextField formPersonPhoneField;
	@FXML
	private ListView<Type> formPersonTypeListView;
	@FXML
	private TextField formPersonEmailField;
	@FXML
	private TextField formPersonCEPField;
	@FXML
	private TextField formPersonStreetField;
	@FXML
	private TextField formPersonStreetNumberField;
	@FXML
	private ComboBox<String> formPersonStateComboBox;
	@FXML
	private TextField formPersonNeighborhoodField;
	@FXML
	private DatePicker formPersonBirthDateDatePicker;
	
	@FXML
	private Text formPersonErrorNameMessageText;
	@FXML
	private Text formPersonErrorNacionalityMessageText;
	@FXML
	private Text formPersonErrorCivilStatusMessageText;
	@FXML
	private Text formPersonErrorJobMessageText;
	@FXML
	private Text formPersonErrorRGMessageText;
	@FXML
	private Text formPersonErrorCPFMessageText;
	@FXML
	private Text formPersonErrorPhoneMessageText;
	@FXML
	private Text formPersonErrorTypeMessageText;
	@FXML
	private Text formPersonErrorEmailMessageText;
	@FXML
	private Text formPersonErrorCEPMessageText;
	@FXML
	private Text formPersonErrorStreetMessageText;
	@FXML
	private Text formPersonErrorStreetNumberMessageText;
	@FXML
	private Text formPersonErrorStateMessageText;
	@FXML
	private Text formPersonErrorNeighborhoodMessageText;
	@FXML
	private Text formPersonErrorBirthDateMessageText;
	@FXML
	private Text formPersonErrorMessageText;
	
	@FXML
	private Button personFormCreateButton;
	@FXML
	private Button personFormUpdateButton;
	@FXML
	private Button personFormCancelButton;
	
	private Stage me;
	private boolean isCreatedForm;
	private Person person;
	private PersonController father;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	public void performReload() {
		initializeNodes();
	}
	
	@FXML
	private void createNewPersonButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_CREATE)) == false) { return; }
		
		String name = formPersonNameField.getText();
		String nacionality = formPersonNacionalityField.getText();
		MaritalStatus maritalStatus = formPersonCivilStateComboBox.getValue();
		String job = formPersonJobField.getText();
		String rg = formPersonRGField.getText();
		String cpf = formPersonCPFField.getText();
		String phone = formPersonPhoneField.getText();
		List<Type> typeList = typeSelectedList;
		String email = formPersonEmailField.getText();
		String cep = formPersonCEPField.getText();
		String street = formPersonStreetField.getText();
		String streetNumber = formPersonStreetNumberField.getText();
		String state = formPersonStateComboBox.getValue();
		String neighborhood = formPersonNeighborhoodField.getText();
		LocalDate birthDate = formPersonBirthDateDatePicker.getValue();
		
		personService.save(name, maritalStatus, nacionality, job, rg, cpf, birthDate, phone, email, street, streetNumber, state, cep, neighborhood, getRestHomeActived(), typeList);
	}
	
	@FXML
	private void updatePersonButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_UPDATE)) == false) { return; }
		
		String name = formPersonNameField.getText();
		String nacionality = formPersonNacionalityField.getText();
		MaritalStatus maritalStatus = formPersonCivilStateComboBox.getValue();
		String job = formPersonJobField.getText();
		String rg = formPersonRGField.getText();
		String cpf = formPersonCPFField.getText();
		String phone = formPersonPhoneField.getText();
		List<Type> typeList = typeSelectedList;
		String email = formPersonEmailField.getText();
		String cep = formPersonCEPField.getText();
		String street = formPersonStreetField.getText();
		String streetNumber = formPersonStreetNumberField.getText();
		String state = formPersonStateComboBox.getValue();
		String neighborhood = formPersonNeighborhoodField.getText();
		LocalDate birthDate = formPersonBirthDateDatePicker.getValue();
		
		person.setName(name);
		person.setNationality(nacionality);
		person.setMaritalStatus(maritalStatus);
		person.setJob(job);
		person.setRg(rg);
		person.setCpf(cpf);
		person.setPhone(phone);
		person.setTypeList(typeList);
		person.setEmail(email);
		person.setAddressCep(cep);
		person.setAddressStreet(street);
		person.setAddressNumber(streetNumber);
		person.setAddressState(state);
		person.setAddressNeighborhood(neighborhood);
		person.setBirthDate(birthDate);
		
		personService.update(person);
	}
	
	@FXML
	private void closePersonFormButtonAction() {
		clearForm();
		closeMe();
	}
	
	private void initializeNodes(){
		cleanErrorMessages();
		clearForm();
		buttonFormAvailable();
		checkIfIsUpdateForm();
		loadCivilState();
		loadTypes();
		loadState();
		loadAddressAutomatic();
		settingDatePicker();
	}
	
	private void clearForm() {
		formPersonNameField.setText("");
		formPersonNacionalityField.setText("");
		//formPersonCivilStateComboBox.setItems(null);;
		formPersonJobField.setText("");
		formPersonRGField.setText("");
		formPersonCPFField.setText("");
		formPersonPhoneField.setText("");
		//formPersonTypeChoiceBox.setItems(null);
		formPersonEmailField.setText("");
		formPersonCEPField.setText("");
		formPersonStreetField.setText("");
		formPersonStreetNumberField.setText("");
		//formPersonStateComboBox.setItems(null);
		formPersonNeighborhoodField.setText("");
		//formPersonBirthDateDatePicker.setValue(null);
	}
	
	private void cleanErrorMessages() {
		formPersonErrorNameMessageText.setText("");
		formPersonErrorNacionalityMessageText.setText("");
		formPersonErrorCivilStatusMessageText.setText("");
		formPersonErrorJobMessageText.setText("");
		formPersonErrorRGMessageText.setText("");
		formPersonErrorCPFMessageText.setText("");
		formPersonErrorPhoneMessageText.setText("");
		formPersonErrorTypeMessageText.setText("");
		formPersonErrorEmailMessageText.setText("");
		formPersonErrorCEPMessageText.setText("");
		formPersonErrorStreetMessageText.setText("");
		formPersonErrorStreetNumberMessageText.setText("");
		formPersonErrorStateMessageText.setText("");
		formPersonErrorNeighborhoodMessageText.setText("");
		formPersonErrorBirthDateMessageText.setText("");
		formPersonErrorMessageText.setText("");
	}
	
	private boolean isValidFields(List<String> fieldsToBeValidate){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields(fieldsToBeValidate);
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("NAME")) {
				formPersonErrorNameMessageText.setText(value);
			}
			
			if(key.equals("NACIONALITY")) {
				formPersonErrorNacionalityMessageText.setText(value);
			}
			
			if(key.equals("CIVIL_STATE")) {
				formPersonErrorCivilStatusMessageText.setText(value);
			}
			
			if(key.equals("JOB")) {
				formPersonErrorJobMessageText.setText(value);
			}
			
			if(key.equals("RG")) {
				formPersonErrorRGMessageText.setText(value);
			}
			
			if(key.equals("CPF")) {
				formPersonErrorCPFMessageText.setText(value);
			}
			
			if(key.equals("PHONE")) {
				formPersonErrorPhoneMessageText.setText(value);
			}
			
			if(key.equals("TYPE")) {
				formPersonErrorTypeMessageText.setText(value);
			}
			
			if(key.equals("EMAIL")) {
				formPersonErrorEmailMessageText.setText(value);
			}
			
			if(key.equals("CEP")) {
				formPersonErrorCEPMessageText.setText(value);
			}
			
			if(key.equals("STREET")) {
				formPersonErrorStreetMessageText.setText(value);
			}
			
			if(key.equals("STREET_NUMBER")) {
				formPersonErrorStreetNumberMessageText.setText(value);
			}
			
			if(key.equals("STATE")) {
				formPersonErrorStateMessageText.setText(value);
			}
			
			if(key.equals("NEIGHBORHOOD")) {
				formPersonErrorNeighborhoodMessageText.setText(value);
			}

			if(key.equals("BIRTH_DATE")) {
				formPersonErrorBirthDateMessageText.setText(value);
			}

		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields(List<String> fieldsToBeValidate) {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if((fieldsToBeValidate.contains("NAME")) && (formPersonNameField == null || formPersonNameField.getText().trim().isEmpty())) { 
			errorsList.put("NAME", "Nome não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("NACIONALITY")) && (formPersonNacionalityField == null || formPersonNacionalityField.getText().trim().isEmpty())) { 
			errorsList.put("NACIONALITY", "Nacionalidade não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("CIVIL_STATE")) && ((formPersonCivilStateComboBox == null) || (formPersonCivilStateComboBox.getValue() == null))) { 
			errorsList.put("CIVIL_STATE", "Estado civil não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("JOB")) && ((formPersonJobField == null) || (formPersonJobField.getText().trim().isEmpty()))) { 
			errorsList.put("JOB", "Função não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("RG")) && ((formPersonRGField == null) || (formPersonRGField.getText().trim().isEmpty()))) { 
			errorsList.put("RG", "RG não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("CPF")) && ((formPersonCPFField == null) || (formPersonCPFField.getText().trim().isEmpty()))) { 
			errorsList.put("CPF", "CPF não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("PHONE")) && ((formPersonPhoneField == null) || (formPersonPhoneField.getText().trim().isEmpty()))) { 
			errorsList.put("PHONE", "Telefone não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("TYPE")) && ((typeSelectedList == null) || (typeSelectedList.isEmpty()))) { 
			errorsList.put("TYPE", "Tipo não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("EMAIL")) && ((formPersonEmailField == null) || (formPersonEmailField.getText().trim().isEmpty()))) { 
			errorsList.put("EMAIL", "Email não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("CEP")) && ((formPersonCEPField == null) || (formPersonCEPField.getText().trim().isEmpty()))) { 
			errorsList.put("CEP", "CEP não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("STREET")) && ((formPersonStreetField == null) || (formPersonStreetField.getText().trim().isEmpty()))) { 
			errorsList.put("STREET", "Rua não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("STREET_NUMBER")) && ((formPersonStreetNumberField == null) || (formPersonStreetNumberField.getText().trim().isEmpty()))) { 
			errorsList.put("STREET_NUMBER", "Número não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("STATE")) && ((formPersonStateComboBox == null) || (formPersonStateComboBox.getValue() == null))) { 
			errorsList.put("STATE", "Número não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("NEIGHBORHOOD")) && ((formPersonNeighborhoodField == null) || (formPersonNeighborhoodField.getText().trim().isEmpty()))) { 
			errorsList.put("NEIGHBORHOOD", "Número não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("BIRTH_DATE")) && ((formPersonBirthDateDatePicker == null) || (formPersonBirthDateDatePicker.getValue() == null))) { 
			errorsList.put("BIRTH_DATE", "Número não pode estar em branco.");
		}
		
		return errorsList;
	}
	
	private void buttonFormAvailable() {
		if(this.isCreatedForm) {
			this.personFormCreateButton.setVisible(true);
			this.personFormUpdateButton.setVisible(false);
		}else {
			this.personFormCreateButton.setVisible(false);
			this.personFormUpdateButton.setVisible(true);
		}
	}
	
	private void checkIfIsUpdateForm() {
		if(this.isCreatedForm) { return; }
		
		if(this.person == null) { return; }
		
		formPersonNameField.setText(this.person.getName());
		formPersonNacionalityField.setText(this.person.getNationality());
		formPersonCivilStateComboBox.getSelectionModel().select(this.person.getMaritalStatus());
		formPersonJobField.setText(this.person.getJob());
		formPersonRGField.setText(this.person.getRg());
		formPersonCPFField.setText(this.person.getCpf());
		formPersonPhoneField.setText(this.person.getPhone());
		typeSelectedList.addAll(this.person.getTypeList());
		formPersonEmailField.setText(this.person.getEmail());
		formPersonCEPField.setText(this.person.getAddressCep());
		formPersonStreetField.setText(this.person.getAddressStreet());
		formPersonStreetNumberField.setText(this.person.getAddressNumber());
		formPersonStateComboBox.getSelectionModel().select(this.person.getAddressState());
		formPersonNeighborhoodField.setText(this.person.getAddressNeighborhood());
		formPersonBirthDateDatePicker.setValue(this.person.getBirthDate());
	}
	
	private void closeMe() {
		this.clearForm();
		this.me.close();
	}
	
	private void loadCivilState() {
		ObservableList<MaritalStatus> observableMarialStatus =  FXCollections.observableArrayList(MaritalStatus.values());
		
		formPersonCivilStateComboBox.setItems(observableMarialStatus);
		formPersonCivilStateComboBox.setCursor(Cursor.HAND);
	}
	
	private void loadTypes() {
		List<Type> typeList = typeService.getTypeByRestHome(getRestHomeActived());
		
		if((typeList == null) || (typeList.isEmpty())) { return; }
		
		ObservableList<Type> observableTypeList =  FXCollections.observableArrayList(typeList);
		
		formPersonTypeListView.setItems(observableTypeList);
		formPersonTypeListView.setCursor(Cursor.HAND);
		formPersonTypeListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		formPersonTypeListView.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				ObservableList<Type> selectedItems =  formPersonTypeListView.getSelectionModel().getSelectedItems();
				
				selectedItems.forEach(type -> {
					if(typeSelectedList.contains(type)) { 
						typeSelectedList.remove(type);
						return; 
					}
					typeSelectedList.add(type);
				});
			}
		});
		
	}
	
	private void loadState() {
		SupportProperties supportProperties = SupportProperties.getInstance();
		
		List<State> states = supportProperties.getStates();
		
		if(states == null) { return; }
		
		List<String> statesName = states.stream().map(State::getSigla).collect(Collectors.toList());
		
		ObservableList<String> observableState =  FXCollections.observableArrayList(statesName);
		
		formPersonStateComboBox.setItems(observableState);
		formPersonStateComboBox.setCursor(Cursor.CLOSED_HAND);
	}
	
	private void loadAddressAutomatic() {
		formPersonCEPField.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(formPersonCEPField.getText().trim().isEmpty()) { return; }
				
				String cep = formPersonCEPField.getText();
				
				Address address = ExternoApi.getAddressDescriptionByCEP(cep);
				
				if(address == null) { return; }
				
				formPersonStreetField.setText(address.getLogradouro());
				formPersonStateComboBox.getSelectionModel().select(address.getUf());
				formPersonNeighborhoodField.setText(address.getBairro());
			}
		});
	}
	
	private void settingDatePicker() {
		formPersonBirthDateDatePicker.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			
			@Override
			public String toString(LocalDate localDate) {
				if(localDate==null) { return ""; }
		         
		        return dateTimeFormatter.format(localDate);
			}
			
			@Override
			public LocalDate fromString(String dateString) {
				if(dateString==null || dateString.trim().isEmpty()){ return null; }
				
		        return LocalDate.parse(dateString,dateTimeFormatter);
			}
		});
	}
	
	public Stage getMe() {
		return me;
	}

	public void setMe(Stage me) {
		this.me = me;
	}

	public boolean isCreatedForm() {
		return isCreatedForm;
	}

	public void setCreatedForm(boolean isCreatedForm) {
		this.isCreatedForm = isCreatedForm;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PersonController getFather() {
		return father;
	}

	public void setFather(PersonController father) {
		this.father = father;
	}

	

}
