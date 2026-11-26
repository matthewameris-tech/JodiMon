package com.monsterbattle.model;

/**
 * Fire-type monster subclass demonstrating inheritance and polymorphism.
 * Features the signature "Blaze" ability: boosts fire moves when health is critical.
 */
public class FireMonster extends Monster {

    public FireMonster(String name, String species, int level, int maxHp, 
                       int baseAttack, int baseDefense, int baseSpeed, 
                       int evolutionLevel, String nextEvolutionSpecies, String asciiArt) {
        super(name, species, ElementType.FIRE, level, maxHp, baseAttack, baseDefense, 
              baseSpeed, evolutionLevel, nextEvolutionSpecies, asciiArt);
    }

    @Override
    public String getPassiveAbilityName() {
        return "Blaze";
    }

    @Override
    public String getPassiveAbilityDescription() {
        return "Powers up Fire-type moves by 50% when HP falls below 35%.";
    }

    @Override
    public double getPassiveAttackMultiplier(ElementType moveType) {
        if (moveType == ElementType.FIRE && currentHp <= (maxHp * 0.35)) {
            return 1.5;
        }
        return 1.0;
    }

    @Override
    public Monster copy() {
        FireMonster copy = new FireMonster(this.name, this.species, this.level, 
                this.maxHp, this.baseAttack, this.baseDefense, this.baseSpeed, 
                this.evolutionLevel, this.nextEvolutionSpecies, this.asciiArt);
        copyBaseAttributesTo(copy);
        return copy;
    }
}
