package com.example.teamdelta100.view.Player;

import com.example.teamdelta100.controller.PlayerController;
import com.example.teamdelta100.entities.Personal;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
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

public class  PlayerPopup {
    InformationForm info = new InformationForm();
    TableView tableView;
    PlayerController playerController;
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
    public PlayerPopup(TableView tableView, PlayerController playerController){
        this.tableView = tableView;
        this.playerController = playerController;
    }
    public PlayerPopup() {

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
    public int deletePlayer (List<Player> playerList){
        titleName = "Delete Player";
        featureText = new Label("Which Player do you want to delete?");

        comboBox = new ComboBox();
        for (Player player : playerList){
            int id = player.getId();
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

    public void addPlayer() throws Exception {

        info.init();  // Initialize the InformationForm
        info.addComponents(tableView, playerController);
        info.start(new Stage());  // Start the InformationForm

    }
}