package com.andersen.islam.hw1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;



public class WorkspaceRepositoryJpa {

    private final EntityManager em;
    private final EntityTransaction transaction;

    public WorkspaceRepositoryJpa() {
        em = HibernateUtil.getEntityManager();
        transaction = em.getTransaction();
    }

    public Optional<String> getTypeById(int workspaceId) {
        Workspace foundWorkspace = em.find(Workspace.class, workspaceId);

        if (foundWorkspace != null) {
            return Optional.of(foundWorkspace.getType());
        } else {
            return Optional.empty();
        }
    }

    protected void addWorkspace(String type, BigDecimal price, boolean available) {
        try {
            transaction.begin();

            Workspace workspace = new Workspace();
            workspace.setType(type);
            workspace.setPrice(price);
            workspace.setAvailable(available);

            em.persist(workspace);

            transaction.commit();
            System.out.println("Workspace added successfully.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error adding workspace: " + e.getMessage());
        }
    }

    protected void deleteWorkspaceById(int id) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Workspace workspace = em.find(Workspace.class, id);

            if (workspace == null) {
                System.out.println("Workspace with ID " + id + " not found.");
                transaction.rollback();
                return;
            }

            em.remove(workspace);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error deleting workspace: " + e.getMessage());
        }
    }

    protected void showAvailableWorkspaces() {

        try {
            List<Workspace> workspaces = em.createQuery(
                    "SELECT w FROM Workspace w WHERE w.available = true", Workspace.class)
                    .getResultList();

            if (workspaces.isEmpty()) {
                System.out.println("No available workspaces");
            } else {
                for (Workspace w : workspaces) {
                    System.out.println("ID: " + w.getId() + ", Type: " + w.getType()
                            + ", Price: " + w.getPrice());
                }
            }
        } catch (Exception e) {
            System.err.println("Error showing workspaces: " + e.getMessage());
        }
    }

    protected Workspace getWorkspaceById(int id) {
        try {
            Workspace workspace = em.find(Workspace.class, id);

            if (workspace == null) {
                System.out.println("There isn't a workspace with this ID");
            } else {
                return workspace;
            }
        } catch (Exception e) {
            System.out.println("Error getting workspace by ID: " + e.getMessage());
        }
        return null;
    }

    protected List<Workspace> getAllWorkspaces() {
        try {
            List<Workspace> workspaces = em.createQuery(
                    "SELECT w FROM Workspace w", Workspace.class)
                    .getResultList();
            if (workspaces.isEmpty()) {
                System.out.println("There are no workspaces");
            } else {
                return workspaces;
            }
        } catch (Exception e) {
            System.out.println("Error getting all workspaces in the list: " + e.getMessage());
        }
        return null;
    }

    protected void setAvailability(int id, boolean available) {
        try {
            transaction.begin();

            Workspace workspace = em.find(Workspace.class, id);
            if (workspace == null) {
                System.out.println("There isn't a workspace with this ID");
            } else {
                workspace.setAvailable(available);
                em.merge(workspace);
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error setting availability: " + e.getMessage());
        }
    }



}
