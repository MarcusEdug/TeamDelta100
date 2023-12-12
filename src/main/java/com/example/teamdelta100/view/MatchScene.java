package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.MatchController;
import com.example.teamdelta100.entities.Match;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

// Klasskommmentar
// Skapar och skickar tillbaka scenen för matcherna

public class MatchScene {
    private MatchController matchController = new MatchController(); // Kontroller för match
    private MatchPopUp popup = new MatchPopUp(); // Ruta som syns vid knapptryck
    private TableView matchTableView; // Tabell som syns med innehåll från databasen

    // Tom Konstruktor
    public MatchScene(MatchController mc) {}


    // Metod: Skapa & skicka tillbaka scenen för matcher
    private Tab CreateAndReturnSceneMatch(){
        // Skapa tab
        Tab matchTab = new Tab("Matches");
        matchTab.setClosable(false);

        // Skapa grund pane
        AnchorPane matchPane = new AnchorPane();

        // Skapa tabell
        createTable();

        // Skapa knappar
        VBox buttonBox = createButtons();

        // Lägga till pane & tab
        matchPane.getChildren().addAll(matchTableView, buttonBox);
        matchTab.setContent(matchPane);

        // Skicka tillbaka tab
        return matchTab;
    }


    // Metoder för att skapa upp GUI //


    // Metod: Skapa upp tabellen
    public void createTable() {
        // Tabeller som syns: Player/Team, Player1, Player2 / Team1, Team2 , Date, kommande / avgjord, (om avgjord vem som vann),

        matchTableView = new TableView<Match>(); // Skapa tabellen

        // Kolumn Player eller Team
        TableColumn<Match, Integer> playerOrTeamColumn = new TableColumn<>("Player or Team");
        playerOrTeamColumn.setCellValueFactory(new PropertyValueFactory<>("playerOrTeam"));

        // Kolumn Player1/Team1
        TableColumn<Match, Integer> matchPTColumnOne = new TableColumn<>("Player/Team 1");
        matchPTColumnOne.setCellValueFactory(new PropertyValueFactory<>(""));

        // Kolumn Player2/Team2
        TableColumn<Match, Integer> matchPTColumnTwo = new TableColumn<>("Player/Team 1");
        matchPTColumnTwo.setCellValueFactory(new PropertyValueFactory<>("matchId"));

        // Kolumn Date
        TableColumn<Match, Integer> matchDateColumn = new TableColumn<>("Date");
        matchDateColumn.setCellValueFactory(new PropertyValueFactory<>("matchDate"));

        // Kolumn Done/Not Done
        TableColumn<Match, Integer> matchPlayedColumn = new TableColumn<>("Played/Not Played");
        matchPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("played"));

        // Kolumn Winner
        TableColumn<Match, Integer> matchWinner = new TableColumn<>("Winner");
        matchWinner.setCellValueFactory(new PropertyValueFactory<>("winner"));

        matchTableView.getColumns().addAll(playerOrTeamColumn, matchPTColumnOne, matchPTColumnTwo, matchDateColumn, matchPlayedColumn, matchWinner);
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
            List<Player> tempList = new ArrayList<Player>(); // Hämta listan med spelare - metodanropf
            popup.createMatchPlayer(); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen

            // Skicka tillbaka två id värden för valda spelare

            // Hämta info om två spelare
            // användaren får välja om spelet har ett resultat
            // Användaren får välja datum
            // lägga in i databasen
        });

        addTeamMatch.setOnAction(event -> {
            List<Teams> tempList = new ArrayList<Teams>();  // Hämta listan med matcher - metodanrop
            popup.createMatchTeam(); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen

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
            List<Match> tempMatchList = new ArrayList<>();// Hämta listan med matcher
            popup.removeMatch(tempMatchList); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen

            // Användaren väljer en match från listan
            // Användaren klickar ok
            // Matchen tas bort från databasen
            // uppdatera listan
        });

        update.setOnAction(event -> {
            List<Match> tempMatchList = new ArrayList<>();// Hämta listan med matcher
            popup.chooseMatchUpdate(tempMatchList); // Visa och hantera popup fönster
            updateTable(); // Uppdatera tabellen

            // Användaren väljer en match att uppdatera info
            // Användaren skriver in ny info
            // databasen och tabellen uppdateras
        });

        logOut.setOnAction(event -> {
            System.out.println("LOGGA UT WOOOP!!");
            // Tillbaka till startscenen
        });

        // Lägga till knapparna i vboxen
        tempButtonBox.getChildren().addAll(addPlayerMatch, addTeamMatch, addResult, delete, update, logOut);
        return tempButtonBox;
    }




    // Metod: Uppdatera tabellen vid förändringar
    public void updateTable(){
        matchTableView.getItems().clear();
        for(Match t : matchController.tableUpdate(true)){
            matchTableView.getItems().add(t);
        }
    }


    // Metod: Hämta en lista med alla matcher
    public void getMatchList(){
        // Hämta hela listan från matchcontroller
    }


    // Metod: Hämta alla spelare - Lista att skicka till popup
    public void getPlayerList(){
        // Hämta hela listan
    }


    // Metod: Hämta alla teams - Lista att skicka till popup
    public void getTeamList(){
        // Hämta hela listan
    }


}