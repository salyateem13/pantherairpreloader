/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantherairpreloader;

import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author samir
 */
public class PantherAirPreloader extends Preloader {
    
    ProgressBar bar;
    Stage stage;
    boolean noLoadingProgress = true;
 
    private Scene createPreloaderScene() {
        bar = new ProgressBar(0);
        BorderPane p = new BorderPane();
        
        
        
        p.setBottom(bar);
        p.setAlignment(bar, Pos.CENTER);
        
   String image = PantherAirPreloader.class.getResource("Georgia_State_Athletics_logo.png").toExternalForm();
    p.setStyle("-fx-background-image: url('" + image + "'); "
            + "-fx-background-size: 500 500;" +
           "-fx-background-position: center center; " +
           "-fx-background-repeat: stretch;");
        
        Scene scene = new Scene(p, 600, 600);
        
        
          
         return scene;
    }
 
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
      
        
        stage.setScene(createPreloaderScene());
        stage.show();
    }
 
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        //application loading progress is rescaled to be first 50%
        //Even if there is nothing to load 0% and 100% events can be
        // delivered
        if (pn.getProgress() != 1.0 || !noLoadingProgress) {
          bar.setProgress(pn.getProgress()/2);
          if (pn.getProgress() > 0) {
              noLoadingProgress = false;
          }
        }
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        //ignore, hide after application signals it is ready
        
        
    }
 
    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof ProgressNotification) {
           //expect application to send us progress notifications 
           //with progress ranging from 0 to 1.0
           double v = ((ProgressNotification) pn).getProgress();
           if (!noLoadingProgress) {
               //if we were receiving loading progress notifications 
               //then progress is already at 50%. 
               //Rescale application progress to start from 50%               
               v = 0.5 + v/2;
           }
           bar.setProgress(v);            
        } else if (pn instanceof StateChangeNotification) {
            //hide after get any state update from application
            stage.hide();
        }
    }  
 }
