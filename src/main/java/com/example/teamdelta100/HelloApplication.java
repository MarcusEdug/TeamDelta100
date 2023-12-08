package com.example.teamdelta100;

import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.view.Menu;

public class HelloApplication  {


    public static void main(String[] args) {
        GameController gameController = new GameController();
        Menu menu = new Menu (gameController);
        menu.showMenu();
    }

}