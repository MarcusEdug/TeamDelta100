package com.example.teamdelta100;

import com.example.teamdelta100.view.FX;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
       launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FX fx = new FX();
        fx.start(stage);

    }
}