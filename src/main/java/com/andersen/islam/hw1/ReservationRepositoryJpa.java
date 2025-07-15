package com.andersen.islam.hw1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Scanner;

public class ReservationRepositoryJpa {

    private EntityManager em;
    private EntityTransaction transaction;

    Scanner scanner = new Scanner(System.in);

    public ReservationRepositoryJpa() {
        em = HibernateUtil.getEntityManager();
        transaction = em.getTransaction();
    }

    protected void addReservation(Reservation reservation) {
        try {
            transaction.begin();

            em.persist(reservation);

            transaction.commit();
            System.out.println("Reservation added successfully.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error adding reservation: " + e.getMessage());
        }
    }

    protected void cancelReservation(String name) {
        try {
            viewReservationsByName(name);
            System.out.println("Enter reservation ID to cancel: ");
            int id = scanner.nextInt();
            Reservation reservation = em.find(Reservation.class, id);

            em.remove(reservation);

        } catch (Exception e) {
            System.err.println("Error cancelling reservation: " + e.getMessage());
        }
    }

    protected void viewReservationsByName(String name) {
        try {
            List<Reservation> reservations = em.createQuery(
                            "SELECT r FROM Reservation r WHERE r.name = :name", Reservation.class)
                    .setParameter("name", name)
                    .getResultList();

            if (reservations.isEmpty()) {
                System.out.println("No reservations found for this name");
            } else {
                for (Reservation r : reservations) {
                    System.out.println("ID: " + r.getId() + ", Start time: " + r.getStartTime()
                            + ", End time: " + r.getEndTime() + ", Workspace ID: " + r.getWorkspaceId());
                }
            }
        } catch (Exception e) {
            System.err.println("Error showing reservations: " + e.getMessage());
        }
    }

    protected void showAllReservations() {
        try {
            List<Reservation> reservations = em.createQuery(
                            "SELECT r FROM Reservation r", Reservation.class)
                    .getResultList();
            if (reservations.isEmpty()) {
                System.out.println("There are no reservations");
            } else {
                for (Reservation r : reservations) {
                    System.out.println("ID: " + r.getId() + ", Start time: " + r.getStartTime()
                            + ", End time: " + r.getEndTime() + ", Workspace ID: " + r.getWorkspaceId());
                }
            }
        } catch (Exception e) {
            System.err.println("Error showing reservations: " + e.getMessage());
        }
    }
}
