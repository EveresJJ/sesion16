package dao;

import interfaces.ICity;
import jakarta.persistence.EntityManager;
import models.City;

import java.util.List;

public class CityDAO implements ICity {

    @Override
    public void save(City city) {
        EntityManager em = EntityManagerAdmin.getInstance();
        em.getTransaction().begin();
        em.persist(city);
        em.getTransaction().commit();
    }

    @Override
    public City getCity(Long id) {
        EntityManager em = EntityManagerAdmin.getInstance();
        return em.find(City.class, id);
    }

    @Override
    public void delete(City city) {
        EntityManager em = EntityManagerAdmin.getInstance();
        em.getTransaction().begin();
        City cityToDelete = em.find(City.class, city.getId());
        if (cityToDelete != null) {
            em.remove(cityToDelete);
        }
    }

    @Override
    public void update(City city) {
        EntityManager em = EntityManagerAdmin.getInstance();
        em.getTransaction().begin();
        em.merge(city);
        em.getTransaction().commit();
    }

    @Override
    public List<City> getCities() {
        EntityManager em = EntityManagerAdmin.getInstance();
        return em.createQuery("select c from City c", City.class).getResultList();
    }

}