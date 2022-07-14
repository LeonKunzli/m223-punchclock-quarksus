package ch.zli.m223.punchclock.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Role;

@ApplicationScoped
public class RoleService {
    @Inject
    private EntityManager entityManager;

    public RoleService() {
    }

    @Transactional 
    public Role createRole(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Transactional 
    public Role removeRole(Long id) {      
        Role role = entityManager.find(Role.class, id);
        try{
        entityManager.remove(role);
        }
        catch(IllegalArgumentException e){
            System.out.println("Object to be deleted does not exist.");
        }
        return role;
    }

    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        var query = entityManager.createQuery("FROM Role");
        return query.getResultList();
    }

    @Transactional 
    public void updateRole(Role role){
        entityManager.merge(role);
    }

}
