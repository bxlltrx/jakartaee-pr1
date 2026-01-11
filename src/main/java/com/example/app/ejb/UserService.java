package com.example.app.ejb;

import com.example.app.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "AppPU")
    private EntityManager em;

    public User create(String fullName, String email) {
        User u = new User(fullName, email);
        em.persist(u);
        return u;
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }

    public User update(Long id, String fullName, String email) {
        User u = find(id);
        if (u == null) return null;
        u.setFullName(fullName);
        u.setEmail(email);
        return u; // managed entity
    }

    public boolean delete(Long id) {
        User u = find(id);
        if (u == null) return false;
        em.remove(u);
        return true;
    }
}
