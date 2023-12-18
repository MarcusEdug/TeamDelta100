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
            em.remove(em.contains(m) ? m:em.merge(m)); // Ta bort objekt
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


    // Metod: Lägga till Player till Match
    public void addPlayerToMatch(int matchId, int playerId){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();

            Optional<Player> player = Optional.ofNullable(entityManager.find(Player.class, playerId));
            Optional<Match> match = Optional.ofNullable(entityManager.find(Match.class, matchId));

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


    // Metod: Lägga till Team till Match
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

}