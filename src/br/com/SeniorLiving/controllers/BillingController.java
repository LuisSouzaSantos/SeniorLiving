package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.ftt.ec6.seniorLiving.entities.Billing;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.entities.support.BillingSupport;
import br.com.ftt.ec6.seniorLiving.exception.BillingException;
import br.com.ftt.ec6.seniorLiving.mail.BuildMail;
import br.com.ftt.ec6.seniorLiving.service.BillingService;
import br.com.ftt.ec6.seniorLiving.service.impl.BillingServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.ViewUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BillingController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/BillingList.fxml";
	private final static BillingService billingService = BillingServiceImpl.getInstance();

	@FXML
	private TableView<BillingSupport> billingTable;
	
	@FXML
	private TableColumn<BillingSupport, Elderly> elderlyColumn;
	@FXML
	private TableColumn<BillingSupport, LocalDate> dueDateColumn;
	@FXML
	private TableColumn<BillingSupport, String> amountColumn;
	@FXML
	private TableColumn<BillingSupport, Pane> actionsColumn;
	
	@FXML
	private TextField textFieldEldelyName;
	@FXML
	private DatePicker dataPicckerBeginDate;
	@FXML
	private DatePicker dataPicckerFinalDate;
	@FXML
	private Slider sliderMaxValue;
	@FXML
	private Text textMaxValue;
	
	@FXML
	private Pane billingProgressIndicator;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		load(billingService.getBillingSupportByRestHome(getRestHomeActived()));
		config();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
    
    public void addNewBillingOnTable() {
	    if(billingTable == null) { return; }

	    billingTable.getItems().clear();
	    load(billingService.getBillingSupportByRestHome(getRestHomeActived()));
    }
    
    public void removeBillingSuppportOnTable(BillingSupport billingSupport) {
    	if((billingSupport == null) || (billingSupport.getId() == null)) { return; }
    	
    	if(billingTable == null) { return; }
    	
    	billingTable.getItems().remove(billingSupport);
    	billingTable.refresh();
    }
    
    public void updateBillingSuppportOnTable() {
    	if(billingTable == null) { return; }
    	
    	billingTable.getItems().clear();
    	load(billingService.getBillingSupportByRestHome(getRestHomeActived()));
    }
	
	private void load(List<BillingSupport> billingList) {
		billingTable.getItems().clear();
		
		elderlyColumn.setCellValueFactory(new PropertyValueFactory<>("elderly"));
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		
		billingList.forEach(billing -> {
			billingTable.getItems().add(billing);
		});

		BillingController currentBillingController = this;
		
		actionsColumn.setCellFactory(param -> new TableCell<BillingSupport, Pane>() {
			@Override
	        protected void updateItem(Pane item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty == false) {
	            	BillingSupport billingSupport = billingTable.getItems().get(getIndex());
	            	
	            	Pane pane = new Pane();
	            	ImageView editImage = ViewUtils.createImageView("EDIT:"+Long.toString(billingSupport.getId()), EDIT_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView deleteImage = ViewUtils.createImageView("DELETE:"+Long.toString(billingSupport.getId()), DELETE_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView sendImage = ViewUtils.createImageView("SEND:"+Long.toString(billingSupport.getId()), SEND_IAMGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			editImage.setLayoutX(10.0);
	            	deleteImage.setLayoutX(60.0);
	            	sendImage.setLayoutX(100.0);
	            	
	    			editImage.setOnMouseClicked(editBillingButtonAction(billingSupport, currentBillingController));
	    			deleteImage.setOnMouseClicked(deleteBillingButtonAction(billingSupport));
	    			sendImage.setOnMouseClicked(sendEmail(billingSupport));
	            	
	            	pane.getChildren().addAll(editImage, deleteImage, sendImage);
	            	setGraphic(pane);
	            	this.setItem(item);
	            }  
	        }
		});
	}
	
	@FXML
	private void openBillingFormButtonAction() throws IOException {
		Task<Object[]> task = new Task<Object[]>() {
			@Override
			protected Object[] call() throws Exception {
				initProgressIndicator();
				BillingFormController billingFormController = new BillingFormController();
				FXMLLoader loader = billingFormController.getFXMLLoader();
				Pane pane = loader.load();

				Object[] object = new Object[2];
				
				object[0] = pane;
				object[1] = loader.getController();
				
				return object;
			}
		};
		
		task.setOnSucceeded(e -> {
			Object[] object = task.getValue();
			
			Pane pane = (Pane) object[0];
			BillingFormController  billingFormControllerLoaded  = (BillingFormController) object[1];
			billingFormControllerLoaded.setCreatedForm(true);
			billingFormControllerLoaded.setBilling(null);
			billingFormControllerLoaded.setFather(this);
			billingFormControllerLoaded.performReload();
			
			Stage stage = new Stage();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.show();
			
			billingFormControllerLoaded.setMe(stage);
			stopProgressIndicator();
		});
	
		new Thread(task).start();
	}
	
	@FXML
	private void billingSearchButtonAction() {
		Task<List<BillingSupport>> task = new Task<List<BillingSupport>>() {
			@Override
			protected List<BillingSupport> call() throws Exception {
				initProgressIndicator();
				String elderlyName = textFieldEldelyName.getText();
				LocalDate initDate = dataPicckerBeginDate.getValue();
				LocalDate endDate = dataPicckerFinalDate.getValue();
				Double maxValue = sliderMaxValue.getValue();
				
				return billingService.billingFilter(elderlyName, getRestHomeActived(), initDate, endDate, maxValue);
			}
		};
		
		task.setOnSucceeded(e -> {
			List<BillingSupport> billingSupportList = task.getValue();
			load(billingSupportList);
			stopProgressIndicator();
		});
		
		new Thread(task).start();
	}
	
	@FXML
	private void cleanSearchButtonAction() {
		System.out.println("Oi");
	}
	
	private EventHandler<Event> editBillingButtonAction(BillingSupport billingSupport, BillingController father) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				Task<Object[]> task = new Task<Object[]>() {
					@Override
					protected Object[] call() throws Exception {
						initProgressIndicator();
						BillingFormController billingFormController = new BillingFormController();
						FXMLLoader loader = billingFormController.getFXMLLoader();
						Pane pane = loader.load();

						Object[] object = new Object[2];
						
						object[0] = pane;
						object[1] = loader.getController();
						
						return object;
					}
				};
				
				task.setOnSucceeded(e -> {
					Object[] object = task.getValue();
					
					Pane pane = (Pane) object[0];
					BillingFormController  billingFormControllerLoaded  = (BillingFormController) object[1];
					billingFormControllerLoaded.setCreatedForm(false);
					billingFormControllerLoaded.setBilling(billingSupport.getBilling());
					billingFormControllerLoaded.setFather(father);
					billingFormControllerLoaded.performReload();
					
					Stage stage = new Stage();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
					
					billingFormControllerLoaded.setMe(stage);
					stopProgressIndicator();
				});
			
				new Thread(task).start();
			}
		};
	}
	
	private EventHandler<Event> sendEmail(BillingSupport billingSupport) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				BuildMail  buildMail = new BuildMail();
				buildMail.buildElderlyBilling(billingSupport.getBilling());
			}
		};
	}
	
	
	private EventHandler<Event> deleteBillingButtonAction(BillingSupport billingSupport) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir o faturamento do idoso"+billingSupport.getBilling().getElderly().getName()+"("+billingSupport.getBilling().getMonth()+")");
				
				if(optionChosen != JOptionPane.YES_OPTION) { return; }
				
				try {
					deleteBilling(billingSupport.getBilling());
					JOptionPane.showMessageDialog(null, "O faturamento do idoso "+billingSupport.getBilling().getElderly().getName()+"("+billingSupport.getBilling().getMonth()+")"+"deletado com sucesso.");
					removeBillingSuppportOnTable(billingSupport);
				}catch (BillingException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}	
			}
		};
	}
	
	private void deleteBilling(Billing billing) throws BillingException {
		String messageInfo = billingService.delete(billing.getId());
		
		if(messageInfo == "ERROR") { throw new BillingException("Erro ao excluir o faturamento do idoso "+billing.getElderly().getName()+"("+billing.getMonth()+")"); }
	}
	
	private void config() {
		sliderConfig();
		datePickerConfig();
	}
	
	private void sliderConfig() {
		sliderMaxValue.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				textMaxValue.setText(String.format("%.2f", newValue));
			}
		});
		
		textMaxValue.setText(String.format("%.2f", 0.00));
		sliderMaxValue.setMin(0.00);
		sliderMaxValue.setMax(10000.00);
	}
	
	private void datePickerConfig() {
		dataPicckerBeginDate.setValue(LocalDate.now());
		dataPicckerFinalDate.setValue(LocalDate.now());
	}
	
	private void initProgressIndicator() {
		billingProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		billingProgressIndicator.setVisible(false);
	}

}