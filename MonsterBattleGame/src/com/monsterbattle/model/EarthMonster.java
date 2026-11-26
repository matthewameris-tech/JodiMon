package com.monsterbattle.model;

/**
 * Earth-type monster subclass demonstrating inheritance and polymorphism.
 * Features the signature "Solid Rock" ability: reduces super-effective damage by 25%.
 */
public class EarthMonster extends Monster {

    public EarthMonster(String name, String species, int level, int maxHp, 
                        int baseAttack, int baseDefense, int baseSpeed, 
                        int evolutionLevel, String nextEvolutionSpecies, String asciiArt) {
        super(name, species, ElementType.EARTH, level, maxHp, baseAttack, baseDefense, 
              baseSpeed, evolutionLevel, nextEvolutionSpecies, asciiArt);
    }

    @Override
    public String getPassiveAbilityName() {
        return "Solid Rock";
    }

    @Override
    public String getPassiveAbilityDescription() {
        return "Reduces incoming super-effective damage by 25%.";
    }

    @Override
    public double getPassiveDefenseModifier(ElementType incomingType, double typeMultiplier) {
        if (typeMultiplier > 1.0) {
            return 0.75; // 25% damage mitigation
        }
        return 1.0;
    }

    @Override
    public Monster copy() {
        EarthMonster copy = new EarthMonster(this.name, this.species, this.level, 
                this.maxHp, this.baseAttack, this.baseDefense, this.baseSpeed, 
                this.evolutionLevel, this.nextEvolutionSpecies, this.asciiArt);
        copyBaseAttributesTo(copy);
        return copy;
    }
}
