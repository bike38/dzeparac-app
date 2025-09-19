package rs.bike.dzeparac.model;

import jakarta.persistence.*;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int maxScore;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    public Activity() {}

    public Activity(String name, String description, int maxScore, ActivityType type) {
        this.name = name;
        this.description = description;
        this.maxScore = maxScore;
        this.type = type;
    }

    // Getteri i setteri

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getMaxScore() { return maxScore; }

    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    public ActivityType getType() { return type; }

    public void setType(ActivityType type) { this.type = type; }

    public int getMaxPoints() {
        return this.maxScore;
    }

}
