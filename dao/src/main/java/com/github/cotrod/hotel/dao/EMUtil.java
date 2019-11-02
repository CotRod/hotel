package com.github.cotrod.hotel.dao;

import org.hibernate.Session;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMUtil {
    private static EntityManagerFactory emFactory = null;

    public static Session getEntityManager() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("com.github.cotrod.hotel");
        }
        return emFactory.createEntityManager().unwrap(Session.class);
    }

    public static void closeEMFactory() {
        emFactory.close();
    }
}
