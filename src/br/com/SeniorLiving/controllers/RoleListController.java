package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RoleListController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/RoleList.fxml";
	
	@FXML
	private TableView<Role> tableViewRole;

	@FXML
	private TableColumn<Role, String> tableColumnRoleName;
	
	@FXML
	private Button btSearch;
	
	@FXML
	private TextField textSearch;
	
	@FXML
	private Button btNew;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableColumnRoleName.setCellValueFactory(new PropertyValueFactory<>("Nome"));
				
//		Stage stage = (Stage) Main.getMainScene().getWindow();
//		tableViewRole.prefHeightProperty().bind(stage.heightProperty());
	}

	private void initializeNodes() {
	}
			
		
	/*private void onBtNewAction(MouseEvent event) throws IOException {
		RoleController roleController = new RoleController();
		FXMLLoader loader = roleController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		
		Scene futureScene = new Scene(anchorPane);
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
		newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
	//	Main.getCurrentStage().close();
		
	}*/
	
	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));	
	}

}
