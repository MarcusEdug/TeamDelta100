package com.example.teamdelta100;

import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.view.GameMenu;
import com.example.teamdelta100.view.GamesFX;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Piper Games");

        GamesFX gamesFX = new GamesFX();
        gamesFX.start(stage);
    }

    public static void main(String[] args) {
        launch();


    }
}