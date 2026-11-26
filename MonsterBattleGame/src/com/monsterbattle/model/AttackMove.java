package com.monsterbattle.model;

import com.monsterbattle.ui.Ansi;
import java.util.Random;

/**
 * Represents an offensive damaging move.
 * Extends Move and implements classic damage calculation, critical hits,
 * accuracy checks, and elemental effectiveness.
 */
public class AttackMove extends Move {
    private final boolean highCritRate;

    public AttackMove(String name, ElementType type, int power, int accuracy, int maxPp, String description) {
        this(name, type, power, accuracy, maxPp, description, false);
    }

    public AttackMove(String name, ElementType type, int power, int accuracy, int maxPp, String description, boolean highCritRate) {
        super(name, type, power, accuracy, maxPp, description);
        this.highCritRate = highCritRate;
    }

    public boolean isHighCritRate() {
        return highCritRate;
    }

    @Override
    public String execute(Monster attacker, Monster defender, Random rng) {
        consumePp();
        StringBuilder log = new StringBuilder();
        log.append(attacker.getName()).append(" used ").append(name).append("!\n");

        // 1. Accuracy Check
        int roll = rng.nextInt(100);
        if (roll >= accuracy) {
            log.append("  -> The attack missed!\n");
            return log.toString();
        }

        // 2. Elemental Effectiveness Check
        double typeMultiplier = type.getMultiplierAgainst(defender.getType());
        if (typeMultiplier == 0.0) {
            log.append("  -> It doesn't affect ").append(defender.getName()).append("!\n");
            return log.toString();
        }

        // 3. Critical Hit Roll (Conditionals & Random numbers)
        double critThreshold = highCritRate ? 0.25 : 0.10;
        boolean isCritical = rng.nextDouble() < critThreshold;
        double critMultiplier = isCritical ? 1.5 : 1.0;

        // 4. Same Type Attack Bonus (STAB)
        double stabMultiplier = (attacker.getType() == type) ? 1.5 : 1.0;

        // 5. Monster Passive Ability Modifier
        double abilityMultiplier = attacker.getPassiveAttackMultiplier(this.type);
        if (abilityMultiplier > 1.0) {
            log.append("  -> ").append(attacker.getName()).append("'s ")
               .append(attacker.getPassiveAbilityName()).append(" flared up! (+50% power)\n");
        }

        // 6. Defender Passive Resistance (e.g., Solid Rock)
        double defenderResistance = defender.getPassiveDefenseModifier(this.type, typeMultiplier);

        // 7. Damage Formula: Classic Pokémon-inspired formula
        int level = attacker.getLevel();
        int attackStat = attacker.getEffectiveAttack();
        int defenseStat = Math.max(1, defender.getEffectiveDefense());

        double baseDamage = (((2.0 * level / 5.0 + 2.0) * power * ((double) attackStat / defenseStat)) / 50.0) + 2.0;

        // Random variance between 0.85 and 1.00
        double variance = 0.85 + (rng.nextDouble() * 0.15);

        double totalMultiplier = typeMultiplier * stabMultiplier * critMultiplier * abilityMultiplier * defenderResistance * variance;
        int finalDamage = Math.max(1, (int) Math.round(baseDamage * totalMultiplier));

        // Apply damage to defender
        defender.takeDamage(finalDamage);

        // Flavor logs
        if (isCritical) {
            log.append("  -> ").append(Ansi.bold(Ansi.BRIGHT_YELLOW + "*** CRITICAL HIT! ***" + Ansi.RESET)).append("\n");
        }
        if (typeMultiplier >= 2.0) {
            log.append("  -> ").append(Ansi.bold(Ansi.BRIGHT_GREEN + "It's super effective! (x" + typeMultiplier + ")" + Ansi.RESET)).append("\n");
        } else if (typeMultiplier <= 0.5) {
            log.append("  -> ").append(Ansi.color("It's not very effective... (x" + typeMultiplier + ")", Ansi.GRAY)).append("\n");
        }

        log.append("  -> ").append(defender.getName()).append(" took ")
           .append(finalDamage).append(" damage! (HP: ")
           .append(defender.getCurrentHp()).append("/").append(defender.getMaxHp()).append(")\n");

        if (defender.isFainted()) {
            log.append("  -> ").append(Ansi.color(defender.getName() + " fainted!", Ansi.BRIGHT_RED)).append("\n");
        }

        return log.toString();
    }
}
