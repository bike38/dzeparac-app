package rs.bike.dzeparac.dto;

import rs.bike.dzeparac.model.Activity;

import java.util.List;

public class ScoreRow {

    private Activity activity;
    private List<Integer> weeklyScores;

    public ScoreRow() {}

    public ScoreRow(Activity activity, List<Integer> weeklyScores) {
        this.activity = activity;
        this.weeklyScores = weeklyScores;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<Integer> getWeeklyScores() {
        return weeklyScores;
    }

    public void setWeeklyScores(List<Integer> weeklyScores) {
        this.weeklyScores = weeklyScores;
    }
}