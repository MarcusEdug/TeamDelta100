package com.example.teamdelta100.entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//CRUD-operationer
@Entity
@Table (name = "games")
public class Games {
    //Primary key
    @Id
    //Id genereras av databasen
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "games_id")
    private int gamesId;
    @Column (length = 50)
    private String gameName;

    @Column (length = 50)
    private String gameGenre;

    @Column (name = "player_id")
    private int playerId;

    //OneToMany - Ett spel till många spelare/lag
    //Fetch - fråga alltid efter entiteter och children.
    //Cascade - sparar entitet och children på en gång
    //Mapped - Förhindrar den här sidan att skapa en extra tabell för relational mapping
    //Mapped - säger till hibernate att den motsatta sidan har kontroll, inget behov av att skapa tabeller (behövs den här?)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "player" + "teams")
    private List<Games> gamesToChose = new ArrayList<>();

    @JoinColumn (name = "player_id")
    @JoinColumn (name = "team_id")
    private Player player;
    private Teams teams;

    public Games() {
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Teams getTeams() {
        return teams;
    }

    public void setTeams(Teams teams) {
        this.teams = teams;
    }

    public void setGamesId(int id) {
        this.gamesId = id;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public int getGamesId() {
        return gamesId;
    }
}
