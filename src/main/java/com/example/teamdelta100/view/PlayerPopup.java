package com.example.teamdelta100.view;

import javafx.stage.Stage;

public class  PlayerPopup {
    InformationForm info = new InformationForm();

    public void addPlayer() throws Exception {

        info.init();  // Initialize the InformationForm
        info.start(new Stage());  // Start the InformationForm

    }
}