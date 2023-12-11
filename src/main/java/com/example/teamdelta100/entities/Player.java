package com.example.teamdelta100.entities;

import javax.persistence.*;

@Entity
@Table (name = "PLAYER")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String playerName;

    private String playerLastname;

    public Player (){

    }
    public Player(int id, String playerName, String playerLastname) {
        this.id = id;
        this.playerName = playerName;
        this.playerLastname = playerLastname;
    }

    public Player(String playerName) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerLastname() {
        return playerLastname;
    }

    public void setPlayerLastname(String playerLastname) {
        this.playerLastname = playerLastname;
    }
}
