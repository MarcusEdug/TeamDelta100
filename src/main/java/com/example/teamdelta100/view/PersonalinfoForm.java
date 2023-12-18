package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.PersonalController;
import com.example.teamdelta100.controller.PlayerController;
import com.example.teamdelta100.entities.Personal;
import com.example.teamdelta100.entities.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PersonalinfoForm extends Application {
    private Stage window;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField nicknameField;
    private TextField adressField;
    private TextField postalCodeField;
    private TextField cityField;
    private TextField countryField;
    private TextField emailField;
    private String userStringInput;
    private TableView tableView;
    private PersonalController personalController;
    Button submitButton = new Button("Submit");
    Personal tempPers;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Information Form");
        this.window = primaryStage;
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

        TextField userText = new TextField();

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

            personalController.save(tempPers);
            update();
        });


        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getNicknameField() {
        return nicknameField;
    }

    public TextField getAdressField() {
        return adressField;
    }

    public TextField getPostalCodeField() {
        return postalCodeField;
    }

    public TextField getCityField() {
        return cityField;
    }

    public TextField getCountryField() {
        return countryField;
    }

    public TextField getEmailField() {
        return emailField;
    }


    public static void main(String[] args) {
        launch(args);
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

    public void addPerComponents(TableView tableView, PersonalController personalController) {
        this.tableView = tableView;
        this.personalController = personalController;
    }
    private void update(){
        tableView.getItems().clear();
        for (Personal temp : personalController.tableUpdate(true) ) {
            tableView.getItems().add(temp);
        }
        window.close();
    }
}