package com.monsterbattle.model;

import java.util.Random;

/**
 * Abstract base class for all combat moves.
 * Demonstrates Java inheritance, encapsulation, and abstract methods.
 */
public abstract class Move {
    protected String name;
    protected ElementType type;
    protected int power;
    protected int accuracy; // 1 - 100
    protected int maxPp;
    protected int currentPp;
    protected String description;

    public Move(String name, ElementType type, int power, int accuracy, int maxPp, String description) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.maxPp = maxPp;
        this.currentPp = maxPp;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public ElementType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getMaxPp() {
        return maxPp;
    }

    public int getCurrentPp() {
        return currentPp;
    }

    public String getDescription() {
        return description;
    }

    public boolean canUse() {
        return currentPp > 0;
    }

    public void consumePp() {
        if (currentPp > 0) {
            currentPp--;
        }
    }

    public void restorePp(int amount) {
        currentPp = Math.min(maxPp, currentPp + amount);
    }

    public void restoreAllPp() {
        currentPp = maxPp;
    }

    /**
     * Executes the move during battle.
     * Overridden by specialized subclasses (AttackMove, StatusMove).
     *
     * @param attacker The monster performing the move
     * @param defender The monster receiving the move
     * @param rng Random instance for rolls
     * @return Formatted combat narrative description
     */
    public abstract String execute(Monster attacker, Monster defender, Random rng);

    @Override
    public String toString() {
        return String.format("%-15s [%s] Pow: %-3d Acc: %-3d%% PP: %d/%d", 
                name, type.getDisplayName(), power, accuracy, currentPp, maxPp);
    }
}
