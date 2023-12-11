package com.example.teamdelta100.entities;

import javax.persistence.*;

@Entity
@Table(name = "Teams")
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int id;

    @Column(name = "team_name", length = 30)
    private String name;

    @Column(name = "Number_of_player")
    private int numberOfPlayer;

    @Column(name = "Player_id")
    private int playerId;

    public Teams() {
    }

    public Teams(int id, String name, int numberOfPlayer, int playerId) {
        this.id = id;
        this.name = name;
        this.numberOfPlayer = numberOfPlayer;
        this.playerId = playerId;
    }

    public Teams(String name, int numberOfPlayer, int playerId) {
        this.name = name;
        this.numberOfPlayer = numberOfPlayer;
        this.playerId = playerId;
    }

    public Teams(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
