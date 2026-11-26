package com.monsterbattle.model;

import java.util.Random;

/**
 * Represents a non-damaging tactical move (buffs, debuffs, heals).
 * Extends Move and showcases inheritance and state modification.
 */
public class StatusMove extends Move {
    public enum EffectType {
        HEAL_SELF,
        BUFF_ATTACK,
        BUFF_DEFENSE,
        BUFF_SPEED,
        DEBUFF_ENEMY_ATTACK,
        DEBUFF_ENEMY_DEFENSE
    }

    private final EffectType effectType;
    private final int magnitude; // e.g., percentage heal or stat boost stages

    public StatusMove(String name, ElementType type, int accuracy, int maxPp, 
                      String description, EffectType effectType, int magnitude) {
        super(name, type, 0, accuracy, maxPp, description);
        this.effectType = effectType;
        this.magnitude = magnitude;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public int getMagnitude() {
        return magnitude;
    }

    @Override
    public String execute(Monster attacker, Monster defender, Random rng) {
        consumePp();
        StringBuilder log = new StringBuilder();
        log.append(attacker.getName()).append(" used ").append(name).append("!\n");

        // Accuracy check (if applicable, e.g. debuffing enemy)
        if (accuracy < 100 && rng.nextInt(100) >= accuracy) {
            log.append("  -> The move missed!\n");
            return log.toString();
        }

        switch (effectType) {
            case HEAL_SELF:
                int healAmount = (int) Math.round(attacker.getMaxHp() * (magnitude / 100.0));
                int actualHealed = attacker.heal(healAmount);
                log.append("  -> ").append(attacker.getName()).append(" regained ")
                   .append(actualHealed).append(" HP! (HP: ")
                   .append(attacker.getCurrentHp()).append("/").append(attacker.getMaxHp()).append(")\n");
                break;

            case BUFF_ATTACK:
                attacker.boostAttack(magnitude);
                log.append("  -> ").append(attacker.getName()).append("'s Attack rose drastically!\n");
                break;

            case BUFF_DEFENSE:
                attacker.boostDefense(magnitude);
                log.append("  -> ").append(attacker.getName()).append("'s Defense rose sharply!\n");
                break;

            case BUFF_SPEED:
                attacker.boostSpeed(magnitude);
                log.append("  -> ").append(attacker.getName()).append("'s Speed increased!\n");
                break;

            case DEBUFF_ENEMY_ATTACK:
                defender.lowerAttack(magnitude);
                log.append("  -> ").append(defender.getName()).append("'s Attack fell!\n");
                break;

            case DEBUFF_ENEMY_DEFENSE:
                defender.lowerDefense(magnitude);
                log.append("  -> ").append(defender.getName()).append("'s Defense dropped!\n");
                break;
        }

        return log.toString();
    }
}
