package com.monsterbattle.model;

/**
 * Water-type monster subclass demonstrating inheritance and polymorphism.
 * Features the signature "Torrent" ability: boosts water moves when health is critical.
 */
public class WaterMonster extends Monster {

    public WaterMonster(String name, String species, int level, int maxHp, 
                         int baseAttack, int baseDefense, int baseSpeed, 
                         int evolutionLevel, String nextEvolutionSpecies, String asciiArt) {
        super(name, species, ElementType.WATER, level, maxHp, baseAttack, baseDefense, 
              baseSpeed, evolutionLevel, nextEvolutionSpecies, asciiArt);
    }

    @Override
    public String getPassiveAbilityName() {
        return "Torrent";
    }

    @Override
    public String getPassiveAbilityDescription() {
        return "Powers up Water-type moves by 50% when HP falls below 35%.";
    }

    @Override
    public double getPassiveAttackMultiplier(ElementType moveType) {
        if (moveType == ElementType.WATER && currentHp <= (maxHp * 0.35)) {
            return 1.5;
        }
        return 1.0;
    }

    @Override
    public Monster copy() {
        WaterMonster copy = new WaterMonster(this.name, this.species, this.level, 
                this.maxHp, this.baseAttack, this.baseDefense, this.baseSpeed, 
                this.evolutionLevel, this.nextEvolutionSpecies, this.asciiArt);
        copyBaseAttributesTo(copy);
        return copy;
    }
}
