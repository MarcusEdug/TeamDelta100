package com.example.teamdelta100.view;
import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.entities.Games;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;


/*
Skapar upp en tab, "flik", som läggs in i klassen CombinedFX
Skapar flik
Skapar knappar
Skapar TableView med kolumner
Tar ut lista från databasen
 */
public class GamesFX {
    private TableView tableView;
    private GameController gameController = new GameController(); //Koppling till controller-klassen
    private GamePopup gamePopup = new GamePopup(); //Tillhörande popup-fönster till knapparna
    private PlayerMenu playerMenu;
    private List<Games> listOfGames;
    private TeamFX teamFX;
    private Scene tabScene;
    private Stage window;
    private LogInWindows logInWindows;

    //Metod: Skapar upp och returnerar en tab för games
    public Tab gameTab() {
        Tab tabLayout = new Tab("Games");
        tabLayout.setClosable(false);

        //Skapar tillhörande knappar
        Button add = button("Add Game");
        Button delete = button("Delete Game");
        Button update = button("Update Game");
        Button assignPlayer = button("Assign player to a game");
        Button assignTeam = button("Assign team to a game");
        Button removePlayer = button("Remove player from game");
        Button removeTeam = button("Remove team from game");
        Button logOut = button("Log out");

        tableView = table();

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(add, assignPlayer, removePlayer, assignTeam, removeTeam, delete, update, logOut);

        tabLayout.setOnSelectionChanged(e-> update()); //Uppdaterar när användaren byter flik

        //Skapar en layout för tableView och tillhörande knappar
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView, buttonV);
        AnchorPane.setTopAnchor(buttonV, 75.0);
        AnchorPane.setLeftAnchor(buttonV, 350.0);

        tabLayout.setContent(anchorPane);

