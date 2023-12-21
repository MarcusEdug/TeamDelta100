package com.example.teamdelta100.view;
import com.example.teamdelta100.controller.PersonalController;
import com.example.teamdelta100.entities.Personal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class  PersonalPopup{
    PersonalinfoForm persInfo = new PersonalinfoForm();
    private int userIntSubmit;
    private Stage window;
    private ComboBox comboBox;
    private Scene scene;
    private String titleName;
    private Label featureText;
    private TableView<Personal> tableView;
    private PersonalController personalController;

    public PersonalPopup(TableView tableView, PersonalController personalController){
        this.tableView = tableView;
        this.personalController = personalController;
    }

    public void popupWindows(){
        window = new Stage();
        //window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titleName);
        window.setHeight(150);
        window.setWidth(320);
        window.setScene(scene);
        window.showAndWait();
    }

    private Personal getPersonalById(int id) {
        return personalController.getPersonalById(id);
    }
    private ObservableList<Integer> getPersonalIds() {
        List<Personal> personals = personalController.getAll(false);
        ObservableList<Integer> personalIds = FXCollections.observableArrayList();
        for (Personal personal : personals) {
            personalIds.add(personal.getId());
        }
        return personalIds;
    }

    public void addPersonal() throws Exception {
        persInfo.init();
        persInfo.addPerComponents(tableView, personalController);
        persInfo.start(new Stage());
    }

    public int deletePersonal (List<Personal> personalList){
        titleName = "Delete Personal";
        featureText = new Label("Which Person do you want to delete?");

        comboBox = new ComboBox();
        for (Personal personal : personalList){
            int id = personal.getId();
            comboBox.getItems().add(id);
        }
        Button submit = new Button("Submit");
        submit.setOnAction(e->{
            userIntSubmit = (int) comboBox.getValue();
            window.close();
        });

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindows();

        return userIntSubmit;
    }

    public void showPersonalInfo() {
        Stage choosePlayerStage = new Stage();
        choosePlayerStage.initModality(Modality.APPLICATION_MODAL);
        choosePlayerStage.setTitle("Choose Personal");

        Label chooseLabel = new Label("Choose a Person:");
        ComboBox<Integer> personalComboBox = new ComboBox<>();
        personalComboBox.setItems((getPersonalIds()));

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            Integer selectedId = personalComboBox.getValue();
            if (selectedId != null) {
                Personal selectedPersonal = getPersonalById(selectedId);
                if (selectedPersonal != null) {
                    showPersonalDetails(selectedPersonal);
                }
            }
            choosePlayerStage.close();
        });

        VBox chooseLayout = new VBox(10);
        chooseLayout.getChildren().addAll(chooseLabel, personalComboBox, submitButton);
        chooseLayout.setAlignment(Pos.CENTER);

        choosePlayerStage.setScene(new Scene(chooseLayout, 250, 100));
        choosePlayerStage.showAndWait();
    }

    public void showPersonalDetails(Personal personal) {
        Stage infoStage = new Stage();
        infoStage.initModality(Modality.APPLICATION_MODAL);
        infoStage.setTitle("Personal Information");

        VBox infoLayout = new VBox(10);
        infoLayout.setAlignment(Pos.CENTER);
        infoLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("personal Information");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Label idLabel = new Label("Personal ID: " + personal.getId());
        Label nameLabel = new Label("Personal Name: " + personal.getPerName());
        Label lastnameLabel = new Label("Personal Lastname: " + personal.getPerLname());
        Label nicknameLabel = new Label("Personal Nickname: " + personal.getPerNickname());
        Label addressLabel = new Label("Personal Address: " + personal.getPerAddress());
        Label postalCodeLabel = new Label("Personal Postal Code: " + personal.getPerPostalCode());
        Label cityLabel = new Label("Personal City: " + personal.getPerCity());
        Label countryLabel = new Label("Personal Country: " + personal.getPerCountry());
        Label emailLabel = new Label("Personal Email: " + personal.getPerEmail());

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> infoStage.close());

        infoLayout.getChildren().addAll(titleLabel, idLabel, nameLabel, lastnameLabel, nicknameLabel, addressLabel,
                postalCodeLabel, cityLabel, countryLabel, emailLabel, closeButton);

        Scene infoScene = new Scene(infoLayout, 300, 350);

        infoStage.setScene(infoScene);
        infoStage.showAndWait();
    }

    public void showUpdatePersonalForm() {
        window = new Stage();
        titleName = "Update Personal Information";
        featureText = new Label("Choose Personal ID to update:");

        comboBox = new ComboBox<>();
        List<Personal> personalList = personalController.getAll(false);
        for (Personal personal : personalList) {
            comboBox.getItems().add(personal);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            Personal selectedPersId = (Personal) comboBox.getValue();

            try {
                persInfo.updatePersInfo(window, selectedPersId);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titleName);
        window.setHeight(150);
        window.setWidth(320);
        window.setScene(scene);
        window.showAndWait();
    }
}