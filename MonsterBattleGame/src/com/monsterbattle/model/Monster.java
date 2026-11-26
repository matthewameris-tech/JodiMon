package com.monsterbattle.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Monster class representing a creature in the battle game.
 * Demonstrates encapsulation, OOP state management, and provides
 * polymorphic hooks for elemental subclasses.
 */
public abstract class Monster {
    protected String name;
    protected String species;
    protected ElementType type;
    protected int level;
    protected int maxHp;
    protected int currentHp;
    protected int baseAttack;
    protected int baseDefense;
    protected int baseSpeed;

    // In-battle temporary stat modifier stages (-6 to +6)
    protected int attackStage = 0;
    protected int defenseStage = 0;
    protected int speedStage = 0;

    // Progression & Evolution
    protected int exp = 0;
    protected int expToNextLevel;
    protected int evolutionLevel = -1; // -1 if cannot evolve
    protected String nextEvolutionSpecies = null;
    protected Move evolutionMove = null;

    protected List<Move> moves;
    protected String asciiArt;

    public Monster(String name, String species, ElementType type, int level, 
                   int maxHp, int baseAttack, int baseDefense, int baseSpeed, 
                   int evolutionLevel, String nextEvolutionSpecies, String asciiArt) {
        this.name = name;
        this.species = species;
        this.type = type;
        this.level = level;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
        this.evolutionLevel = evolutionLevel;
        this.nextEvolutionSpecies = nextEvolutionSpecies;
        this.asciiArt = asciiArt;
        this.moves = new ArrayList<>();
        this.expToNextLevel = calculateExpThreshold(level);
    }

    private int calculateExpThreshold(int lvl) {
        return (int) Math.round(Math.pow(lvl, 2) * 12.0);
    }

