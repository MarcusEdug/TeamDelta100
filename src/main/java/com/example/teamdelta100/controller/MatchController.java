package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Match;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

}