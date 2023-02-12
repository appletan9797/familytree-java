/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package familytree;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

import static familytree.FamilyTree.btnLoad;
import static familytree.FamilyTree.btnSave;
import static familytree.FamilyTree.btnCreate;
import static familytree.FamilyTree.bPane;
import static familytree.FamilyTree.btnAddRoot;
import static familytree.FamilyTree.btnSaveNewRoot;
import static familytree.FamilyTree.btnDiscard;
import static familytree.FamilyTree.btnEditProfile;
import static familytree.FamilyTree.btnAddRelative;
import static familytree.FamilyTree.btnSaveCreateTree;
import static familytree.FamilyTree.btnUpdateDetail;
import java.io.FileNotFoundException;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;

/**
 *
 * @author User
 */
public class Action {
    //Declaration
    public static Label title,lblLoadTree,lblTitle, lblFName, lblSurname,lblGender,lblDesc,lblAddTitle,lblStreetNo,lblStreetName,lblSuburb,lblPostcode;
    public static Label detailTitle,addressTitle,relativeTitle,firstname,surname,gender,lifedesc,streetno,streetname,suburb,postcode,father,mother,spouse,children,grandchild; //LoadPersonDetail
    public static Label tfFirstname,tfSurname,tfGender,tfStreetno,tfStreetname,tfSuburb,tfPostcode,tfFather,tfMother,tfChildren,tfSpouse,tfGrandchild;//LoadPersonDetail
    public static Label lblRelative;
    public static TextField id,txtFName,txtSurname,txtStreetNo,txtStreetName,txtSuburb,txtPostcode;
    public static ComboBox cmbGender,cmbRelative;
    public static TextArea txtaDesc;
    public static TextArea talife;//LoadPersonDetail
    public static VBox vbTree,vbPersonDetail,vbAddRoot,vbViewPersonDetail,vbEditDetail;
    public static HBox hbButtons,hbAddBtn,hbBox,hbFName,hbSurname,hbGender,hbDesc,hbStreetNo,hbStreetName,
            hbSuburb,hbPostcode,hbBtnRP,hbEdit;
    public static HBox hfn,hsn,hgender,hld,hsno,hsname,hs,hpc,hfather,hmother,hchild,hspouse,hgrandchild,hbuttons;//LoadPersonDetail
    
    private static Person rootPerson,userChecker,userChecker2,userChecker3,userRelative,mainUser,editUser,updatePerson,newRootPerson,oriRoot;
    private static File filePath;
    private static File[] fileArray;
    private static String filename,filenameRelative,filenameEdit;
    private static int counter, fileNumber;
    private static TreeItem rootItem,parentTreeLabel,childrenTreeLabel,spouseTreeLabel;
    private static TreeItem<String> rootNode;
    private static TreeView treeView;
    private static TreeView<String> treeView2;
    private static Alert alert;
    private static Optional<ButtonType> result;
    
    //Action Start Here
    public static void myLoadMainPage(){
        //Create elements on top part of first page
        FamilyTree.bPane = new BorderPane();
        title = new Label();
        title.setText("Welcome to the Family Tree Application");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        title.setPadding(new Insets(15,0,0,20));
        btnLoad = new Button();
        btnLoad.setText("Load Tree");
        btnCreate= new Button();
        btnCreate.setText("Create New Tree");
        hbButtons = new HBox();
        hbButtons.getChildren().addAll(btnLoad);
        hbButtons.getChildren().addAll(btnCreate);
        hbButtons.setSpacing(25);
        hbButtons.setPadding(new Insets(55,0,25,20));
        
        //Set top part of border pane
        Group layout = new Group(title,hbButtons);
        bPane.setTop(layout);
             
        //The pane that show family tree (Left)
        vbTree = new VBox();
        vbTree.setPrefWidth(350);
        String style1 = "-fx-background-color: rgb(25,255,255);";
        vbTree.setStyle(style1);
        
        //The pane that shows details of family member (Right)
        vbPersonDetail=new VBox();
        btnAddRoot = new Button();
        btnAddRoot.setText("Add root person");
        hbAddBtn = new HBox();
        hbAddBtn.getChildren().addAll(btnAddRoot);
        hbAddBtn.setPadding(new Insets(5,0,0,10));
        hbAddBtn.setSpacing(25);
        lblLoadTree = new Label();
        lblLoadTree.setText("Load a tree or add a new root person");
        lblLoadTree.setPadding(new Insets(5,0,0,10));
        vbPersonDetail.getChildren().add(hbAddBtn);
        vbPersonDetail.getChildren().add(lblLoadTree);
        String style = "-fx-background-color: rgb(255, 255, 255);";
        vbPersonDetail.setStyle(style);
        vbPersonDetail.setPrefWidth(460);
        
        //Set button action to call handle()
        btnAddRoot.setOnAction(new FamilyTree());
        btnLoad.setOnAction(new FamilyTree());
        btnCreate.setOnAction(new FamilyTree());
        //Set middle part of first page to show content
        hbBox = new HBox();
        hbBox.getChildren().clear();
        hbBox.getChildren().addAll(vbTree,vbPersonDetail);
        bPane.setCenter(hbBox);
    }
    
