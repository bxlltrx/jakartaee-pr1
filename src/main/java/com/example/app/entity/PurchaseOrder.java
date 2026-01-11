package com.example.app.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_order", schema="public")
@NamedQuery(name = "PurchaseOrder.findAll", query = "SELECT o FROM PurchaseOrder o ORDER BY o.id")
public class PurchaseOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @Column(name="total_amount", nullable=false, precision=12, scale=2)
    private BigDecimal totalAmount;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public PurchaseOrder() {}

    public PurchaseOrder(LocalDateTime createdAt, BigDecimal totalAmount) {
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
    }

    public Long getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
