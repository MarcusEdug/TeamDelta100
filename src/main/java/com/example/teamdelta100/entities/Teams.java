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

    @Column(name = "Number_of_player")
    private int numberOfPlayer;

    /*@Column(name = "Player_id")
    private int playerId;

     */

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "teams")
    private List<TestPlay> numberOfPlayerList = new ArrayList<>();


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

    public void addPlayer (TestPlay testPlay){
        testPlay.setTeams(this);
        numberOfPlayerList.add(testPlay);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", ID: "+ id;

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

    public List<TestPlay> getNumberOfPlayerList() {
        return numberOfPlayerList;
    }

    public void setNumberOfPlayerList(List<TestPlay> numberOfPlayerList) {
        this.numberOfPlayerList = numberOfPlayerList;
    }
}
