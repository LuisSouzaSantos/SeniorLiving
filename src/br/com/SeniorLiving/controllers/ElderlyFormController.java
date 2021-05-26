package br.com.SeniorLiving.controllers;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.service.AccommodationService;
import br.com.ftt.ec6.seniorLiving.service.ElderlyService;
import br.com.ftt.ec6.seniorLiving.service.PersonService;
import br.com.ftt.ec6.seniorLiving.service.TypeService;
import br.com.ftt.ec6.seniorLiving.service.impl.AccommodationServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.ElderlyServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.PersonServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.TypeServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.MaritalStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ElderlyFormController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/ElderlyForm.fxml";
	private final static TypeService typeService =  TypeServiceImpl.getInstance();
	private final static ElderlyService elderlyService = ElderlyServiceImpl.getInstance();
	private final static PersonService personService = PersonServiceImpl.getInstance();
	private final static AccommodationService accommodationService = AccommodationServiceImpl.getInstance();
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_CREATE = {"NAME", "CIVIL_STATE", "NACIONALITY", "BIRTH_DATE", "CURATOR", "RG", "CPF", "MONTHLY_PAYMENT", "SYMPATHETIC", "ACCOMMODATION"};
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_UPDATE = {"NAME", "CIVIL_STATE", "NACIONALITY", "BIRTH_DATE", "CURATOR", "RG", "CPF", "MONTHLY_PAYMENT", "SYMPATHETIC", "ACCOMMODATION"};
	
	
	@FXML
	private TextField formElderlyNameField;
	@FXML
	private ComboBox<MaritalStatus> formElderlyMaritalStatusComboBox;
	@FXML
	private TextField formElderlyNationalityField;
	@FXML
	private DatePicker formElderlyBirthDateDatePicker;
	@FXML
	private ComboBox<Person> formElderlyCuratorComboBox;
	@FXML
	private TextField formElderlyRGField;
	@FXML
	private TextField formElderlyCPFField;
	@FXML
	private TextField formElderlyMonthlyPaymentField;
	@FXML
	private ComboBox<Person> formElderlySympatheticComboBox;
	@FXML
	private ComboBox<Accommodation> formElderlyAccommodationComboBox;

	@FXML
	private Text formElderlyErrorNameMessageText;
	@FXML
	private Text formElderlyErrorMaritalStatusMessageText;
	@FXML
	private Text formElderlyErrorNationalityMessageText;
	@FXML
	private Text formElderlyErrorBirthDateMessageText;
	@FXML
	private Text formElderlyErrorCuratorMessageText;
	@FXML
	private Text formElderlyErrorRGMessageText;
	@FXML
	private Text formElderlyErrorCPFMessageText;
	@FXML
	private Text formElderlyErrorMonthlyPaymentMessageText;
	@FXML
	private Text formElderlyErrorSympatheticMessageText;
	@FXML
	private Text formElderlyErrorAccommodationMessageText;
	@FXML
	private Text formElderlyErrorMessageText;
	
	@FXML
	private Button elderlyFormCreateButton;
	@FXML
	private Button elderlyFormUpdateButton;
	@FXML
	private Button elderlyFormCancelButton;
	
	@FXML
	private Pane elderlyFormProgressIndicator;
	
	private Stage me;
	private boolean isCreatedForm;
	private Elderly elderly;
	private ElderlyController father;
	
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
	private void createNewElderlyButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_CREATE)) == false) { return; }
		
		String name = formElderlyNameField.getText();
		String nacionality = formElderlyNationalityField.getText();
		MaritalStatus maritalStatus = formElderlyMaritalStatusComboBox.getValue();
		String rg = formElderlyRGField.getText();
		String cpf = formElderlyCPFField.getText();
		LocalDate birthDate = formElderlyBirthDateDatePicker.getValue();
		Person curator = formElderlyCuratorComboBox.getValue();
		Person sympathetic = formElderlySympatheticComboBox.getValue();
		Accommodation accommodation = formElderlyAccommodationComboBox.getValue();
		BigDecimal monthlyPayment = new BigDecimal(formElderlyMonthlyPaymentField.getText());
		
		Task<Elderly> elderlyTask = createNewElderlyTask(name, maritalStatus, nacionality, rg, cpf, birthDate, monthlyPayment, curator, sympathetic, getRestHomeActived(), accommodation, null);

		new Thread(elderlyTask).start();
	}
	
	private Task<Elderly> createNewElderlyTask(String name, MaritalStatus maritalStatus, String nacionality, String rg,
			String cpf, LocalDate birthDate, BigDecimal monthlyPayment, Person curator, Person sympathetic,
			RestHome restHomeActived, Accommodation accommodation, Object object) {
		
		Task<Elderly> task = new Task<Elderly>() {
			@Override
			protected Elderly call() throws Exception {
				initProgressIndicator();
				return elderlyService.save(name, maritalStatus, nacionality, rg, cpf, birthDate, monthlyPayment, curator, sympathetic, getRestHomeActived(), accommodation, null);
			}
		};
		
		task.setOnSucceeded(e -> {
			Elderly elderlyCreated = task.getValue();
			closeMe();
			father.addNewElderlyOnTable(elderlyCreated);
			stopProgressIndicator();
		});
		
		task.setOnFailed(e -> {
			if(task != null && task.getException() != null && task.getException().getMessage() != null) {
				formElderlyErrorMessageText.setText(task.getException().getMessage());
			}
			stopProgressIndicator();
		});
		
		return task;
	}

	@FXML
	private void updateElderlyButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_UPDATE)) == false) { return; }
		
		String name = formElderlyNameField.getText();
		String nacionality = formElderlyNationalityField.getText();
		MaritalStatus maritalStatus = formElderlyMaritalStatusComboBox.getValue();
		String rg = formElderlyRGField.getText();
		String cpf = formElderlyCPFField.getText();
		LocalDate birthDate = formElderlyBirthDateDatePicker.getValue();
		Person curator = formElderlyCuratorComboBox.getValue();
		Person sympathetic = formElderlySympatheticComboBox.getValue();
		Accommodation accommodation = formElderlyAccommodationComboBox.getValue();
		BigDecimal monthlyPayment = new BigDecimal(formElderlyMonthlyPaymentField.getText());
		
		Task<Elderly> elderlyTask = updateElderlyTask(name, nacionality, maritalStatus, rg, cpf, birthDate, curator, sympathetic, accommodation, monthlyPayment);
		
		new Thread(elderlyTask).start();
	}
	
	private Task<Elderly> updateElderlyTask(String name, String nacionality, MaritalStatus maritalStatus, String rg,
			String cpf, LocalDate birthDate, Person curator, Person sympathetic, Accommodation accommodation,
			BigDecimal monthlyPayment) {
		
		Task<Elderly> task = new Task<Elderly>() {
			@Override
			protected Elderly call() throws Exception {
				initProgressIndicator();
				
				elderly.setName(name);
				elderly.setNationality(nacionality);
				elderly.setMaritalStatus(maritalStatus);
				elderly.setRg(rg);
				elderly.setCpf(cpf);
				elderly.setBirthDate(birthDate);
				elderly.setCurator(curator);
				elderly.setSympathetic(sympathetic);
				elderly.setAccommodation(accommodation);
				elderly.setMonthlyPayment(monthlyPayment);

				return elderlyService.update(elderly);
			}
		};
		
		task.setOnSucceeded(e -> {
			Elderly elderlyUpdated = task.getValue();
			closeMe();
			father.updateElderlyOnTable(elderlyUpdated);
			stopProgressIndicator();
		});
		
		task.setOnFailed(e -> {
			if(task != null && task.getException() != null && task.getException().getMessage() != null) {
				formElderlyErrorMessageText.setText(task.getException().getMessage());
			}
			stopProgressIndicator();
		});
		
		return task;
	}

	@FXML
	private void closeElderlyFormButtonAction() {
		clearForm();
		closeMe();
	}
	
	private void initializeNodes(){
		if(father == null) { return; } 
		
		cleanErrorMessages();
		clearForm();
		buttonFormAvailable();
		checkIfIsUpdateForm();
		loadCivilState();
		loadCuratorPerson();
		loadSympatheticPerson();
		loadAccommodation();
		settingDatePicker();
	}
	
	private void clearForm() {
		formElderlyNameField.setText("");
		formElderlyNationalityField.setText("");
		//formElderlyMaritalStatusComboBox.setItems(null);
		formElderlyRGField.setText("");
		formElderlyCPFField.setText("");
		formElderlyMonthlyPaymentField.setText("");
		//formElderlyAccommodationComboBox.setItems(null);
		//formElderlyCuratorComboBox.setItems(null);
		//formElderlySympatheticComboBox.setItems(null);
		//formElderlyBirthDateDatePicker.setValue(null);
	}
	
	private void cleanErrorMessages() {
		formElderlyErrorNameMessageText.setText("");
		formElderlyErrorMaritalStatusMessageText.setText("");
		formElderlyErrorNationalityMessageText.setText("");
		formElderlyErrorBirthDateMessageText.setText("");
		formElderlyErrorCuratorMessageText.setText("");
		formElderlyErrorRGMessageText.setText("");
		formElderlyErrorCPFMessageText.setText("");
		formElderlyErrorMonthlyPaymentMessageText.setText("");
		formElderlyErrorSympatheticMessageText.setText("");
		formElderlyErrorAccommodationMessageText.setText("");
		formElderlyErrorMessageText.setText("");
	}
	
	private boolean isValidFields(List<String> fieldsToBeValidate){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields(fieldsToBeValidate);
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("NAME")) {
				formElderlyErrorNameMessageText.setText(value);
			}
			
			if(key.equals("CIVIL_STATE")) {
				formElderlyErrorMaritalStatusMessageText.setText(value);
			}
			
			if(key.equals("NACIONALITY")) {
				formElderlyErrorNationalityMessageText.setText(value);
			}
			
			if(key.equals("BIRTH_DATE")) {
				formElderlyErrorBirthDateMessageText.setText(value);
			}
			
			if(key.equals("CURATOR")) {
				formElderlyErrorCuratorMessageText.setText(value);
			}

			if(key.equals("RG")) {
				formElderlyErrorRGMessageText.setText(value);
			}
			
			if(key.equals("CPF")) {
				formElderlyErrorCPFMessageText.setText(value);
			}
			
			if(key.equals("MONTHLY_PAYMENT")) {
				formElderlyErrorMonthlyPaymentMessageText.setText(value);
			}
			
			if(key.equals("SYMPATHETIC")) {
				formElderlyErrorSympatheticMessageText.setText(value);
			}
			
			if(key.equals("ACCOMMODATION")) {
				formElderlyErrorAccommodationMessageText.setText(value);
			}
			
		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields(List<String> fieldsToBeValidate) {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if((fieldsToBeValidate.contains("NAME")) && (formElderlyNameField == null || formElderlyNameField.getText().trim().isEmpty())) { 
			errorsList.put("NAME", "Nome não pode estar em branco.");
		}

		if((fieldsToBeValidate.contains("CIVIL_STATE")) && ((formElderlyMaritalStatusComboBox == null) || (formElderlyMaritalStatusComboBox.getValue() == null))) { 
			errorsList.put("CIVIL_STATE", "Estado civil não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("NACIONALITY")) && (formElderlyNationalityField == null || formElderlyNationalityField.getText().trim().isEmpty())) { 
			errorsList.put("NACIONALITY", "Nacionalidade não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("BIRTH_DATE")) && ((formElderlyBirthDateDatePicker == null) || (formElderlyBirthDateDatePicker.getValue() == null))) { 
			errorsList.put("BIRTH_DATE", "Data de Nascimento não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("CURATOR")) && ((formElderlyCuratorComboBox == null) || (formElderlyCuratorComboBox.getValue() == null))) { 
			errorsList.put("CURATOR", "Curador não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("RG")) && ((formElderlyRGField == null) || (formElderlyRGField.getText().trim().isEmpty()))) { 
			errorsList.put("RG", "RG não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("CPF")) && ((formElderlyCPFField == null) || (formElderlyCPFField.getText().trim().isEmpty()))) { 
			errorsList.put("CPF", "CPF não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("MONTHLY_PAYMENT")) && ((formElderlyMonthlyPaymentField == null) || (formElderlyMonthlyPaymentField.getText().trim().isEmpty()))) { 
			errorsList.put("MONTHLY_PAYMENT", "Pagamento mensal pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("SYMPATHETIC")) && ((formElderlySympatheticComboBox == null) || (formElderlySympatheticComboBox.getValue() == null))) { 
			errorsList.put("SYMPATHETIC", "Solidário não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("ACCOMMODATION")) && ((formElderlyAccommodationComboBox == null) || (formElderlyAccommodationComboBox.getValue() == null))) { 
			errorsList.put("ACCOMMODATION", "Acomodação não pode estar em branco.");
		}

		return errorsList;
	}
	
	private void buttonFormAvailable() {
		if(this.isCreatedForm) {
			this.elderlyFormCreateButton.setVisible(true);
			this.elderlyFormUpdateButton.setVisible(false);
		}else {
			this.elderlyFormCreateButton.setVisible(false);
			this.elderlyFormUpdateButton.setVisible(true);
		}
	}
	
	private void checkIfIsUpdateForm() {
		if(this.isCreatedForm) { return; }
		
		if(this.elderly == null) { return; }
		
		formElderlyNameField.setText(this.elderly.getName());
		formElderlyNationalityField.setText(this.elderly.getNationality());
		formElderlyMaritalStatusComboBox.getSelectionModel().select(this.elderly.getMaritalStatus());
		formElderlyBirthDateDatePicker.setValue(this.elderly.getBirthDate());
		formElderlyCuratorComboBox.getSelectionModel().select(this.elderly.getCurator());
		formElderlyRGField.setText(this.elderly.getRg());
		formElderlyCPFField.setText(this.elderly.getCpf());
		formElderlyMonthlyPaymentField.setText(this.elderly.getMonthlyPayment().toString());
		formElderlySympatheticComboBox.getSelectionModel().select(this.elderly.getSympathetic());
		formElderlyAccommodationComboBox.getSelectionModel().select(this.elderly.getAccommodation());
	}
	
	private void closeMe() {
		this.clearForm();
		this.me.close();
	}
	
	private void loadCivilState() {
		ObservableList<MaritalStatus> observableMarialStatus = FXCollections.observableArrayList(MaritalStatus.values());
		
		formElderlyMaritalStatusComboBox.setItems(observableMarialStatus);
		formElderlyMaritalStatusComboBox.setCursor(Cursor.HAND);
	}
	
	private void loadCuratorPerson() {
		Type type = typeService.getTypeByName("CURADOR");
		
		if(type == null) { return; }
			
		List<Person> personList = personService.getPersonByRestHomeAndType(getRestHomeActived(), type);
		
		if(personList == null || personList.isEmpty()) { return; }
		
		ObservableList<Person> observableCuratorPersonList = FXCollections.observableArrayList(personList);
		
		formElderlyCuratorComboBox.setItems(observableCuratorPersonList);
		formElderlyCuratorComboBox.setCursor(Cursor.HAND);
	}
	
	private void loadSympatheticPerson() {
		Type type = typeService.getTypeByName("SOLIDARIO");
		
		if(type == null) { return; }
		
		List<Person> personList = personService.getPersonByRestHomeAndType(getRestHomeActived(), type);
		
		if(personList == null || personList.isEmpty()) { return; }
		
		ObservableList<Person> observableSympatheticPersonList = FXCollections.observableArrayList(personList);
		
		formElderlySympatheticComboBox.setItems(observableSympatheticPersonList);
		formElderlySympatheticComboBox.setCursor(Cursor.HAND);
	}
	
	private void loadAccommodation() {
		List<Accommodation> accommodationList = accommodationService.getAccommodationByRestHome(getRestHomeActived());
		
		if(accommodationList == null || accommodationList.isEmpty()) { return; }
		
		ObservableList<Accommodation> observableAccommodationList = FXCollections.observableArrayList(accommodationList);
		
		formElderlyAccommodationComboBox.setItems(observableAccommodationList);
		formElderlyAccommodationComboBox.setCursor(Cursor.HAND);
	}
	
	private void settingDatePicker() {
		formElderlyBirthDateDatePicker.setConverter(new StringConverter<LocalDate>() {
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

	public Elderly getElderly() {
		return elderly;
	}

	public void setElderly(Elderly elderly) {
		this.elderly = elderly;
	}

	public ElderlyController getFather() {
		return father;
	}

	public void setFather(ElderlyController father) {
		this.father = father;
	}

	public boolean isCreatedForm() {
		return isCreatedForm;
	}

	public void setCreatedForm(boolean isCreatedForm) {
		this.isCreatedForm = isCreatedForm;
	}
	
	private void initProgressIndicator() {
		elderlyFormProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		elderlyFormProgressIndicator.setVisible(false);
	}

}
