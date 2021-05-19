package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.support.BillingProductForm;
import br.com.ftt.ec6.seniorLiving.entities.support.ComboBoxProduct;
import br.com.ftt.ec6.seniorLiving.service.ElderlyService;
import br.com.ftt.ec6.seniorLiving.service.ProductService;
import br.com.ftt.ec6.seniorLiving.service.impl.ElderlyServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.ProductServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.ViewUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BillingFormController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/BillingForm.fxml";
	private final ElderlyService elderlyService = ElderlyServiceImpl.getInstance();
	private final ProductService productService = ProductServiceImpl.getInstance();
	private static List<Product> productsAvailable = new ArrayList<>();
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_CREATE = {"DUE_DATE", "ELDERLY"};
	private final static String[] FIELDS_TO_BE_VALIDATE_IN_UPDATE = {"DUE_DATE", "ELDERLY"};
	
	
	@FXML
	private DatePicker formBillingDueDatePicker;
	@FXML
	private ComboBox<Elderly> formBillingElderlyComboBox;
	
	@FXML
	private TableView<BillingProductForm> billingProductTable;
	@FXML
	private TableColumn<BillingProductForm, ComboBox<Product>> productColumn;
	@FXML
	private TableColumn<BillingProductForm, String> unitaryValueColumn;
	@FXML
	private TableColumn<BillingProductForm, String> quantityColumn;
	@FXML
	private TableColumn<BillingProductForm, String> amountValue;
	@FXML
	private TableColumn<BillingProductForm, Pane> actionsColumn;
	
	@FXML
	private Text formBillingErrorDueDateMessageText;
	@FXML
	private Text formBillingErrorElderlyMessageText;
	@FXML
	private Text formBillingErrorMessageText;
	
	@FXML
	private Button billingFormAddItemButton;
	@FXML
	private Button billingFormCreateButton;
	@FXML
	private Button billingFormUpdateButton;
	@FXML
	private Button billingFormCancelButton;
	
	private Stage me;
	private boolean isCreatedForm;
	private Billing billing;
	private BillingController father;
	
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
	private void createNewBillingButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_CREATE)) == false) { return; }
		
	}
	
	@FXML
	private void updateBillingButtonAction() {
		if(isValidFields(Arrays.asList(FIELDS_TO_BE_VALIDATE_IN_UPDATE)) == false) { return; }
	}
	
	@FXML
	private void closeBillingFormButtonAction() {
		clearForm();
		closeMe();
	}
	
	@FXML
	private void addItemBillingButtonAction() {
		BillingProductForm billingProductForm = new BillingProductForm();
		ComboBoxProduct comboBox = new ComboBoxProduct();
		ObservableList<Product> observableProductList = FXCollections.observableArrayList(productsAvailable);
		comboBox.setItems(observableProductList);
		
		billingProductForm.setBillingProductTable(billingProductTable);
		billingProductForm.setComboBoxProduct(comboBox);
		billingProductForm.setUnitaryValue("Clique aqui para preecher");
		billingProductForm.setQuantity("Clique aqui para preecher");
		billingProductForm.setAmount("Total");
		
		billingProductTable.getItems().add(billingProductForm);
	
		actionsColumn.setCellFactory(param -> new TableCell<BillingProductForm, Pane>() {
			@Override
		    protected void updateItem(Pane item, boolean empty) {
				super.updateItem(item, empty);
		        if(empty == false) {
		            Pane pane = new Pane();
		            BillingProductForm billingProductForm = billingProductTable.getItems().get(getIndex());
		            ImageView lessItem = ViewUtils.createImageView("1", LESS_IMAGE, 20.0, 20.0, true, true, Cursor.HAND);
		            lessItem.setLayoutX(20.0);
		            lessItem.setOnMouseClicked(removeBillingFormControllerOnBillingListAction(billingProductForm));
		           
		            pane.getChildren().addAll(lessItem);
		            setGraphic(pane);
		        }  
		    }
		});
	}
	
	private void initializeNodes(){
		cleanErrorMessages();
		clearForm();
		buttonFormAvailable();
		checkIfIsUpdateForm();
		initializeTable();
		loadElderlyList();
		loadProductAvailable();
	}
	
	private void clearForm() {
		formBillingDueDatePicker.setValue(null);
		formBillingElderlyComboBox.setValue(null);
	}
	
	private void cleanErrorMessages() {
		formBillingErrorDueDateMessageText.setText("");
		formBillingErrorElderlyMessageText.setText("");
		formBillingErrorMessageText.setText("");
	}
	
	private boolean isValidFields(List<String> fieldsToBeValidate){
		cleanErrorMessages();
		
		Map<String, String> fieldsAndMessages = checkFields(fieldsToBeValidate);
		
		fieldsAndMessages.forEach((key, value) -> {
			if(key.equals("DUE_DATE")) {
				formBillingErrorDueDateMessageText.setText(value);
			}
			
			if(key.equals("ELDERLY")) {
				formBillingErrorElderlyMessageText.setText(value);
			}

		});
		
		return fieldsAndMessages.size() <= 0;
	}
	
	private Map<String, String> checkFields(List<String> fieldsToBeValidate) {
		Map<String, String> errorsList = new HashMap<String, String>();
		
		if((fieldsToBeValidate.contains("DUE_DATE")) && ((formBillingDueDatePicker == null) || (formBillingDueDatePicker.getValue() == null))) { 
			errorsList.put("DUE_DATE", "Data de Vencimento não pode estar em branco.");
		}
		
		if((fieldsToBeValidate.contains("ELDERLY")) && ((formBillingElderlyComboBox == null) || (formBillingElderlyComboBox.getValue() == null))) { 
			errorsList.put("ELDERLY", "Idoso não pode estar em branco.");
		}
		
		return errorsList;
	}
	
	private void buttonFormAvailable() {
		if(this.isCreatedForm) {
			this.billingFormCreateButton.setVisible(true);
			this.billingFormUpdateButton.setVisible(false);
		}else {
			this.billingFormCreateButton.setVisible(false);
			this.billingFormUpdateButton.setVisible(true);
		}
	}

	private void checkIfIsUpdateForm() {
		if(this.isCreatedForm) { return; }
		
		if(this.billing == null) { return; }
		
		//formTypeNameField.setText(this.type.getName());
	}

	private void loadElderlyList() {
		List<Elderly> elderlyList = elderlyService.getElderlyByRestHome(getRestHomeActived());
		
		if(elderlyList == null || elderlyList.isEmpty()) { return; }
		
		ObservableList<Elderly> observableSympatheticPersonList = FXCollections.observableArrayList(elderlyList);
		
		formBillingElderlyComboBox.setItems(observableSympatheticPersonList);
		formBillingElderlyComboBox.setCursor(Cursor.HAND);
	}
	
	private void initializeTable() {
		
		productColumn.setCellValueFactory(new PropertyValueFactory<>("comboBoxProduct"));
		
		unitaryValueColumn.setCellValueFactory(new PropertyValueFactory<>("unitaryValue"));
		unitaryValueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		unitaryValueColumn.setOnEditCommit((TableColumn.CellEditEvent<BillingProductForm, String> t) ->
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setUnitaryValue(
					t.getNewValue().startsWith("R$")?t.getNewValue():"R$"+t.getNewValue()
			)
		);
		
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		quantityColumn.setOnEditCommit((TableColumn.CellEditEvent<BillingProductForm, String> t) ->
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity(t.getNewValue())
		);
		
		amountValue.setCellValueFactory(new PropertyValueFactory<>("amount"));
		
	}
	
	private void loadProductAvailable() {
		productsAvailable = productService.getProductByRestHome(getRestHomeActived());
	}
	
	private EventHandler<Event> removeBillingFormControllerOnBillingListAction(BillingProductForm billingProductForm) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				billingProductTable.getItems().remove(billingProductForm);
				billingProductTable.refresh();
			}
		};
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

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public BillingController getFather() {
		return father;
	}

	public void setFather(BillingController father) {
		this.father = father;
	}

}
