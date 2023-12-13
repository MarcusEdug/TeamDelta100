package com.example.teamdelta100.view;
import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.entities.Games;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.Scanner;

public class GamesFX extends Application {
    private TableView<Games> tableView;
    private GameController gameController = new GameController();
    GamePopup gamePopup = new GamePopup();
    Stage window;



    @Override
    public void start(Stage stage) throws Exception {

    Button add = button("Add Game");
    Button delete = button("Delete Game");
    Button update = button("Update Game");
    Button listAll = button("List all games");

    tableView = table();

    VBox buttonV = new VBox(10);
    buttonV.getChildren().addAll(add,delete,update,listAll);

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
        Button delete = button("Delete game");
        Button update = button("Update Game");
        Button listAll = button("List all games");

        tableView = table();

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(add,delete,update,listAll);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView,buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,270.0);

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

                } else if (input.equals("Delete Game")) {
                    gameController.getAll(true);
                    if (gameController.deleteGameById(gamePopup.deleteGame())) {
                        System.out.println("Spel borttaget");
                    } else {
                        System.out.println("Misslyckades att ta bort spel");
                    }

                    update();

                } else if (input.equals("Update Game")) {
                gameController.getAll(true);
                System.out.print("Välj id:");
                Games GamesToUpdate = gameController.getGameById(new Scanner(System.in).nextInt());
                System.out.print("Ändra namn från " + GamesToUpdate.getGameName() + " till?: ");
                GamesToUpdate.setGameName(new Scanner(System.in).nextLine());
                if (gameController.updateGames(GamesToUpdate)) {
                    System.out.println("Games updated");
                } else {
                    System.out.println("Games update failed");
               /* }else if (input.equals("Log out")) {
                //Skapa så att man kan stänga av programet

                */
                }
                update();

                } else if (input.equals("List all games")) {
                gameController.getAll(true);
                }
                update();
        });
        return button;
    }
    public TableView<Games> table() {
        TableView<Games> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<Games, Integer> gameIdColumn = new TableColumn<>("Game Id");
        gameIdColumn.setCellFactory(new PropertyValueFactory("gameId"));

        TableColumn<Games, String> gameNameColumn = new TableColumn("Game name");
        gameNameColumn.setCellFactory(new PropertyValueFactory("gameName"));
        gameNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Games, String> gameGenreColumn = new TableColumn("Game genre");
        gameGenreColumn.setCellFactory(new PropertyValueFactory("gameGenre"));

        tableView.setItems(FXCollections.observableList(gameController.getAll(true)));

        tableView.getColumns().addAll(gameIdColumn, gameNameColumn, gameGenreColumn);

        return tableView;
    }
    public void update() {
        if (tableView == null || gameController == null) {
            throw new IllegalStateException("tableView or gameController is not initialized");
        }
        tableView.getItems().clear();
        for (Games temp : gameController.getAll(true)) {
            tableView.getItems().add(temp);
        }
    }

    public GameController getGameController(){
        return gameController;
    }
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public GamePopup getGamePopup() {
        return gamePopup;
    }

    public void setGamePopup(GamePopup gamePopup) {
        this.gamePopup = gamePopup;
    }

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }
}






