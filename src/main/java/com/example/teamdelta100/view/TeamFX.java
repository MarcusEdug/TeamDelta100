package com.example.teamdelta100.view;

import com.example.teamdelta100.entities.Player;
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

/*
    Den här klassen skapar upp teams Tab som kommer lägga in i klassen CombinedFX
    Klass har 5 metoder som gör
    * Skapar taben
    * Skapar knappar
    * Skapar TableView
    * Upprättare TableView
    * Tar ut en lista av Databasen
 */
public class TeamFX {
    private TeamsController controller = new TeamsController();
    private TeamPopup popup = new TeamPopup();
    private TableView tableView;
    private Teams tempTeam;
    private PlayerMenu playerMenu;
    private List<Teams> teamList;
    private Scene tabScene;
    private Stage window;
    private LogInWindows logInWindows;

    //Metod: skapar och skickar tillbaka en tab för Teams
    public Tab teamTab(){
        //skapar Taben
        Tab teamTab = new Tab("Teams");
        teamTab.setClosable(false);

        //skapar alla knappar
        Button add = createButton("Add team");
        Button assign = createButton("Assign Player");
        Button remove = createButton("Remove Player");
        Button delete = createButton("Delete team");
        Button update = createButton("Update team");
        Button logOut = createButton("Log out");

        createTable(); //Skapar tableView
        teamTab.setOnSelectionChanged(e-> updateTable()); //Uppdatara tableView

        //skapar en VBox för att lägga in alla knappar
        VBox vBoxForButton = new VBox(10);
        vBoxForButton.getChildren().addAll(add,assign,remove,delete,update,logOut);

        //skapar en AnchorPane som funkar som layout
        AnchorPane teamLayout = new AnchorPane();
        teamLayout.getChildren().addAll(tableView, vBoxForButton);
        AnchorPane.setTopAnchor(vBoxForButton,100.0);
        AnchorPane.setLeftAnchor(vBoxForButton,350.0);

        teamTab.setContent(teamLayout);

        return teamTab;
    }

    //Metod: Skapar knappar och lägger in funktioner på dom
    public Button createButton(String input) {
        //Skapar knappen och beroende på vad den heter gör den olika saker
        Button button = new Button(input);

        button.setOnAction(e->
        {

            if (input.equals("Add team"))
            {
                String teamName = popup.addTeam(); // tar emot en String som användar har skrivit

                if (teamName != null) // kollar så att användare har valt ett namn
                {
                    if (controller.save(new Teams(teamName))) // försöker ladda in den i databasen
                    {
                        System.out.println(teamName + " added");
                    }
                    else
                    {
                        System.out.println("Failed to add a player");
                    }
                }
                else
                {
                    System.out.println("No player");
                }
                updateTable(); //uppdatare tableView
            }

            else if (input.equals("Assign Player"))
            {
                teamDatabaseList(); //tar fram en lista på vad som finns i databasen

                if (!teamList.isEmpty() && !playerMenu.playerDatabaseList().isEmpty()) // kollar ifall de finns någon team och player att assign
                {
                    popup.assignPlayerToTeam(teamList, playerMenu.playerDatabaseList()); // väljer ut ett Team objekt och ett player objekt att koppla ihop

                    if (controller.addPlayerToTeams(popup.getPlayerId(), popup.getTeamId())) // Kopplar ihop ett team objekt med ett player objekt
                    {
                        System.out.println("Successfully assigned a player");
                    }
                    else
                    {
                        System.out.println("Failed to assign a player");
                    }
                }
                else
                {
                    System.out.println("There are no teams or no player added");
                }

                updateTable(); // uppdatera tableView
            }

            else if (input.equals("Remove Player"))
            {
                teamDatabaseList();//tar fram en lista på vad som finns i databasen

                popup.removePlayerFromTeam(playerMenu.playerDatabaseList()); // tar fram ett player objekt att koppla ifrån ett team objekt

                if (popup.getPlayerId() != 0 && popup.getTeamId() != 0) // kollar så att användar har valt något
                {
                    if (controller.removePlayerToTeams(popup.getPlayerId(), popup.getTeamId())) // Kopplar ifrån player objekt ifrån teams objekt
                    {
                        System.out.println("Successfully assigned a player");
                    }
                    else
                    {
                        System.out.println("Failed to assign a player");
                    }
                }
                else
                {
                    System.out.println("close");
                }
                updateTable(); // uppdatara TableView
            }

            else if (input.equals("Delete team"))
            {
                teamDatabaseList();//tar fram en lista på vad som finns i databasen

                int teamInt = popup.deleteTeam(teamList); //tar fram ett id på de team objekt som ska tas bort

                if(teamInt != 0) // kollar så att användar har valt något
                {
                    if (controller.deleteTeamsById(teamInt)) // tar bort teams objektet
                    {
                        System.out.println("The team is deleted");
                    }
                    else
                    {
                        System.out.println("Failed to delete the team");
                    }
                }
                else
                {
                    System.out.println("No team");
                }
                updateTable(); // uppdaterar tableview
            }

            else if (input.equals("Update team"))
            {
                teamDatabaseList();//tar fram en lista på vad som finns i databasen

                tempTeam = popup.updateTeamName(teamList); //tar fram ett befintligt team objekt som har blivt justerad

                if (tempTeam != null) // kollar så att något har valts
                {
                    if (controller.updateTeams(tempTeam)) // laddar in de justerade teams objekt i databasen
                    {
                        changePlayerTeamName(tempTeam);
                        System.out.println("The team is updatade");
                    }
                    else
                    {
                        System.out.println("Failed to update.");
                    }
                }
                else
                {
                    System.out.println("Failed to update.");
                }
                updateTable(); // uppdaterar database
            }
            else if (input.equals("Log out"))
            {
               logInWindows.LogIn(window,tabScene); // tar upp login stagen

               window.close(); // stänger tab stagen
            }
        });

        return button;
    }

