package com.example.teamdelta100;

import com.example.teamdelta100.controller.GameController;
import com.example.teamdelta100.view.GameMenu;
import com.example.teamdelta100.view.GameMenu;

public class HelloApplication  {


    public static void main(String[] args) {
        GameController gameController = new GameController();
        GameMenu menu = new GameMenu (gameController);
        menu.showMenu();
    }

}