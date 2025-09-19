package rs.bike.dzeparac.model;

import jakarta.persistence.*;

@Entity
public class WeeklyScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Activity activity;

    @ManyToOne
    private Child child;

    private int weekIndex; // 0–51
    private int score;
    private int schoolYear;

    private boolean locked;

    public WeeklyScore() {}

    public WeeklyScore(Activity activity, Child child, int weekIndex, int score, int schoolYear, boolean locked) {
        this.activity = activity;
        this.child = child;
        this.weekIndex = weekIndex;
        this.score = score;
        this.schoolYear = schoolYear;
        this.locked = locked;
    }

    // Getteri i setteri

    public Long getId() { return id; }

    public Activity getActivity() { return activity; }

    public void setActivity(Activity activity) { this.activity = activity; }

    public Child getChild() { return child; }

    public void setChild(Child child) { this.child = child; }

    public int getWeekIndex() { return weekIndex; }

    public void setWeekIndex(int weekIndex) { this.weekIndex = weekIndex; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public int getSchoolYear() { return schoolYear; }

    public void setSchoolYear(int schoolYear) { this.schoolYear = schoolYear; }

    public boolean isLocked() { return locked; }

    public void setLocked(boolean locked) { this.locked = locked; }
}
