package rs.bike.dzeparac.dto;

import rs.bike.dzeparac.model.Activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreMatrixRow {

    private Activity activity;
    private List<Integer> weeklyScores;

    public ScoreMatrixRow(Activity activity) {
        this.activity = activity;
        this.weeklyScores = new ArrayList<>(Collections.nCopies(52, 0));
    }

    public Activity getActivity() { return activity; }

    public void setActivity(Activity activity) { this.activity = activity; }

    public List<Integer> getWeeklyScores() { return weeklyScores; }

    public void setWeeklyScores(List<Integer> weeklyScores) { this.weeklyScores = weeklyScores; }

    public void setScoreForWeek(int weekIndex, int score) {
        if (weekIndex >= 0 && weekIndex < 52) {
            weeklyScores.set(weekIndex, score);
        }
    }
}