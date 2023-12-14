package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.MatchController;
import com.example.teamdelta100.entities.Match;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.time.LocalDate;


// Skapar och skickar tillbaka en tab för matcherna samt innehåller koppling till popupfönster.
// Evelina Daun

public class MatchTab {
    private MatchController matchController; // Kontroller för match
    private MatchPopUp popup; // Ruta som syns vid knapptryck
    private TableView<Match> matchTableView; // Tabell som syns med innehåll från databasen

    // Knapparna bredvid tabellen
    private Button addPlayerMatch;
    private Button addTeamMatch;
    private Button addResult;
    private Button delete;
    private Button update;
    private Button logOut;
    private TeamFX fx;


    // Konstruktor
    public MatchTab(TeamFX fx) {
        matchController  = new MatchController();
        popup = new MatchPopUp(matchController);
        this.fx = fx;
    }


    // Metod: Skapa & skicka tillbaka scenen för matcher
    public Tab createAndReturnTabMatch(){
        Tab matchTab = new Tab("Matches"); // Skapa tab att skicka tillbaka
        matchTab.setClosable(false);

        AnchorPane matchPane = new AnchorPane(); // Grund pane

        createTable(); // Skapa tabell
        updateTable(); // Uppdatera tabell

        VBox buttonBox = createButtons(); // Skapa knappar
        buttonBox.setLayoutX(650);
        buttonBox.setLayoutY(20);
        buttonBox.setSpacing(15);
        setButtonAction();

        matchPane.getChildren().addAll(matchTableView, buttonBox); // Innehåll till grund pane
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
        matchPTColumnOne.setCellValueFactory(new PropertyValueFactory<>("playerTeamOneName"));

        // Kolumn: Player2/Team2 Namn
        TableColumn<Match, String> matchPTColumnTwo = new TableColumn<>("Player/Team 2");
        matchPTColumnTwo.setCellValueFactory(new PropertyValueFactory<>("playerTeamTwoName"));

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
    public VBox createButtons(){
        VBox tempButtonBox = new VBox();

        addPlayerMatch = new Button("Add Match (Player vs Player)");
        addTeamMatch = new Button("Add Match (Team vs Team)");
        addResult = new Button("Add Match Result");
        delete = new Button("Delete Match");
        update = new Button("Update Match");
        logOut = new Button("Log out");

        tempButtonBox.getChildren().addAll(addPlayerMatch, addTeamMatch, addResult, delete, update, logOut); // Lägga till knapparna i vboxen
        return tempButtonBox;
    }

    // Metod: Sätta händelselyssnare till knapparna
    public void setButtonAction(){

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
    }


    // Metod: Uppdatera tabellen efter popupfönstret
    public void updateTable(){
        matchTableView.getItems().clear(); // Tömma tabellen
        for(Match m : matchController.getAllMatchObjects()){ // Hämta och gå igenom listan
            matchTableView.getItems().add(m);
        }
    }

}