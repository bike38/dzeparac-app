package rs.bike.dzeparac.model;

import jakarta.persistence.*;

@Entity
public class Child extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Child() {}

    public Child(String name) {
        this.name = name;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Child{id=" + id + ", name='" + name + "'}";
    }
}