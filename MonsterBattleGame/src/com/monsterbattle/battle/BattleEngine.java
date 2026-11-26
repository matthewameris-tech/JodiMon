package com.monsterbattle.battle;

import com.monsterbattle.data.MonsterFactory;
import com.monsterbattle.model.*;
import com.monsterbattle.ui.AsciiArt;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Core battle engine managing turn progression, speed priority, action execution,
 * experience rewards, level ups, and monster evolutions.
 */
public class BattleEngine {
    private final Trainer player;
    private final Trainer opponent;
    private final Random rng;
    private final Scanner scanner;
    private final boolean automated; // If true, runs automatically (for demos/tests)

    public BattleEngine(Trainer player, Trainer opponent, Scanner scanner) {
        this(player, opponent, scanner, false);
    }

    public BattleEngine(Trainer player, Trainer opponent, Scanner scanner, boolean automated) {
        this.player = player;
        this.opponent = opponent;
        this.scanner = scanner;
        this.automated = automated;
        this.rng = new Random();
    }

    /**
     * Starts and executes the battle until one trainer runs out of monsters.
     * @return true if player won, false if player lost
     */
    public boolean runBattle() {
        printBattleHeader();

        while (player.hasUsableMonsters() && opponent.hasUsableMonsters()) {
            Monster playerMonster = player.getActiveMonster();
            Monster oppMonster = opponent.getActiveMonster();

            renderBattlefield(playerMonster, oppMonster);

            // Determine Player Action
            Trainer.AiDecision playerDecision = determinePlayerAction(playerMonster, oppMonster);
            if (playerDecision == null) {
                // Player cancelled or switched monster already handled in turn
                continue;
            }

            // Determine Opponent Action (AI)
            Trainer.AiDecision oppDecision = opponent.decideAction(playerMonster, rng);

            // Execute Turn
            executeTurn(playerDecision, oppDecision);

            // Check post-turn status
            if (!player.hasUsableMonsters() || !opponent.hasUsableMonsters()) {
                break;
            }
        }

        // Cleanup stat stages after battle ends
        player.getActiveMonster().resetBattleStages();
        if (opponent.getActiveMonster() != null) {
            opponent.getActiveMonster().resetBattleStages();
        }

        boolean playerWon = player.hasUsableMonsters();
        printBattleOutcome(playerWon);
        return playerWon;
    }

    private void printBattleHeader() {
        System.out.println("\n=======================================================");
        System.out.println("  " + opponent.getTitle() + " " + opponent.getName() + " wants to battle!");
        System.out.println("  " + opponent.getName() + " sent out " + opponent.getActiveMonster().getName() + "!");
        System.out.println("  Go! " + player.getName() + " sent out " + player.getActiveMonster().getName() + "!");
        System.out.println("=======================================================\n");
        pause(1000);
    }

    private void renderBattlefield(Monster playerMonster, Monster oppMonster) {
        System.out.println("----------------------------------------------------------------------");
        System.out.println(" [OPPONENT] " + opponent.getName() + "'s " + oppMonster.getName() + 
                           " Lv." + oppMonster.getLevel() + " " + oppMonster.getType().getBadge());
        System.out.println(" HP: " + AsciiArt.getHpBar(oppMonster.getCurrentHp(), oppMonster.getMaxHp(), 15));
        System.out.println(oppMonster.getAsciiArt());
        System.out.println();
        System.out.println("                                [YOU] " + playerMonster.getName() + 
                           " Lv." + playerMonster.getLevel() + " " + playerMonster.getType().getBadge());
        System.out.println("                                HP:  " + 
                           AsciiArt.getHpBar(playerMonster.getCurrentHp(), playerMonster.getMaxHp(), 15));
        System.out.println("                                EXP: " + 
                           AsciiArt.getExpBar(playerMonster.getExp(), playerMonster.getExpToNextLevel(), 12));
        System.out.println("                                Passive: [" + playerMonster.getPassiveAbilityName() + "]");
        System.out.println("----------------------------------------------------------------------");
    }

    private Trainer.AiDecision determinePlayerAction(Monster playerMonster, Monster oppMonster) {
        if (automated) {
            // Automated testing / Demo logic
            return player.decideAction(oppMonster, rng);
        }

        while (true) {
            System.out.println("\nWhat will " + playerMonster.getName() + " do?");
            System.out.println("  [1] Fight (Attacks & Moves)");
            System.out.println("  [2] Bag (Use Item)");
            System.out.println("  [3] Switch Monster (" + player.getUsableCount() + " available)");
            System.out.println("  [4] Inspect Opponent Weaknesses");
            System.out.print("Select action (1-4): ");

            String input = readInput();
            if ("1".equals(input)) {
                int moveIdx = selectMove(playerMonster);
                if (moveIdx != -1) {
                    return new Trainer.AiDecision(Trainer.ActionType.ATTACK, moveIdx, null);
                }
            } else if ("2".equals(input)) {
                Item item = selectItem();
                if (item != null) {
                    return new Trainer.AiDecision(Trainer.ActionType.USE_ITEM, -1, item);
                }
            } else if ("3".equals(input)) {
                if (selectSwitchMonster()) {
                    // Switching consumes the player turn
                    return new Trainer.AiDecision(Trainer.ActionType.SWITCH, -1, null);
                }
            } else if ("4".equals(input)) {
                inspectMatchup(playerMonster, oppMonster);
            } else {
                System.out.println("Invalid selection. Please choose 1, 2, 3, or 4.");
            }
        }
    }

