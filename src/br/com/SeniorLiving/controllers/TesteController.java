package br.com.SeniorLiving.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TesteController implements Initializable{

	@FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private AnchorPane pane3;

    @FXML
    private AnchorPane pane4,signupPane,signinPane;

    @FXML
    private JFXTextField nameTF,usernameTF,addressTF,phoneNumberTF,specTF,usernameLogin;

    @FXML
    private JFXPasswordField passwordTF,confirmPasswordTF,passwordLogin;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

     //   teachersList=new ArrayList<>();

        pane1.setStyle("-fx-background-image: url(\"/br/com/SeniorLiving/images/senior01.png\")");
        pane2.setStyle("-fx-background-image: url(\"/br/com/SeniorLiving/images/senior02.png\")");
        pane3.setStyle("-fx-background-image: url(\"/br/com/SeniorLiving/images/senior03.png\")");

        addAnimation();

        signupPane.setVisible(true);

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(.1),signupPane);
        translateTransition.setByX(600);
        translateTransition.play();
    }
    
    private void addAnimation() {

        FadeTransition fade0=new FadeTransition(Duration.seconds(3),pane1);
        fade0.setFromValue(1);
        fade0.setToValue(0);
        fade0.play();

        fade0.setOnFinished(event -> {

            FadeTransition fade1=new FadeTransition(Duration.seconds(3),pane3);
            fade1.setFromValue(1);
            fade1.setToValue(0);
            fade1.play();

            fade1.setOnFinished(event1 -> {

                FadeTransition fade2=new FadeTransition(Duration.seconds(3),pane2);
                fade2.setFromValue(1);
                fade2.setToValue(0);
                fade2.play();

                        fade2.setOnFinished(event4 -> {

                            FadeTransition fade5=new FadeTransition(Duration.seconds(3),pane2);
                            fade5.setFromValue(0);
                            fade5.setToValue(1);
                            fade5.play();

                            fade5.setOnFinished(event5 -> {

                                FadeTransition fade6=new FadeTransition(Duration.seconds(3),pane3);
                                fade6.setFromValue(0);
                                fade6.setToValue(1);
                                fade6.play();


                                    fade6.setOnFinished(event2 -> {

                                        addAnimation();

                                    });

                                });

                            });

                        });

                    });
    }
}
