package rs.bike.dzeparac.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Gift extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Child child;

    private String source;

    private Integer amount;

    private String note;

    private LocalDate date;

    public Gift() {}

    public Gift(Child child, String source, Integer amount, String note, LocalDate date) {
        this.child = child;
        this.source = source;
        this.amount = amount;
        this.note = note;
        this.date = date;
    }

    public Long getId() { return id; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public String getSource() { return source; }

    public void setSource(String source) { this.source = source; }

    public Integer getAmount() { return amount; }

    public void setAmount(Integer amount) { this.amount = amount; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "Gift{id=" + id + ", child=" + child + ", source='" + source + "', amount=" + amount + ", note='" + note + "', date=" + date + "}";
    }
}