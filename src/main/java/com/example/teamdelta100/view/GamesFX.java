package com.example.teamdelta100.view;
import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.entities.Games;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javafx.application.Application;
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

public class GamesFX extends Application {
    TableView tableView;
    GameController gameController = new GameController();
    GamePopup gamePopup = new GamePopup();
    Games games = new Games();
    Stage window;
    private PlayerMenu playerMenu;
    private List<Games> listOfGames;
    private TeamFX teamFX;

    public GamesFX() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

    Button add = button("Add Game");
    Button delete = button("Delete Game");
    Button update = button("Update Game");
    Button assignPlayer = button("Assign player to a game");
    Button assignTeam = button("Assign team to a game");
    Button logOut = button("Log out");

    tableView = table();

    VBox buttonV = new VBox(10);
    buttonV.getChildren().addAll(add,delete,update,assignPlayer,assignTeam,logOut);

    AnchorPane anchorPane = new AnchorPane();
    anchorPane.getChildren().addAll(tableView,buttonV);
    AnchorPane.setTopAnchor(buttonV,100.0);
    AnchorPane.setLeftAnchor(buttonV,270.0);
    //AnchorPane.setRightAnchor(buttonV,50.0);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();


    }
    public Tab gameTab(){
        Tab tabLayout = new Tab("Games");
        tabLayout.setClosable(false);
        Button add = button("Add Game");
        Button delete = button("Delete Game");
        Button update = button("Update Game");
        Button assignPlayer = button("Assign player to a game");
        Button assignTeam = button("Assign team to a game");
        Button remove = button("Remove from game");
        Button logOut = button("Log out");


        tableView = table();

        VBox buttonV = new VBox(20);
        buttonV.getChildren().addAll(add,assignPlayer,assignTeam,delete,update,remove,logOut);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView,buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,350.0);

        tabLayout.setContent(anchorPane);


        return tabLayout;
    }
    public Button button (String input) {
        Button button = new Button(input);
            button.setOnAction(e-> {
                if (input.equals("Add Game")) {
                    String name = gamePopup.addGame();
                    if (gameController.save(new Games(name))) {
                        System.out.println(name + " added");
                    } else {
                        System.out.println("Failed to add game");
                    }
                    update();

                } else if (input.equals("Assign player to a game")) {
                    try {
                        gameDatabaseList();
                        List<Player> players = playerMenu.playerDatabaseList();
                        if (listOfGames == null || players == null) {
                            throw new Exception("Games or player are null");
                        }
                    } catch (Exception n1) {
                        System.out.println("Error 1: " + n1.getMessage());
                    }
                    if (!listOfGames.isEmpty() && !playerMenu.playerDatabaseList().isEmpty()) {
                        gamePopup.assignPlayerToGame(listOfGames, playerMenu.playerDatabaseList());
                        try {
                            if (gamePopup.getPlayerId() != 0 && gamePopup.getGameId() != 0) {
                                if (gameController.addPlayerToGame(gamePopup.getPlayerId(), gamePopup.getGameId())) {
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
                    update();

                } else if (input.equals("Assign team to a game")) {
                    try {
                        gameDatabaseList();
                        List<Teams> teams = teamFX.teamDatabaseList();
                        if (listOfGames == null || teams == null) {
                            throw new Exception("Games or teams are null");
                        }
                    } catch (Exception e1) {
                        System.out.println("Error 1: " + e1.getMessage());
                        return;
                    }
                    if (!listOfGames.isEmpty() && !teamFX.teamDatabaseList().isEmpty()) {
                        gamePopup.assignTeamToGame(listOfGames, teamFX.teamDatabaseList());
                        if (gamePopup.getTeamId() != 0 && gamePopup.getGameId() != 0) {
                            try {
                                if (gameController.addTeamToGame(gamePopup.getTeamId(), gamePopup.getGameId())) {
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
                    gameController.getAll(true);
                    List<Games> gamesList = gameController.getAll(true);
                    if (gameController.deleteGameById(gamePopup.deleteGame(gamesList))) {
                        System.out.println("Game deleted");
                    } else {
                        System.out.println("Failed to remove game");
                    }

                    update();

                } else if (input.equals("Update Game")) {
                    List<Games> gamesToUpDate = gameController.getAll(true);

                    if (gameController.updateGames(gamePopup.updateGameName(gamesToUpDate))) {
                        System.out.println("Games updated");
                    } else {
                        System.out.println("Games update failed");
                    }
                    update();

                } else if (input.equals("Remove from game")) {

                }
                update();


            }else if (input.equals("Log out")) {
            window.close();

        });
        return button;
    }

    public TableView table() {
        tableView = new TableView<Games>();
        tableView.setEditable(true);

        TableColumn<Games, Integer> gameIdColumn = new TableColumn<>("Game Id");
        gameIdColumn.setCellValueFactory(new PropertyValueFactory("gameId"));

        TableColumn<Games, String> gameNameColumn = new TableColumn("Game name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory("gameName"));

        TableColumn<Games, String> gamePlayerColumn = new TableColumn("Player name");
        gamePlayerColumn.setCellValueFactory(new PropertyValueFactory("playerList"));

        TableColumn<Games, String> gameTeamColumn = new TableColumn<>("Team name");
        gameTeamColumn.setCellValueFactory(new PropertyValueFactory("teamsList"));

        tableView.getColumns().addAll(gameIdColumn, gameNameColumn, gamePlayerColumn,gameTeamColumn);

        return tableView;
    }
    public void update() {
        if (tableView == null || gameController == null) {
            throw new IllegalStateException("tableView or gameController is not initialized");
        }
        tableView.getItems().clear();
        for (Games temp : gameController.tableUpdate()) {
            tableView.getItems().add(temp);
        }
    }
   /* public List<Games> gameDatabaseList() {
        for (Games temp : gameController.tableUpdate()){
            System.out.println("Game: " + temp.getGameName() + "and ID: " + temp.getGameId());
        }
        return gameController.tableUpdate()
    }*/
    public List<Games> gameDatabaseList() {
        listOfGames = gameController.tableUpdate();
        return listOfGames;
    }

    public PlayerMenu getPlayerMenu() {
        return playerMenu;
    }

    public void setPlayerMenu(PlayerMenu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    public TeamFX getTeamFX() {
        return teamFX;
    }

    public void setTeamFX(TeamFX teamFX) {
        this.teamFX = teamFX;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

}







