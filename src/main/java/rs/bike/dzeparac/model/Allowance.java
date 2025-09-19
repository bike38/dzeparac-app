package rs.bike.dzeparac.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Allowance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Child child;

    private int amount;

    private LocalDate date;

    public Allowance() {}

    public Allowance(Child child, int amount, LocalDate date) {
        this.child = child;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() { return id; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    private int weekIndex;

    public int getWeekIndex() {
        return weekIndex;
    }

    public void setWeekIndex(int weekIndex) {
        this.weekIndex = weekIndex;
    }

}