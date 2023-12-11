package com.example.teamdelta100.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.teamdelta100.controller.Share;
import com.example.teamdelta100.controller.TeamsController;
import com.example.teamdelta100.entities.Teams;



public class FX extends Application implements Share {
    TeamsController controller = new TeamsController();
    Popup popup = new Popup();
    TableView tableView;


    @Override
    public void start(Stage stage) throws Exception {

        //mainThread.start();
        Button add = button("Add team");
        Button assign = button("Assign Player");
        Button delete = button("Delete team");
        Button update = button("Update team");
        Button logOut = button("Log out");

        TableView viewTeams = table();

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(add,assign,delete,update,logOut);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(viewTeams, buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,270.0);
       // AnchorPane.setRightAnchor(buttonV,50.0);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();

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

    public TableView table (){
        tableView = new TableView<Teams>();
        TableColumn teamIdColumn = new TableColumn<Teams, Integer>("Team ID");
        teamIdColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("id"));

        TableColumn teamNameColumn = new TableColumn<Teams, Integer>("Team Name");
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<Teams, Integer>("name"));

        tableView.getColumns().addAll(teamIdColumn,teamNameColumn);

        return tableView;
    }
    public void update (){
        tableView.getItems().clear();
        for (Teams temp : controller.tableUpdate(true) ) {
            tableView.getItems().add(temp);
        }

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