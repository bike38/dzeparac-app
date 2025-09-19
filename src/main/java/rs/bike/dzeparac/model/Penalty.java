package rs.bike.dzeparac.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Penalty extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Child child;

    private String reason;

    private Integer amount;

    private LocalDate date;

    private String description;

    public Penalty() {}

    public Penalty(Child child, String reason, Integer amount, LocalDate date) {
        this.child = child;
        this.reason = reason;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() { return id; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

    public Integer getAmount() { return amount; }

    public void setAmount(Integer amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Penalty{id=" + id + ", child=" + child + ", reason='" + reason + "', amount=" + amount + ", date=" + date + "}";
    }
}