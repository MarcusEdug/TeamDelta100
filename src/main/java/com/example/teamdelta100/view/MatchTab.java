package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.MatchController;
import com.example.teamdelta100.entities.Match;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;


// Skapar och skickar tillbaka en tab för matcherna samt innehåller koppling till popupfönster.
// Evelina Daun

public class MatchTab {
    private MatchController matchController;
    private MatchPopUp popup;
    private TeamFX teamFX;
    private TableView<Match> matchTableView;

    private Button addPlayerMatch;
    private Button addTeamMatch;
    private Button addResult;
    private Button delete;
    private Button update;
    private Button logOut;

    private Button showAll;
    private Button showComming;
    private Button showDone;

    private Label tabelLabel;


    // Konstruktor
    public MatchTab(TeamFX teamFX) {
        this.teamFX = teamFX;
        matchController  = new MatchController();
        popup = new MatchPopUp(matchController, teamFX);
    }


    // Metod: Skapa & skicka tillbaka scenen för matcher
    public Tab createAndReturnTabMatch(){ // createMatchTab
        Tab matchTab = new Tab("Matches"); // Skapa tab att skicka tillbaka
        matchTab.setClosable(false);

        AnchorPane matchPane = new AnchorPane(); // Grund pane

        tabelLabel = new Label("all"); // Label för att visa vilka matcher som visas i tabellen

        createTable(); // Skapa tabell
        updateTable(); // Uppdatera tabell

        VBox buttonVBox = createButtonsSide(); // Skapa knappar
        buttonVBox.setLayoutX(650);
        buttonVBox.setLayoutY(20);
        buttonVBox.setSpacing(15);

        HBox buttonHBox = creatButtonTable();
        buttonHBox.setLayoutX(20);
        buttonHBox.setLayoutY(420);
        buttonHBox.setSpacing(15);

        setButtonAction(); // Skapa upp händelser för knapparna

        matchPane.getChildren().addAll(matchTableView, buttonVBox, buttonHBox); // Innehåll till grund pane
        matchTab.setContent(matchPane); // Pane till tab
        return matchTab;
    }


    // Metod: Skapa upp tabellen
    public void createTable() {
        matchTableView = new TableView<Match>(); // Skapa tabellen

        // Kolumn: Player eller Team
        TableColumn<Match, String> playerOrTeamColumn = new TableColumn<>("Player/Team");
        playerOrTeamColumn.setCellValueFactory(new PropertyValueFactory<>("playerOrTeam"));

        // Kolumn: Player1/Team1 Namn
        TableColumn<Match, String> matchPTColumnOne = new TableColumn<>("Player/Team 1");
        matchPTColumnOne.setCellValueFactory(new PropertyValueFactory<>("playerTeamNameOne"));

        // Kolumn: Player2/Team2 Namn
        TableColumn<Match, String> matchPTColumnTwo = new TableColumn<>("Player/Team 2");
        matchPTColumnTwo.setCellValueFactory(new PropertyValueFactory<>("playerTeamNameTwo"));

        // Kolumn: Date
        TableColumn<Match, LocalDate> matchDateColumn = new TableColumn<>("Date");
        matchDateColumn.setCellValueFactory(new PropertyValueFactory<>("matchDate"));

        // Kolumn: Played/Not Played
        TableColumn<Match, String> matchPlayedColumn = new TableColumn<>("Played/Not Played");
        matchPlayedColumn.setCellValueFactory(new PropertyValueFactory<>("played"));

        // Kolumn: Result 1 & 2
        TableColumn<Match, String> matchResultOneColumn = new TableColumn<>("Result 1");
        TableColumn<Match, String> matchResultTwoColumn = new TableColumn<>("Result 2");
        matchResultOneColumn.setCellValueFactory(new PropertyValueFactory<>("resultOne"));
        matchResultTwoColumn.setCellValueFactory(new PropertyValueFactory<>("resultTwo"));

        // Kolumn: Winner
        TableColumn<Match, String> matchWinner = new TableColumn<>("Winner");
        matchWinner.setCellValueFactory(new PropertyValueFactory<>("winner"));

        matchTableView.getColumns().addAll(playerOrTeamColumn, matchPTColumnOne, matchPTColumnTwo, matchDateColumn, matchPlayedColumn, matchResultOneColumn, matchResultTwoColumn, matchWinner);
    }


    // Metod: Skapa knapparna
    public VBox createButtonsSide(){
        VBox tempButtonBox = new VBox();
        addPlayerMatch = new Button("Add Match (Player vs Player)");
        addTeamMatch = new Button("Add Match (Team vs Team)");
        addResult = new Button("Add Match Result");
        delete = new Button("Delete Match");
        update = new Button("Update Match");
        logOut = new Button("Log out");
        tempButtonBox.getChildren().addAll(addPlayerMatch, addTeamMatch, addResult, delete, update, logOut);
        return tempButtonBox;
    }

    //Metod: Skapa upp knappraden under tabellen
    public HBox creatButtonTable(){
        HBox hbox = new HBox();
        showAll = new Button("Show all");
        showComming = new Button("Show not played matches");
        showDone = new Button("Show played matches");
        hbox.getChildren().addAll(showAll, showComming, showDone);
        return hbox;
    }

    // Metod: Sätta händelselyssnare till knapparna
    public void setButtonAction(){

        // Knapparna på sidan
        addPlayerMatch.setOnAction(event -> {         // Vad som händer vid knapptryck
            popup.createMatch("player");  // Visa och hantera popup fönster
            updateTable();                            // Uppdatera tabellen
        });

        addTeamMatch.setOnAction(event -> {
            popup.createMatch("team");
            updateTable();
        });

        addResult.setOnAction(event -> {
            popup.addResult();
            updateTable();
        });

        delete.setOnAction(event -> {
            popup.deleteMatch();
            updateTable();
        });

        update.setOnAction(event -> {
            popup.matchUpdate();
            updateTable();
        });

        logOut.setOnAction(event -> {
            System.out.println("LOGGA UT WOOOP!!");
            // Tillbaka till startscenen
        });


        // Knapparna för att hantera vilken tabell som syns
        showAll.setOnAction(event -> {
            tabelLabel.setText("all");    // Label text ändras
            updateTable();                // Tabellen uppdateras med det nya label namnet
        });

        showComming.setOnAction(event -> {
            tabelLabel.setText("not played");
            updateTable();
        });

        showDone.setOnAction(event -> {
            tabelLabel.setText("played");
            updateTable();
        });
    }


    // Metod: Uppdatera tabellen efter popupfönstret
    public void updateTable(){

        if(tabelLabel.getText().equals("all")){   // Hela listan syns
            matchTableView.getItems().clear(); // Tömma tabellen
            for(Match m : matchController.getAllMatchObjects()){ // Hämta alla och gå igenom listan
                matchTableView.getItems().add(m);
            }
        }else if(tabelLabel.getText().equals("not played")){  // Endast inte spelade matcher syns
            matchTableView.getItems().clear();
            for(Match m : matchController.getAllMatchObjects()){
                if(m.getPlayed().equals("Not Played")){
                    matchTableView.getItems().add(m);
                }
            }
        }else if(tabelLabel.getText().equals("played")){  // Endast spelade matcher syns
            matchTableView.getItems().clear();
            for(Match m : matchController.getAllMatchObjects()){
                if(m.getPlayed().equals("Played")){
                    matchTableView.getItems().add(m);
                }
            }
        }
    }

}