        return tabLayout;
    }

    //Metod: Skapar knappar och hanterar deras funktioner
    public Button button(String input) {
        Button button = new Button(input);

        button.setOnAction(e -> {
            if (input.equals("Add Game")) {
                String name = gamePopup.addGame(); //Tar emot sträng från användaren
                if (gameController.save(new Games(name))) { //Försöker spara till databasen
                    System.out.println(name + " added");
                } else {
                    System.out.println("Failed to add game");
                }
                update(); //Uppdaterar med nu information

            } else if (input.equals("Assign player to a game")) {
                try {
                    gameDatabaseList(); //Hämtar lista från databas
                    List<Player> players = playerMenu.playerDatabaseList(); //Sparar listan
                    if (listOfGames == null || players == null) { //Kollar så att värden finns
                        throw new Exception("Games or player are null");
                    }
                } catch (Exception n1) {
                    System.out.println("Error 1: " + n1.getMessage());
                }
                if (!listOfGames.isEmpty() && !playerMenu.playerDatabaseList().isEmpty()) { //Kollar så att listan inte är tom
                    gamePopup.assignPlayerToGame(listOfGames, playerMenu.playerDatabaseList()); //Öppnar popup för att välja spel/spelare
                    try {
                        if (gamePopup.getPlayerId() != 0 && gamePopup.getGameId() != 0) { //Om användaren har gjort ett val
                            if (gameController.addPlayerToGame(gamePopup.getPlayerId(), gamePopup.getGameId())) { //Kopplar ihop player-objekt med games-objekt
                                System.out.println("Player assigned to game");
                            } else {
                                System.out.println("Failed to assign player to game");
                            }
                        }
                    } catch (Exception n2) {
                        System.out.println("Error 2: " + n2.getMessage());
                    }
                } else {
                    System.out.println("No player assigned");
                }
                update(); //Uppdaterar ny information

            } else if (input.equals("Assign team to a game")) {
                try {
                    gameDatabaseList(); //Hämtar lista från databasen
                    List<Teams> teams = teamFX.teamDatabaseList(); //Sparar listan
                    if (listOfGames == null || teams == null) { //Kollar om det finns värdet
                        throw new Exception("Games or teams are null");
                    }
                } catch (Exception e1) {
                    System.out.println("Error 1: " + e1.getMessage());
                    return;
                }
                if (!listOfGames.isEmpty() && !teamFX.teamDatabaseList().isEmpty()) { //Kollar så att listan inte är tom
                    gamePopup.assignTeamToGame(listOfGames, teamFX.teamDatabaseList()); //Öppnar popup för användaren att välja lag/spel
                    if (gamePopup.getTeamId() != 0 && gamePopup.getGameId() != 0) { //Om användaren har valt
                        try {
                            if (gameController.addTeamToGame(gamePopup.getTeamId(), gamePopup.getGameId())) { //Kopplar ihop game-objekt med team-objekt
                                System.out.println("Team assigned to game");
                            } else {
                                System.out.println("Failed to assign team");
                            }
                        } catch (Exception e2) {
                            System.out.println("Error 2: " + e2.getMessage());
                        }
                    } else {
                        System.out.println("No teams added");
                    }
                }
                update();

            } else if (input.equals("Delete Game")) {
                gameController.getAll(true); //Hämtar alla spel från databasen
                List<Games> gamesList = gameController.getAll(true); //Sparar
                if (gameController.deleteGameById(gamePopup.deleteGame(gamesList))) { //Tar bort den valda spelet från databasen
                    System.out.println("Game deleted");
                } else {
                    System.out.println("Failed to remove game");
                }

                update();

            } else if (input.equals("Update Game")) {
                List<Games> gamesToUpDate = gameController.getAll(true); //Skapar en ny lista och sparar listan från databasen

                if (gameController.updateGames(gamePopup.updateGameName(gamesToUpDate))){ //Låter användaren skriva in ett nytt namn på spel
                    System.out.println("Games updated");
                } else {
                    System.out.println("Games update failed");
                }
                update();

            } else if (input.equals("Remove player from game")) {
                gameDatabaseList();
                gamePopup.removePlayerFromGame(playerMenu.playerDatabaseList()); //Öppnar popup med en lista med spelare som är kopplade till ett spel

                if (gameController.removePlayerFromGame(gamePopup.getPlayerId(), gamePopup.getGameId())) { //Låter användaren välja en spelare att ta bort utifrån ID
                    System.out.println("Player removed");
                } else {
                    System.out.println("Failed to remove player");
                }
                update();
            } else if (input.equals("Remove team from game")) {
                gameDatabaseList();
                gamePopup.removeTeamFromGame(teamFX.teamDatabaseList()); //Öppnar popup med lista över vilka lag som är kopplade till ett spel

                if (gameController.removeTeamFromGame(gamePopup.getTeamId(), gamePopup.getGameId())) { //Låter användaren välja ett lag att ta bort
                    System.out.println("Team removed");
                } else {
                    System.out.println("Failed to remove team");
                }
                update();

            } else if (input.equals("Log out")) {
                logInWindows.LogIn(window, tabScene); // Öppnar login

                window.close(); // Stänger tab stagen
            }
        });

        return button;
    }

    //Metod: Skapar upp och returnerar tableView
    public TableView table() {
        tableView = new TableView<Games>();
        tableView.setEditable(true);

        //Lägger in de värden som ska visas i kolumnerna

        //Kolumn för spel-id
        TableColumn<Games, Integer> gameIdColumn = new TableColumn<>("Game Id");
        gameIdColumn.setCellValueFactory(new PropertyValueFactory("gameId"));

        //Kolumn för spelnamn
        TableColumn<Games, String> gameNameColumn = new TableColumn("Game name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory("gameName"));

        //Kolumn för spelarnamn
        TableColumn<Games, String> gamePlayerColumn = new TableColumn("Player name");
        gamePlayerColumn.setCellValueFactory(new PropertyValueFactory("playerList"));

        //Kolumn för lagnamn
        TableColumn<Games, String> gameTeamColumn = new TableColumn<>("Team name");
        gameTeamColumn.setCellValueFactory(new PropertyValueFactory("teamsList"));

        tableView.getColumns().addAll(gameIdColumn, gameNameColumn, gamePlayerColumn, gameTeamColumn);

        return tableView;
    }

    //Metod: Uppdaterar tableView
    public void update() {
        if (tableView == null || gameController == null) {
            throw new IllegalStateException("tableView or gameController is not initialized");
        }
        tableView.getItems().clear();
        for (Games temp : gameController.tableUpdate()) {
            tableView.getItems().add(temp);
        }
    }

    //Hämtar lista från databasen
    public List<Games> gameDatabaseList() {
        listOfGames = gameController.tableUpdate();
        return listOfGames;
    }

    //Getters & setters
    public void setPlayerMenu(PlayerMenu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public void setTeamFX(TeamFX teamFX) {
        this.teamFX = teamFX;
    }

    public void setTabScene(Scene tabScene) {
        this.tabScene = tabScene;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public void setLogInWindows(LogInWindows logInWindows) {
        this.logInWindows = logInWindows;
    }
}







