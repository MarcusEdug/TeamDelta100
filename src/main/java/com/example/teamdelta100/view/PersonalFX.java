package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.PersonalController;
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
import javafx.stage.Stage;

import java.util.List;

public class PersonalFX extends Application {
    PersonalController personalController = new PersonalController();
    TableView tableView;
    private Scene tabScene;
    private Stage window;
    private LogInWindows logInWindows;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        TableView viewPersonal = table();

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(viewPersonal, buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,650.0);

        anchorPane.setPrefSize(800, 500);
        tableView.setPrefSize(600, 400);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public Tab PersonalTab(){
        Tab tabLayout = new Tab("Personal");
        tabLayout.setClosable(false);
        Button addPersonal = button("Add Personal");
        Button deletePersonal = button("Delete Personal");
        Button updatePlayer = button( "Update Player");
        Button showInfo =  button("Show Info");
        Button logOut = button("Logout");

        tableView = table();
        tabLayout.setOnSelectionChanged(e-> update());

        VBox buttonV = new VBox(10);
        buttonV.getChildren().addAll(addPersonal, deletePersonal, updatePlayer, showInfo, logOut);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(tableView, buttonV);
        AnchorPane.setTopAnchor(buttonV,100.0);
        AnchorPane.setLeftAnchor(buttonV,270.0);

        tabLayout.setContent(anchorPane);

        return tabLayout;
    }


    public TableView table (){
        tableView = new TableView<Personal>();
        tableView.setEditable(true);
        TableColumn personalId = new TableColumn("Personal ID");
        personalId.setCellValueFactory(new PropertyValueFactory<Personal, Integer>("id"));

        TableColumn personalName = new TableColumn("personal Name");
        personalName.setCellValueFactory(new PropertyValueFactory<Personal, String>("perName"));

        TableColumn personalLastname = new TableColumn("Personal Lastname");
        personalLastname.setCellValueFactory(new PropertyValueFactory<Player, String>("perLname"));

        tableView.getColumns().addAll(personalId, personalName, personalLastname);

        return tableView;
    }
    public void update (){
        tableView.getItems().clear();
        for (Personal temp : personalController.tableUpdate(true) ) {
            tableView.getItems().add(temp);
        }

    }
    public Button button(String input) {
        Button button = new Button(input);
        button.setOnAction(e-> {
            PersonalPopup personalPopup = new PersonalPopup(tableView, personalController);
            if (input.equals("Add Personal")) {
                try {
                    personalPopup.addPersonal();
                } catch (Exception ex) {
                    ex.printStackTrace(); // Handle the exception appropriately (e.g., log or display an error message)
                }

            } else if (input.equals("Delete Personal")) {
                List<Personal> personalList = personalController.getAll();
                if (personalController.deletePersonalById(personalPopup.deletePersonal(personalList))){
                    System.out.println("Bortagen Person");
                }
                else {
                    System.out.println("Tog ej bort Person");
                }
                update();
            }

            else if (input.equals("Show Info")) {
                personalPopup.showPersonalInfo();
            } else if (input.equals("Update Player")) {
                personalPopup.showUpdatePersonalForm();
            } else if (input.equals("Logout")) {
                logInWindows.LogIn(window,tabScene); // tar upp login stagen
                window.close(); // stänger tab stagen
            }
        });


        return button;
    }

    public List<Personal> personalDatabaseList (){
        List <Personal> personalList = personalController.tableUpdate(true);
        return personalList;
    }

    /*public void createPersonal(){

        personalController.save(new Personal("Sara", "Svensson", "Miss", "Saravägen", "65752", "Stockholm","Sverige" , "Sara@sarason" ));
        personalController.save(new Personal("Rolf", "Rolfsson", "Gubben", "Rolfvägen", "63241", "Stockholm","Sverige" , "Rolf@Rolfsons" ));
        personalController.save(new Personal("Sockan", "Hundsson", "Mr dog", "Hundgatan", "98970", "Uppsala","Sverige" , "Sockan@dogsspn" ));
        personalController.save(new Personal("Hellena", "Svensson", "queen", "Hell", "875887", "Uppsala","Sverige" , "Hellen@hell" ));
    }

     */

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
