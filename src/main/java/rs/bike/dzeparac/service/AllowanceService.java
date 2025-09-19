package rs.bike.dzeparac.service;

import org.springframework.stereotype.Service;
import rs.bike.dzeparac.model.*;
import rs.bike.dzeparac.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class AllowanceService {

    private final ScoreRepository scoreRepo;
    private final ActivityRepository activityRepo;
    private final PenaltyRepository penaltyRepo;
    private final AchievementRewardRepository rewardRepo;
    private final GiftRepository giftRepo;

    public AllowanceService(
            ScoreRepository scoreRepo,
            ActivityRepository activityRepo,
            PenaltyRepository penaltyRepo,
            AchievementRewardRepository rewardRepo,
            GiftRepository giftRepo
    ) {
        this.scoreRepo = scoreRepo;
        this.activityRepo = activityRepo;
        this.penaltyRepo = penaltyRepo;
        this.rewardRepo = rewardRepo;
        this.giftRepo = giftRepo;
    }

    public int calculateWeeklyAllowance(Long childId, LocalDate start, LocalDate end) {
        // 1. Ocene
        List<Score> scores = scoreRepo.findByChildIdAndDateBetween(childId, start, end);

        double baseScore = 0;
        double baseMax = 0;
        double bonusScore = 0;
        double teamScore = 0;

        for (Score score : scores) {
            Activity activity = score.getActivity();
            double normalized = (score.getValue() / 10.0) * activity.getMaxPoints();

            switch (activity.getType()) {
                case OSNOVNA -> {
                    baseScore += normalized;
                    baseMax += activity.getMaxPoints();
                }
                case DODATNA -> bonusScore += normalized;
                case TIMSKA -> teamScore += normalized;
            }
        }

        // 2. Ponderisanje
        double basePercent = baseMax == 0 ? 0 : baseScore / baseMax;
        double bonusWeighted = bonusScore * basePercent;
        double teamWeighted = teamScore * basePercent;

        double totalPoints = baseScore + bonusWeighted + teamWeighted;
        int allowance = (int) Math.round(totalPoints * 10);

        // 3. Limit: max 2× osnovni džeparac
        int baseCap = 1000;
        int maxCap = baseCap * 2;
        if (allowance > maxCap) allowance = maxCap;

        // 4. Kazne
        List<Penalty> penalties = penaltyRepo.findByChildIdAndDateBetween(childId, start, end);
        int penaltySum = penalties.stream().mapToInt(Penalty::getAmount).sum();
        allowance -= penaltySum;

        // 5. Pokloni i nagrade
        List<AchievementReward> rewards = rewardRepo.findByChildIdAndDateBetween(childId, start, end);
        List<Gift> gifts = giftRepo.findByChildIdAndDateBetween(childId, start, end);

        int rewardSum = rewards.stream().mapToInt(AchievementReward::getAmount).sum();
        int giftSum = gifts.stream().mapToInt(Gift::getAmount).sum();

        // 6. Konačan iznos
        int finalAmount = Math.max(0, allowance + rewardSum + giftSum);

        return finalAmount;
    }
}