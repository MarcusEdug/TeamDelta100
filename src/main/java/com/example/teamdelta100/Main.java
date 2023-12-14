package com.example.teamdelta100;

import com.example.teamdelta100.view.CombinedFX;
import com.example.teamdelta100.view.TeamFX;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
       launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        CombinedFX combinedFX = new CombinedFX();
        combinedFX.start(stage);

    }
}