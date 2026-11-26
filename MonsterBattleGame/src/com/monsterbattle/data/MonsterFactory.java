package com.monsterbattle.data;

import com.monsterbattle.model.*;
import com.monsterbattle.ui.AsciiArt;

/**
 * Factory class generating pre-configured monster species, starters,
 * and high-tier evolutions with custom movesets and base stats.
 */
public class MonsterFactory {

    public static Monster createStarterFire(int level) {
        FireMonster m = new FireMonster("Ignispark", "Ignispark", level, 
                55 + level * 4, 18 + level * 2, 14 + level * 2, 16 + level * 2, 
                8, "Pyrodrake", AsciiArt.IGNISPARK_SPRITE);

        m.learnMove(new AttackMove("Ember", ElementType.FIRE, 40, 100, 20, "Spits fiery embers at target."));
        m.learnMove(new AttackMove("Scratch", ElementType.NORMAL, 40, 100, 25, "Hard scratch with claws."));
        m.learnMove(new AttackMove("Flame Claw", ElementType.FIRE, 60, 95, 15, "Fiery slash with high critical hit rate.", true));
        m.learnMove(new StatusMove("Roar", ElementType.NORMAL, 100, 20, "Fierce roar that boosts Attack.", StatusMove.EffectType.BUFF_ATTACK, 1));

        m.setEvolutionMove(new AttackMove("Inferno Blast", ElementType.FIRE, 90, 85, 5, "Unleashes a devastating fire tempest!"));
        return m;
    }

    public static Monster createStarterWater(int level) {
        WaterMonster m = new WaterMonster("Aquashell", "Aquashell", level, 
                60 + level * 4, 15 + level * 2, 19 + level * 2, 13 + level * 2, 
                8, "Hydroclaw", AsciiArt.AQUASHELL_SPRITE);

        m.learnMove(new AttackMove("Water Gun", ElementType.WATER, 40, 100, 20, "Shoots a pressurized water stream."));
        m.learnMove(new AttackMove("Tackle", ElementType.NORMAL, 40, 100, 25, "Charges forward with a body blow."));
        m.learnMove(new AttackMove("Aqua Jet", ElementType.WATER, 55, 100, 15, "Lithe high-speed watery strike."));
        m.learnMove(new StatusMove("Iron Shell", ElementType.WATER, 100, 20, "Retracts into shell, raising Defense.", StatusMove.EffectType.BUFF_DEFENSE, 1));

        m.setEvolutionMove(new AttackMove("Hydro Cannon", ElementType.WATER, 95, 80, 5, "Discharges a torrential deluge at target!"));
        return m;
    }

    public static Monster createStarterGrass(int level) {
        GrassMonster m = new GrassMonster("Leafox", "Leafox", level, 
                58 + level * 4, 17 + level * 2, 16 + level * 2, 15 + level * 2, 
                8, "Thornstalker", AsciiArt.LEAFOX_SPRITE);

        m.learnMove(new AttackMove("Vine Whip", ElementType.GRASS, 45, 100, 20, "Strikes enemy with slender vines."));
        m.learnMove(new AttackMove("Quick Attack", ElementType.NORMAL, 40, 100, 25, "Swift rush attack."));
        m.learnMove(new AttackMove("Leaf Blade", ElementType.GRASS, 65, 95, 15, "Sharp leaf strike with high crit rate.", true));
        m.learnMove(new StatusMove("Synthesis", ElementType.GRASS, 100, 10, "Absorbs sunlight to heal 35% HP.", StatusMove.EffectType.HEAL_SELF, 35));

        m.setEvolutionMove(new AttackMove("Solar Beam", ElementType.GRASS, 95, 85, 5, "Fires a concentrated solar energy beam!"));
        return m;
    }

    public static Monster createElectricMonster(int level) {
        ElectricMonster m = new ElectricMonster("Voltrod", "Voltrod", level, 
                50 + level * 4, 19 + level * 2, 13 + level * 2, 20 + level * 2, 
                8, "Sparkstrike", AsciiArt.VOLTROD_SPRITE);

        m.learnMove(new AttackMove("Thunder Shock", ElementType.ELECTRIC, 40, 100, 25, "Zaps opponent with jolt of electricity."));
        m.learnMove(new AttackMove("Slam", ElementType.NORMAL, 45, 90, 20, "Slams target with great force."));
        m.learnMove(new AttackMove("Spark Punch", ElementType.ELECTRIC, 65, 95, 15, "Electrified knuckle punch."));
        m.learnMove(new StatusMove("Charge", ElementType.ELECTRIC, 100, 15, "Stores electrical power to boost Attack.", StatusMove.EffectType.BUFF_ATTACK, 1));

        m.setEvolutionMove(new AttackMove("Thunderbolt Storm", ElementType.ELECTRIC, 90, 90, 5, "Summons a torrential sky-shattering lightning strike!"));
        return m;
    }

    public static Monster createEarthMonster(int level) {
        EarthMonster m = new EarthMonster("Boulderbug", "Boulderbug", level, 
                65 + level * 4, 18 + level * 2, 22 + level * 2, 10 + level * 2, 
                8, "Stonemaul", AsciiArt.BOULDERBUG_SPRITE);

        m.learnMove(new AttackMove("Rock Throw", ElementType.EARTH, 50, 90, 20, "Hurls boulders at the enemy."));
        m.learnMove(new AttackMove("Tackle", ElementType.NORMAL, 40, 100, 25, "Heavy direct tackle."));
        m.learnMove(new AttackMove("Earthquake", ElementType.EARTH, 70, 95, 10, "Quakes the ground beneath target."));
        m.learnMove(new StatusMove("Harden", ElementType.EARTH, 100, 20, "Stiffens muscular shell to boost Defense.", StatusMove.EffectType.BUFF_DEFENSE, 1));

        m.setEvolutionMove(new AttackMove("Fissure Slam", ElementType.EARTH, 95, 80, 5, "Splits tectonic bedrock with immense force!"));
        return m;
    }

    public static String getEvolvedSprite(String species) {
        switch (species) {
            case "Pyrodrake":
                return AsciiArt.PYRODRAKE_SPRITE;
            case "Hydroclaw":
                return AsciiArt.HYDROCLAW_SPRITE;
            case "Thornstalker":
                return AsciiArt.THORNSTALKER_SPRITE;
            case "Sparkstrike":
                return AsciiArt.SPARKSTRIKE_SPRITE;
            case "Stonemaul":
                return AsciiArt.STONEMAUL_SPRITE;
            default:
                return AsciiArt.IGNISPARK_SPRITE;
        }
    }
}
