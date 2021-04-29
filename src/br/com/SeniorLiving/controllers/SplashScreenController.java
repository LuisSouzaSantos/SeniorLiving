/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.SeniorLiving.controllers;

import com.sun.javafx.charts.ChartLayoutAnimator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author COMPUMAGIC
 */
public class SplashScreenController implements Initializable {
    
    private Label label;
    @FXML
    private ImageView image;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(5000),image);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0);
        
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                Stage loginScreen=new Stage();
                Parent root=null;
                
                try {
                   // root=FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
                } catch (Exception e) {
                }
                
                Stage current=(Stage)image.getScene().getWindow();
                Scene scene=new Scene(root,720,600);
                
                loginScreen.setScene(scene);
                loginScreen.initStyle(StageStyle.TRANSPARENT);
                current.hide();
                loginScreen.show();
                
            }
        });
        fadeTransition.play();
        
    }    
    
}
