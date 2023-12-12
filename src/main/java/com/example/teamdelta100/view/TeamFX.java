package com.example.teamdelta100.view;

import javafx.application.Application;
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


public class TeamFX extends Application {
    TeamsController controller = new TeamsController();
    Popup popup = new Popup();
    TableView tableView;
    Stage window;


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        Button add = button("Add team");
        Button assign = button("Assign Player");
        Button delete = button("Delete team");
        Button logOut = button("Log out");

        tableView = table();

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(add,assign,delete,logOut);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView, buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,270.0);
       // AnchorPane.setRightAnchor(buttonV,50.0);

        Scene scene = new Scene(anchorPane);
        window.setScene(scene);
        window.show();

    }
    public Tab teamTab(){
        Tab tabLayout = new Tab("Teams");
        tabLayout.setClosable(false);
        Button add = button("Add team");
        Button assign = button("Assign Player");
        Button delete = button("Delete team");
        Button update = button("Update team");
        Button logOut = button("Log out");

        tableView = table();

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(add,assign,delete,update,logOut);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView, buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,270.0);

        tabLayout.setContent(anchorPane);

        return tabLayout;
    }
    public Button button(String input) {
        Button button = new Button(input);
        button.setOnAction(e-> {
            if (input.equals("Add team")) {
                String teamName = popup.addTeam();

                if(controller.save(new Teams(teamName))){
                    System.out.println(teamName + " added");
                } else {
                    System.out.println("Failed to add customer");
                }
                update();


            } else if (input.equals("Assign Player")) {
                //SKAPA SÅ ATT MAN SKA ASSIGN A SPELAR TIL LETT LAG


            }
            else if (input.equals("Delete team")) {
                List<Teams> teamsList = controller.getAll(true);
                if (controller.deleteTeamsById(popup.deleteTeam(teamsList))){
                    System.out.println("Laget är borta");
                }
                else {
                    System.out.println("misslyckades att ta bort laget");
                }


                update();


            }
            else if (input.equals("Update team")) {
                List<Teams> teamsList = controller.getAll(true);

                Teams temp = popup.choosTeam(teamsList);


                if(controller.updateTeams(popup.updateTeam(temp))){
                    System.out.println(temp);
                } else {
                    System.out.println("Failed to add customer");
                }
                update();


            }
            else if (input.equals("Log out")) {
                //teamDatabaseList();
            }
        });


        return button;
    }

    public TableView table (){
        tableView = new TableView<Teams>();
        tableView.setEditable(true);

        TableColumn teamIdColumn = new TableColumn<Teams, Integer>("Team ID");
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("id"));


        TableColumn teamNameColumn = new TableColumn<Teams, String>("Team Name");
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<Teams, String>("name"));
        teamNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn playersIdColumn = new TableColumn<Teams, Integer>("Players");
        playersIdColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("numberOfPlayer"));

        tableView.getColumns().addAll(teamIdColumn,teamNameColumn);

        return tableView;
    }
    public void update (){
        tableView.getItems().clear();
        for (Teams temp : controller.tableUpdate() ) {
            tableView.getItems().add(temp);
        }
    }
    public List<Teams> teamDatabaseList (){
        for (Teams temp : controller.tableUpdate() ){
            System.out.println("Team: " + temp.getName() + " och ID: " + temp.getId());
        }
        return controller.tableUpdate();
    }

    public TeamsController getController() {
        return controller;
    }

    public void setController(TeamsController controller) {
        this.controller = controller;
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }
    /*else if (input.equals("Change team name")) {
        controller.getAll(true);
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