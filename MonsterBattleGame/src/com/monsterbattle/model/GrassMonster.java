package com.monsterbattle.model;

/**
 * Grass-type monster subclass demonstrating inheritance and polymorphism.
 * Features the signature "Overgrowth" ability: boosts grass moves when health is critical.
 */
public class GrassMonster extends Monster {

    public GrassMonster(String name, String species, int level, int maxHp, 
                        int baseAttack, int baseDefense, int baseSpeed, 
                        int evolutionLevel, String nextEvolutionSpecies, String asciiArt) {
        super(name, species, ElementType.GRASS, level, maxHp, baseAttack, baseDefense, 
              baseSpeed, evolutionLevel, nextEvolutionSpecies, asciiArt);
    }

    @Override
    public String getPassiveAbilityName() {
        return "Overgrowth";
    }

    @Override
    public String getPassiveAbilityDescription() {
        return "Powers up Grass-type moves by 50% when HP falls below 35%.";
    }

    @Override
    public double getPassiveAttackMultiplier(ElementType moveType) {
        if (moveType == ElementType.GRASS && currentHp <= (maxHp * 0.35)) {
            return 1.5;
        }
        return 1.0;
    }

    @Override
    public Monster copy() {
        GrassMonster copy = new GrassMonster(this.name, this.species, this.level, 
                this.maxHp, this.baseAttack, this.baseDefense, this.baseSpeed, 
                this.evolutionLevel, this.nextEvolutionSpecies, this.asciiArt);
        copyBaseAttributesTo(copy);
        return copy;
    }
}
