package com.monsterbattle;

import com.monsterbattle.battle.BattleEngine;
import com.monsterbattle.data.MonsterFactory;
import com.monsterbattle.data.TrainerFactory;
import com.monsterbattle.model.Trainer;
import com.monsterbattle.tournament.Tournament;
import com.monsterbattle.ui.GameMenu;

import java.util.Scanner;

/**
 * Entry point for the Monster Battle Game.
 * Supports standard interactive console play and non-interactive demo/testing flags.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            String arg = args[0].toLowerCase();
            if (arg.equals("--demo")) {
                GameMenu.runAutomatedDemo();
                return;
            } else if (arg.equals("--test-tournament")) {
                runAutomatedTournamentTest();
                return;
            }
        }

        Scanner scanner = new Scanner(System.in);
        GameMenu menu = new GameMenu(scanner);
        menu.start();
    }

    private static void runAutomatedTournamentTest() {
        System.out.println("Running automated JodiMon tournament test harness...");
        Trainer testPlayer = TrainerFactory.createPlayer("ChampionTest", MonsterFactory.createStarterFire(6));
        Tournament tournament = new Tournament(testPlayer, null, true);
        boolean success = tournament.startTournament();
        System.out.println("Tournament Completed. Won: " + success);
    }
}
