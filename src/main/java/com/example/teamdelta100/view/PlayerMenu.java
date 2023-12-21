package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.PlayerController;
import com.example.teamdelta100.entities.Player;
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


public class PlayerMenu {
    PlayerController playerController = new PlayerController();
    TableView tableView;
    private Scene tabScene;
    private Stage window;
    private LogInWindows logInWindows;


    public Tab playerTab() {
        Tab tabLayout = new Tab("Players");
        tabLayout.setClosable(false);
        Button addPlayer = button("Add Player");
        Button deletePlayer = button("Delete Player");
        Button showInfo = button("Show info");
        Button updatePlayer = button("Update Player");

        tableView = table();
        tabLayout.setOnSelectionChanged(e-> update());

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(addPlayer, deletePlayer, showInfo, updatePlayer); // Replaced logOut with updatePlayer

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView, buttonV);
        AnchorPane.setTopAnchor(buttonV, 100.0);
        AnchorPane.setLeftAnchor(buttonV, 270.0);

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

        TableColumn playerLastNameColumn = new TableColumn("Team name");
        playerLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("teamName"));

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
        button.setOnAction(e -> {
            PlayerPopup playerPopup = new PlayerPopup(tableView, playerController);
            if (input.equals("Add Player")) {
                try {
                    playerPopup.addPlayer();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (input.equals("Delete Player")) {
                List<Player> playerList = playerController.getAll();
                if (playerController.deletePlayerById(playerPopup.deletePlayer(playerList))) {
                    System.out.println("Deleted Player");
                } else {
                    System.out.println("Failed to delete Player");}
                update();
            } else if (input.equals("Show info")) {
                playerPopup.showPlayerInfo();
            } else if (input.equals("Update Player")) {
                playerPopup.showUpdatePlayerForm();
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
    public void setWindow(Stage window) {
        this.window = window;
    }
}