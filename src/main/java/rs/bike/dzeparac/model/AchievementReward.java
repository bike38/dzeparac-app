package rs.bike.dzeparac.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AchievementReward extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Child child;

    private String category;

    private String description;

    private Integer amount;

    private LocalDate date;

    public AchievementReward() {}

    public AchievementReward(Child child, String category, String description, Integer amount, LocalDate date) {
        this.child = child;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() { return id; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getAmount() { return amount; }

    public void setAmount(Integer amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "AchievementReward{id=" + id + ", child=" + child + ", category='" + category + "', amount=" + amount + ", date=" + date + "}";
    }
}