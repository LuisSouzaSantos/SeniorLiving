package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.AccommodationException;
import br.com.ftt.ec6.seniorLiving.service.AccommodationService;
import br.com.ftt.ec6.seniorLiving.service.impl.AccommodationServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.ServiceProxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccommodationFormController extends Controller implements Initializable {
	
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/AccommodationForm.fxml";
	private final AccommodationService accommodationService = (AccommodationService) ServiceProxy.newInstance(AccommodationServiceImpl.getInstance());
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_CREATE = {"NAME", "DESCRIPTION"};
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_UPDATE = {"NAME", "DESCRIPTION"};
	
	@FXML
	private TextField formAccommodationNameField;
	@FXML
	private TextArea formAccommodationDescriptionFieldArea;
	
	@FXML
	private Text formAccommodationErrorNameMessageText;
	@FXML
	private Text formAccommodationErrorDescriptionMessageText;
	@FXML
	private Text formAccommodationErrorMessageText;
	
	@FXML
	private Button accommodationFormCreateButton;
	@FXML
	private Button accommodationFormUpdateButton;

	private Stage me;
	private boolean isCreatedForm;
	private Accommodation accommodation;
	private AccommodationController father;
	
	
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
	
	private void initializeNodes() {
		cleanErrorMessages();
		clearForm();
		buttonFormAvailable();
		checkIfIsUpdateForm();
	}
	
	@FXML
	private void createNewAccommodationButtonAction() {
		try {
			if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_CREATE)) == false) { return; }
			
			String name = formAccommodationNameField.getText();
			String description = formAccommodationDescriptionFieldArea.getText();
			RestHome restHome = getRestHomeActived();
			
			accommodationService.save(name, description, restHome);
			closeMe();
		}catch(AccommodationException e) {
			formAccommodationErrorMessageText.setText(e.getMessage());
		}	
	}
	
	@FXML
	private void createUpdateAccommodationButtonAction() {
		try {
			if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_UPDATE)) == false) { return; }
			
			String name = formAccommodationNameField.getText();
			String description = formAccommodationDescriptionFieldArea.getText();
			
			accommodation.setName(name);
			accommodation.setDescription(description);
			
			accommodationService.update(accommodation);
			closeMe();
		}catch(AccommodationException e) {
			formAccommodationErrorMessageText.setText(e.getMessage());
		}	
	}
	
	@FXML
	private void closeAccommodationFormButtonAction() {
		clearForm();
		closeMe();
	}
	
	private void clearForm() {
		formAccommodationNameField.setText("");
		formAccommodationDescriptionFieldArea.setText("");
	}
	
	private void cleanErrorMessages() {
		formAccommodationErrorNameMessageText.setText("");
		formAccommodationErrorDescriptionMessageText.setText("");
		formAccommodationErrorMessageText.setText("");
	}
	
	private boolean isValidFields(List<String> fieldsToBeValidate){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields(fieldsToBeValidate);
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("NAME")) {
				formAccommodationErrorNameMessageText.setText(value);
			}
			
			if(key.equals("DESCRIPTION")) {
				formAccommodationErrorDescriptionMessageText.setText(value);
			}

		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields(List<String> fieldsToBeValidate) {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if((fieldsToBeValidate.contains("NAME")) && (formAccommodationNameField == null || formAccommodationNameField.getText().trim().isEmpty())) { 
			errorsList.put("NAME", "Nome não pode estar em branco.");
		} 
		
		if((fieldsToBeValidate.contains("DESCRIPTION")) && (formAccommodationDescriptionFieldArea == null || formAccommodationDescriptionFieldArea.getText().trim().isEmpty())) { 
			errorsList.put("DESCRIPTION", "Descrição não pode estar em branco.");
		}
		
		return errorsList;
	}
	
	private void buttonFormAvailable() {
		if(this.isCreatedForm) {
			this.accommodationFormCreateButton.setVisible(true);
			this.accommodationFormUpdateButton.setVisible(false);
		}else {
			this.accommodationFormCreateButton.setVisible(false);
			this.accommodationFormUpdateButton.setVisible(true);
		}
	}
	
	private void checkIfIsUpdateForm() {
		if(this.isCreatedForm) { return; }
		
		if(this.accommodation == null) { return; }
		
		formAccommodationNameField.setText(this.accommodation.getName());
		formAccommodationDescriptionFieldArea.setText(this.accommodation.getDescription());
	}
	
	private void closeMe() {
		this.clearForm();
		this.me.close();
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

	public Accommodation getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}

	public AccommodationController getFather() {
		return father;
	}

	public void setFather(AccommodationController father) {
		this.father = father;
	}

}
