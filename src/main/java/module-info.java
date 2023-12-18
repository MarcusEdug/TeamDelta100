module com.example.teamdelta100 {
    requires javafx.controls;
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires hibernate.entitymanager;
    exports com.example.teamdelta100;
    opens com.example.teamdelta100.entities to org.hibernate.orm.core;
    exports com.example.teamdelta100.entities;

    //Måste finnas för att CombinedFX ska funka
    opens com.example.teamdelta100.view to javafx.graphics;

    exports com.example.teamdelta100.view;
    exports com.example.teamdelta100.view.Player;
    opens com.example.teamdelta100.view.Player to javafx.graphics;
    exports com.example.teamdelta100.view.Match;
    opens com.example.teamdelta100.view.Match to javafx.graphics;
    exports com.example.teamdelta100.view.Games;
    opens com.example.teamdelta100.view.Games to javafx.graphics;
    exports com.example.teamdelta100.view.Team;
    opens com.example.teamdelta100.view.Team to javafx.graphics;
    exports com.example.teamdelta100.view.Personal;
    opens com.example.teamdelta100.view.Personal to javafx.graphics;
}