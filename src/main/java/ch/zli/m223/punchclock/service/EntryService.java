package ch.zli.m223.punchclock.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.User;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;

    public EntryService() {
    }

    @Transactional 
    public Entry createEntry(Entry entry) {
        entityManager.persist(entry);
        return entry;
    }

    @Transactional 
    public Entry removeEntry(Long id) {      
        Entry entry = entityManager.find(Entry.class, id);
        try{
        entityManager.remove(entry);
        }
        catch(IllegalArgumentException e){
            System.out.println("Object to be deleted does not exist.");
        }
        return entry;
    }

    @SuppressWarnings("unchecked")
    public List<Entry> findAll(User user) {
        var query = entityManager.createQuery("FROM Entry");
        List<Entry> entries = query.getResultList();
        List<Entry> toRemove = new ArrayList<Entry>(); //Using this method to avoid ConcurrentActionException
        if(user.getRoles().get(0).getRole().equals("user")){
            for (Entry entry : entries) {
                if(entry.getUser()!=user){
                    toRemove.add(entry);
                }
            }
            entries.removeAll(toRemove);
        }
        return entries;
    }

    @Transactional 
    public void updateEntry(Entry entry){
        entityManager.merge(entry);
    }
}
