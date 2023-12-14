package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.PlayerController;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class  PlayerPopup {
    InformationForm info = new InformationForm();
    TableView tableView;
    PlayerController playerController;
    public PlayerPopup(TableView tableView, PlayerController playerController){
        this.tableView = tableView;
        this.playerController = playerController;
    }

    public void addPlayer() throws Exception {

        info.init();  // Initialize the InformationForm
        info.addComponents(tableView, playerController);
        info.start(new Stage());  // Start the InformationForm

    }
}