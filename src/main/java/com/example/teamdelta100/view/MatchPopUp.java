package com.example.teamdelta100.view;

// Klasskommentar
// Hanterar popup rutorna för att hantera matcher

import com.example.teamdelta100.entities.Match;
import com.example.teamdelta100.entities.Player;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.ArrayList;
import java.util.List;

public class MatchPopUp {
    private String temp;

    // Tom Konstruktor
    public MatchPopUp(){}

    // Metod: Popup för att lägga till match player vs player
    public void createMatchPlayer(){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Add Match Player vs. Player");
        popup.setMinWidth(200);

        // HÄMTA INNEHÅLL FRÅN VILKA SPELARE SOM FINNS ATT VÄLJA PÅ
        ComboBox comboBox = new ComboBox();
        List<Player> tempList = new ArrayList<>();
        for(Player p : tempList){
            // Player player = tempList.getName();
            //comboBox.getItems().add(player);
        }

        Button createButton = new Button("Create Match");
        createButton.setOnAction(event -> {
            popup.close();
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(createButton);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att lägga till match team vs team
    public void createMatchTeam(){
        // Samma som createMatchPlayer förutom att hämta teams ist för players
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Add Match Team vs. Team");
        popup.setMinWidth(200);

        Button createButton = new Button("Create Match");
        createButton.setOnAction(event -> {
            // temp = player2.getText();
            popup.close();
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(createButton);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att lägga till ett resultat för en match
    public void addResult(){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Add Result");
        popup.setMinWidth(200);

        TextField resultOne = new TextField("Player 1"); // Resultat player/team 1
        TextField resultTwo = new TextField("Player 2"); // Resultat player/team 2

        Button addButton = new Button("Add Result");

        addButton.setOnAction(event -> {
            String tempResultOne = resultOne.getText();
            String tempResultTwo = resultTwo.getText();
            popup.close();
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(resultOne, resultTwo, addButton);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att ta bort en match
    public void removeMatch(List<Match> inList){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Remove Match");
        popup.setMinWidth(200);

        // Skapa upp en lista att visa för användaren
        ListView<Match> list = new ListView<Match>();
        list.setItems((ObservableList<Match>) inList);

        list.setPrefHeight(150);
        list.setPrefWidth(150);

        Button removeButton = new Button("Delete Match");

        removeButton.setOnAction(event -> {
            Match selectedMatch = list.getSelectionModel().getSelectedItem();
            // Ta bort vald match
            popup.close();
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(removeButton);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


    // Metod: Popup för att uppdatera match
    public void chooseMatchUpdate(List<Match> inList){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Update Match");
        popup.setMinWidth(200);

        // Skapa upp en lista att visa för användaren
        ListView<Match> list = new ListView<Match>();
        list.setItems((ObservableList<Match>) inList);

        list.setPrefHeight(150);
        list.setPrefWidth(150);


        // Välj match att ändra
        Button chooseButton = new Button("Choose Match");

        chooseButton.setOnAction(event -> {
            Match selectedMatch = list.getSelectionModel().getSelectedItem();
            popup.close();
            updateMatch(selectedMatch);
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(chooseButton);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }

    // Metod: Popup för att uppdatera match
    public void updateMatch(Match selectedMatch){
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Update Match");
        popup.setMinWidth(200);

        // rubrik
        Label label = new Label();
        String teamPlayer = selectedMatch.getPlayerOrTeam();
        label.setText(teamPlayer + " vs. " + teamPlayer);


        TextField playerTeamOne = new TextField();
        TextField playerTeamTwo = new TextField();
        TextField resultOne = new TextField();
        // resultOne.setTextFormatter((new TextFormatter<>(new NumberStringConverter())));
        TextField resultTwo = new TextField();
        // setTextFormatter((new TextFormatter<>(new NumberStringConverter())));
        TextField winner = new TextField();
        DatePicker date = new DatePicker(selectedMatch.getMatchDate());

        // Skriva in de värden som redan finns
        playerTeamOne.setText(selectedMatch.getPlayerTeamOne());
        playerTeamTwo.setText(selectedMatch.getPlayerTeamTwo());

        // Resultat 1
        // Resultat 2


        winner.setText(selectedMatch.getWinner());



        Button updateButton = new Button("Update Match");
        updateButton.setOnAction(event -> {
            // Hämta varje fält och lägg in i objektet & databasen

            popup.close();
        });

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(updateButton);
        Scene popupscene = new Scene(box);
        popup.setScene(popupscene);
        popup.showAndWait();
    }


}
