package com.example.teamdelta100.view;

import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.entity.Games;

import java.util.Scanner;

public class Menu {
    private GameController gameController;

    public Menu(GameController gameController) {
        this.gameController = gameController;
    }

    public void showMenu () {
        System.out.println("-----------------Meny------------------");
        System.out.println("1. Lägg ett spel till turneringen");
        System.out.println("2. Uppdatera ett spel i turneringen");
        System.out.println("3. Ta bort ett spel från turneringen");
        System.out.println("4. Lista alla spel i turneringen");
        handleUserInput();
    }
    public void space () {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public void handleUserInput () {
        System.out.println("Gör ett val: ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        space();
        switch (userInput) {
            case "1":
                System.out.println("Ange ett spel: ");
                String addGame = new Scanner(System.in).nextLine();
                if(gameController.save(new Games(addGame))) {
                    System.out.println(addGame + " tillagt till turneringen.");
                } else {
                    System.out.println("Misslyckades att lägga till spel.");
                }
                break;
            case "2":
                //Uppdatera spel
                gameController.getAll(true);
                System.out.println("Välj ett spel-ID: ");
                Games gameToUpdate = gameController.getGameById(new Scanner(System.in).nextInt());
                System.out.println("Ändra namn på spel från " + gameToUpdate.getGameName() + " till?: ");
                gameToUpdate.setGameName(new Scanner(System.in).nextLine());
                if (gameController.updateGames(gameToUpdate)) {
                    System.out.println("Spel uppdaterat");
                } else {
                    System.out.println("Misslyckades");
                }
                break;
            case "3":
                //Ta bort ett spel
                gameController.getAll(true);
                System.out.println("Välj spel-ID: ");
                if (gameController.deleteGames(new Scanner(System.in).nextInt()))
                break;
            case "4":
                //Hämta alla spel
                System.out.println("Lista av samtliga spel i turneringen: ");
                for (Games games:
                     gameController.getAll(true)) {
                    System.out.println(games.getGameName());
                }
                break;
            default:
                break;
        }
        showMenu();
    }
}
