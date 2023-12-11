package com.example.teamdelta100.controller;
import com.example.teamdelta100.entities.Games;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private String hej;
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    // Create
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
    public List<Games> getAll(boolean printOut) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            List<Games> listToReturn = new ArrayList<>(entityManager.createQuery("FROM Games", Games.class).getResultList());
            //TypedQuery<Games> resultlist = entityManager.createNamedQuery("FROM Games", Games.class);
            transaction.commit();
            if (printOut) {
                for (Games games :
                        listToReturn) {
                    System.out.println(games.getGameId() + ". " + games.getGameName());
                   /* for (Player player :
                            player.getPlayerName()) {
                        System.out.println(games.getGameName());
                    }*/
                }
            }
            return listToReturn;
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

    public Games getGameById (int gameId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Games gamesToReturn = entityManager.find(Games.class, gameId);
            transaction.commit();
            return gamesToReturn;
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
    public boolean updateGames(Games games) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(games);
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

    //Delete
    public boolean deleteGames(Games games) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //Om entitet finns med så ta bort spel, annars slå ihop och sen ta bort
            entityManager.remove(entityManager.contains(games) ? games:entityManager.merge(games));
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

}
