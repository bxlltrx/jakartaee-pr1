package com.example.app.ejb;

import com.example.app.entity.PurchaseOrder;
import com.example.app.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class OrderService {

    @PersistenceContext(unitName = "AppPU")
    private EntityManager em;

    public PurchaseOrder create(Long userId, BigDecimal totalAmount) {
        User u = em.find(User.class, userId);
        if (u == null) return null;

        PurchaseOrder o = new PurchaseOrder(LocalDateTime.now(), totalAmount);
        o.setUser(u);
        em.persist(o);
        return o;
    }

    public PurchaseOrder find(Long id) {
        return em.find(PurchaseOrder.class, id);
    }

    public List<PurchaseOrder> findAll() {
        return em.createNamedQuery("PurchaseOrder.findAll", PurchaseOrder.class)
                .getResultList();
    }

    public boolean delete(Long id) {
        PurchaseOrder o = find(id);
        if (o == null) return false;
        em.remove(o);
        return true;
    }
}
