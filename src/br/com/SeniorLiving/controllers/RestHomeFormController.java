package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.entities.support.Address;
import br.com.ftt.ec6.seniorLiving.entities.support.State;
import br.com.ftt.ec6.seniorLiving.service.RestHomeService;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.service.impl.RestHomeServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.UserServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.ExternoApi;
import br.com.ftt.ec6.seniorLiving.utils.SupportProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class RestHomeFormController extends FormController<RestHomeController> implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/RestHomeForm.fxml";
	private final RestHomeService restHomeService = RestHomeServiceImpl.getInstance();
	private final UserService userService = UserServiceImpl.getInstance();
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_CREATE = {"SOCIAL_REASON", "CNPJ", "ADMIN", "CEP", "STREET", "STREET_NUMBER", "STREET_STATE", "STREET_NEIGHBORHOOD"};
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_UPDATE = {"SOCIAL_REASON", "CNPJ", "ADMIN", "CEP", "STREET", "STREET_NUMBER", "STREET_STATE", "STREET_NEIGHBORHOOD"};
	
	@FXML
	private TextField formRestHomeSocialReasonField;
	@FXML
	private TextField formRestHomeCNPJField;
	@FXML
	private ComboBox<User> formRestHomeAdminComboBox;
	@FXML
	private TextField formResetHomeCEPField;
	@FXML
	private TextField formRestHomeStreetField;
	@FXML
	private TextField formRestHomeStreetNumberField;
	@FXML
	private ComboBox<String> formRestHomeStateComboBox;
	@FXML
	private TextField formRestHomeNeighborhoodField;
	
	@FXML
	private Text formRestHomeErrorSocialReasonMessageText;
	@FXML
	private Text formRestHomeErrorCNPJMessageText;
	@FXML
	private Text formRestHomeErrorAdminMessageText;
	@FXML
	private Text formRestHomeErrorCEPMessageText;
	@FXML
	private Text formRestHomeErrorStreetMessageText;
	@FXML
	private Text formRestHomeErrorStreetNumberMessageText;
	@FXML
	private Text formRestHomeErrorStateMessageText;
	@FXML
	private Text formRestHomeErrorNeighborhoodMessageText;
	@FXML
	private Text formRestHomeErrorMessageText;
	
	@FXML
	private Button restHomeFormCreateButton;
	@FXML
	private Button restHomeFormUdpateButton;
	@FXML
	private Button restHomeFormCloseButton;
	
	@FXML
	private Pane restHomeProgressIndicator;
	
	private RestHome restHome;
	
    @Override
    public void initialize(URL url, ResourceBundle db) {
    	initializeNodes();
    }
    
	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	public RestHome getRestHome() {
		return restHome;
	}

	public void setRestHome(RestHome restHome) {
		this.restHome = restHome;
	}
	
	
	public void performReload() {
		initializeNodes();
	}
	
	private void initializeNodes() {
		cleanErrorMessages();
		clearForm();
		buttonFormAvailable();
		loadStatesOnComboBox();
		loadUsersOnComboBox();
		checkIfIsUpdateForm();
		functionInFields();
	}
	
	public void cleanErrorMessages() {
		formRestHomeErrorSocialReasonMessageText.setText("");
		formRestHomeErrorCNPJMessageText.setText("");
		formRestHomeErrorAdminMessageText.setText("");
		formRestHomeErrorCEPMessageText.setText("");
		formRestHomeErrorStreetMessageText.setText("");
		formRestHomeErrorStreetNumberMessageText.setText("");
		formRestHomeErrorStateMessageText.setText("");
		formRestHomeErrorNeighborhoodMessageText.setText("");
		formRestHomeErrorMessageText.setText("");
	}
	
	public void clearForm() {
		formRestHomeSocialReasonField.setText("");
		formRestHomeCNPJField.setText("");
		formRestHomeAdminComboBox.setItems(null);
		formResetHomeCEPField.setText("");
		formRestHomeStreetField.setText("");
		formRestHomeStreetNumberField.setText("");
		formRestHomeStateComboBox.setItems(null);
		formRestHomeNeighborhoodField.setText("");
	}
	
	private void buttonFormAvailable() {
		if(this.isCreatedForm) {
			this.restHomeFormCreateButton.setVisible(true);
			this.restHomeFormUdpateButton.setVisible(false);
		}else {
			this.restHomeFormCreateButton.setVisible(false);
			this.restHomeFormUdpateButton.setVisible(true);
		}
	}
	
	private void checkIfIsUpdateForm() {
		if(this.isCreatedForm) { return; }
		
		if(this.restHome == null) { return; }
		
		formRestHomeSocialReasonField.setText(this.restHome.getSocialReason());
		formRestHomeCNPJField.setText(this.restHome.getCnpj());
		formResetHomeCEPField.setText(this.restHome.getAddressCep());
		formRestHomeStreetField.setText(this.restHome.getAddressStreet());
		formRestHomeStreetNumberField.setText(this.restHome.getAddressNumber());
		formRestHomeNeighborhoodField.setText(this.restHome.getAddressNeighborhood());
		formRestHomeAdminComboBox.getSelectionModel().select(this.restHome.getAdmin());
		formRestHomeStateComboBox.getSelectionModel().select(this.restHome.getAddressState());
	}
	
	private void loadUsersOnComboBox() {
		List<User> users = userService.getUsersByRole("ADMIN_LOCAL");
		
		if(users == null || users.isEmpty()) { return; }
		
		ObservableList<User> observableUsers =  FXCollections.observableArrayList(users);
		formRestHomeAdminComboBox.setItems(observableUsers);
		
		Callback<ListView<User>, ListCell<User>> factory = lv -> new ListCell<User>() {
		    @Override
		    protected void updateItem(User user, boolean empty) {
		        super.updateItem(user, empty);
		        setText(empty ? "" : user.getEmail());
		    }
		};
		
		formRestHomeAdminComboBox.setCellFactory(factory);
		formRestHomeAdminComboBox.setCursor(Cursor.CLOSED_HAND);
	}
	
	private void loadStatesOnComboBox() {
		SupportProperties supportProperties = SupportProperties.getInstance();
		List<State> states = supportProperties.getStates();
		
		if(states == null) { return; }
		
		List<String> statesName = states.stream().map(State::getSigla).collect(Collectors.toList());
		
		ObservableList<String> observableState =  FXCollections.observableArrayList(statesName);
		
		formRestHomeStateComboBox.setItems(observableState);
		formRestHomeStateComboBox.setCursor(Cursor.CLOSED_HAND);
	}
	
	private void functionInFields() {
		loadAddressAutomatic();
	}
	
	private void loadAddressAutomatic() {
		formResetHomeCEPField.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(formResetHomeCEPField.getText().trim().isEmpty()) { return; }
				
				String cep = formResetHomeCEPField.getText();
				
				Address address = ExternoApi.getAddressDescriptionByCEP(cep);
				
				if(address == null) { return; }
				
				formRestHomeStreetField.setText(address.getLogradouro());
				formRestHomeStateComboBox.getSelectionModel().select(address.getUf());
				formRestHomeNeighborhoodField.setText(address.getBairro());
			}
		});
	}
	
	@FXML
	private void createRestHomeButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_CREATE)) == false) { return; }
		
		String socialReason = formRestHomeSocialReasonField.getText();
		String cnpj = formRestHomeCNPJField.getText();			
		User adminUser = formRestHomeAdminComboBox.getValue();
		String cep = formResetHomeCEPField.getText();
		String street = formRestHomeStreetField.getText();
		String streetNumber = formRestHomeStreetNumberField.getText();			
		String state = formRestHomeStateComboBox.getValue();
		String streetNeighborhood = formRestHomeNeighborhoodField.getText();
			
		Task<RestHome> createNewRestHomeTask = createNewRestHomeTask(socialReason, cnpj, adminUser, cep, street, streetNumber, state, streetNeighborhood);
			
		new Thread(createNewRestHomeTask).start();
	}
	
	private Task<RestHome> createNewRestHomeTask(String socialReason, String cnpj, User adminUser, String cep,
			String street, String streetNumber, String state, String streetNeighborhood) {
		
		Task<RestHome> task = new Task<RestHome>() {
			@Override
			protected RestHome call() throws Exception {
				initProgressIndicator();
				return restHomeService.save(socialReason, cnpj, street, streetNumber, state, cep, streetNeighborhood, adminUser, null, null, null);
			}
		};
		
		task.setOnSucceeded(e -> {
			RestHome restHomeCreated = task.getValue();
			closeMe();
			father.addNewRestHomeOnTable(restHomeCreated);
			stopProgressIndicator();
			Thread.interrupted();
		});
		
		task.setOnFailed(e -> {
			if(task != null && task.getException() != null && task.getException().getMessage() != null) {
				formRestHomeErrorMessageText.setText(task.getException().getMessage());
			}
			stopProgressIndicator();
			Thread.interrupted();
		});
		
		return task;
	}

	@FXML
	private void updateRestHomeButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_UPDATE)) == false) { return; }
			
		String socialReason = formRestHomeSocialReasonField.getText();
		String cnpj = formRestHomeCNPJField.getText();			
		User adminUser = formRestHomeAdminComboBox.getValue();
		String cep = formResetHomeCEPField.getText();
		String street = formRestHomeStreetField.getText();
		String streetNumber = formRestHomeStreetNumberField.getText();			
		String state = formRestHomeStateComboBox.getValue();
		String streetNeighborhood = formRestHomeNeighborhoodField.getText();
			
		Task<RestHome> restHomeUpdate = updateRestHomeTask(socialReason, cnpj, adminUser, cep, street, streetNumber, state, streetNeighborhood);
			
		new Thread(restHomeUpdate).start();
	}
	
	private Task<RestHome> updateRestHomeTask(String socialReason, String cnpj, User adminUser, String cep,
			String street, String streetNumber, String state, String streetNeighborhood) {
		Task<RestHome> task = new Task<RestHome>() {
			@Override
			protected RestHome call() throws Exception {
				initProgressIndicator();
				
				restHome.setSocialReason(socialReason);
				restHome.setCnpj(cnpj);
				restHome.setAdmin(adminUser);
				restHome.setAddressCep(cep);
				restHome.setAddressStreet(street);
				restHome.setAddressNumber(streetNumber);
				restHome.setAddressState(state);
				restHome.setAddressNeighborhood(streetNeighborhood);
				
				return restHomeService.update(restHome);
			}
		};
		
		task.setOnSucceeded(e -> {
			RestHome restHomeUpdated = task.getValue();
			closeMe();
			father.updateRestHomeOnTable(restHomeUpdated);
			stopProgressIndicator();	
			Thread.interrupted();
		});
		
		task.setOnFailed(e -> {
			if(task != null && task.getException() != null && task.getException().getMessage() != null) {
				formRestHomeErrorMessageText.setText(task.getException().getMessage());
			}
			stopProgressIndicator();
			Thread.interrupted();
		});
		
		return task;
	}

	@FXML
	private void closeRestHomeFormButtonAction() {
		closeMe();
	}

	private boolean isValidFields(List<String> fieldsToBeValidate){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields(fieldsToBeValidate);
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("SOCIAL_REASON")) {
				formRestHomeErrorSocialReasonMessageText.setText(value);
			}
			
			if(key.equals("CNPJ")) {
				formRestHomeErrorCNPJMessageText.setText(value);
			}
			
			if(key.equals("ADMIN")) {
				formRestHomeErrorAdminMessageText.setText(value);
			}
			
			if(key.equals("CEP")) {
				formRestHomeErrorCEPMessageText.setText(value);
			}
			
			if(key.equals("STREET")) {
				formRestHomeErrorStreetMessageText.setText(value);
			}
			
			if(key.equals("STREET_NUMBER")) {
				formRestHomeErrorStreetNumberMessageText.setText(value);
			}
			
			if(key.equals("STREET_STATE")) {
				formRestHomeErrorStateMessageText.setText(value);
			}
			
			if(key.equals("STREET_NEIGHBORHOOD")) {
				formRestHomeErrorNeighborhoodMessageText.setText(value);
			}
		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields(List<String> fieldsToBeValidate) {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if((fieldsToBeValidate.contains("SOCIAL_REASON")) && ((formRestHomeSocialReasonField == null) || (formRestHomeSocialReasonField.getText().trim().isEmpty()))) { 
			errorsList.put("SOCIAL_REASON", "Razão Social não pode estar em branco.");
		} 
		
		if((fieldsToBeValidate.contains("CNPJ")) && ((formRestHomeCNPJField == null) || (formRestHomeCNPJField.getText().trim().isEmpty()))) { 
			errorsList.put("CNPJ", "CNPJ não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("ADMIN")) && ((formRestHomeAdminComboBox == null) || (formRestHomeAdminComboBox.getValue() == null))) { 
			errorsList.put("ADMIN", "Admin não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("CEP")) && ((formResetHomeCEPField == null) || formResetHomeCEPField.getText().trim().isEmpty())) { 
			errorsList.put("CEP", "CEP não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("STREET")) && ((formRestHomeStreetField == null) || (formRestHomeStreetField.getText().trim().isEmpty()))) {
			errorsList.put("STREET", "Rua não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("STREET_NUMBER")) && ((formRestHomeStreetNumberField == null) || (formRestHomeStreetNumberField.getText().trim().isEmpty()))) {
			errorsList.put("STREET_NUMBER", "Número da rua não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("STREET_STATE")) && ((formRestHomeStateComboBox == null) || (formRestHomeStateComboBox.getValue() == null))) {
			errorsList.put("STREET", "Estado não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("STREET_NEIGHBORHOOD")) && ((formRestHomeNeighborhoodField == null) || (formRestHomeNeighborhoodField.getText().trim().isEmpty()))) {
			errorsList.put("STREET_NEIGHBORHOOD", "Bairro não pode estar em branco.");
		}
		
		return errorsList;
	}

	
	private void closeMe() {
		clearForm();
		this.me.close();
	}
	
	private void initProgressIndicator() {
		restHomeProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		restHomeProgressIndicator.setVisible(false);
	}
	
}
