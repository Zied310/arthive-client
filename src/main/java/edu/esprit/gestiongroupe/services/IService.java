package edu.esprit.gestiongroupe.services;

import edu.esprit.gestiongroupe.entities.User;

import java.util.Set;

public interface IService <T>{
    void add(User user);

    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(int id);
    public Set<T> getAll();
    public T getOneByID(int id);

    void update(User user);

    void delete(int id);
}