package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.PlayerController;
import com.example.teamdelta100.entities.Player;

public class PlayerMenu {
    public static void main(String[] args) {
        // Example: Creating a new player and saving it to the database
        Player newPlayer = new Player();
        newPlayer.setPlayerName("John");
        newPlayer.setPlayerLastname("Doe");


        PlayerController playerController = new PlayerController();
        if (playerController.save(newPlayer)) {
            System.out.println("Player data saved successfully!");
        } else {
            System.out.println("Failed to save player data.");
        }

        // Example: Retrieving and printing all players
        playerController.printAll();
    }
}