    private int selectMove(Monster monster) {
        List<Move> moves = monster.getMoves();
        System.out.println("\n--- SELECT A MOVE ---");
        for (int i = 0; i < moves.size(); i++) {
            Move m = moves.get(i);
            System.out.printf("  [%d] %-15s | Type: %-8s | PP: %2d/%2d | Pow: %-3d | %s\n",
                    (i + 1), m.getName(), m.getType().getDisplayName(), 
                    m.getCurrentPp(), m.getMaxPp(), m.getPower(), m.getDescription());
        }
        System.out.println("  [0] Back to Action Menu");
        System.out.print("Choose move (0-" + moves.size() + "): ");

        String in = readInput();
        try {
            int val = Integer.parseInt(in);
            if (val == 0) return -1;
            if (val >= 1 && val <= moves.size()) {
                Move chosen = moves.get(val - 1);
                if (!chosen.canUse()) {
                    System.out.println("No PP left for this move!");
                    return -1;
                }
                return val - 1;
            }
        } catch (NumberFormatException ignored) {}
        System.out.println("Invalid move number.");
        return -1;
    }

    private Item selectItem() {
        List<Item> inventory = player.getInventory();
        System.out.println("\n--- BAG INVENTORY ---");
        int count = 0;
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            if (item.getQuantity() > 0) {
                count++;
                System.out.printf("  [%d] %-15s (Qty: %2d) - %s\n", (i + 1), item.getName(), item.getQuantity(), item.getDescription());
            }
        }
        if (count == 0) {
            System.out.println("Your bag is empty!");
            return null;
        }
        System.out.println("  [0] Back to Action Menu");
        System.out.print("Select item to use (0-" + inventory.size() + "): ");