    public static void myAddRootPerson(){
        vbAddRoot = new VBox();
        
        //Create elements on add root person page
        lblTitle = new Label();
        lblTitle.setText("Person Info:");
        lblTitle.setPadding(new Insets(0,0,0,10));
        lblFName = new Label();
        lblFName.setText("Name");
        txtFName = new TextField ();
        hbFName = new HBox();
        hbFName.getChildren().addAll(lblFName, txtFName);
        hbFName.setSpacing(85);
        hbFName.setPrefHeight(45);
        hbFName.setPadding(new Insets(0,0,0,10));

        lblSurname = new Label();
        lblSurname.setText("Surname");
        txtSurname = new TextField();
        hbSurname = new HBox();
        hbSurname.getChildren().addAll(lblSurname,txtSurname);
        hbSurname.setSpacing(70);
        hbSurname.setPrefHeight(45);
        hbSurname.setPadding(new Insets(0,0,0,10));

        lblGender = new Label();
        lblGender.setText("Gender");
        cmbGender = new ComboBox();
        cmbGender.getItems().addAll("Male","Female");
        hbGender = new HBox();
        hbGender.getChildren().addAll(lblGender,cmbGender);
        hbGender.setSpacing(80);
        hbGender.setPrefHeight(45);
        hbGender.setPadding(new Insets(0,0,0,10));

        lblDesc = new Label();
        lblDesc.setText("Life Description");
        txtaDesc = new TextArea();
        txtaDesc.setPrefWidth(260);
        hbDesc = new HBox();
        hbDesc.getChildren().addAll(lblDesc,txtaDesc);
        hbDesc.setSpacing(35);
        hbDesc.setPrefHeight(125);
        hbDesc.setPadding(new Insets(0,0,0,10));

        lblAddTitle = new Label();
        lblAddTitle.setText("Address Info:");
        lblAddTitle.setPadding(new Insets(15,0,0,10));

        lblStreetNo = new Label();
        lblStreetNo.setText("Street Number");
        txtStreetNo = new TextField();
        txtStreetNo.setPromptText("Eg.123456");
        hbStreetNo = new HBox();
        hbStreetNo.getChildren().addAll(lblStreetNo,txtStreetNo);
        hbStreetNo.setSpacing(38);
        hbStreetNo.setPrefHeight(45);
        hbStreetNo.setPadding(new Insets(0,0,0,10));

        lblStreetName = new Label();
        lblStreetName.setText("Street Name");
        txtStreetName = new TextField();
        txtStreetName.setPromptText("Eg.Punggol Field Walk");
        hbStreetName = new HBox();
        hbStreetName.getChildren().addAll(lblStreetName,txtStreetName);
        hbStreetName.setSpacing(50);
        hbStreetName.setPrefHeight(45);
        hbStreetName.setPadding(new Insets(0,0,0,10));

        lblSuburb = new Label();
        lblSuburb.setText("Suburb");
        txtSuburb = new TextField();
        txtSuburb.setPromptText("Eg.Punggol");
        hbSuburb = new HBox();
        hbSuburb.getChildren().addAll(lblSuburb,txtSuburb);
        hbSuburb.setSpacing(77);
        hbSuburb.setPrefHeight(45);
        hbSuburb.setPadding(new Insets(0,0,0,10));

        lblPostcode = new Label();
        lblPostcode.setText("Postcode");
        txtPostcode = new TextField();
        txtPostcode.setPromptText("Eg.820293");
        hbPostcode = new HBox();
        hbPostcode.getChildren().addAll(lblPostcode,txtPostcode);
        hbPostcode.setSpacing(67);
        hbPostcode.setPrefHeight(45);
        hbPostcode.setPadding(new Insets(0,0,0,10));

        btnSaveNewRoot = new Button();
        btnSaveNewRoot.setText("Save");
        btnSaveNewRoot.setOnAction(new FamilyTree());

        btnDiscard = new Button();
        btnDiscard.setText("Discard");
        btnDiscard.setOnAction(new FamilyTree());
        
        hbBtnRP = new HBox();
        hbBtnRP.getChildren().addAll(btnSaveNewRoot);
        hbBtnRP.getChildren().addAll(btnDiscard);
        hbBtnRP.setSpacing(25);
        hbBtnRP.setPadding(new Insets(0,0,0,305));
        
        //Add the elements to the VBox
        vbAddRoot.getChildren().add(lblTitle);
        vbAddRoot.getChildren().add(hbFName);
        vbAddRoot.getChildren().add(hbSurname);
        vbAddRoot.getChildren().add(hbGender);
        vbAddRoot.getChildren().add(hbDesc);
        vbAddRoot.getChildren().add(lblAddTitle);
        vbAddRoot.getChildren().add(hbStreetNo);
        vbAddRoot.getChildren().add(hbStreetName);
        vbAddRoot.getChildren().add(hbSuburb);
        vbAddRoot.getChildren().add(hbPostcode);
        vbAddRoot.getChildren().add(hbBtnRP);

        //Clear the hbBox, and add new VBox to the right pane
        hbBox.getChildren().clear();
        hbBox.getChildren().addAll(vbTree,vbAddRoot);
    }
    
    public static void mySaveNewRoot(){
        //Declaration
        rootPerson = new Person();
        filePath = new File("userData/");
        fileArray = filePath.listFiles();
        fileNumber= filePath.list().length+1;
        counter = 0;
        
        //Set data in text field of add new root person to the person object (that will be stored into file later)
        rootPerson.ID =fileNumber;
        rootPerson.FName=txtFName.getText();
        rootPerson.Surname=txtSurname.getText();
        rootPerson.Gender=(String) cmbGender.getValue();
        rootPerson.LifeDesc=txtaDesc.getText();
        rootPerson.StreetName=txtStreetName.getText();
        rootPerson.StreetNo=Integer.parseInt(txtStreetNo.getText());
        rootPerson.Suburb=txtSuburb.getText();
        rootPerson.Postcode=Integer.parseInt(txtPostcode.getText());
        rootPerson.parentList = new ArrayList<Person>();
        rootPerson.childrenList = new ArrayList<Person>();
        rootPerson.grandChildList = new ArrayList<Person>();
        rootPerson.Spouse = new Person();
        
        //Check the path whether got file with same name
        //If so, check whether the Firstname, Surname, Gender, StreetName, StreetNo, Suburb and Postcode are the same
        //If same, add 1 to the counter, or the counter will remain 0
        filename = "userData/"+fileNumber+"_"+rootPerson.Surname+" "+rootPerson.FName+".bin";
        for (int f=0; f<fileArray.length;f++){
            if (fileArray[f].getPath().contains(rootPerson.Surname+" "+rootPerson.FName)){
                try {
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileArray[f].getPath()));
                    userChecker = (Person) is.readObject();
                    if ((userChecker.FName.equalsIgnoreCase(rootPerson.FName)) && (userChecker.Surname.equalsIgnoreCase(rootPerson.Surname)) && (userChecker.Gender.equalsIgnoreCase(rootPerson.Gender)) && (userChecker.StreetName.equalsIgnoreCase(rootPerson.StreetName)) && (userChecker.StreetNo==rootPerson.StreetNo) && (userChecker.Suburb.equalsIgnoreCase(rootPerson.Suburb)) && (userChecker.Postcode == rootPerson.Postcode)){
                        counter = counter + 1;
                    }
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                counter = 0;
            }
        }
        
