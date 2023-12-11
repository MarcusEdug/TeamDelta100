package com.example.teamdelta100.controller;

import com.example.teamdelta100.entities.Player;
import com.example.teamdelta100.entities.Teams;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerController {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    // CREATE
    public boolean save(Object customer) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
    // READ
    public List<Player> getAll(boolean printOut) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            List<Player> listToReturn = new ArrayList<>(entityManager.createQuery("FROM Player", Player.class).getResultList());
            if (printOut) {
                for (Player player : listToReturn) {
                    System.out.println(player.getId() + ". " + player.getPlayerName() + " " + player.getPlayerLastname());
                }
            }
            return listToReturn;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    // Overloaded method with a default value for printOut
    public List<Player> getAll() {
        return getAll(true); // Set default value to true (print results)
    }

    public void printAll() {
        List<Player> players = getAll();
        if (players != null) {
            for (Player player : players) {
                System.out.println(player.getId() + ". " + player.getPlayerName() + " " + player.getPlayerLastname());
            }
        }
    }
    public List<Player> tableUpdate(boolean printOut){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Player> playerListToReturn = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Player> resultList = entityManager.createQuery("FROM Player ", Player.class);
            playerListToReturn.addAll(resultList.getResultList());
            transaction.commit();
            return playerListToReturn;
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