        String in = readInput();
        try {
            int val = Integer.parseInt(in);
            if (val == 0) return null;
            if (val >= 1 && val <= inventory.size()) {
                Item item = inventory.get(val - 1);
                if (item.getQuantity() > 0) {
                    return item;
                }
            }
        } catch (NumberFormatException ignored) {}
        System.out.println("Invalid item.");
        return null;
    }

    private boolean selectSwitchMonster() {
        List<Monster> team = player.getTeam();
        if (team.size() <= 1) {
            System.out.println("No other monsters in your team!");
            return false;
        }

        System.out.println("\n--- TEAM ROSTER ---");
        for (int i = 0; i < team.size(); i++) {
            Monster m = team.get(i);
            String status = m.isFainted() ? "[FAINTED]" : (i == player.getActiveIndex() ? "[ACTIVE]" : "[READY]");
            System.out.printf("  [%d] %-12s Lv.%-2d %-7s %s (HP: %d/%d)\n",
                    (i + 1), m.getName(), m.getLevel(), m.getType().getBadge(), status, m.getCurrentHp(), m.getMaxHp());
        }
        System.out.println("  [0] Back to Action Menu");
        System.out.print("Select monster to switch to: ");

        String in = readInput();
        try {
            int val = Integer.parseInt(in);
            if (val == 0) return false;
            int targetIdx = val - 1;
            if (targetIdx == player.getActiveIndex()) {
                System.out.println(team.get(targetIdx).getName() + " is already active!");
                return false;
            }
            if (player.setActiveIndex(targetIdx)) {
                System.out.println("Come back! Go, " + player.getActiveMonster().getName() + "!");
                return true;
            } else {
                System.out.println("Cannot switch to that monster (fainted or invalid)!");
            }
        } catch (NumberFormatException ignored) {}
        return false;
    }

    private void inspectMatchup(Monster playerMonster, Monster oppMonster) {
        System.out.println("\n=== TACTICAL ANALYSIS ===");
        System.out.println("Your Monster: " + playerMonster.getName() + " (" + playerMonster.getType().getDisplayName() + ")");
        System.out.println("Opponent:     " + oppMonster.getName() + " (" + oppMonster.getType().getDisplayName() + ")");
        System.out.println("Matchup vs Opponent: " + playerMonster.getType().getMatchupDescription(oppMonster.getType()));
        System.out.println("Opponent vs You:     " + oppMonster.getType().getMatchupDescription(playerMonster.getType()));
        System.out.println("Ability: " + playerMonster.getPassiveAbilityName() + " - " + playerMonster.getPassiveAbilityDescription());
        System.out.println("=========================\n");
    }

    private void executeTurn(Trainer.AiDecision playerDecision, Trainer.AiDecision oppDecision) {
        System.out.println("\n----------------- TURN IN PROGRESS -----------------");

        // 1. Handle Item actions or Monster Switches (Highest Priority)
        if (playerDecision.action == Trainer.ActionType.USE_ITEM) {
            String itemLog = playerDecision.itemToUse.use(player.getActiveMonster());
            System.out.println("[YOU] " + itemLog);
            pause(800);
        }

        if (oppDecision.action == Trainer.ActionType.USE_ITEM) {
            String itemLog = oppDecision.itemToUse.use(opponent.getActiveMonster());
            System.out.println("[" + opponent.getName() + "] " + itemLog);
            pause(800);
        }

        // 2. Resolve Attacks based on Speed Priority
        boolean playerAttacks = (playerDecision.action == Trainer.ActionType.ATTACK);
        boolean oppAttacks = (oppDecision.action == Trainer.ActionType.ATTACK);

        if (playerAttacks && oppAttacks) {
            Monster pM = player.getActiveMonster();
            Monster oM = opponent.getActiveMonster();

            boolean playerFirst = pM.getEffectiveSpeed() > oM.getEffectiveSpeed();
            if (pM.getEffectiveSpeed() == oM.getEffectiveSpeed()) {
                playerFirst = rng.nextBoolean(); // Speed tie coinflip
            }

            if (playerFirst) {
                resolveSingleAttack(player, opponent, playerDecision.moveIndex);
                if (!opponent.getActiveMonster().isFainted()) {
                    resolveSingleAttack(opponent, player, oppDecision.moveIndex);
                }
            } else {
                resolveSingleAttack(opponent, player, oppDecision.moveIndex);
                if (!player.getActiveMonster().isFainted()) {
                    resolveSingleAttack(player, opponent, playerDecision.moveIndex);
                }
            }
        } else if (playerAttacks) {
            resolveSingleAttack(player, opponent, playerDecision.moveIndex);
        } else if (oppAttacks) {
            resolveSingleAttack(opponent, player, oppDecision.moveIndex);
        }

        // 3. Post-attack faint resolution
        handleFaints();
    }

    private void resolveSingleAttack(Trainer attackerTrainer, Trainer defenderTrainer, int moveIdx) {
        Monster attacker = attackerTrainer.getActiveMonster();
        Monster defender = defenderTrainer.getActiveMonster();

        if (attacker.isFainted() || defender.isFainted()) {
            return;
        }

        List<Move> moves = attacker.getMoves();
        if (moveIdx < 0 || moveIdx >= moves.size()) {
            moveIdx = 0;
        }
        Move move = moves.get(moveIdx);

        String combatText = move.execute(attacker, defender, rng);
        System.out.print(combatText);
        pause(800);
    }

    private void handleFaints() {
        // Check opponent active monster
        if (opponent.getActiveMonster().isFainted()) {
            System.out.println("\n[KNOCKOUT] Opponent " + opponent.getActiveMonster().getName() + " was knocked out!");
            
            // Experience award
            int expReward = 30 + (opponent.getActiveMonster().getLevel() * 20);
            Monster pM = player.getActiveMonster();
            String expLog = pM.gainExp(expReward);
            System.out.println(expLog);

            // Evolution Check!
            if (pM.canEvolve()) {
                System.out.println(AsciiArt.EVOLUTION_BANNER);
                String evoSpecies = pM.getNextEvolutionSpecies();
                String evoArt = MonsterFactory.getEvolvedSprite(evoSpecies);
                String evoLog = pM.evolve(evoSpecies, evoArt);
                System.out.println(evoLog);
                pause(1200);
            }

            if (opponent.hasUsableMonsters()) {
                opponent.autoSwitchToNextUsable();
                System.out.println("  " + opponent.getName() + " sent out " + opponent.getActiveMonster().getName() + "!\n");
                pause(800);
            }
        }

        // Check player active monster
        if (player.getActiveMonster().isFainted()) {
            System.out.println("\n[FAINTED] Your " + player.getActiveMonster().getName() + " fainted!");
            if (player.hasUsableMonsters()) {
                if (automated) {
                    player.autoSwitchToNextUsable();
                    System.out.println("Go! " + player.getActiveMonster().getName() + "!");
                } else {
                    System.out.println("Choose your next monster:");
                    selectSwitchMonster();
                }
                pause(800);
            }
        }
    }

    private void printBattleOutcome(boolean playerWon) {
        System.out.println("\n=======================================================");
        if (playerWon) {
            System.out.println(AsciiArt.VICTORY_BANNER);
            System.out.println(" Congratulations! You defeated " + opponent.getTitle() + " " + opponent.getName() + "!");
        } else {
            System.out.println(" [DEFEAT] All your monsters have fainted! You lost the battle...");
        }
        System.out.println("=======================================================\n");
        pause(1000);
    }

    private String readInput() {
        if (scanner != null && scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return "1";
    }

    private void pause(long millis) {
        if (automated) return;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
