module com.example.teamdelta100 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.teamdelta100 to javafx.fxml;
    exports com.example.teamdelta100;
}