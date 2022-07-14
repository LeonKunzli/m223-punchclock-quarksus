package ch.zli.m223.punchclock.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.User;

@ApplicationScoped
public class UserService {
    @Inject
    private EntityManager entityManager;

    public UserService() {
    }

    @Transactional 
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional 
    public User removeUser(Long id) {      
        User user = entityManager.find(User.class, id);
        try{
        entityManager.remove(user);
        }
        catch(IllegalArgumentException e){
            System.out.println("Object to be deleted does not exist.");
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        var query = entityManager.createQuery("FROM User");
        return query.getResultList();
    }

    @Transactional 
    public void updateUser(User user){
        entityManager.merge(user);
    }

    public User getUserByName(String username){
        var query = entityManager.createQuery("FROM User WHERE username = :name", User.class);        
        query.setParameter("name", username);
        return query.getSingleResult();
    }
}
