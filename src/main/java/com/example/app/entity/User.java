package com.example.app.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="app_user", schema="public")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u ORDER BY u.id")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="full_name", nullable=false, length=100)
    private String fullName;

    @Column(nullable=false, unique=true, length=120)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrder> orders = new ArrayList<>();

    public User() {}

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<PurchaseOrder> getOrders() { return orders; }

    public void addOrder(PurchaseOrder order) {
        orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(PurchaseOrder order) {
        orders.remove(order);
        order.setUser(null);
    }
}
