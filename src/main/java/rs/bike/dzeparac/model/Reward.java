package rs.bike.dzeparac.model;

import jakarta.persistence.*;

@Entity
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer requiredPoints;

    private Boolean isRepeatable;

    public Reward() {}

    public Reward(String name, String description, Integer requiredPoints, Boolean isRepeatable) {
        this.name = name;
        this.description = description;
        this.requiredPoints = requiredPoints;
        this.isRepeatable = isRepeatable;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getRequiredPoints() { return requiredPoints; }

    public void setRequiredPoints(Integer requiredPoints) { this.requiredPoints = requiredPoints; }

    public Boolean getIsRepeatable() { return isRepeatable; }

    public void setIsRepeatable(Boolean isRepeatable) { this.isRepeatable = isRepeatable; }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", requiredPoints=" + requiredPoints +
                ", isRepeatable=" + isRepeatable +
                '}';
    }
}