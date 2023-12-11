package com.example.teamdelta100.view;

// Klasskommentar
// Hanterar popup rutorna för att hantera matcher

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MatchPopUp {
    private String temp;

    // Tom Konstruktor
    public MatchPopUp(){}

    // Metod: Popup för att lägga till match player vs player
    public void createMatchPlayer(){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Add Match Player vs Player");
        popup.setMinWidth(200);

        TextField player1 = new TextField();
        TextField player2 = new TextField();

        // HÄMTA INNEHÅLL FRÅN VILKA SPELARE SOM FINNS ATT VÄLJA PÅ


        Button createButton = new Button("Create Match");
        createButton.setOnAction(event -> {
            temp = player2.getText();
            System.out.println(temp);
            popup.close();
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(player1, player2, createButton);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att lägga till match team vs team
    public void createMatchTeam(){
        // Samma som createMatchPlayer förutom att hämta teams ist för players

    }


    // Metod: Popup för att lägga till ett resultat för en match
    public void addResult(){

    }


    // Metod: Popup för att ta bort en match
    public void removeMatch(){}


    // Metod: Popup för att uppdatera match
    public void updateMatch(){}


}
