package com.example.teamdelta100.view.Personal;
import com.example.teamdelta100.controller.PersonalController;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class  PersonalPopup{
    PersonalinfoForm info = new PersonalinfoForm();
    PersonalController personalController;
    TableView tableView;

    public PersonalPopup(TableView tableView, PersonalController personalController){
        this.tableView = tableView;
        this.personalController = personalController;
    }

    public void addPersonal() throws Exception {
        info.init();
        info.addPerComponents(tableView, personalController);
        info.start(new Stage());

    }
}