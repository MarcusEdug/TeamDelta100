package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.PlayerController;
import com.example.teamdelta100.entities.Personal;
import com.example.teamdelta100.entities.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.List;

public class PlayerMenu extends Application {

    PlayerController playerController = new PlayerController();
    PlayerPopup playerPopup = new PlayerPopup();
    InformationForm info = new InformationForm();
    TableView tableView;
    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        PlayerPopup playerPopup = new PlayerPopup(tableView, playerController);
        Button addplayer = button("Add Player");

        TableView viewPlayers = table();

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(addplayer);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(viewPlayers, buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,650.0);

        anchorPane.setPrefSize(800, 500);
        tableView.setPrefSize(600, 400);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public Tab playerTab(){
        Tab tabLayout = new Tab("Players");
        tabLayout.setClosable(false);
        Button addPlayer = button("Add Player");
        Button deletePlayer = button("Delete Player");
        Button logOut = button("Log out");

        tableView = table();

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(addPlayer,deletePlayer,logOut);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView, buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,270.0);

        tabLayout.setContent(anchorPane);

        return tabLayout;
    }


    public TableView table (){
        tableView = new TableView<Player>();
        tableView.setEditable(true);
        TableColumn playerIdColumn = new TableColumn("Player ID");
        playerIdColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("Id"));

        TableColumn playerNameColumn = new TableColumn("Player Name");
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("playerName"));

        TableColumn playerLastNameColumn = new TableColumn("Player Lastname");
        playerLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("playerLastname"));

        tableView.getColumns().addAll(playerIdColumn,playerNameColumn, playerLastNameColumn);

        return tableView;
    }
    public void update (){
        tableView.getItems().clear();
        for (Player temp : playerController.tableUpdate(true) ) {
            tableView.getItems().add(temp);
        }

    }
    public Button button(String input) {
        Button button = new Button(input);
        button.setOnAction(e-> {
            if (input.equals("Add Player")) {
                try {
                    PlayerPopup playerPopup = new PlayerPopup(tableView, playerController);
                    playerPopup.addPlayer();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else if (input.equals("Delete Player")) {
                List<Player> playerList = playerController.getAll();
                if (playerController.deletePlayerById(playerPopup.deletePlayer(playerList))){
                    System.out.println("Bortagen Player");
                }
                else {
                    System.out.println("Tog ej bort Player");
                }


                update();
            } else if (input.equals("Show Info")) {

            } else if (input.equals("Log out")) {

            }
        });


        return button;
    }

    public List<Player> playerDatabaseList (){
        List <Player> teamlista = playerController.tableUpdate(true);
        return teamlista;
    }
    public PlayerController getController() {
        return playerController;
    }

    public void setController(PlayerController playerController) {
        this.playerController = playerController;
    }
}