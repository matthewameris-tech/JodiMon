package com.monsterbattle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a human player or computer-controlled AI trainer.
 * Manages monster party, inventory, active combatant, and AI behavior.
 */
public class Trainer {
    private final String name;
    private final String title;
    private final boolean isAi;
    private final List<Monster> team;
    private int activeIndex = 0;
    private final List<Item> inventory;

    public Trainer(String name, String title, boolean isAi) {
        this.name = name;
        this.title = title;
        this.isAi = isAi;
        this.team = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAi() {
        return isAi;
    }

    public List<Monster> getTeam() {
        return team;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void addMonster(Monster monster) {
        if (monster != null) {
            team.add(monster);
        }
    }

    public void addItem(Item item) {
        if (item != null) {
            // Check if item already exists in inventory, increment count
            for (Item existing : inventory) {
                if (existing.getName().equalsIgnoreCase(item.getName())) {
                    existing.addQuantity(item.getQuantity());
                    return;
                }
            }
            inventory.add(item);
        }
    }

    public Monster getActiveMonster() {
        if (activeIndex >= 0 && activeIndex < team.size()) {
            return team.get(activeIndex);
        }
        return null;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public boolean setActiveIndex(int index) {
        if (index >= 0 && index < team.size() && !team.get(index).isFainted()) {
            this.activeIndex = index;
            return true;
        }
        return false;
    }

    public boolean hasUsableMonsters() {
        for (Monster m : team) {
            if (!m.isFainted()) {
                return true;
            }
        }
        return false;
    }

    public int getUsableCount() {
        int count = 0;
        for (Monster m : team) {
            if (!m.isFainted()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the next unfainted monster when the active one faints.
     */
    public boolean autoSwitchToNextUsable() {
        for (int i = 0; i < team.size(); i++) {
            if (!team.get(i).isFainted()) {
                this.activeIndex = i;
                return true;
            }
        }
        return false;
    }

    public void restoreParty() {
        for (Monster m : team) {
            m.fullRestore();
        }
    }

    // --- AI Decision Logic ---

    public enum ActionType {
        ATTACK,
        USE_ITEM,
        SWITCH
    }

    public static class AiDecision {
        public ActionType action;
        public int moveIndex;
        public Item itemToUse;

        public AiDecision(ActionType action, int moveIndex, Item itemToUse) {
            this.action = action;
            this.moveIndex = moveIndex;
            this.itemToUse = itemToUse;
        }
    }

    /**
     * AI determines its best action during battle based on conditionals,
     * remaining HP, move effectiveness, and random tactical variety.
     */
    public AiDecision decideAction(Monster opponent, Random rng) {
        Monster active = getActiveMonster();
        if (active == null || active.isFainted()) {
            return new AiDecision(ActionType.ATTACK, 0, null);
        }

        // 1. Consider using a healing item if low on HP (< 30%)
        if (active.getCurrentHp() <= (active.getMaxHp() * 0.30)) {
            for (Item item : inventory) {
                if (item instanceof HealingItem && item.getQuantity() > 0 && item.canUseOn(active)) {
                    // 65% chance the AI decides to heal
                    if (rng.nextDouble() < 0.65) {
                        return new AiDecision(ActionType.USE_ITEM, -1, item);
                    }
                }
            }
        }

        // 2. Select the most tactically sound move
        List<Move> moves = active.getMoves();
        int bestMoveIndex = 0;
        double bestScore = -999.0;

        for (int i = 0; i < moves.size(); i++) {
            Move m = moves.get(i);
            if (!m.canUse()) {
                continue;
            }

            double score = 0.0;
            if (m instanceof AttackMove) {
                AttackMove am = (AttackMove) m;
                double multiplier = am.getType().getMultiplierAgainst(opponent.getType());
                score = am.getPower() * multiplier;

                // STAB bonus
                if (am.getType() == active.getType()) {
                    score *= 1.3;
                }

                // If super effective, encourage AI to use it
                if (multiplier >= 2.0) {
                    score += 40.0;
                }

                // Add small random noise to prevent 100% predictability
                score += rng.nextDouble() * 10.0;
            } else if (m instanceof StatusMove) {
                StatusMove sm = (StatusMove) m;
                // AI uses buffs early when high on HP
                if (active.getCurrentHp() > (active.getMaxHp() * 0.70)) {
                    score = 45.0 + rng.nextDouble() * 10.0;
                } else {
                    score = 10.0;
                }
            }

            if (score > bestScore) {
                bestScore = score;
                bestMoveIndex = i;
            }
        }

        return new AiDecision(ActionType.ATTACK, bestMoveIndex, null);
    }
}
