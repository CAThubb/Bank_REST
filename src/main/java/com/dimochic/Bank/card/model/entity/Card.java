package com.dimochic.Bank.card.model.entity;

import com.dimochic.Bank.user.model.entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "card_number", unique = true, nullable = false, length = 16)
    private String cardNumber;

    @Column(name = "card_secret", nullable = false, length = 3)
    private String cardSecret;

    @Column(name = "card_owner_name")
    private String cardOwnerName;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "balance", precision = 19, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    // number is encoded by unix epoch encoding, formula: value = (year - 1970) * 12 + month
    @Column(name = "expiry_date", nullable = false)
    private int expiryDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return deleted == card.deleted && expiryDate == card.expiryDate && Objects.equals(id, card.id) && Objects.equals(user, card.user) && Objects.equals(cardNumber, card.cardNumber) && Objects.equals(cardSecret, card.cardSecret) && Objects.equals(cardOwnerName, card.cardOwnerName) && status == card.status && Objects.equals(balance, card.balance) && Objects.equals(currency, card.currency) && Objects.equals(createdAt, card.createdAt) && Objects.equals(updatedAt, card.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, cardNumber, cardSecret, cardOwnerName, status, deleted, balance, currency, expiryDate, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", user=" + user +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardSecret='" + cardSecret + '\'' +
                ", cardOwnerName='" + cardOwnerName + '\'' +
                ", cardStatus=" + status +
                ", deleted=" + deleted +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", expiryDate=" + expiryDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
