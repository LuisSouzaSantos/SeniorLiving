package gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import db.ConnectionUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController implements Initializable {
    
    @FXML
    private TextField textEmail;
    
    @FXML
    private PasswordField textPassword;
    
    Stage dialogStage = new Stage();
    Scene scene;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public LoginController() {
        connection = ConnectionUtil.connectdb();
    }
    
    
    
    public void loginAction(ActionEvent event){
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
    
        String sql = "SELECT * FROM employee WHERE email = ? and password = ?";
        
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                infoBox("Please enter correct Email and Password", null, "Failed");
            }else{
                infoBox("Login Successfull",null,"Success" );
                Node node = (Node)event.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
                scene = new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml")));
                dialogStage.setScene(scene);
                dialogStage.show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
}
/*import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import model.entities.Person;
import model.services.DepartmentService;
import model.services.PersonService;


public class LoginController implements Initializable {

	
	
    @FXML
    private PasswordField password;

    @FXML
    private TextField email;


    @FXML
    private Button signup;

    @FXML
    private Button login;
    
    @Override
    public void initialize(URL url, ResourceBundle db) {
		initializeNodes();
    }
    private void initializeNodes() {
		Constraints.setTextFieldMaxLength(email, 30);
		Constraints.setTextFieldMaxLength(password, 30);		
	}
	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Person obj = new Person();
		createDialogForm(obj, "/gui/UserForm.fxml", parentStage);
	}
	
	private void createDialogForm(Person obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PersonFormController controller = loader.getController();
			controller.setPerson(obj);
			controller.setServices(new PersonService(), new DepartmentService());
			controller.loadAssociateObjects();
			//controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter User data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void Login() {
		System.out.println("Logado");
		
	}
	
}

   /*     /*	
        	Stage home = new Stage();
        	Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        	home.setScene(new Scene(root));
        	home.show();
        

    @FXML
    void createregister(ActionEvent event) throws IOException {
    	
    	
    	Stage signup = new Stage();
    	Parent root = FXMLLoader.load(getClass().getResource("CadastroUsuario.fxml"));
    	Scene scene = new Scene(root);
    	signup.setScene(scene);
    	signup.show();

    }
    */
    
