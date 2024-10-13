package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerAdmin {
    private static final String PERSISTENCE = "sesion16";
    private static EntityManager instance;

    public EntityManagerAdmin() {

    }
    public static EntityManager getInstance() {
        if (instance == null||!instance.isOpen()) {
            EntityManagerFactory ef = Persistence.createEntityManagerFactory(PERSISTENCE);
            instance = ef.createEntityManager();
        }
        return instance;
    }
}

