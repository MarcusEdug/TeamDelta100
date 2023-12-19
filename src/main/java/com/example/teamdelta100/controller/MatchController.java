package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Match;
import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Hantera kommunikationen med databasen för Match tabellen.
// Evelina Daun

public class MatchController {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");


    // Tom konstruktor
    public MatchController(){}


    // Metod: Lägga till Match objekt i databasen
    public void addMatch(Match m){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(m); // Lägga in nytt objekt
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

    // Metod: Uppdatera ett inskickat Match objekt
    public void updateMatchObject(Match m){ // Skicka in vald match att ändra
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(m); // Uppdatera objekt
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
    }


    // Metod: Ta bord inskickat Match objekt
    public void deleteMatchObject(Match m){ // Skicka in vald match att ta bort
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = em.getTransaction();
            transaction.begin();

            Match tempMatch = em.find(Match.class, m.getMatchId());
            if(tempMatch.getPlayerOrTeam().equals("player")){
                List<Player> players = tempMatch.getPlayerList();
                for(Player p : players){
                    p.setMatch(null);
                }
                tempMatch.getPlayerList().clear();
            }else{
                List<Teams> teams = tempMatch.getTeamsList();
                for(Teams t : teams){
                    t.setMatch(null);
                }
                tempMatch.getTeamsList().clear();
            }

            em.remove(tempMatch);
            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
    }


    // Metod: Hämta alla Match objekt från databasen till en lista och skicka tillbaka
    public List<Match> getAllMatchObjects(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Match> returnList = new ArrayList<>(); // Listan som objekten läggs i

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Match> resultList = entityManager.createQuery("FROM Match ", Match.class);
            returnList.addAll(resultList.getResultList());
            transaction.commit();
            return returnList;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    // Metod: Hämta Player lista för ett specifikt Match objekt - Används på grund av LAZY
    public List<Player> getAllPlayers(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Match tempMatch = entityManager.find(Match.class, match.getMatchId()); // Söka efter Match med id
            List<Player> returnList = new ArrayList<>(tempMatch.getPlayerList()); // Hämta listan med Players
            transaction.commit();
            return returnList;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }


    // Metod: Hämta Team lista för ett specifikt Match objekt - Används på grund av LAZY
    public List<Teams> getAllTeams(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Match tempMatch = entityManager.find(Match.class, match.getMatchId()); // Söka efter Match med id
            List<Teams> returnList = new ArrayList<>(tempMatch.getTeamsList()); // Hämta listan med Players
            transaction.commit();
            return returnList;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }


    // Metod: Lägga till Player till Match objekt
    public void addPlayerToMatch(int matchId, int playerId){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Player> player = Optional.ofNullable(entityManager.find(Player.class, playerId));
            Optional<Match> match = Optional.ofNullable(entityManager.find(Match.class, matchId));

            // If isPresent
            Player tempPlayer = player.get();
            Match tempMatch = match.get();
            tempMatch.addPlayer(tempPlayer);

            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }


    // Metod: Lägga till Team till Match objekt
    public void addTeamToMatch(int matchId, int teamsId){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Teams> team = Optional.ofNullable(entityManager.find(Teams.class, teamsId));
            Optional<Match> match = Optional.ofNullable(entityManager.find(Match.class, matchId));

            Teams tempTeam = team.get();
            Match tempMatch = match.get();
            tempMatch.addTeams(tempTeam);

            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }


    // Metod: Ta bort Player från Match objekt
    public void removeAllPlayers(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Match tempMatch = entityManager.find(Match.class, match.getMatchId());
            List<Player> players = tempMatch.getPlayerList();

            for(Player p : players){
                p.setMatch(null);
                entityManager.merge(p);
            }

            tempMatch.getPlayerList().clear();
            entityManager.merge(tempMatch);

            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }


    // Metod: Ta bort Team från Match objekt
    public void removeAllTeams(Match match){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Match tempMatch = entityManager.find(Match.class, match.getMatchId());
            List<Teams> teams = tempMatch.getTeamsList();

            for(Teams t : teams){
                t.setMatch(null);
                entityManager.merge(t);
            }

            tempMatch.getPlayerList().clear();
            entityManager.merge(tempMatch);

            transaction.commit();
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

}