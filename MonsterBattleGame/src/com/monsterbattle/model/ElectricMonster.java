package com.monsterbattle.model;

/**
 * Electric-type monster subclass demonstrating inheritance and polymorphism.
 * Features the signature "Static Surge" ability: +20% damage to Electric attacks.
 */
public class ElectricMonster extends Monster {

    public ElectricMonster(String name, String species, int level, int maxHp, 
                           int baseAttack, int baseDefense, int baseSpeed, 
                           int evolutionLevel, String nextEvolutionSpecies, String asciiArt) {
        super(name, species, ElementType.ELECTRIC, level, maxHp, baseAttack, baseDefense, 
              baseSpeed, evolutionLevel, nextEvolutionSpecies, asciiArt);
    }

    @Override
    public String getPassiveAbilityName() {
        return "Static Surge";
    }

    @Override
    public String getPassiveAbilityDescription() {
        return "Passive crackle enhances all Electric-type moves by 20%.";
    }

    @Override
    public double getPassiveAttackMultiplier(ElementType moveType) {
        if (moveType == ElementType.ELECTRIC) {
            return 1.20;
        }
        return 1.0;
    }

    @Override
    public Monster copy() {
        ElectricMonster copy = new ElectricMonster(this.name, this.species, this.level, 
                this.maxHp, this.baseAttack, this.baseDefense, this.baseSpeed, 
                this.evolutionLevel, this.nextEvolutionSpecies, this.asciiArt);
        copyBaseAttributesTo(copy);
        return copy;
    }
}