        //If counter = 0, create a new file and write the object to the file
        //Then load the person to the tree view on the left pane
        //If counter !=0, tell the user there is already got a record in here
        if (counter == 0){
            myWriteToFile(filename,rootPerson);
            mySetDisplayScreen(rootPerson.Surname,rootPerson.FName,filename,rootPerson);
        }
        else{
            Alert alert6 = new Alert(Alert.AlertType.WARNING);
             alert6.setTitle("Error");
             alert6.setHeaderText("Error!");
             alert6.setContentText("This record already exist. Operation abort.");
             alert6.showAndWait();
        }
    }
    
    public static void myWriteToFile(String filename,Person user){
        //Write the person object to the file with filename of format below:
        //ID+Surname+FirstName.bin
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename,false));
            os.writeObject(user);
            os.close();
        }
        catch (IOException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void myDiscardNewRoot(){
        //Show confirmation box to ask whether they make sure to discard changes
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Discard Changes");
        alert.setContentText("Changes will not be saved if you discard changes");

        result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            hbBox.getChildren().clear();
            hbBox.getChildren().addAll(vbTree,vbPersonDetail);
        } else {
            alert.close();
        }
    }
    
    public static void myAddRelative(){
        //Create elements to be on the page, similar to add new root, but with new relative type
        lblRelative = new Label();
        lblRelative.setText("Relative Type");
        cmbRelative = new ComboBox();
        cmbRelative.getItems().addAll("Mother","Father","Spouse","Children");
        HBox hbRel = new HBox();
        hbRel.getChildren().addAll(lblRelative,cmbRelative);
        hbRel.setSpacing(80);
        hbRel.setPrefHeight(45);
        hbRel.setPadding(new Insets(0,0,0,10));
        
        vbAddRoot = new VBox();
        lblTitle = new Label();
        lblTitle.setText("Person Info:");
        lblTitle.setPadding(new Insets(0,0,0,10));
        lblFName = new Label();
        lblFName.setText("Name");
        txtFName = new TextField ();
        hbFName = new HBox();
        hbFName.getChildren().addAll(lblFName, txtFName);
        hbFName.setSpacing(85);
        hbFName.setPrefHeight(45);
        hbFName.setPadding(new Insets(0,0,0,10));

        lblSurname = new Label();
        lblSurname.setText("Surname");
        txtSurname = new TextField();
        hbSurname = new HBox();
        hbSurname.getChildren().addAll(lblSurname,txtSurname);
        hbSurname.setSpacing(70);
        hbSurname.setPrefHeight(45);
        hbSurname.setPadding(new Insets(0,0,0,10));

        lblGender = new Label();
        lblGender.setText("Gender");
        cmbGender = new ComboBox();
        cmbGender.getItems().addAll("Male","Female");
        hbGender = new HBox();
        hbGender.getChildren().addAll(lblGender,cmbGender);
        hbGender.setSpacing(80);
        hbGender.setPrefHeight(45);
        hbGender.setPadding(new Insets(0,0,0,10));

        lblDesc = new Label();
        lblDesc.setText("Life Description");
        txtaDesc = new TextArea();
        txtaDesc.setPrefWidth(260);
        hbDesc = new HBox();
        hbDesc.getChildren().addAll(lblDesc,txtaDesc);
        hbDesc.setSpacing(35);
        hbDesc.setPrefHeight(125);
        hbDesc.setPadding(new Insets(0,0,0,10));

        lblAddTitle = new Label();
        lblAddTitle.setText("Address Info:");
        lblAddTitle.setPadding(new Insets(15,0,0,10));

        lblStreetNo = new Label();
        lblStreetNo.setText("Street Number");
        txtStreetNo = new TextField();
        txtStreetNo.setPromptText("Eg.123456");
        hbStreetNo = new HBox();
        hbStreetNo.getChildren().addAll(lblStreetNo,txtStreetNo);
        hbStreetNo.setSpacing(38);
        hbStreetNo.setPrefHeight(45);
        hbStreetNo.setPadding(new Insets(0,0,0,10));

        lblStreetName = new Label();
        lblStreetName.setText("Street Name");
        txtStreetName = new TextField();
        txtStreetName.setPromptText("Eg.Punggol Field Walk");
        hbStreetName = new HBox();
        hbStreetName.getChildren().addAll(lblStreetName,txtStreetName);
        hbStreetName.setSpacing(50);
        hbStreetName.setPrefHeight(45);
        hbStreetName.setPadding(new Insets(0,0,0,10));

        lblSuburb = new Label();
        lblSuburb.setText("Suburb");
        txtSuburb = new TextField();
        txtSuburb.setText("eeer");
        hbSuburb = new HBox();
        hbSuburb.getChildren().addAll(lblSuburb,txtSuburb);
        hbSuburb.setSpacing(77);
        hbSuburb.setPrefHeight(45);
        hbSuburb.setPadding(new Insets(0,0,0,10));

        lblPostcode = new Label();
        lblPostcode.setText("Postcode");
        txtPostcode = new TextField();
        txtPostcode.setPromptText("Eg.820293");
        hbPostcode = new HBox();
        hbPostcode.getChildren().addAll(lblPostcode,txtPostcode);
        hbPostcode.setSpacing(67);
        hbPostcode.setPrefHeight(45);
        hbPostcode.setPadding(new Insets(0,0,0,10));

        btnSaveCreateTree = new Button();
        btnSaveCreateTree.setText("Save");
        btnSaveCreateTree.setOnAction(new FamilyTree());
        btnDiscard = new Button();
        btnDiscard.setText("Discard");
        btnDiscard.setOnAction(new FamilyTree());
        
        hbBtnRP = new HBox();
        hbBtnRP.getChildren().addAll(btnSaveCreateTree);
        hbBtnRP.getChildren().addAll(btnDiscard);
        hbBtnRP.setSpacing(25);
        hbBtnRP.setPadding(new Insets(0,0,0,305));
        
        //Add the elements to the VBox
        vbAddRoot.getChildren().add(hbRel);
        vbAddRoot.getChildren().add(lblTitle);
        vbAddRoot.getChildren().add(hbFName);
        vbAddRoot.getChildren().add(hbSurname);
        vbAddRoot.getChildren().add(hbGender);
        vbAddRoot.getChildren().add(hbDesc);
        vbAddRoot.getChildren().add(lblAddTitle);
        vbAddRoot.getChildren().add(hbStreetNo);
        vbAddRoot.getChildren().add(hbStreetName);
        vbAddRoot.getChildren().add(hbSuburb);
        vbAddRoot.getChildren().add(hbPostcode);
        vbAddRoot.getChildren().add(hbBtnRP);

        //Clear the hbBox, and add new VBox to the right pane
        hbBox.getChildren().clear();
        hbBox.getChildren().addAll(vbTree,vbAddRoot);
    }
    
    public static void mySaveRelative(){
        //Initialization
        userRelative = new Person();
        filePath = new File("userData/");
        fileArray = filePath.listFiles();
        fileNumber = filePath.list().length+1;
        
        //Save the value to the person object
        userRelative.ID =fileNumber;
        userRelative.FName=txtFName.getText();
        userRelative.Surname=txtSurname.getText();
        userRelative.Gender=(String) cmbGender.getValue();
        userRelative.LifeDesc=txtaDesc.getText();
        userRelative.StreetName=txtStreetName.getText();
        userRelative.StreetNo=Integer.parseInt(txtStreetNo.getText());
        userRelative.Suburb=txtSuburb.getText();
        userRelative.Postcode=Integer.parseInt(txtPostcode.getText());
        userRelative.parentList = new ArrayList<Person>();
        userRelative.childrenList = new ArrayList<Person>();
        userRelative.grandChildList = new ArrayList<Person>();
        userRelative.Spouse = new Person();
        
        //Check whether file exist
        filenameRelative = "userData/"+fileNumber+"_"+userRelative.Surname+" "+userRelative.FName+".bin";
        for (int f=0; f<fileArray.length;f++){
            if (fileArray[f].getPath().contains(userRelative.Surname)){
                try {
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileArray[f].getPath()));
                    userChecker = (Person) is.readObject();
                    if ((userChecker.FName.equalsIgnoreCase(userRelative.FName)) && (userChecker.Surname.equalsIgnoreCase(userRelative.Surname)) && (userChecker.Gender.equalsIgnoreCase(userRelative.Gender)) && (userChecker.StreetName.equalsIgnoreCase(userRelative.StreetName)) && (userChecker.StreetNo==userRelative.StreetNo) && (userChecker.Suburb.equalsIgnoreCase(userRelative.Suburb)) && (userChecker.Postcode == userRelative.Postcode)){
                        counter = counter + 1;
                    }
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                counter = 0;
            }
        }
        if (counter == 0){
            myWriteToFile(filenameRelative,userRelative);
        }
        else{
            Alert alert3 = new Alert(Alert.AlertType.WARNING);
             alert3.setTitle("Error");
             alert3.setHeaderText("Error!");
             alert3.setContentText("This record already exist. Operation abort.");
             alert3.showAndWait();
        }
        String text = cmbRelative.getValue().toString();
        myLinkRelative(userRelative,text);
    }
    
    public static void myLinkRelative(Person relative,String type){
        String idnumber,fname,sname,gend,ld,streetnum,streetName,Suburb,Postcode,filenameRel;
        String idnumber2,fname2,sname2,gend2,ld2,streetnum2,streetName2,Suburb2,Postcode2;
        Person Spouse,Spouse2;
        ArrayList<Person> pl,cl,gcl,relativepl,relativecl,relativegcl;
        filename = "userData/"+rootPerson.ID+"_"+rootPerson.Surname+" "+rootPerson.FName+".bin";
        //Read the file, store the variables to local variable
        try {
            filename = "userData/"+rootPerson.ID+"_"+rootPerson.Surname+" "+rootPerson.FName+".bin";
            ObjectInputStream is2 = new ObjectInputStream(new FileInputStream(filename));
            
            //Below are to update main user
            mainUser = (Person) is2.readObject();
            idnumber =String.valueOf(mainUser.ID);
            fname = mainUser.FName;
            sname = mainUser.Surname;
            gend = mainUser.Gender;
            ld = mainUser.LifeDesc;
            streetnum = String.valueOf(mainUser.StreetNo);
            streetName = mainUser.StreetName;
            Suburb = mainUser.Suburb;
            Postcode = String.valueOf(mainUser.Postcode);
            pl = mainUser.parentList;
            cl = mainUser.childrenList;
            gcl = mainUser.grandChildList;
            Spouse = mainUser.Spouse;
            
            //Below are to update relative's 
            idnumber2 =String.valueOf(relative.ID);
            fname2 = relative.FName;
            sname2 = relative.Surname;
            gend2 = relative.Gender;
            ld2 = relative.LifeDesc;
            streetnum2 = String.valueOf(relative.StreetNo);
            streetName2 = relative.StreetName;
            Suburb2 = relative.Suburb;
            Postcode2 = String.valueOf(relative.Postcode);
            relativepl = relative.parentList;
            relativecl = relative.childrenList;
            relativegcl = relative.grandChildList;
            Spouse2 = relative.Spouse;
            
            is2.close();
            
            try{
                ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream(filename));
                Person updatePerson = new Person();
                updatePerson.ID=Integer.parseInt(idnumber);
                updatePerson.FName=fname;
                updatePerson.Surname=sname;
                updatePerson.Gender=gend;
                updatePerson.LifeDesc = ld;
                updatePerson.StreetNo = Integer.parseInt(streetnum);
                updatePerson.StreetName = streetName;
                updatePerson.Suburb = Suburb;
                updatePerson.Postcode = Integer.parseInt(Postcode);
                switch(type){
                    case "Mother":
                        pl.add(relative);
                        relativecl.add(mainUser);
                        break;
                    
                    case "Father":
                        pl.add(relative);
                        relativecl.add(mainUser);
                        break;
                        
                    case "Children":
                        cl.add(relative);
                        relativepl.add(mainUser);
                        if (!mainUser.parentList.isEmpty()){
                            for (int q=0;q<mainUser.parentList.size();q++){
                                mainUser.parentList.get(q).grandChildList.add(relative);
                            }
                        }
                        break;
                        
                    case "Spouse":
                        Spouse = relative;
                        Spouse2 =mainUser;
                        break;
                }
                updatePerson.parentList =pl;
                updatePerson.childrenList =cl;
                updatePerson.grandChildList=gcl;
                updatePerson.Spouse = Spouse;
                
                //Below are to update relative's file
                filenameRel = "userData/"+relative.ID+"_"+relative.Surname+" "+relative.FName+".bin";
                ObjectOutputStream os3 = new ObjectOutputStream(new FileOutputStream(filenameRel));
                relative.ID =Integer.parseInt(idnumber2);
                relative.FName = fname2;
                relative.Surname = sname2;
                relative.Gender =gend2 ;
                relative.LifeDesc = ld2;
                relative.StreetNo = Integer.parseInt(streetnum2);
                relative.StreetName = streetName2;
                relative.Suburb = Suburb2;
                relative.Postcode = Integer.parseInt(Postcode2);
                relative.parentList = relativepl;
                relative.childrenList = relativecl;
                relative.grandChildList = relativegcl;
                relative.Spouse = Spouse2;
                
                os2.writeObject(updatePerson);
                os2.close();
                os3.writeObject(relative);
                os3.close();
                mySetDisplayScreen(sname,fname,filename,rootPerson);
            }
           catch (IOException ex) {
                Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void myLoadTree(){
        rootPerson = new Person();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Root User File");
        fileChooser.setInitialDirectory(new File("userData/"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Bin files", "*.bin");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
         try{
           ObjectInputStream is3 = new ObjectInputStream(new FileInputStream(selectedFile));
           rootPerson = (Person) is3.readObject();
           mySetDisplayScreen(rootPerson.Surname, rootPerson.FName, selectedFile.getPath(),rootPerson);
           
         }  catch (FileNotFoundException ex) {
                Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                 Alert alert = new Alert(Alert.AlertType.WARNING);
                 alert.setTitle("Error");
                 alert.setHeaderText("Error!");
                 alert.setContentText("You have selected a wrong file!");
                 alert.showAndWait();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
             alert2.setTitle("Information");
             alert2.setHeaderText("Information!");
             alert2.setContentText("You have closed the file selection session");
             alert2.showAndWait();
        }
    }
    
    public static void myEditProfile(){
        filenameEdit = "userData/"+id.getText()+"_"+tfSurname.getText()+" "+tfFirstname.getText()+".bin";
        try{
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(filenameEdit));
            editUser = (Person) is.readObject();      
            lblTitle = new Label();
            lblTitle.setText("Person Info:");
            lblTitle.setPadding(new Insets(0,0,0,10));
            id.setText(String.valueOf(editUser.ID));
            lblFName = new Label();
            lblFName.setText("Name");
            txtFName = new TextField ();
            txtFName.setText(editUser.FName);
            txtFName.setEditable(false);
            txtFName.setStyle("-fx-background-color: rgb(220,220,220);");
            hbFName = new HBox();
            hbFName.getChildren().addAll(lblFName, txtFName);
            hbFName.setSpacing(85);
            hbFName.setPrefHeight(45);
            hbFName.setPadding(new Insets(0,0,0,10));

            lblSurname = new Label();
            lblSurname.setText("Surname");
            txtSurname = new TextField();
            txtSurname.setText(editUser.Surname);
            txtSurname.setEditable(false);
            txtSurname.setStyle("-fx-background-color: rgb(220,220,220);");
            hbSurname = new HBox();
            hbSurname.getChildren().addAll(lblSurname,txtSurname);
            hbSurname.setSpacing(70);
            hbSurname.setPrefHeight(45);
            hbSurname.setPadding(new Insets(0,0,0,10));

            lblGender = new Label();
            lblGender.setText("Gender");
            tfGender = new Label();
            //cmbGender.getItems().addAll("Male","Female");
            tfGender.setText(editUser.Gender);
            tfGender.setStyle("-fx-background-color: rgb(220,220,220);");
            hbGender = new HBox();
            hbGender.getChildren().addAll(lblGender,tfGender);
            hbGender.setSpacing(80);
            hbGender.setPrefHeight(45);
            hbGender.setPadding(new Insets(0,0,0,10));

            lblDesc = new Label();
            lblDesc.setText("Life Description");
            txtaDesc = new TextArea();
            txtaDesc.setPrefWidth(260);
            txtaDesc.setText(editUser.LifeDesc);
            hbDesc = new HBox();
            hbDesc.getChildren().addAll(lblDesc,txtaDesc);
            hbDesc.setSpacing(35);
            hbDesc.setPrefHeight(125);
            hbDesc.setPadding(new Insets(0,0,0,10));

            lblAddTitle = new Label();
            lblAddTitle.setText("Address Info:");
            lblAddTitle.setPadding(new Insets(15,0,0,10));

            lblStreetNo = new Label();
            lblStreetNo.setText("Street Number");
            txtStreetNo = new TextField();
            txtStreetNo.setPromptText("Eg.123456");
            txtStreetNo.setText(String.valueOf(editUser.StreetNo));
            hbStreetNo = new HBox();
            hbStreetNo.getChildren().addAll(lblStreetNo,txtStreetNo);
            hbStreetNo.setSpacing(38);
            hbStreetNo.setPrefHeight(45);
            hbStreetNo.setPadding(new Insets(0,0,0,10));

            lblStreetName = new Label();
            lblStreetName.setText("Street Name");
            txtStreetName = new TextField();
            txtStreetName.setPromptText("Eg.Punggol Field Walk");
            txtStreetName.setText(editUser.StreetName);
            hbStreetName = new HBox();
            hbStreetName.getChildren().addAll(lblStreetName,txtStreetName);
            hbStreetName.setSpacing(50);
            hbStreetName.setPrefHeight(45);
            hbStreetName.setPadding(new Insets(0,0,0,10));

            lblSuburb = new Label();
            lblSuburb.setText("Suburb");
            txtSuburb = new TextField();
            txtSuburb.setPromptText("Eg.Punggol");
            txtSuburb.setText(editUser.Suburb);
            hbSuburb = new HBox();
            hbSuburb.getChildren().addAll(lblSuburb,txtSuburb);
            hbSuburb.setSpacing(77);
            hbSuburb.setPrefHeight(45);
            hbSuburb.setPadding(new Insets(0,0,0,10));

            lblPostcode = new Label();
            lblPostcode.setText("Postcode");
            txtPostcode = new TextField();
            txtPostcode.setPromptText("Eg.820293");
            txtPostcode.setText(String.valueOf(editUser.Postcode));
            hbPostcode = new HBox();
            hbPostcode.getChildren().addAll(lblPostcode,txtPostcode);
            hbPostcode.setSpacing(67);
            hbPostcode.setPrefHeight(45);
            hbPostcode.setPadding(new Insets(0,0,0,10));

            btnUpdateDetail = new Button();
            btnUpdateDetail.setText("Update");
            btnUpdateDetail.setOnAction(new FamilyTree());

            btnDiscard = new Button();
            btnDiscard.setText("Discard");
            btnDiscard.setOnAction(new FamilyTree());
            
            hbBtnRP = new HBox();
            hbBtnRP.getChildren().addAll(btnUpdateDetail);
            hbBtnRP.getChildren().addAll(btnDiscard);
            hbBtnRP.setSpacing(25);
            hbBtnRP.setPadding(new Insets(0,0,0,305));
        
            //Add the elements to the VBox
            vbEditDetail = new VBox();
            vbEditDetail.getChildren().add(lblTitle);
            vbEditDetail.getChildren().add(hbFName);
            vbEditDetail.getChildren().add(hbSurname);
            vbEditDetail.getChildren().add(hbGender);
            vbEditDetail.getChildren().add(hbDesc);
            vbEditDetail.getChildren().add(lblAddTitle);
            vbEditDetail.getChildren().add(hbStreetNo);
            vbEditDetail.getChildren().add(hbStreetName);
            vbEditDetail.getChildren().add(hbSuburb);
            vbEditDetail.getChildren().add(hbPostcode);
            vbEditDetail.getChildren().add(hbBtnRP);

            //Clear the hbBox, and add new VBox to the right pane
            hbBox.getChildren().clear();
            hbBox.getChildren().addAll(vbTree,vbEditDetail);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void myUpdateProfile(){
        updatePerson = new Person();
        userChecker2 = new Person();
        String ID,Surname,Firstname;
        ID =id.getText();
        Surname = txtSurname.getText();
        Firstname = txtFName.getText();
        filename = "userData/"+ ID+"_"+Surname+" "+Firstname+".bin";
        try{
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
            userChecker2 = (Person) is.readObject();
            
            updatePerson.ID = Integer.parseInt(id.getText());
            updatePerson.FName= txtFName.getText();
            updatePerson.Surname = txtSurname.getText();
            updatePerson.Gender = tfGender.getText();
            updatePerson.LifeDesc=txtaDesc.getText();
            updatePerson.StreetNo = Integer.parseInt(txtStreetNo.getText());
            updatePerson.StreetName = txtStreetName.getText();
            updatePerson.Suburb = txtSuburb.getText();
            updatePerson.Postcode = Integer.parseInt(txtPostcode.getText());
            updatePerson.parentList = userChecker2.parentList;
            updatePerson.childrenList = userChecker2.childrenList;
            updatePerson.grandChildList = userChecker2.grandChildList;
            updatePerson.Spouse = userChecker2.Spouse;

            myWriteToFile(filename,updatePerson);
            //mySetDisplayScreen(rootPerson.Surname,rootPerson.FName,filename,rootPerson);  
        } catch (IOException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mySetDisplayScreen(String surname, String firstName, String file1,Person userDetail){
        VBox vbox = myLoadMainTree(surname,firstName,file1,userDetail);
        VBox personDetail = myLoadPersonDetail(surname,firstName,file1,userDetail);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(personDetail);
        //Add the VBox to current HBox(the page)
        hbBox.getChildren().clear();
        hbBox.getChildren().addAll(vbox,scrollPane);
    }
    
    public static VBox myLoadMainTree(String surname, String firstName, String file2,Person userDetail){
        //Set the root item of family tree
        //Read the file of root item, if relative's list got something then append them as tree item
        //rootItem = new TreeItem(surname+" "+firstName);
        rootNode = new TreeItem<String>(surname+" "+firstName);
        try{
            rootNode.setExpanded(true);
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file2));
            userChecker = (Person) is.readObject();
            //Read the person, then get parent list, if there is something in the list, add as tree item
            if (!userChecker.parentList.isEmpty()){
                for (int i=0;i<userChecker.parentList.size();i++){
                    TreeItem<String> item = new TreeItem<String>(userChecker.parentList.get(i).getFullName());
                    boolean found= false;
                    for (TreeItem<String> depNode : rootNode.getChildren()){
                        if (depNode.getValue().contentEquals("Parents")){
                            depNode.getChildren().add(item);
                            found = true;
                            break;
                        }
                    }
                    if (!found){
                        TreeItem<String> depNode = new TreeItem<String>("Parents");
                        rootNode.getChildren().add(depNode);
                        depNode.getChildren().add(item);
                    }
                }
            }
            
            if (!userChecker.childrenList.isEmpty()){
                for (int i=0;i<userChecker.childrenList.size();i++){
                    TreeItem<String> item = new TreeItem<String>(userChecker.childrenList.get(i).getFullName());
                    boolean found= false;
                    for (TreeItem<String> depNode : rootNode.getChildren()){
                        if (depNode.getValue().contentEquals("Children")){
                            depNode.getChildren().add(item);
                            found = true;
                            break;
                        }
                    }
                    if (!found){
                        TreeItem<String> depNode = new TreeItem<String>("Children");
                        rootNode.getChildren().add(depNode);
                        depNode.getChildren().add(item);
                    }
                }
            }
            
            if ((userChecker.Spouse.FName) != null){
                TreeItem<String> item = new TreeItem<String>(userChecker.Spouse.getFullName());
                boolean found = false;
                for (TreeItem<String> depNode : rootNode.getChildren()){
                        if (depNode.getValue().contentEquals("Spouse")){
                            depNode.getChildren().add(item);
                            found = true;
                            break;
                        }
                    }
                    if (!found){
                        TreeItem<String> depNode = new TreeItem<String>("Spouse");
                        rootNode.getChildren().add(depNode);
                        depNode.getChildren().add(item);
                    }
            }
            
        }catch (IOException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Set the tree view
        treeView2 = new TreeView<String>(rootNode);
        treeView2.setCellFactory(tree -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
            cell.setOnMouseClicked(event -> {
            if (! cell.isEmpty()) {
                String cellValue = cell.getTreeItem().getValue();
                if (!cellValue.equals("Parents") && !cellValue.equals("Spouse") && !cellValue.equals("Children")){
                    myChangeRootUser(cellValue);
                }
            }
            });
            return cell ;
        });
        
        //Add the tree view to the VBox (for left pane)
        VBox vbox = new VBox(treeView2);
        vbox.setPrefWidth(350);
        return vbox;
    }
    
    public static void myChangeRootUser(String currentPerson){
        String checkFile = "userData/"+rootPerson.ID+"_"+rootPerson.Surname+" "+rootPerson.FName+".bin";
        String id1,surname1,firstname1,gender1,ld1,streetno1,streetname1,suburb1,postcode1;
        ArrayList<Person> pl1,cl1,gcl1;
        Person spouse1;
        String latestFile;
        int newID=0;
        try{
           ObjectInputStream is7 = new ObjectInputStream(new FileInputStream(checkFile));
           oriRoot = (Person) is7.readObject();
           
           for (int a=0;a<oriRoot.parentList.size();a++){
               if (oriRoot.parentList.get(a).getFullName().contains(currentPerson)){
                   newID = oriRoot.parentList.get(a).getID();
                   rootPerson = oriRoot.parentList.get(a);
               }
           }
           for (int a=0;a<oriRoot.childrenList.size();a++){
               if(oriRoot.childrenList.get(a).getFullName().contains(currentPerson)){
                   newID = oriRoot.childrenList.get(a).getID();
                   rootPerson = oriRoot.childrenList.get(a);
               }
           }
           for (int a=0;a<oriRoot.grandChildList.size();a++){
               if(oriRoot.grandChildList.get(a).getFullName().contains(currentPerson)){
                   newID = oriRoot.grandChildList.get(a).getID();
                   rootPerson = oriRoot.grandChildList.get(a);
               }
           }
           
           if ((oriRoot.Spouse.FName) != null){
               newID = oriRoot.Spouse.getID();
               rootPerson = oriRoot.Spouse;
           }
           
           latestFile = "userData/"+newID+"_"+currentPerson+".bin";
           try{
               ObjectInputStream is6 = new ObjectInputStream(new FileInputStream(latestFile));
               newRootPerson = (Person) is6.readObject();
               id1 = String.valueOf(newRootPerson.ID);
               surname1 = newRootPerson.Surname;
               firstname1 = newRootPerson.FName;
               gender1= newRootPerson.Gender;
               ld1 = newRootPerson.LifeDesc;
               streetno1= String.valueOf(newRootPerson.StreetNo);
               streetname1 =newRootPerson.StreetName;
               suburb1 = newRootPerson.Suburb;
               postcode1 = String.valueOf(newRootPerson.Postcode);
               pl1 = newRootPerson.parentList;
               cl1 = newRootPerson.childrenList;
               gcl1 = newRootPerson.grandChildList;
               spouse1 = newRootPerson.Spouse;
               
               mySetDisplayScreen(surname1,firstname1,latestFile,newRootPerson);
           } catch (FileNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException ex) {
               Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
           }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static VBox myLoadPersonDetail(String surnamepara, String firstNamepara,String file3, Person userDetail){
        //Create elements to show on Detail Page (right pane)
        detailTitle = new Label();
        detailTitle.setText("Person Info:");
        id = new TextField();
        id.setText(String.valueOf(userDetail.ID));
        id.setVisible(false);
        firstname = new Label();
        firstname.setText("First Name");
        tfFirstname = new Label();
        tfFirstname.setText(userDetail.FName);
        hfn = new HBox();
        hfn.getChildren().addAll(firstname,tfFirstname);
        hfn.setSpacing(77);
        hfn.setPrefHeight(45);
        hfn.setPadding(new Insets(0,0,0,10));
        
        surname = new Label();
        surname.setText("Surname");
        tfSurname = new Label();
        tfSurname.setText(userDetail.Surname);
        hsn = new HBox();
        hsn.getChildren().addAll(surname,tfSurname);
        hsn.setSpacing(77);
        hsn.setPrefHeight(45);
        hsn.setPadding(new Insets(0,0,0,10));
        
        gender = new Label();
        gender.setText("Gender");
        tfGender = new Label();
        tfGender.setText(userDetail.Gender);
        hgender = new HBox();
        hgender.getChildren().addAll(gender,tfGender);
        hgender.setSpacing(77);
        hgender.setPrefHeight(45);
        hgender.setPadding(new Insets(0,0,0,10));
        
        lifedesc = new Label();
        lifedesc.setText("Life Description");
        talife = new TextArea();
        talife.setText(userDetail.LifeDesc);
        talife.setEditable(false);
        hld = new HBox();
        hld.getChildren().addAll(lifedesc,talife);
        hld.setSpacing(77);
        hld.setPrefHeight(45);
        hld.setPadding(new Insets(0,0,0,10));
        
        addressTitle = new Label();
        addressTitle.setText("Address Info:");
        streetno = new Label();
        streetno.setText("Street Number");
        tfStreetno = new Label();
        tfStreetno.setText(String.valueOf(userDetail.StreetNo));
        hsno = new HBox();
        hsno.getChildren().addAll(streetno,tfStreetno);
        hsno.setSpacing(77);
        hsno.setPrefHeight(45);
        hsno.setPadding(new Insets(0,0,0,10));
        
        streetname = new Label();
        streetname.setText("Street Name");
        tfStreetname = new Label();
        tfStreetname.setText(userDetail.StreetName);
        hsname = new HBox();
        hsname.getChildren().addAll(streetname,tfStreetname);
        hsname.setSpacing(77);
        hsname.setPrefHeight(45);
        hsname.setPadding(new Insets(0,0,0,10));
        
        suburb = new Label();
        suburb.setText("Suburb");
        tfSuburb = new Label();
        tfSuburb.setText(userDetail.Suburb);
        hs = new HBox();
        hs.getChildren().addAll(suburb,tfSuburb);
        hs.setSpacing(77);
        hs.setPrefHeight(45);
        hs.setPadding(new Insets(0,0,0,10));
        
        postcode = new Label();
        postcode.setText("Postcode");
        tfPostcode = new Label();
        tfPostcode.setText(String.valueOf(userDetail.Postcode));
        hpc = new HBox();
        hpc.getChildren().addAll(postcode,tfPostcode);
        hpc.setSpacing(77);
        hpc.setPrefHeight(45);
        hpc.setPadding(new Insets(0,0,0,10));
        
        relativeTitle = new Label();
        relativeTitle.setText("Relative Info:");
        father = new Label();
        father.setText("Father");
        tfFather = new Label();
        
        mother = new Label();
        mother.setText("Mother");
        tfMother = new Label();
        
        children = new Label();
        children.setText("Children");
        tfChildren = new Label();
        StringBuilder childrenname = new StringBuilder();
        
        grandchild = new Label();
        grandchild.setText("Grandchildren");
        tfGrandchild = new Label();
        StringBuilder grandchildname = new StringBuilder();
            
        spouse = new Label();
        spouse.setText("Spouse");
        tfSpouse = new Label();
        
        try{
            ObjectInputStream is5 = new ObjectInputStream(new FileInputStream(file3));
            userChecker3 = (Person) is5.readObject();
            if (!userChecker3.parentList.isEmpty()){
                tfFather.setText(userChecker3.parentList.get(0).getFullName());
            }
            else{
                tfFather.setText("-Not Provided-");
            }
            
            if (!userChecker3.parentList.isEmpty()){
                tfMother.setText(userChecker3.parentList.get(0).getFullName());
            }
            else{
                tfMother.setText("-Not Provided-");
            }
            
            if (!userChecker3.childrenList.isEmpty()){
            for (int i=0; i<userChecker3.childrenList.size();i++){
                childrenname.append(userChecker3.childrenList.get(i).getFullName());
                childrenname.append("\n");
                    
                    //Get grandchildren
//                    ArrayList<Person> gclist = userDetail.childrenList.get(i).childrenList;
//                    for (int g=0;g<userChecker3.childrenList.get(i).childrenList.size();g++){
//                        grandchildname.append(gclist.get(g).getFullName());
//                        grandchildname.append("\n");
//                    }
//                    tfGrandchild.setText(grandchildname.toString());
            }
            tfChildren.setText(childrenname.toString());
            }
            else{
                tfChildren.setText("-Not Provided-");
            }
            
            if ((userChecker.Spouse.FName) != null){
                tfSpouse.setText(userChecker3.Spouse.getFullName());
            }
            else{
                tfSpouse.setText("-Not Provided-");
            }
            
            if (!userChecker3.grandChildList.isEmpty()){
                for (int i=0; i<userChecker3.grandChildList.size();i++){
                    grandchildname.append(userDetail.grandChildList.get(i).getFullName());
                    grandchildname.append("\n");
                }
                tfGrandchild.setText(grandchildname.toString());
            }
            else{
                tfGrandchild.setText("-Not Provided-");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hfather = new HBox();
        hfather.getChildren().addAll(father,tfFather);
        hfather.setSpacing(77);
        hfather.setPrefHeight(45);
        hfather.setPadding(new Insets(0,0,0,10));

        hmother = new HBox();
        hmother.getChildren().addAll(mother,tfMother);
        hmother.setSpacing(77);
        hmother.setPrefHeight(45);
        hmother.setPadding(new Insets(0,0,0,10));
        
        hchild = new HBox();
        hchild.getChildren().addAll(children,tfChildren);
        hchild.setSpacing(77);
        hchild.setPrefHeight(45);
        hchild.setPadding(new Insets(0,0,0,10));
        
        hspouse = new HBox();
        hspouse.getChildren().addAll(spouse,tfSpouse);
        hspouse.setSpacing(77);
        hspouse.setPrefHeight(45);
        hspouse.setPadding(new Insets(0,0,0,10));
        
        
        hgrandchild = new HBox();
        hgrandchild.getChildren().addAll(grandchild,tfGrandchild);
        hgrandchild.setSpacing(77);
        hgrandchild.setPrefHeight(45);
        hgrandchild.setPadding(new Insets(0,0,0,10));
        
        btnEditProfile = new Button();
        btnEditProfile.setText("Edit");
        btnAddRelative = new Button();
        btnAddRelative.setText("Add Relative");
        btnAddRelative.setOnAction(new FamilyTree());
        hbuttons = new HBox();
        hbuttons.getChildren().addAll(btnEditProfile,btnAddRelative);
        hbuttons.setSpacing(77);
        
        btnEditProfile.setOnAction(new FamilyTree());
        
        //Add the elements to the View Person Detail VBox (to show on right pane)
        vbViewPersonDetail = new VBox();
        vbViewPersonDetail.setPrefWidth(700);
        
        vbViewPersonDetail.getChildren().add(detailTitle);
        vbViewPersonDetail.getChildren().add(id);
        vbViewPersonDetail.getChildren().add(hfn);
        vbViewPersonDetail.getChildren().add(hsn);
        vbViewPersonDetail.getChildren().add(hgender);
        vbViewPersonDetail.getChildren().add(hld);
        vbViewPersonDetail.getChildren().add(addressTitle);
        vbViewPersonDetail.getChildren().add(hsno);
        vbViewPersonDetail.getChildren().add(hsname);
        vbViewPersonDetail.getChildren().add(hs);
        vbViewPersonDetail.getChildren().add(hpc);
        vbViewPersonDetail.getChildren().add(relativeTitle);
        vbViewPersonDetail.getChildren().add(hfather);
        vbViewPersonDetail.getChildren().add(hmother);
        vbViewPersonDetail.getChildren().add(hchild);
        vbViewPersonDetail.getChildren().add(hspouse);
        vbViewPersonDetail.getChildren().add(hgrandchild);
        vbViewPersonDetail.getChildren().add(hbuttons);
        return vbViewPersonDetail;
    }

}
