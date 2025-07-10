package com.andersen.islam.hw1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.util.Optional;

public class WorkspaceRepositoryJpa {

    private EntityManager em;

    public WorkspaceRepositoryJpa() {
        em = HibernateUtil.getEntityManager();
        fillWorkspace();
    }

    private void fillWorkspace() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        if (em.find(Workspace.class, 1) == null) {
            em.persist(new Workspace(1, "Private Room", BigDecimal.valueOf(100), true));
        }
        transaction.commit();
    }

    public Optional<String> getTypeById(int workspaceId) {
        Workspace foundWorkspace = em.find(Workspace.class, workspaceId);

        if (foundWorkspace != null) {
            return Optional.of(foundWorkspace.getType());
        } else {
            return Optional.empty();
        }
    }

    public void addWorkspace(String type, BigDecimal price, boolean available) {
        EntityTransaction transaction = em.getTransaction();
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


}
