package com.example.teamdelta100.entities;

import javax.persistence.*;

@Entity
@Table (name = "PLAYER")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerName;

    private String playerLastname;

    public Player (){

    }
    public Player(Long id, String playerName, String playerLastname) {
        this.id = id;
        this.playerName = playerName;
        this.playerLastname = playerLastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
