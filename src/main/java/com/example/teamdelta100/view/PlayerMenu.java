package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.PlayerController;
import com.example.teamdelta100.entities.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerMenu extends Application {

    PlayerController playerController = new PlayerController();
    Popup popup = new Popup();
    TableView tableView;
    public static void main(String[] args) {
        // Example: Creating a new player and saving it to the database
        Player newPlayer = new Player();
        newPlayer.setPlayerName("John");
        newPlayer.setPlayerLastname("Doe");


        PlayerController playerController = new PlayerController();
        if (playerController.save(newPlayer)) {
            System.out.println("Player data saved successfully!");
        } else {
            System.out.println("Failed to save player data.");
        }

        // Example: Retrieving and printing all players
        playerController.printAll();

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
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

    public TableView table (){
        tableView = new TableView<Player>();
        TableColumn playerIdColumn = new TableColumn<Player, Integer>("Player ID");
        playerIdColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("ID"));

        TableColumn playerNameColumn = new TableColumn<Player, String>("Player Name");
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));

        TableColumn playerLastNameColumn = new TableColumn<Player, String>("Player Lastname");
        playerLastNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("Lastname"));

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
                String playerName = popup.addTeam();

                if(playerController.save(new Player(playerName))){
                    System.out.println(playerName + " added");
                } else {
                    System.out.println("Failed to add customer");
                }
                //update();


            } else if (input.equals("Assign Player")) {
                //SKAPA SÅ ATT MAN SKA ASSIGN A SPELAR TIL LETT LAG


            } /*else if (input.equals("Delete team")) {
                controller.getAll(true);
                if (controller.deleteTeams(popup.deleteTeam())){
                    System.out.println("Laget är borta");
                }
                else {
                    System.out.println("misslyckades att ta bort laget");
                }


                update();


            }
            */else if (input.equals("Update team")) {




                /*controller.getAll(true);
                System.out.print("Välj id:");
                Teams teamsToUpdate = controller.getTeamsById(new Scanner(System.in).nextInt());
                System.out.print("Ändra namn från " + teamsToUpdate.getName() + " till?: ");
                teamsToUpdate.setName(new Scanner(System.in).nextLine());
                if(controller.updateTeams(teamsToUpdate)){
                    System.out.println("Teams updated");
                } else {
                    System.out.println("Teams update failed");
                };

                 */
            }

            else if (input.equals("Log out")) {
                //Skapa så att man kan stänga av programet
            }
        });


        return button;
    }
    public PlayerController getController() {
        return playerController;
    }

    public void setController(PlayerController playerController) {
        this.playerController = playerController;
    }
}
