package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.MatchController;
import com.example.teamdelta100.entities.Match;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

// Klasskommmentar
// Skapar och skickar tillbaka scenen för matcherna

public class MatchScene {
    private MatchController matchController = new MatchController(); // Kontroller för match
    private MatchPopUp popup = new MatchPopUp(); // Ruta som syns vid knapptryck
    private TableView matchTable; // Tabell som syns med innehåll från databasen

    // Tom Konstruktor
    public MatchScene(MatchController mc) {}


    // Metod: Skapa & skicka tillbaka scenen för matcher
    private Tab CreateAndReturnSceneMatch(){
        Tab matchTab = new Tab("Matches");
        matchTab.setClosable(false);

        // Grundpane
        AnchorPane matchPane = new AnchorPane();
        // Lägga in storleken för matchpane

        // Tabellen
        TableView viewMatches = createTable();

        // Knapparna
        VBox buttonBox = createButtons();

        matchPane.getChildren().addAll(viewMatches, buttonBox);
        matchTab.setContent(matchPane);

        // Skicka tillbaka
        return matchTab;
    }


    // Metoder för att skapa upp GUI //

    // Metod: Skapa upp tabellen
    public TableView createTable() {
        // Användaren ska se: datum, matcher, om de är klara, vem som vunnit etc
        // Lägga tabellnamnen

        // Tabeller som syns: Player1, Player2 / Team1, Team2 , Date, kommande / avgjord, (om avgjord vem som vann),

        matchTable = new TableView<Match>(); // Skapa tabellen

        // Kolumn Player1/Team1
        // Kolumn Player2/Team2
        // Kolumn Date
        // Kolumn Done/Not Done
        // Kolumn Winner

        TableColumn matchIdColumn = new TableColumn<Match, Integer>("Match id");
        matchIdColumn.setCellValueFactory(new PropertyValueFactory<Match, Integer>("matchId"));

        TableColumn matchNameColumn = new TableColumn<Match, Integer>("Match Date");
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<Match, Integer>("matchDate"));

        matchTable.getColumns().addAll(matchIdColumn, matchNameColumn);

        return matchTable;
    }

    // Metod: Skapa knapparna
    public VBox createButtons(){
        VBox tempButtonBox = new VBox();

        // Knapparna
        Button addPlayerMatch = new Button("Add Match (Player vs Player)");
        Button addTeamMatch = new Button("Add Match (Team vs Team)");
        Button addResult = new Button("Add Match Result");
        Button delete = new Button("Delete Match");
        Button update = new Button("Update Match");
        Button logOut = new Button("Log out");

        // Vad som händer vid tryck på knapp
        // Lägga till Match Player
        addPlayerMatch.setOnAction(event -> {
            popup.createMatchPlayer(); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen


            // ruta med att användaren får välja två spelare från listan
            // Hämta info om två spelare
            // användaren får välja om spelet har ett resultat
            // Användaren får välja datum
            // lägga in i databasen
        });

        addTeamMatch.setOnAction(event -> {
            popup.createMatchTeam(); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen

            // Ruta med att användaren får välja två lag från listan
            // Hämta info om lagen
            // användaren får välja om spelet har ett resultat
            // Användaren får välja datum
            // lägg in i databasen
        });

        addResult.setOnAction(event -> {
            popup.addResult(); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen

            // Användaren väljer en match från listan
            // Skriver in resultatet
            // Uppdaterar databasen
        });

        delete.setOnAction(event -> {
            popup.removeMatch(); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen

            // Användaren väljer en match från listan
            // Användaren klickar ok
            // Matchen tas bort från databasen
            // uppdatera listan
        });

        update.setOnAction(event -> {
            popup.updateMatch(); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen

            // Användaren väljer en match att uppdatera info
            // Användaren skriver in ny info
            // databasen och tabellen uppdateras
        });

        logOut.setOnAction(event -> {
            System.out.println("HELVETE LOGGAT UT WOOOP!");
        });

        // Lägga till knapparna i vboxen
        tempButtonBox.getChildren().addAll(addPlayerMatch, addTeamMatch, addResult, delete, update, logOut);
        return tempButtonBox;
    }



    // Metoder för att hantera användarens val //



    // Metod: Uppdatera tabellen vid förändringar
    public void updateTable(){
        matchTable.getItems().clear();
        for(Match t : matchController.tableUpdate(true)){
            matchTable.getItems().add(t);
        }
    }





}