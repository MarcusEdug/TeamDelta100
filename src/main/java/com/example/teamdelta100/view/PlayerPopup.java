package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.PlayerController;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import com.example.teamdelta100.view.InformationForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class  PlayerPopup {

    InformationForm info = new InformationForm();
    private int userIntSubmit;
    private Stage window;
    private ComboBox comboBox;
    private Scene scene;
    private String titleName;
    private Label featureText;
    private TableView<Player> tableView;
    private PlayerController playerController;



    public PlayerPopup(TableView<Player> tableView, PlayerController playerController) {
        this.tableView = tableView;
        this.playerController = playerController;

    }
    public void popupWindows(){
        window = new Stage();
        //window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titleName);
        window.setHeight(150);
        window.setWidth(320);
        window.setScene(scene);
        window.showAndWait();
    }
    public void InfoPopup(){
        window = new Stage();
        window.setTitle(titleName);
        window.setHeight(150);
        window.setWidth(750);
        window.setScene(scene);
        window.showAndWait();
    }
    public int deletePlayer (List<Player> playerList){
        titleName = "Delete Player";
        featureText = new Label("Which Player do you want to delete?");

        comboBox = new ComboBox();
        for (Player player : playerList){
            int id = player.getId();
            comboBox.getItems().add(id);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e->{

            userIntSubmit = (int) comboBox.getValue();
            window.close();
        });

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(featureText, comboBox, submit);
        vBox.setAlignment(Pos.CENTER);

        scene = new Scene(vBox);

        popupWindows();

        return userIntSubmit;
    }
    public void addPlayer() throws Exception {
        info.init();
        info.addComponents(tableView, playerController);
        info.start(new Stage());

    }
    public void showPlayerInfo() {
        Stage choosePlayerStage = new Stage();
        choosePlayerStage.initModality(Modality.APPLICATION_MODAL);
        choosePlayerStage.setTitle("Choose Player");

        Label chooseLabel = new Label("Choose a Player:");
        ComboBox<Integer> playerComboBox = new ComboBox<>();
        playerComboBox.setItems(getPlayerIds());

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            Integer selectedId = playerComboBox.getValue();
            if (selectedId != null) {
                Player selectedPlayer = getPlayerById(selectedId);
                if (selectedPlayer != null) {
                    showPlayerDetails(selectedPlayer);
                }
            }
            choosePlayerStage.close();
        });

        VBox chooseLayout = new VBox(10);
        chooseLayout.getChildren().addAll(chooseLabel, playerComboBox, submitButton);
        chooseLayout.setAlignment(Pos.CENTER);

        choosePlayerStage.setScene(new Scene(chooseLayout, 250, 100));
        choosePlayerStage.showAndWait();
    }

    private ObservableList<Integer> getPlayerIds() {
        List<Player> players = playerController.getAll(false);
        ObservableList<Integer> playerIds = FXCollections.observableArrayList();
        for (Player player : players) {
            playerIds.add(player.getId());
        }
        return playerIds;
    }

    private Player getPlayerById(int id) {
        return playerController.getPlayerById(id);
    }

    public void showPlayerDetails(Player player) {
        Stage infoStage = new Stage();
        infoStage.initModality(Modality.APPLICATION_MODAL);
        infoStage.setTitle("Player Information");

        VBox infoLayout = new VBox(10);
        infoLayout.setAlignment(Pos.CENTER);
        infoLayout.setPadding(new Insets(20));

        Label titleLabel = new Label("Player Information");
        titleLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Label idLabel = new Label("Player ID: " + player.getId());
        Label nameLabel = new Label("Player Name: " + player.getPlayerName());
        Label lastnameLabel = new Label("Player Lastname: " + player.getPlayerLastname());
        Label nicknameLabel = new Label("Player Nickname: " + player.getNickname());
        Label addressLabel = new Label("Player Address: " + player.getAddress());
        Label postalCodeLabel = new Label("Player Postal Code: " + player.getPostalCode());
        Label cityLabel = new Label("Player City: " + player.getCity());
        Label countryLabel = new Label("Player Country: " + player.getCountry());
        Label emailLabel = new Label("Player Email: " + player.getEmail());
        Label teamLabel = new Label("Team Name: " + player.getTeamName());


        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> infoStage.close());

        infoLayout.getChildren().addAll(titleLabel, idLabel, nameLabel, lastnameLabel, teamLabel, nicknameLabel, addressLabel,
                                        postalCodeLabel, cityLabel, countryLabel, emailLabel, closeButton);

        Scene infoScene = new Scene(infoLayout, 300, 350);
        //infoScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm()); // Add your stylesheet if needed

        infoStage.setScene(infoScene);
        infoStage.showAndWait();
    }

}