    //Metod: Skapar tableView och returnar en tableView
    public TableView createTable (){
        //skapar tableviewn
        tableView = new TableView<Teams>();
        tableView.setEditable(true);

        //läggar in dom värderna som jag vill se på min tableview
        TableColumn teamIdColumn = new TableColumn<Teams, Integer>("Team ID");
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("id"));

        TableColumn teamNameColumn = new TableColumn<Teams, String>("Team Name");
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<Teams, String>("name"));

        TableColumn playersIdColumn = new TableColumn<Teams, String>("Players");
        playersIdColumn.setCellValueFactory(new PropertyValueFactory<Teams, String>("numberOfPlayerList"));

        TableColumn gameNameColumn = new TableColumn<Teams, String>("Game");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<Teams, String>("gameName"));

        tableView.getColumns().addAll(teamIdColumn,teamNameColumn,playersIdColumn,gameNameColumn);

        return tableView;
    }

    //Uppdaterar tableview
    public void updateTable(){
        tableView.getItems().clear();
        for (Teams temp : teamDatabaseList() ) {
            tableView.getItems().add(temp);
        }
    }

    //tar fram en lista på de som finns i database
    public List<Teams> teamDatabaseList (){
       teamList = controller.getAListOfDatabase();
        return teamList;
    }

    //Metod: uppdatar teams namn på en player obejekt ifall den har blivit ihop kopplad
    public void changePlayerTeamName(Teams teams){
        for (Teams team1: teamDatabaseList()){
            if(!team1.getNumberOfPlayerList().isEmpty()){
                for(Player teamPlayer : team1.getNumberOfPlayerList()){
                    if (teamPlayer.getTeams().getId() == teams.getId()){
                        teamPlayer.setTeamName(teams.getName());
                        teamPlayer.setTeams(teams);
                        playerMenu.playerController.updatePlayer(teamPlayer);
                    }
                }
            }
        }
    }

    //Getters och Setters
    public void setPlayerMenu(PlayerMenu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public TeamsController getController() {
        return controller;
    }

    public void setController(TeamsController controller) {
        this.controller = controller;
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