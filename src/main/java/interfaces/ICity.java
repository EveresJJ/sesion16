package interfaces;

import models.City;

import java.util.List;

public interface ICity {
    public void save(City city);
    public City getCity(Long id);
    public void delete(City city);
    public void update(City city);
    public List<City> getCities();
}