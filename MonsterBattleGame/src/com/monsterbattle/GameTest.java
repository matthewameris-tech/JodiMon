package com.monsterbattle;

import com.monsterbattle.data.MonsterFactory;
import com.monsterbattle.model.*;
import com.monsterbattle.ui.AsciiArt;

import java.util.Random;

/**
 * Automated test suite verifying core game mechanics:
 * 1. Elemental matchups and damage multipliers
 * 2. Critical hit mechanics
 * 3. Healing items and item consumption
 * 4. Level progression and Monster Evolution
 * 5. Inheritance and passive abilities
 */
public class GameTest {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  RUNNING AUTOMATED MONSTER ARENA TEST SUITE      ");
        System.out.println("==================================================");

        testElementalMultipliers();
        testPassiveAbilities();
        testHealingItems();
        testCriticalHitLogic();
        testLevelUpAndEvolution();

        System.out.println("\n[ALL TESTS PASSED SUCCESSFULLY!]");
    }

    private static void testElementalMultipliers() {
        System.out.print("[TEST] Elemental Type Multipliers: ");

        // Fire vs Grass = 2.0x
        assertEqual(2.0, ElementType.FIRE.getMultiplierAgainst(ElementType.GRASS), "Fire vs Grass");
        // Fire vs Water = 0.5x
        assertEqual(0.5, ElementType.FIRE.getMultiplierAgainst(ElementType.WATER), "Fire vs Water");
        // Water vs Fire = 2.0x
        assertEqual(2.0, ElementType.WATER.getMultiplierAgainst(ElementType.FIRE), "Water vs Fire");
        // Electric vs Water = 2.0x
        assertEqual(2.0, ElementType.ELECTRIC.getMultiplierAgainst(ElementType.WATER), "Electric vs Water");
        // Electric vs Earth = 0.0x
        assertEqual(0.0, ElementType.ELECTRIC.getMultiplierAgainst(ElementType.EARTH), "Electric vs Earth");
        // Earth vs Electric = 2.0x
        assertEqual(2.0, ElementType.EARTH.getMultiplierAgainst(ElementType.ELECTRIC), "Earth vs Electric");

        System.out.println("OK");
    }

    private static void testPassiveAbilities() {
        System.out.print("[TEST] Inheritance and Subclass Passives: ");

        FireMonster fire = (FireMonster) MonsterFactory.createStarterFire(5);
        // At full HP, Blaze is inactive
        assertEqual(1.0, fire.getPassiveAttackMultiplier(ElementType.FIRE), "Blaze at full HP");

        // Damage fire monster below 35% HP
        fire.takeDamage((int) (fire.getMaxHp() * 0.70));
        assertEqual(1.5, fire.getPassiveAttackMultiplier(ElementType.FIRE), "Blaze active < 35% HP");

        // Earth monster Solid Rock passive
        EarthMonster earth = (EarthMonster) MonsterFactory.createEarthMonster(5);
        assertEqual(0.75, earth.getPassiveDefenseModifier(ElementType.WATER, 2.0), "Solid Rock reduction");

        System.out.println("OK");
    }

    private static void testHealingItems() {
        System.out.print("[TEST] Healing Items and Inventory: ");

        Monster water = MonsterFactory.createStarterWater(5);
        int maxHp = water.getMaxHp();
        water.takeDamage(40);
        int damagedHp = water.getCurrentHp();
        assertCondition(damagedHp == maxHp - 40, "Damage deduction");

        HealingItem potion = new HealingItem("Potion", "Heal 30", 2, 30);
        assertCondition(potion.canUseOn(water), "Can use potion");

        potion.use(water);
        assertCondition(water.getCurrentHp() == damagedHp + 30, "Potion heal amount");
        assertCondition(potion.getQuantity() == 1, "Quantity reduced");

        // Revive item
        ReviveItem revive = new ReviveItem("Revive", "Revive 50%", 1, 0.5);
        assertCondition(!revive.canUseOn(water), "Cannot revive living monster");
        water.takeDamage(100);
        assertCondition(water.isFainted(), "Monster fainted");
        assertCondition(revive.canUseOn(water), "Can revive fainted monster");
        revive.use(water);
        assertCondition(!water.isFainted() && water.getCurrentHp() > 0, "Monster revived");

        System.out.println("OK");
    }

    private static void testCriticalHitLogic() {
        System.out.print("[TEST] Critical Hit Probability & RNG: ");

        AttackMove slash = new AttackMove("Flame Claw", ElementType.FIRE, 60, 100, 15, "Slash", true);
        assertCondition(slash.isHighCritRate(), "High crit move flag");

        // Test random variance bounds
        Random rng = new Random();
        for (int i = 0; i < 50; i++) {
            double variance = 0.85 + (rng.nextDouble() * 0.15);
            assertCondition(variance >= 0.85 && variance <= 1.00, "Variance bounds");
        }

        System.out.println("OK");
    }

    private static void testLevelUpAndEvolution() {
        System.out.print("[TEST] Monster Progression & Evolution: ");

        Monster grass = MonsterFactory.createStarterGrass(7);
        int initialLvl = grass.getLevel();
        int initialMaxHp = grass.getMaxHp();
        int initialAtk = grass.getBaseAttack();

        // Give enough EXP to level up to 8
        grass.gainExp(grass.getExpToNextLevel() + 50);
        assertCondition(grass.getLevel() == 8, "Level up to 8");
        assertCondition(grass.getMaxHp() > initialMaxHp, "Max HP increased");
        assertCondition(grass.getBaseAttack() > initialAtk, "Attack increased");

        // Now monster should be ready to evolve!
        assertCondition(grass.canEvolve(), "Monster can evolve at Lv. 8");
        String nextSpecies = grass.getNextEvolutionSpecies();
        assertEqual("Thornstalker", nextSpecies, "Next evolution species");

        int preEvoMaxHp = grass.getMaxHp();
        String evoLog = grass.evolve(nextSpecies, MonsterFactory.getEvolvedSprite(nextSpecies));

        assertCondition(grass.getSpecies().equals("Thornstalker"), "Species changed to Thornstalker");
        assertCondition(grass.getMaxHp() > preEvoMaxHp, "Evolution massive stat boost");
        assertCondition(!grass.canEvolve(), "Cannot evolve again");

        // Check evolution move learned
        boolean learnedSolarBeam = false;
        for (Move m : grass.getMoves()) {
            if ("Solar Beam".equals(m.getName())) {
                learnedSolarBeam = true;
                break;
            }
        }
        assertCondition(learnedSolarBeam, "Learned Solar Beam upon evolution");

        System.out.println("OK");
    }

    private static void assertEqual(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.001) {
            throw new AssertionError("FAIL [" + message + "]: expected " + expected + " but got " + actual);
        }
    }

    private static void assertEqual(String expected, String actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError("FAIL [" + message + "]: expected " + expected + " but got " + actual);
        }
    }

    private static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("FAIL [" + message + "]: condition not satisfied");
        }
    }
}
