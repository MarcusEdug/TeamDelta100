package com.example.teamdelta100.view;

import javafx.stage.Stage;

public class  PlayerPopup {

    public void addPlayer() throws Exception {
        InformationForm info = new InformationForm();
        info.init();  // Initialize the InformationForm
        info.start(new Stage());  // Start the InformationForm


    }
}