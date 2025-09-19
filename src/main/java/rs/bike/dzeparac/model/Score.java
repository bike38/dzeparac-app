package rs.bike.dzeparac.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Child child;

    @ManyToOne
    private Activity activity;

    private LocalDate date;

    private Integer value;

    @ManyToOne
    private Penalty penalty;

    @ManyToOne
    private Reward reward;

    @ManyToOne
    private Gift gift;

    // Getteri i setteri

    public Long getId() { return id; }

    public Child getChild() { return child; }
    public void setChild(Child child) { this.child = child; }

    public Activity getActivity() { return activity; }
    public void setActivity(Activity activity) { this.activity = activity; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public Penalty getPenalty() { return penalty; }
    public void setPenalty(Penalty penalty) { this.penalty = penalty; }

    public Reward getReward() { return reward; }
    public void setReward(Reward reward) { this.reward = reward; }

    public Gift getGift() { return gift; }
    public void setGift(Gift gift) { this.gift = gift; }
}