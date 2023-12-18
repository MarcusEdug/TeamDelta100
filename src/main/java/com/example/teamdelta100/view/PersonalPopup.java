package com.example.teamdelta100.view;
import com.example.teamdelta100.controller.PersonalController;
import com.example.teamdelta100.entities.Personal;
import com.example.teamdelta100.entities.Teams;
import com.example.teamdelta100.view.PersonalinfoForm;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class  PersonalPopup{
    PersonalinfoForm info = new PersonalinfoForm();
    PersonalController personalController;
    TableView tableView;
    private String userStringSubmit;
    private int userIntSubmit;
    private Stage window;
    private ComboBox comboBox;
    private Scene scene;
    private String titleName;
    private Label featureText;
    private int teamId;
    private int playerId;
    private Teams tempTeam;
    private Text error = new Text();

    public PersonalPopup(TableView tableView, PersonalController personalController){
        this.tableView = tableView;
        this.personalController = personalController;
    }

    public PersonalPopup() {

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

    public void addPersonal() throws Exception {
        info.init();
        info.addPerComponents(tableView, personalController);
        info.start(new Stage());
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
}