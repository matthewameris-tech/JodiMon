package com.monsterbattle.ui;

import com.monsterbattle.battle.BattleEngine;
import com.monsterbattle.data.MonsterFactory;
import com.monsterbattle.data.TrainerFactory;
import com.monsterbattle.model.*;
import com.monsterbattle.tournament.Tournament;

import java.util.List;
import java.util.Scanner;

/**
 * Main game loop and interactive terminal interface.
 * Coordinates starter selection, tournament mode, exhibition matches,
 * roster inspection, evolution, inventory management, and type charts.
 */
public class GameMenu {
    private final Scanner scanner;
    private Trainer player;

    public GameMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println(AsciiArt.TITLE_BANNER);
        System.out.println("Welcome to the world of JodiMon! Turn-Based Battle Arena!");

        initPlayerProfile();
        mainMenuLoop();
    }

    private void initPlayerProfile() {
        System.out.print("\nEnter your Trainer Name (or press ENTER for 'John'): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "John";
        }

        System.out.println("\n--- PROFESSOR OAK'S LAB: CHOOSE YOUR STARTER JODIMON ---");
        System.out.println("1. [FIRE]  Ignispark  - High Attack & Speed. Ability: Blaze");
        System.out.println("2. [WATER] Aquashell  - High Defense & HP.   Ability: Torrent");
        System.out.println("3. [GRASS] Leafox     - Balanced & Recovery. Ability: Overgrowth");
        System.out.print("Select your partner JodiMon (1-3, default 1): ");

        String choice = scanner.nextLine().trim();
        Monster starter;
        if ("2".equals(choice)) {
            starter = MonsterFactory.createStarterWater(5);
        } else if ("3".equals(choice)) {
            starter = MonsterFactory.createStarterGrass(5);
        } else {
            starter = MonsterFactory.createStarterFire(5);
        }

        this.player = TrainerFactory.createPlayer(name, starter);
        System.out.println("\n[!] Great choice! " + player.getName() + " obtained " + starter.getName() + "!");
        System.out.println(Ansi.colorSprite(starter.getAsciiArt(), starter.getType()));
    }

    private void mainMenuLoop() {
        boolean running = true;
        while (running) {
            System.out.println("\n====================== JODIMON MENU ======================");
            System.out.println("  [1] Enter JodiMon Championship Tournament (4 Rounds)");
            System.out.println("  [2] Exhibition / Wild JodiMon Battle");
            System.out.println("  [3] View & Manage JodiMon Team (Stats & Evolution)");
            System.out.println("  [4] Inspect Bag & Inventory");
            System.out.println("  [5] Elemental Type Effectiveness Guide");
            System.out.println("  [6] Run Automated JodiMon Battle Demo (Self-Playing)");
            System.out.println("  [7] Exit Game");
            System.out.print("Enter choice (1-7): ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    Tournament tournament = new Tournament(player, scanner, false);
                    tournament.startTournament();
                    break;
                case "2":
                    runExhibitionBattle();
                    break;
                case "3":
                    viewRoster();
                    break;
                case "4":
                    viewInventory();
                    break;
                case "5":
                    printTypeGuide();
                    break;
                case "6":
                    runAutomatedDemo();
                    break;
                case "7":
                    System.out.println("\nThank you for playing JodiMon! See you next time!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please choose an option from 1 to 7.");
                    break;
            }
        }
    }

    private void runExhibitionBattle() {
        System.out.println("\n--- EXHIBITION BATTLE SELECTION ---");
        System.out.println("  [1] Wild Monster (Scaled to your level)");
        System.out.println("  [2] Rookie Scout Timmy (Grass)");
        System.out.println("  [3] Gym Leader Terra (Earth/Electric)");
        System.out.println("  [4] Ace Trainer Marina (Water/Fire)");
        System.out.print("Select opponent (1-4): ");

        String in = scanner.nextLine().trim();
        Trainer opponent;
        int playerLvl = player.getActiveMonster().getLevel();
        switch (in) {
            case "2": opponent = TrainerFactory.createScoutTimmy(); break;
            case "3": opponent = TrainerFactory.createLeaderTerra(); break;
            case "4": opponent = TrainerFactory.createAceMarina(); break;
            default:  opponent = TrainerFactory.createRandomWildEncounter(playerLvl); break;
        }

        BattleEngine battle = new BattleEngine(player, opponent, scanner, false);
        battle.runBattle();

        System.out.println("\nRestoring party after exhibition match...");
        player.restoreParty();
    }

    private void viewRoster() {
        List<Monster> team = player.getTeam();
        System.out.println("\n=================== JODIMON ROSTER ===================");
        for (int i = 0; i < team.size(); i++) {
            Monster m = team.get(i);
            String activeTag = (i == player.getActiveIndex()) ? " [ACTIVE]" : "";
            System.out.printf("[%d] %-12s Lv.%-2d %-8s HP: %3d/%-3d%s\n",
                    (i + 1), m.getName(), m.getLevel(), m.getType().getBadge(), 
                    m.getCurrentHp(), m.getMaxHp(), activeTag);
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Enter JodiMon number to inspect, 'e' to evolve, or 0 to return: ");
        String in = scanner.nextLine().trim();

        if (in.equalsIgnoreCase("e")) {
            checkAndTriggerEvolutions();
            return;
        }

        try {
            int idx = Integer.parseInt(in) - 1;
            if (idx >= 0 && idx < team.size()) {
                inspectMonsterDetails(team.get(idx));
            }
        } catch (NumberFormatException ignored) {}
    }

    private void inspectMonsterDetails(Monster m) {
        System.out.println("\n----------------- JODIMON DETAILS -----------------");
        System.out.println(Ansi.colorSprite(m.getAsciiArt(), m.getType()));
        System.out.println("Name:     " + Ansi.bold(m.getName()) + " (Species: " + m.getSpecies() + ")");
        System.out.println("Type:     " + Ansi.getTypeBadge(m.getType()));
        System.out.println("Level:    " + m.getLevel());
        System.out.println("HP:       " + Ansi.getHpBar(m.getCurrentHp(), m.getMaxHp(), 15));
        System.out.println("EXP:      " + Ansi.getExpBar(m.getExp(), m.getExpToNextLevel(), 12));
        System.out.println("Attack:   " + m.getBaseAttack());
        System.out.println("Defense:  " + m.getBaseDefense());
        System.out.println("Speed:    " + m.getBaseSpeed());
        System.out.println("Passive:  " + Ansi.bold(m.getPassiveAbilityName()) + " (" + m.getPassiveAbilityDescription() + ")");

        if (m.canEvolve()) {
            System.out.println("[EVOLUTION READY] Ready to evolve into " + m.getNextEvolutionSpecies() + "!");
        } else if (m.getEvolutionLevel() > 0) {
            System.out.println("Evolution: Evolves into " + m.getNextEvolutionSpecies() + " at Lv. " + m.getEvolutionLevel());
        } else {
            System.out.println("Evolution: Fully evolved.");
        }

        System.out.println("\nMoveset:");
        for (Move move : m.getMoves()) {
            System.out.println("  * " + move.toString());
        }
        System.out.println("---------------------------------------------------");
    }

    private void checkAndTriggerEvolutions() {
        boolean any = false;
        for (Monster m : player.getTeam()) {
            if (m.canEvolve()) {
                any = true;
                System.out.println(AsciiArt.EVOLUTION_BANNER);
                String evoSpecies = m.getNextEvolutionSpecies();
                String evoArt = MonsterFactory.getEvolvedSprite(evoSpecies);
                String evoLog = m.evolve(evoSpecies, evoArt);
                System.out.println(evoLog);
            }
        }
        if (!any) {
            System.out.println("No JodiMon are currently ready to evolve.");
        }
    }

    private void viewInventory() {
        System.out.println("\n================= BAG INVENTORY =================");
        List<Item> bag = player.getInventory();
        if (bag.isEmpty()) {
            System.out.println("Your bag is empty.");
        } else {
            for (int i = 0; i < bag.size(); i++) {
                Item item = bag.get(i);
                System.out.printf("  [%d] %-16s (Qty: %2d) - %s\n", 
                        (i + 1), item.getName(), item.getQuantity(), item.getDescription());
            }
        }
        System.out.println("=================================================");
    }

    private void printTypeGuide() {
        System.out.println("\n=============== ELEMENTAL EFFECTIVENESS GUIDE ===============");
        System.out.println("  ELEMENT    STRONG AGAINST (x2.0)     WEAK AGAINST (x0.5)");
        System.out.println("  -------------------------------------------------------------");
        System.out.println("  FIRE       Grass                     Water, Fire, Earth");
        System.out.println("  WATER      Fire, Earth               Water, Grass");
        System.out.println("  GRASS      Water, Earth              Fire, Grass");
        System.out.println("  ELECTRIC   Water                     Electric, Grass (x0 vs Earth)");
        System.out.println("  EARTH      Fire, Electric            Grass");
        System.out.println("  NORMAL     Neutral against all       Neutral against all");
        System.out.println("=============================================================");
        System.out.println("  * Same Type Attack Bonus (STAB): Moves matching monster type get +50% power!");
        System.out.println("  * Critical Hits: 10% base chance (25% on high crit moves) for +50% damage!");
        System.out.println("=============================================================\n");
    }

    public static void runAutomatedDemo() {
        System.out.println("\n>>> STARTING AUTOMATED BATTLE DEMONSTRATION <<<");
        Trainer demoPlayer = TrainerFactory.createPlayer("John", MonsterFactory.createStarterFire(6));
        demoPlayer.addMonster(MonsterFactory.createStarterWater(6));

        Trainer demoOpponent = TrainerFactory.createLeaderTerra();

        BattleEngine engine = new BattleEngine(demoPlayer, demoOpponent, null, true);
        engine.runBattle();
        System.out.println(">>> AUTOMATED DEMONSTRATION COMPLETED <<<\n");
    }
}
