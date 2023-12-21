package com.example.teamdelta100.controller;
import com.example.teamdelta100.entities.Games;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*
Hanterar kommunikationen mellan databasen och Games-klassen
MS
 */
public class GameController {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    public GameController() {
    }

    // Create
    //Metod för att lägga till Games-objekt i databasen
    public boolean save(Games games) {
        // Boiler plate
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(games); //Persist = spara
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Read
    //Metod för att hämta alla objekt från databasen med en lista
    //samt kunna skicka tillbaka
    public List<Games> getAll(boolean printOut) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Games> gamesListToReturn = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Games> resultList = entityManager.createQuery("FROM Games", Games.class); //Hämtar från databas
            gamesListToReturn.addAll(resultList.getResultList()); //Lägger till i databas
            transaction.commit();
            if (printOut) {
                for (Games games :
                        gamesListToReturn) {
                    System.out.println(games.getGameId() + ". " + games.getGameName());
                }
            }
            return gamesListToReturn;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    //Update
    //Metod för att uppdatera games-objekt
    public boolean updateGames(Games games) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(games); //Uppdaterar
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.getMessage();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Delete med ID
    //Metod:Tar bort games-objekt från databasen med hjälp av ID
    public boolean deleteGameById(int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            //Hämtar games entitet från databasen med gameId
            Games games = entityManager.find(Games.class, gameId);

            if (games != null) {
                List<Player> list = games.getPlayerList();
                for (Player player : list) {
                    player.setGames(null); //Kopplar bort värdet på player från games
                }
                games.getPlayerList().clear();

            } else {
                List<Teams> list2 = games.getTeamsList();
                for (Teams teams : list2) {
                    teams.setGames(null);
                    teams.setGameName(null);
                }
                games.getTeamsList().clear();
            }
            entityManager.remove(games);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Metod: Kopplar bort Player-objekt från games
    public boolean removePlayerFromGame(int gameId, int playerId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            //Letar objekten med tillhörande id i från databas
            Optional<Player> selectPlayer= Optional.ofNullable(entityManager.find(Player.class, playerId));
            Optional<Games> selectGames = Optional.ofNullable(entityManager.find(Games.class, gameId));

            if (selectGames != null && selectPlayer != null) {
                Player player = selectPlayer.get();
                Games games = selectGames.get();

                games.getPlayerList().remove(0);
                games.setPlayerName(null); //Kopplar bort player-objekt
                games.setPlayerId(0);
                player.setGames(null); //Kopplar bort games-objekt från player
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
    //Metod: Kopplar bort teams-objekt från games
    public boolean removeTeamFromGame (int teamId, int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            //Letar objekten med tillhörande ID i databas
            Optional<Teams> selectTeam = Optional.ofNullable(entityManager.find(Teams.class, teamId));
            Optional<Games> selectGames = Optional.ofNullable(entityManager.find(Games.class, gameId));

            if (selectGames != null && selectTeam != null) {
                Teams teams = selectTeam.get(); //Hämtar
                Games games = selectGames.get();

                games.getTeamsList().remove(0);
                games.setTeamName(null); //Kopplar bort teams från games
                games.setTeamId(0);
                teams.setGameName(null); //Kopplar bort games från teams
                teams.setGames(null);

            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Metod: Tar ut en ny "aktuell" lista från databasen
    public List<Games> tableUpdate() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Games> gamesListToReturn = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Games> resultList = entityManager.createQuery("FROM Games", Games.class);
            gamesListToReturn.addAll(resultList.getResultList());
            return gamesListToReturn;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return null;
    }

    //Metod: Kopplar ihop teams med games
    public boolean addPlayerToGame(int playerId, int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        Games games;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            //Letar i databasen efter objekt med tillhörande ID
            Optional<Player> selectPlayer = Optional.ofNullable(entityManager.find(Player.class, playerId));
            Optional<Games> selectGames = Optional.ofNullable(entityManager.find(Games.class, gameId));

            Player player = selectPlayer.get(); //Hämtar
            games = selectGames.get();
            games.setPlayerId(player.getId());
            games.setPlayerName(player.getPlayerName());
            games.addPlayer(player); //Lägger till

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }

    //Metod: Kopplar ihop teams-objekt med games
    public boolean addTeamToGame(int teamsId, int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        Games games;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            //Letar i databasen efter objekten med tillhörande ID
            Optional<Teams> selectTeam = Optional.ofNullable(entityManager.find(Teams.class, teamsId));
            Optional<Games> selectGame = Optional.ofNullable(entityManager.find(Games.class, gameId));

            Teams teams = selectTeam.get();//Hämtar
            games = selectGame.get();
            teams.setGameName(games.getGameName());
            games.setTeamId(teams.getId());
            games.setTeamName(teams.getName());
            games.addTeams(teams); //Lägger till

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
}


