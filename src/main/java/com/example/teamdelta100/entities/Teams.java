package com.example.teamdelta100.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/*
    Den här klassen skapar Teams objekt
 */
@Entity
@Table(name = "Teams")
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int id;

    @Column(name = "team_name", length = 30)
    private String name;

    //Här koppling mellan teams och player. Detta gör så att ett teams objekt kan ha flera player objekt i sig
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "teams" )
    private List<Player> numberOfPlayerList = new ArrayList<>();

    //Här är koppling mellan teams och match. Detta gör så att en match objekt kan ha flera teams objekt i sig
    @ManyToOne
    @JoinColumn (name = "match_id")
    private Match match;

    //Här är koppling mellan teams och match. Detta gör så att en games objekt kan ha flera teams objekt i sig
    @ManyToOne
    @JoinColumn (name = "game_id")
    private Games games;

    //värden som är till TableView
    private String gameName;


    //Konstruktorer


    //en tom konstruktor
    public Teams() {
    }

    //en konstruktor med allt i sig
    public Teams(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //en konstruktor med allt förutom Id som Primary key
    public Teams(String name) {
        this.name = name;
    }

    //Metod för att lägga till spelar i listan av spelare
    public void addPlayer (Player player){
        player.setTeams(this);
        numberOfPlayerList.add(player);
    }

    @Override
    public String toString() {
        return name;
    }

    //Getter and setter
    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Match getMatch() {
        return match;
    }
    public void setMatch(Match match) {
        this.match = match;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<Player> getNumberOfPlayerList() {
        return numberOfPlayerList;
    }

    public void setNumberOfPlayerList(List<Player> numberOfPlayerList) {
        this.numberOfPlayerList = numberOfPlayerList;
    }
}