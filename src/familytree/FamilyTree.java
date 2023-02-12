/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package familytree;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class FamilyTree extends Application implements EventHandler<ActionEvent> {
    public static Button btnLoad,btnSave,btnCreate,btnAddRoot,btnSaveNewRoot,btnDiscard,btnAddRelative,
            btnEditProfile,btnSaveCreateTree,btnUpdateDetail;
    public static BorderPane bPane;
    public static Scene scene;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Family Tree");
        //menubar= new Menubar();
        Action.myLoadMainPage();
        scene = new Scene(bPane, 780, 660);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource()==btnAddRoot){
            //Add Root Person
            Action.myAddRootPerson();
        }
        else if (event.getSource()==btnSaveNewRoot){
            //Save new root person
            Action.mySaveNewRoot();
        }
        else if(event.getSource()==btnDiscard){
            //Discard add root person action
            Action.myDiscardNewRoot();
        }
        else if(event.getSource()==btnAddRelative){
            //Add Relative
            Action.myAddRelative();
        }
        else if(event.getSource()==btnSaveCreateTree){
            //Save relative
            Action.mySaveRelative();
        }
        else if(event.getSource()==btnLoad){
            //Load Tree from file
            Action.myLoadTree();
        }
        else if(event.getSource()==btnCreate){
            //Create new root user
            Action.myAddRootPerson();
        }
        else if(event.getSource()==btnEditProfile){
            //Open the detail on editable mode
            Action.myEditProfile();
        }
        else if(event.getSource()==btnUpdateDetail){
            //Save the updated detail
            Action.myUpdateProfile();
        }
    }
}
