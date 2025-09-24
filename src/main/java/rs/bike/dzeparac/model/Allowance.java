package rs.bike.dzeparac.model;

import jakarta.persistence.*;

@Entity
public class Allowance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Child child;

    private int weekIndex;
    private int amount;
    private int schoolYear;

    public Allowance() {}

    public Allowance(Child child, int weekIndex, int amount, int schoolYear) {
        this.child = child;
        this.weekIndex = weekIndex;
        this.amount = amount;
        this.schoolYear = schoolYear;
    }

    public Long getId() { return id; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public int getWeekIndex() { return weekIndex; }

    public void setWeekIndex(int weekIndex) { this.weekIndex = weekIndex; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public int getSchoolYear() { return schoolYear; }

    public void setSchoolYear(int schoolYear) { this.schoolYear = schoolYear; }
}
