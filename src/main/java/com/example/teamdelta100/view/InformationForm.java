package com.example.teamdelta100.view;

import com.example.teamdelta100.entities.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class InformationForm extends Application {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Player.class);
            configuration.configure();

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Information Form");

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

        Button submitButton = new Button("Submit");
        GridPane.setColumnSpan(submitButton, 2);
        grid.add(submitButton, 0, 8);

        submitButton.setOnAction(e -> saveToDatabase(
                firstNameField.getText(),
                lastNameField.getText(),
                nicknameField.getText(),
                addressField.getText(),
                postalCodeField.getText(),
                cityField.getText(),
                countryField.getText(),
                emailField.getText()
        ));

        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void saveToDatabase(String firstName, String lastName, String nickname,
                                String address, String postalCode, String city,
                                String country, String email) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Player person = new Player();
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setNickname(nickname);
            person.setAddress(address);
            person.setPostalCode(postalCode);
            person.setCity(city);
            person.setCountry(country);
            person.setEmail(email);

            session.save(person);

            session.getTransaction().commit();
            System.out.println("Data saved to the database successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to save data to the database.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
