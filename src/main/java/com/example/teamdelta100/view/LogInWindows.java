package com.example.teamdelta100.view;

import com.example.teamdelta100.entities.Personal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
    Klassen skapar en Login Stage
    Klassen har två metoder:
    * Skapa LogIn stagen
    * Skapa en ListView
 */

public class LogInWindows {
    private PersonalFX personalFX;
    private String title;
    private Text error = new Text();
    private Personal selectPersonal;
    private Personal tempPers;
    private Button submitButton = new Button("Submit");
    private ListView personalList;
    private Scene tabScene;

    //Konduktor som tar in peronsal
    public LogInWindows(PersonalFX personalFX) {
        this.personalFX = personalFX;
    }

    //Metod:Skapar en Login/start Stage som vissar när man starta programet
    public void LogIn (Stage stage, Scene tabScene){
        this.tabScene = tabScene;
        //skapar stagen
        Stage logInStage = new Stage();
        logInStage.setTitle("Program Delta");

        // skapar en Layout
        BorderPane Layout = new BorderPane();
        Layout.setPadding(new Insets(10,10,10,10));
        Layout.setMinHeight(48);
        Layout.setMinWidth(120);

        //Skapar en Vbox för att få allt att ligga på rad
        VBox vbox = new VBox(5);
        vbox.setMinHeight(24);
        vbox.setMinWidth(60);

        HBox hBox = new HBox(5);


        //skapar en listView
        personalList = personalUser();

        Button createAdmin = new Button("Create admin");
        createAdmin.setOnAction(e->{
            error.setText("");
            if(personalFX.personalDatabaseList().isEmpty()){
                createPersonalpopup(stage);
                logInStage.close();
            }
            else {
                error.setText("Select admin");
            }
        });



        //skapar login knappen som tar värdet på de användar objekt som användare har valt i listview och så öppnar den upp tab stagen
        Button logIn = new Button("Log in");
        logIn.setOnAction(e-> {
            error.setText("");
            selectPersonal = (Personal) personalList.getSelectionModel().getSelectedItem(); // tar in objekt som har valts i liteview
            if(!personalFX.personalDatabaseList().isEmpty()) {
                if (selectPersonal != null) {                   // kollar så något har valts
                    title = selectPersonal.getPerName();
                    stage.setTitle("Program Delta " + title);
                    stage.setScene(tabScene);                   //byter stage
                    stage.show();                               //öppnar de nya tabstage
                    logInStage.close();                         //stänger log in stage
                } else {
                    error.setText("Select a user!");
                }
            }
            else {
                error.setText("Create a admin");
            }
        });

        hBox.getChildren().addAll(logIn,createAdmin);
        hBox.setAlignment(Pos.CENTER);

        //lägger in listeview, knappen och error text i Vbox
        vbox.getChildren().addAll(personalList,hBox,error);
        vbox.setAlignment(Pos.CENTER);



        Layout.setCenter(vbox); // lägger in allt i layouten

        //öppnar login stagen
        Scene returnScene = new Scene(Layout);
        logInStage.setScene(returnScene);
        logInStage.show();

    }

    //Metod: Skapar en ListView
    public ListView personalUser(){
        ObservableList<Personal> personal = FXCollections.observableList(personalFX.personalDatabaseList());
        ListView<Personal> listView = new ListView<>(personal);
        listView.setPrefHeight(personal.size() * 24);


        return listView;
    }
    public void createPersonalpopup(Stage createStage){

        createStage.setTitle("Information Form");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);


        Label firstNameLabel = new Label("Förnamn:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Efternamn:");
        TextField lastNameField = new TextField();

        Label nicknameLabel = new Label("Nickname:");
        TextField nicknameField = new TextField();

        Label addressLabel = new Label("Gatuadress:");
        TextField addressField = new TextField();

        Label postalCodeLabel = new Label("Postnummer:");
        TextField postalCodeField = new TextField();

        Label cityLabel = new Label("Postort:");
        TextField cityField = new TextField();

        Label countryLabel = new Label("Land:");
        TextField countryField = new TextField();

        Label emailLabel = new Label("E-mail:");
        TextField emailField = new TextField();

        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameField, 1, 0);

        grid.add(lastNameLabel, 0, 1);
        grid.add(lastNameField, 1, 1);

        grid.add(nicknameLabel, 0, 2);
        grid.add(nicknameField, 1, 2);

        grid.add(addressLabel, 0, 3);
        grid.add(addressField, 1, 3);

        grid.add(postalCodeLabel, 0, 4);
        grid.add(postalCodeField, 1, 4);

        grid.add(cityLabel, 0, 5);
        grid.add(cityField, 1, 5);

        grid.add(countryLabel, 0, 6);
        grid.add(countryField, 1, 6);

        grid.add(emailLabel, 0, 7);
        grid.add(emailField, 1, 7);

        GridPane.setColumnSpan(submitButton, 2);
        grid.add(submitButton, 0, 8);

        submitButton.setOnAction(e -> {
            tempPers = createPersonal (
                    firstNameField.getText(),
                    lastNameField.getText(),
                    nicknameField.getText(),
                    addressField.getText(),
                    postalCodeField.getText(),
                    cityField.getText(),
                    countryField.getText(),
                    emailField.getText());

            personalFX.personalController.save(tempPers);
            LogIn(createStage,tabScene);
            createStage.close();
        });


        Scene scene = new Scene(grid, 300, 400);
        createStage.setScene(scene);

        createStage.show();

    }

    public Personal createPersonal(String firstName, String lastName, String nickname,
                                   String address, String postalCode, String city,
                                   String country, String email){
        Personal personal = new Personal();
        personal.setPerName(firstName);
        personal.setPerLname(lastName);
        personal.setPerNickname(nickname);
        personal.setPerAddress(address);
        personal.setPerPostalCode(postalCode);
        personal.setPerCity(city);
        personal.setPerCountry(country);
        personal.setPerEmail(email);
        return personal;
    }

}
