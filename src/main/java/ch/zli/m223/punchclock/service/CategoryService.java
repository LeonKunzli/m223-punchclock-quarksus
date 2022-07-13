package ch.zli.m223.punchclock.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Category;

@ApplicationScoped
public class CategoryService {
    @Inject
    private EntityManager entityManager;

    public CategoryService() {
    }

    @Transactional 
    public Category createCategory(Category category) {
        entityManager.persist(category);
        return category;
    }

    @Transactional 
    public Category removeCategory(Long id) {      
        Category category = entityManager.find(Category.class, id);
        try{
        entityManager.remove(category);
        }
        catch(IllegalArgumentException e){
            System.out.println("Object to be deleted does not exist.");
        }
        return category;
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAll() {
        var query = entityManager.createQuery("FROM Category");
        return query.getResultList();
    }

    @Transactional 
    public void updateCategory(Category category){
        entityManager.merge(category);
    }
}
