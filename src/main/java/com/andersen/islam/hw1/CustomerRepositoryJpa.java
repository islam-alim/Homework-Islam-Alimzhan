package com.andersen.islam.hw1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CustomerRepositoryJpa {

    private EntityManager em;
    private EntityTransaction transaction;

    public CustomerRepositoryJpa() {
        em = HibernateUtil.getEntityManager();
        transaction = em.getTransaction();
    }

    protected void addCustomer(String name) {
        try {
            transaction.begin();

            Customer customer = new Customer();
            customer.setCustomer_name(name);

            em.persist(customer);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error adding customer: " + e.getMessage());
        }
    }

    protected String getNameById(int id) {
        try {
            Customer customer = em.find(Customer.class, id);
            return customer.getCustomer_name();
        } catch (Exception e) {
            System.err.println("Error finding customer's name: " + e.getMessage());
        }
        return null;
    }


}
