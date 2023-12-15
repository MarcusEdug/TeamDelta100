package com.example.teamdelta100.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Teams")
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int id;

    @Column(name = "team_name", length = 30)
    private String name;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "teams" )
    private List<Player> numberOfPlayerList = new ArrayList<>();



    @ManyToOne
    @JoinColumn (name = "game_id")
    private Games games;

    @Column(name = "Number_of_player")
    private int numberOfPlayer;

    @Column(name = "Player_id")
    private int playerId;






    public Teams() {
    }

    public Teams(int id, String name, int numberOfPlayer) {
        this.id = id;
        this.name = name;
        this.numberOfPlayer = numberOfPlayer;

    }

    public Teams(String name, int numberOfPlayer) {
        this.name = name;
        this.numberOfPlayer = numberOfPlayer;
    }

    public void addPlayer (Player player){
        player.setTeams(this);
        numberOfPlayerList.add(player);
    }



    public void countPlayer(){
        setNumberOfPlayer(getNumberOfPlayerList().size());
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ID: "+ id;

    }

    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
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

    public List<Player> getNumberOfPlayerList() {
        return numberOfPlayerList;
    }

    public void setNumberOfPlayerList(List<Player> numberOfPlayerList) {
        this.numberOfPlayerList = numberOfPlayerList;
    }


}