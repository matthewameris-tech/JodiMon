package com.monsterbattle.tournament;

import com.monsterbattle.battle.BattleEngine;
import com.monsterbattle.data.MonsterFactory;
import com.monsterbattle.data.TrainerFactory;
import com.monsterbattle.model.*;
import com.monsterbattle.ui.AsciiArt;

import java.util.Scanner;

/**
 * Manages the multi-stage Championship Tournament.
 * Features 4 escalating rounds, bracket visualization, unlocks of new monsters,
 * item rewards, and healing between bouts.
 */
public class Tournament {
    private final Trainer player;
    private final Scanner scanner;
    private final boolean automated;

    public Tournament(Trainer player, Scanner scanner, boolean automated) {
        this.player = player;
        this.scanner = scanner;
        this.automated = automated;
    }

    public boolean startTournament() {
        System.out.println("\n***************************************************************");
        System.out.println("  WELCOME TO THE GRAND JODIMON CHAMPIONSHIP TOURNAMENT!");
        System.out.println("  Battle through 4 fierce rounds to claim the title of Champion!");
        System.out.println("***************************************************************\n");

        int currentRound = 1;

        // ROUND 1: Quarterfinals
        if (!playRound(currentRound, "QUARTERFINALS", TrainerFactory.createScoutTimmy())) {
            return false;
        }
        rewardRound1();
        currentRound++;

        // ROUND 2: Semifinals
        if (!playRound(currentRound, "SEMIFINALS", TrainerFactory.createLeaderTerra())) {
            return false;
        }
        rewardRound2();
        currentRound++;

        // ROUND 3: Finals
        if (!playRound(currentRound, "FINALS", TrainerFactory.createAceMarina())) {
            return false;
        }
        rewardRound3();
        currentRound++;

        // ROUND 4: Grand Championship
        if (!playRound(currentRound, "GRAND CHAMPIONSHIP", TrainerFactory.createChampionCynthia())) {
            return false;
        }

        printChampionshipVictory();
        return true;
    }

    private boolean playRound(int roundNumber, String roundName, Trainer opponent) {
        System.out.println(AsciiArt.renderTournamentBracket(roundNumber));
        System.out.println("\n>>> [ROUND " + roundNumber + ": " + roundName + "] <<<");
        System.out.println("Your Opponent: " + opponent.getTitle() + " " + opponent.getName());
        System.out.println("Opponent Team Size: " + opponent.getTeam().size() + " monster(s)");

        if (!automated) {
            System.out.println("\nPress [ENTER] to enter the arena and begin the battle...");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }

        BattleEngine battle = new BattleEngine(player, opponent, scanner, automated);
        boolean victory = battle.runBattle();

        if (!victory) {
            System.out.println("\n[ELIMINATED] You were eliminated from the tournament in the " + roundName + "!");
            return false;
        }

        // Heal player team after each round
        System.out.println("\n[NURSE JOY] Rest well, champion! Your entire party has been restored to full HP and PP.");
        player.restoreParty();
        return true;
    }

    private void rewardRound1() {
        System.out.println("\n[REWARD] --- ROUND 1 REWARDS ---");
        System.out.println("  + Earned 2x Super Potion & 1x Revive!");
        player.addItem(new HealingItem("Super Potion", "Restores 60 HP to a monster", 2, 60));
        player.addItem(new ReviveItem("Revive", "Revives a fainted monster to 50% HP", 1, 0.5));
    }

    private void rewardRound2() {
        System.out.println("\n[REWARD] --- ROUND 2 REWARDS ---");
        System.out.println("  + Unlocked a new team member: Voltrod (Electric-type Lv. 7)!");
        System.out.println("  + Earned 1x X-Attack & 1x Max Potion!");
        Monster newElectric = MonsterFactory.createElectricMonster(7);
        player.addMonster(newElectric);
        player.addItem(new HealingItem("Max Potion", "Fully restores HP to an active monster", 1, -1));
        player.addItem(new BuffItem("X-Attack", "Sharply raises active monster's Attack", 1, BuffItem.StatToBuff.ATTACK, 2));
    }

    private void rewardRound3() {
        System.out.println("\n[REWARD] --- ROUND 3 REWARDS ---");
        System.out.println("  + Unlocked a new team member: Boulderbug (Earth-type Lv. 9)!");
        System.out.println("  + Earned 2x Max Potion!");
        Monster newEarth = MonsterFactory.createEarthMonster(9);
        player.addMonster(newEarth);
        player.addItem(new HealingItem("Max Potion", "Fully restores HP to an active monster", 2, -1));
    }

    private void printChampionshipVictory() {
        System.out.println("\n***************************************************************");
        System.out.println("  [CHAMPION] *** JODIMON TOURNAMENT CHAMPION DECLARED! ***");
        System.out.println("  " + player.getName() + " has defeated Champion Cynthia and won");
        System.out.println("  the Grand JodiMon League Championship Cup!");
        System.out.println("***************************************************************\n");
    }
}
