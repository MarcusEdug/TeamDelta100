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
    private List<Games> gamesList;
    private TeamFX teamFX;

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
        Button logOut = button("Log out");


        tableView = table();

        VBox buttonV = new VBox(20);
        buttonV.getChildren().addAll(add,assignPlayer,assignTeam,delete,update,logOut);

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
                    List<Player> playerList = playerMenu.playerDatabaseList();
                    gamePopup.assignPlayerToGame(gameDatabaseList(), playerList);

                    if (gameController.addPlayerToGame(gamePopup.getPlayerId(), gamePopup.getGameId())) {
                        System.out.println("Player added");
                    } else {
                        System.out.println("Failed do add player");
                    }

                    update();

                }else if (input.equals("Assign team to a game")) {
                    gameDatabaseList();
                    if (!gamesList.isEmpty() && !teamFX.teamDatabaseList().isEmpty()) {
                        gamePopup.assignTeamToGame(gamesList, teamFX.teamDatabaseList());

                        if (gamePopup.getTeamId() != 0 && gamePopup.getTeamId() != 0) {
                            if (gameController.addTeamToGame(gamePopup.getTeamId(), gamePopup.getGameId())) {
                                System.out.println("Team assigned to game");
                            } else {
                                System.out.println("Failed to assign team");
                            }
                        } else {
                            System.out.println("Close");
                        }
                    }
                         else{
                            System.out.println("No teams added");
                        }
                        update();
                    }

                 else if (input.equals("Delete Game")) {
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

                /*} else if (input.equals("List all games")) {
                    gameController.getAll(true)
                }
                update();*/


                }else if (input.equals("Log out")) {
                window.close();
            }
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
        List<Games> listOfGames = gameController.tableUpdate();
        return listOfGames;
    }

    public PlayerMenu getPlayerMenu() {
        return playerMenu;
    }

    public void setPlayerMenu(PlayerMenu playerMenu) {
        this.playerMenu = playerMenu;
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







