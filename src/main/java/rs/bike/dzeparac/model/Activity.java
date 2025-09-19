package rs.bike.dzeparac.model;

import jakarta.persistence.*;

@Entity
public class Activity extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private Integer maxPoints; // npr. 40 za školu, 10 za zube

    @Enumerated(EnumType.STRING)
    private ActivityType type; // OSNOVNA, DODATNA, TIMSKA

    private Boolean activeThisWeek;

    public Activity() {}

    public Activity(String name, String description, Integer maxPoints, ActivityType type, Boolean activeThisWeek) {
        this.name = name;
        this.description = description;
        this.maxPoints = maxPoints;
        this.type = type;
        this.activeThisWeek = activeThisWeek;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getMaxPoints() { return maxPoints; }

    public void setMaxPoints(Integer maxPoints) { this.maxPoints = maxPoints; }

    public ActivityType getType() { return type; }

    public void setType(ActivityType type) { this.type = type; }

    public Boolean getActiveThisWeek() { return activeThisWeek; }

    public void setActiveThisWeek(Boolean activeThisWeek) { this.activeThisWeek = activeThisWeek; }

    @Override
    public String toString() {
        return "Activity{id=" + id + ", name='" + name + "', maxPoints=" + maxPoints + ", type=" + type + ", activeThisWeek=" + activeThisWeek + "}";
    }
}