package com.example.teamdelta100.view;

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
import com.example.teamdelta100.controller.TeamsController;
import com.example.teamdelta100.entities.Teams;

import java.util.List;


public class TeamFX {
    TeamsController controller = new TeamsController();
    private TeamPopup popup = new TeamPopup();
    private TableView tableView;

    private PlayerMenu playerMenu;
    private List<Teams> teamList;
    private Scene tabScene;
    private Stage window;
    private LogInWindows logInWindows;

    public Tab teamTab(){
        Tab teamTab = new Tab("Teams");
        teamTab.setClosable(false);

        Button add = createButton("Add team");
        Button assign = createButton("Assign Player");
        Button remove = createButton("Remove Player");
        Button delete = createButton("Delete team");
        Button update = createButton("Update team");
        Button logOut = createButton("Log out");

        createTable();
        updateTable();

        VBox vBoxForButton = new VBox(10);
        vBoxForButton.getChildren().addAll(add,assign,remove,delete,update,logOut);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView, vBoxForButton);
        AnchorPane.setTopAnchor(vBoxForButton,100.0);
        AnchorPane.setLeftAnchor(vBoxForButton,420.0);

        teamTab.setContent(anchorPane);

        return teamTab;
    }
    public Button createButton(String input) {
        Button button = new Button(input);
        button.setOnAction(e-> {
            if (input.equals("Add team")) {
                String teamName = popup.addTeam();

                if(controller.save(new Teams(teamName))){
                    System.out.println(teamName + " added");
                } else {
                    System.out.println("Failed to add a player");
                }
                updateTable();


            }else if (input.equals("Assign Player")) {
                teamDatabaseList();
                if (!teamList.isEmpty() && !playerMenu.playerDatabaseList().isEmpty()) {
                    popup.assignPlayerToTeam(teamList, playerMenu.playerDatabaseList());

                    if (popup.getPlayerId() != 0 && popup.getTeamId() != 0) {
                        if (controller.addPlayerToTeams(popup.getPlayerId(), popup.getTeamId())) {
                            System.out.println("Successfully assigned a player");
                        } else {
                            System.out.println("Failed to assign a player");
                        }
                    } else {
                        System.out.println("close");
                    }
                }
                else {
                    System.out.println("There are no teams or no player added");
                }

                updateTable();
            }
            else if (input.equals("Remove Player")){
                teamDatabaseList();
                    popup.removePlayerFromTeam(playerMenu.playerDatabaseList());

                        if (controller.removePlayerToTeams(popup.getPlayerId(), popup.getTeamId())) {
                            System.out.println("Successfully assigned a player");
                        } else {
                            System.out.println("Failed to assign a player");
                        }

                updateTable();
            }

            else if (input.equals("Delete team")) {
                teamDatabaseList();
                if (controller.deleteTeamsById(popup.deleteTeam(teamList))){
                    System.out.println("The team is deleted");
                }
                else {
                    System.out.println("Failed to delete the team");
                }


                updateTable();


            }
            else if (input.equals("Update team")) {
                teamDatabaseList();

                if(controller.updateTeams(popup.updateTeamName(teamList))){
                    System.out.println("The team is updatade");
                } else {
                    System.out.println("Failed to update.");
                }
                updateTable();


            }
            else if (input.equals("Log out")) {
                window.setScene(logInWindows.LogIn(window,tabScene));
                //teamDatabaseList();
            }
        });


        return button;
    }

    public TableView createTable (){
        tableView = new TableView<Teams>();
        tableView.setEditable(true);

        TableColumn teamIdColumn = new TableColumn<Teams, Integer>("Team ID");
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("id"));


        TableColumn teamNameColumn = new TableColumn<Teams, String>("Team Name");
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<Teams, String>("name"));
        teamNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn playersIdColumn = new TableColumn<Teams, Integer>("Players");
        playersIdColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("numberOfPlayerList"));

        TableColumn gameNameColumn = new TableColumn<Teams, Integer>("Game");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("gameName"));

        TableColumn matchNameColumn = new TableColumn<Teams, Integer>("Match");
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("matchName"));

        tableView.getColumns().addAll(teamIdColumn,teamNameColumn,playersIdColumn,gameNameColumn,matchNameColumn);

        return tableView;
    }
    public void updateTable(){
        tableView.getItems().clear();
        for (Teams temp : controller.tableUpdate() ) {
           temp.countPlayer();
            tableView.getItems().add(temp);
        }
    }
    public List<Teams> teamDatabaseList (){
       teamList = controller.tableUpdate();
        return teamList;
    }

    public PlayerMenu getPlayerMenu() {
        return playerMenu;
    }

    public void setPlayerMenu(PlayerMenu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public TeamsController getController() {
        return controller;
    }

    public void setController(TeamsController controller) {
        this.controller = controller;
    }

    public Scene getTabScene() {
        return tabScene;
    }

    public void setTabScene(Scene tabScene) {
        this.tabScene = tabScene;
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }

    public LogInWindows getLogInWindows() {
        return logInWindows;
    }

    public void setLogInWindows(LogInWindows logInWindows) {
        this.logInWindows = logInWindows;
    }
}