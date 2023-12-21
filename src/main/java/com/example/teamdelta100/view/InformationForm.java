package com.example.teamdelta100.view;;
import com.example.teamdelta100.controller.PlayerController;;
import com.example.teamdelta100.entities.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class InformationForm extends Application {
    private Stage window;
    private TableView tableView;
    private PlayerController playerController;
    Button submitButton = new Button("Submit");
    Player tempPlayer;



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Information Form");
        this.window = primaryStage;
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);


        Label firstNameLabel = new Label("Förnamn:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Efternamn:");
        TextField lastNameField = new TextField();

        Label nicknameLabel = new Label("Nickname:");
        TextField nicknameField = new TextField();

        Label addressLabel = new Label("Gatuadress:");
        TextField addressField = new TextField();

        Label postalCodeLabel = new Label("Postnummer:");
        TextField postalCodeField = new TextField();

        Label cityLabel = new Label("Postort:");
        TextField cityField = new TextField();

        Label countryLabel = new Label("Land:");
        TextField countryField = new TextField();

        Label emailLabel = new Label("E-mail:");
        TextField emailField = new TextField();

        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameField, 1, 0);

        grid.add(lastNameLabel, 0, 1);
        grid.add(lastNameField, 1, 1);

        grid.add(nicknameLabel, 0, 2);
        grid.add(nicknameField, 1, 2);

        grid.add(addressLabel, 0, 3);
        grid.add(addressField, 1, 3);

        grid.add(postalCodeLabel, 0, 4);
        grid.add(postalCodeField, 1, 4);

        grid.add(cityLabel, 0, 5);
        grid.add(cityField, 1, 5);

        grid.add(countryLabel, 0, 6);
        grid.add(countryField, 1, 6);

        grid.add(emailLabel, 0, 7);
        grid.add(emailField, 1, 7);

        GridPane.setColumnSpan(submitButton, 2);
        grid.add(submitButton, 0, 8);

        submitButton.setOnAction(e -> {
            tempPlayer = createPlayer (
                    firstNameField.getText(),
                    lastNameField.getText(),
                    nicknameField.getText(),
                    addressField.getText(),
                    postalCodeField.getText(),
                    cityField.getText(),
                    countryField.getText(),
                    emailField.getText());

            playerController.save(tempPlayer);
            update();
        });


        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    public void updateInfo(Stage primaryStage, Player player){
        Player updatePlayer = player;
        primaryStage.setTitle("Information Form");
        this.window = primaryStage;
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);


        Label firstNameLabel = new Label("Förnamn:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Efternamn:");
        TextField lastNameField = new TextField();

        Label nicknameLabel = new Label("Nickname:");
        TextField nicknameField = new TextField();

        Label addressLabel = new Label("Gatuadress:");
        TextField addressField = new TextField();

        Label postalCodeLabel = new Label("Postnummer:");
        TextField postalCodeField = new TextField();

        Label cityLabel = new Label("Postort:");
        TextField cityField = new TextField();

        Label countryLabel = new Label("Land:");
        TextField countryField = new TextField();

        Label emailLabel = new Label("E-mail:");
        TextField emailField = new TextField();

        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameField, 1, 0);

        grid.add(lastNameLabel, 0, 1);
        grid.add(lastNameField, 1, 1);

        grid.add(nicknameLabel, 0, 2);
        grid.add(nicknameField, 1, 2);

        grid.add(addressLabel, 0, 3);
        grid.add(addressField, 1, 3);

        grid.add(postalCodeLabel, 0, 4);
        grid.add(postalCodeField, 1, 4);

        grid.add(cityLabel, 0, 5);
        grid.add(cityField, 1, 5);

        grid.add(countryLabel, 0, 6);
        grid.add(countryField, 1, 6);

        grid.add(emailLabel, 0, 7);
        grid.add(emailField, 1, 7);

        GridPane.setColumnSpan(submitButton, 2);
        grid.add(submitButton, 0, 8);
        submitButton.setOnAction(e -> {
            updatePlayer.setId(player.getId());
            updatePlayer.setPlayerName(firstNameField.getText());
            updatePlayer.setPlayerLastname(lastNameField.getText());
            updatePlayer.setNickname(nicknameField.getText());
            updatePlayer.setAddress(addressField.getText());
            updatePlayer.setPostalCode(postalCodeField.getText());
            updatePlayer.setCity(cityField.getText());
            updatePlayer.setCountry(countryField.getText());
            updatePlayer.setEmail(emailField.getText());

            playerController.updatePlayer(updatePlayer);

            window.close();
        });


        Scene scene = new Scene(grid, 700, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public Player createPlayer(String firstName, String lastName, String nickname,
                               String address, String postalCode, String city,
                               String country, String email){
        Player person = new Player();
        person.setPlayerName(firstName);
        person.setPlayerLastname(lastName);
        person.setNickname(nickname);
        person.setAddress(address);
        person.setPostalCode(postalCode);
        person.setCity(city);
        person.setCountry(country);
        person.setEmail(email);
        return person;
    }

    public void addComponents(TableView tableView, PlayerController playerController) {
        this.tableView = tableView;
        this.playerController = playerController;
    }
    private void update(){
        tableView.getItems().clear();
        for (Player temp : playerController.tableUpdate(true) ) {
            tableView.getItems().add(temp);
        }
        window.close();
    }
}