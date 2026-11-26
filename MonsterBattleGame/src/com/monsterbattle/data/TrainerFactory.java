package com.monsterbattle.data;

import com.monsterbattle.model.*;

/**
 * Generates AI rival trainers and opponents with customized rosters,
 * levels, and battle items for exhibition and tournament mode.
 */
public class TrainerFactory {

    public static Trainer createPlayer(String name, Monster starter) {
        Trainer player = new Trainer(name, "Monster Trainer", false);
        player.addMonster(starter);
        player.addItem(new HealingItem("Potion", "Restores 30 HP to an active monster.", 3, 30));
        player.addItem(new HealingItem("Super Potion", "Restores 60 HP to an active monster.", 1, 60));
        player.addItem(new ReviveItem("Revive", "Revives a fainted monster to 50% HP.", 1, 0.5));
        player.addItem(new BuffItem("X-Attack", "Sharply raises active monster's Attack.", 1, BuffItem.StatToBuff.ATTACK, 2));
        return player;
    }

    public static Trainer createScoutTimmy() {
        Trainer ai = new Trainer("Scout Timmy", "Rookie Scout", true);
        Monster m = MonsterFactory.createStarterGrass(4);
        ai.addMonster(m);
        ai.addItem(new HealingItem("Potion", "Heals 30 HP", 1, 30));
        return ai;
    }

    public static Trainer createLeaderTerra() {
        Trainer ai = new Trainer("Leader Terra", "Earth Gym Leader", true);
        Monster m1 = MonsterFactory.createEarthMonster(6);
        Monster m2 = MonsterFactory.createElectricMonster(6);
        ai.addMonster(m1);
        ai.addMonster(m2);
        ai.addItem(new HealingItem("Super Potion", "Heals 60 HP", 1, 60));
        return ai;
    }

    public static Trainer createAceMarina() {
        Trainer ai = new Trainer("Ace Marina", "Tidal Tactician", true);
        Monster m1 = MonsterFactory.createStarterWater(8);
        // Marina's Aquashell has evolved!
        m1.evolve(m1.getNextEvolutionSpecies(), MonsterFactory.getEvolvedSprite(m1.getNextEvolutionSpecies()));

        Monster m2 = MonsterFactory.createStarterFire(7);
        ai.addMonster(m1);
        ai.addMonster(m2);
        ai.addItem(new HealingItem("Super Potion", "Heals 60 HP", 2, 60));
        return ai;
    }

    public static Trainer createChampionCynthia() {
        Trainer ai = new Trainer("Champion Cynthia", "Grand Arena Champion", true);
        Monster m1 = MonsterFactory.createStarterFire(10);
        m1.evolve(m1.getNextEvolutionSpecies(), MonsterFactory.getEvolvedSprite(m1.getNextEvolutionSpecies()));

        Monster m2 = MonsterFactory.createEarthMonster(10);
        m2.evolve(m2.getNextEvolutionSpecies(), MonsterFactory.getEvolvedSprite(m2.getNextEvolutionSpecies()));

        Monster m3 = MonsterFactory.createElectricMonster(11);
        m3.evolve(m3.getNextEvolutionSpecies(), MonsterFactory.getEvolvedSprite(m3.getNextEvolutionSpecies()));

        ai.addMonster(m1);
        ai.addMonster(m2);
        ai.addMonster(m3);

        ai.addItem(new HealingItem("Max Potion", "Fully restores HP", 2, -1));
        ai.addItem(new ReviveItem("Revive", "Revives fainted monster to 50% HP", 1, 0.5));
        return ai;
    }

    public static Trainer createRandomWildEncounter(int playerLevel) {
        Trainer wild = new Trainer("Wild Monster", "Untamed Creature", true);
        int level = Math.max(2, playerLevel + (int)(Math.random() * 3 - 1));
        int pick = (int)(Math.random() * 5);
        Monster m;
        switch (pick) {
            case 0: m = MonsterFactory.createStarterFire(level); break;
            case 1: m = MonsterFactory.createStarterWater(level); break;
            case 2: m = MonsterFactory.createStarterGrass(level); break;
            case 3: m = MonsterFactory.createElectricMonster(level); break;
            default: m = MonsterFactory.createEarthMonster(level); break;
        }
        wild.addMonster(m);
        return wild;
    }
}
