package com.example.teamdelta100.view;

import com.example.teamdelta100.entities.Personal;
import com.example.teamdelta100.entities.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LogInWindows {
    private PersonalFX personalFX;

    public LogInWindows(PersonalFX personalFX) {
        this.personalFX = personalFX;
    }

    public Scene LogIn (Stage stage, Scene tabScene){
        BorderPane gridPane = new BorderPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setMinHeight(48);
        gridPane.setMinWidth(120);




        VBox logInLayout = new VBox(5);
        logInLayout.setMinHeight(24);
        logInLayout.setMinWidth(60);


        ListView persnalList = personalUser();

        Button logIn = new Button("Log in");
        logIn.setOnAction(e-> {
            stage.setScene(tabScene);
        });


        logInLayout.getChildren().addAll(persnalList,logIn);
        logInLayout.setAlignment(Pos.CENTER);


        gridPane.setCenter(logInLayout);

        Scene returnScene = new Scene(gridPane);

        return returnScene;
    }

    public ListView personalUser(){
        ObservableList<Personal> personal = FXCollections.observableList(personalFX.personalDatabaseList());
        ListView<Personal> listView = new ListView<>(personal);
        listView.setPrefHeight(personal.size() * 24);


        return listView;
    }
}
