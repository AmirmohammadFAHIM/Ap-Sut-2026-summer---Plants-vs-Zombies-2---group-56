// file: Plants vs Zombies 2 - Ap/src/models/Quest.java
package models;

import controllers.datacontroller.Data;
import controllers.menus.SecondaryMenus.News;
import models.QuestObserver;
import java.io.Serializable;

public class Quest implements Serializable, QuestObserver {
    private String questName;
    private int priority;
    private String actionType;
    private float progress;
    private float target;
    private boolean isDone;
    private String rewardType;
    private int rewardAmount;

    public Quest(String questName, int priority, String actionType, float target, String rewardType, int rewardAmount) {
        this.questName = questName;
        this.priority = priority;
        this.actionType = actionType;
        this.target = target;
        this.rewardType = rewardType;
        this.rewardAmount = rewardAmount;
        this.progress = 0;
        this.isDone = false;
    }

    @Override
    public void updateQuestProgress(String action, int amount) {
        if (!isDone && this.actionType.equalsIgnoreCase(action)) {
            progress += amount;
            if (progress >= target) {
                progress = target;
                isDone = true;
                reward();
            }
        }
    }

    public void reward() {
        User user = Data.getCurrentUser();
        if (user != null) {
            if (rewardType.equalsIgnoreCase("Gems") || rewardType.equalsIgnoreCase("Gem")) {
                user.addDiamonds(rewardAmount);
            } else if (rewardType.equalsIgnoreCase("Coins") || rewardType.equalsIgnoreCase("Coin")) {
                user.addCoins(rewardAmount);
            }

            String msg = "Quest Completed: " + questName + "! Reward: " + rewardAmount + " " + rewardType;
            News.pushNewsToUser(user, msg);
            System.out.println("\n[Achievement Unlocked] " + msg);
            Data.saveUser();
        }
    }

    public String getQuestName() { return questName; }
    public int getPriority() { return priority; }
    public float getProgress() { return progress; }
    public float getTarget() { return target; }
    public boolean isDone() { return isDone; }
    public String getRewardType() { return rewardType; }
    public int getRewardAmount() { return rewardAmount; }
}