    // --- Getters & Setters ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public ElementType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getExp() {
        return exp;
    }

    public int getExpToNextLevel() {
        return expToNextLevel;
    }

    public int getEvolutionLevel() {
        return evolutionLevel;
    }

    public String getNextEvolutionSpecies() {
        return nextEvolutionSpecies;
    }

    public Move getEvolutionMove() {
        return evolutionMove;
    }

    public void setEvolutionMove(Move evolutionMove) {
        this.evolutionMove = evolutionMove;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public String getAsciiArt() {
        return asciiArt;
    }

    public void setAsciiArt(String asciiArt) {
        this.asciiArt = asciiArt;
    }

    public boolean isFainted() {
        return currentHp <= 0;
    }

    // --- Combat Calculations ---

    public int getEffectiveAttack() {
        return (int) Math.round(baseAttack * getStageMultiplier(attackStage));
    }

    public int getEffectiveDefense() {
        return (int) Math.round(baseDefense * getStageMultiplier(defenseStage));
    }

    public int getEffectiveSpeed() {
        return (int) Math.round(baseSpeed * getStageMultiplier(speedStage));
    }

    private double getStageMultiplier(int stage) {
        if (stage >= 0) {
            return (2.0 + stage) / 2.0;
        } else {
            return 2.0 / (2.0 - stage);
        }
    }

    public void boostAttack(int stages) {
        attackStage = Math.min(6, attackStage + stages);
    }

    public void boostDefense(int stages) {
        defenseStage = Math.min(6, defenseStage + stages);
    }

    public void boostSpeed(int stages) {
        speedStage = Math.min(6, speedStage + stages);
    }

    public void lowerAttack(int stages) {
        attackStage = Math.max(-6, attackStage - stages);
    }

    public void lowerDefense(int stages) {
        defenseStage = Math.max(-6, defenseStage - stages);
    }

    public void resetBattleStages() {
        attackStage = 0;
        defenseStage = 0;
        speedStage = 0;
    }

    public void takeDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    public int heal(int amount) {
        int previousHp = currentHp;
        currentHp = Math.min(maxHp, currentHp + amount);
        return currentHp - previousHp;
    }

    public void fullRestore() {
        currentHp = maxHp;
        for (Move m : moves) {
            m.restoreAllPp();
        }
        resetBattleStages();
    }

    public void learnMove(Move move) {
        if (moves.size() < 4) {
            moves.add(move);
        } else {
            // Replace first or allow caller to handle
            moves.set(3, move);
        }
    }

    // --- Progression & Leveling ---

    /**
     * Adds EXP and checks for level-ups and evolution readiness.
     * @param gained Amount of EXP gained
     * @return Log message detailing level ups or evolutions
     */
    public String gainExp(int gained) {
        StringBuilder log = new StringBuilder();
        exp += gained;
        log.append(name).append(" gained ").append(gained).append(" EXP! (")
           .append(exp).append("/").append(expToNextLevel).append(")\n");

        while (exp >= expToNextLevel) {
            exp -= expToNextLevel;
            levelUp(log);
        }

        return log.toString();
    }

    protected void levelUp(StringBuilder log) {
        level++;
        int hpGain = 5 + (level % 3);
        int atkGain = 2 + (level % 2);
        int defGain = 2 + (level % 2);
        int spdGain = 2 + (level % 2);

        maxHp += hpGain;
        currentHp += hpGain;
        baseAttack += atkGain;
        baseDefense += defGain;
        baseSpeed += spdGain;
        expToNextLevel = calculateExpThreshold(level);

        log.append("\n=========================================\n");
        log.append(" LEVEL UP! ").append(name).append(" reached Level ").append(level).append("!\n");
        log.append(String.format(" HP: +%d (%d) | ATK: +%d (%d) | DEF: +%d (%d) | SPD: +%d (%d)\n",
                hpGain, maxHp, atkGain, baseAttack, defGain, baseDefense, spdGain, baseSpeed));
        log.append("=========================================\n");
    }

    public boolean canEvolve() {
        return evolutionLevel > 0 && level >= evolutionLevel && nextEvolutionSpecies != null;
    }

    /**
     * Evolves this monster into its higher stage form,
     * significantly boosting stats and unlocking evolution moves.
     */
    public String evolve(String newSpeciesName, String newAsciiArt) {
        if (!canEvolve()) {
            return name + " is not ready to evolve yet.";
        }

        String oldSpecies = this.species;
        String oldName = this.name;
        this.species = newSpeciesName;
        if (this.name.equals(oldSpecies)) {
            this.name = newSpeciesName; // update name if default
        }
        this.asciiArt = newAsciiArt;

        // Big evolution stat boost: +35% to stats
        int hpBonus = (int) Math.round(maxHp * 0.35);
        maxHp += hpBonus;
        currentHp += hpBonus;
        baseAttack = (int) Math.round(baseAttack * 1.30);
        baseDefense = (int) Math.round(baseDefense * 1.30);
        baseSpeed = (int) Math.round(baseSpeed * 1.25);

        // Consume evolution stage (can set next evolution or clear)
        this.evolutionLevel = -1;
        this.nextEvolutionSpecies = null;

        StringBuilder sb = new StringBuilder();
        sb.append("\n***************************************************\n");
        sb.append(" WHAT? ").append(oldName).append(" is evolving!\n");
        sb.append(" ... ... ... Congratulations!\n");
        sb.append(" Your ").append(oldSpecies).append(" evolved into ").append(this.species).append("!\n");
        sb.append(" Stats substantially increased! (+").append(hpBonus).append(" Max HP)\n");

        if (evolutionMove != null) {
            learnMove(evolutionMove);
            sb.append(" ").append(this.name).append(" learned ").append(evolutionMove.getName()).append("!\n");
        }
        sb.append("***************************************************\n");

        return sb.toString();
    }

    // --- Polymorphic Passive Ability Hooks (Subclasses implement these) ---

    public abstract String getPassiveAbilityName();

    public abstract String getPassiveAbilityDescription();

    /**
     * Subclasses can boost attack power conditionally (e.g. Blaze, Torrent, Overgrowth).
     */
    public double getPassiveAttackMultiplier(ElementType moveType) {
        return 1.0;
    }

    /**
     * Subclasses can resist or mitigate incoming damage (e.g. Solid Rock, Thick Skin).
     */
    public double getPassiveDefenseModifier(ElementType incomingType, double typeMultiplier) {
        return 1.0;
    }

    /**
     * Deep copy or clone for fresh battles.
     */
    public abstract Monster copy();

    protected void copyBaseAttributesTo(Monster target) {
        target.currentHp = this.currentHp;
        target.exp = this.exp;
        target.expToNextLevel = this.expToNextLevel;
        target.evolutionMove = this.evolutionMove;
        target.moves.clear();
        for (Move m : this.moves) {
            if (m instanceof AttackMove) {
                AttackMove am = (AttackMove) m;
                target.moves.add(new AttackMove(am.getName(), am.getType(), am.getPower(), 
                        am.getAccuracy(), am.getMaxPp(), am.getDescription(), am.isHighCritRate()));
            } else if (m instanceof StatusMove) {
                StatusMove sm = (StatusMove) m;
                target.moves.add(new StatusMove(sm.getName(), sm.getType(), sm.getAccuracy(), 
                        sm.getMaxPp(), sm.getDescription(), sm.getEffectType(), sm.getMagnitude()));
            }
        }
    }
}
