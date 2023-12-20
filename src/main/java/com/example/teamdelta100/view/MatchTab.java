package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.MatchController;
import com.example.teamdelta100.entities.Match;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;


// Skapar och skickar tillbaka en tab för matcherna samt innehåller koppling till popupfönster.
// Evelina Daun

public class MatchTab {
    private MatchController matchController;
    private MatchPopUp popup; // Popupfönster som tillhör knapparna
    private TableView<Match> matchTableView; // Tabellen för alla matcher

    // Knappar
    private Button addPlayerMatch;
    private Button addTeamMatch;
    private Button addResult;
    private Button delete;
    private Button update;
    private Button logOut;
    private Button showAll;
    private Button showComming;
    private Button showDone;
    private Scene tabScene;
    private Stage window;
    private LogInWindows logInWindows;

    private Label tabelLabel; // Label som ändras efter vilka delar av tabellen som ska synas




    // Konstruktor
    public MatchTab() {
        matchController  = new MatchController();
        popup = new MatchPopUp(matchController);
    }


    // Metod: Skapa & skicka tillbaka scenen för matcher
    public Tab createMatchTab(){
        Tab matchTab = new Tab("Matches"); // Skapa en tab
        matchTab.setClosable(false);
        AnchorPane matchPane = new AnchorPane(); // Grund pane

        tabelLabel = new Label("all"); // Label för att visa vilka matcher som visas i tabellen
        createTable(); // Skapa tabell
        matchTab.setOnSelectionChanged(e->updateTable()); // Uppdatera tabell

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

        // Kolumn: Player/Team
        TableColumn<Match, String> playerOrTeam = new TableColumn<>("Player/Team");
        playerOrTeam.setCellValueFactory(new PropertyValueFactory<>("playerOrTeam"));

        // Kolumner: Player/Team 1 & 2 Namn
        TableColumn<Match, String> nameOne = new TableColumn<>("Player/Team 1");
        TableColumn<Match, String> nameTwo = new TableColumn<>("Player/Team 2");
        nameOne.setCellValueFactory(new PropertyValueFactory<>("nameOne"));
        nameTwo.setCellValueFactory(new PropertyValueFactory<>("nameTwo"));

        // Kolumn: Date
        TableColumn<Match, LocalDate> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("matchDate"));

        // Kolumn: Played/Not Played
        TableColumn<Match, String> played = new TableColumn<>("Played/Not Played");
        played.setCellValueFactory(new PropertyValueFactory<>("played"));

        // Kolumner: Result 1 & 2
        TableColumn<Match, String> resultOne = new TableColumn<>("Result 1");
        TableColumn<Match, String> resultTwo = new TableColumn<>("Result 2");
        resultOne.setCellValueFactory(new PropertyValueFactory<>("resultOne"));
        resultTwo.setCellValueFactory(new PropertyValueFactory<>("resultTwo"));

        // Kolumn: Winner
        TableColumn<Match, String> winner = new TableColumn<>("Winner");
        winner.setCellValueFactory(new PropertyValueFactory<>("winner"));

        matchTableView.getColumns().addAll(playerOrTeam, nameOne, nameTwo, date, played, resultOne, resultTwo, winner);
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
            logInWindows.LogIn(window,tabScene); // tar upp login stagen

            window.close(); // stänger tab stagen
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

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void setLogInWindows(LogInWindows logInWindows) {
        this.logInWindows = logInWindows;
    }

    public void setTabScene(Scene tabScene) {
        this.tabScene = tabScene;
    }
}