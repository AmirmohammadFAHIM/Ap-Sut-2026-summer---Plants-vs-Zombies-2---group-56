package models;

public interface QuestObserver {
    void updateQuestProgress(String action, int amount);
}