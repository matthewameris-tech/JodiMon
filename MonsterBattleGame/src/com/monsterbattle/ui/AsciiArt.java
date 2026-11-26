package com.monsterbattle.ui;

/**
 * Rich ASCII visuals, battle title banners, monster sprites, and brackets.
 */
public class AsciiArt {

    public static final String TITLE_BANNER = 
        "       _  ____  _____  _____ __  __  ____  _   _\n" +
        "      | |/ __ \\|  __ \\|_   _|  \\/  |/ __ \\| \\ | |\n" +
        "      | | |  | | |  | | | | | \\  / | |  | |  \\| |\n" +
        "  _   | | |  | | |  | | | | | |\\/| | |  | | . ` |\n" +
        " | |__| | |__| | |__| |_| |_| |  | | |__| | |\\  |\n" +
        "  \\____/ \\____/|_____/|_____|_|  |_|\\____/|_| \\_|\n" +
        "          == TURN-BASED JODIMON BATTLE ARENA ==\n";

    public static final String VICTORY_BANNER =
        "  __      _______ _____ _______ ____  _______     __\n" +
        "  \\ \\    / /_   _/ ____|__   __/ __ \\|  __ \\ \\   / /\n" +
        "   \\ \\  / /  | || |       | | | |  | | |__) \\ \\_/ / \n" +
        "    \\ \\/ /   | || |       | | | |  | |  _  / \\   /  \n" +
        "     \\  /   _| || |____   | | | |__| | | \\ \\  | |   \n" +
        "      \\/   |_____\\_____|  |_|  \\____/|_|  \\_\\ |_|   \n";

    public static final String EVOLUTION_BANNER =
        "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
        "  * * *       *** JODIMON EVOLUTION CEREMONY ***       * * *\n" +
        "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";

    // --- JODIMON DETAILED ASCII SPRITES ---

    public static final String IGNISPARK_SPRITE =
        "            (  .      )\n" +
        "          )           (        \n" +
        "         (   /\\---/\\   )       \n" +
        "          ) (  o.o  ) (    (~) \n" +
        "         (   \\  ^  /   ) .'  ) \n" +
        "              /`---'\\   /  .'  \n" +
        "             | |   | | /  /    \n" +
        "            (_|  _  |_/  /     \n" +
        "            (_/ (_) \\_)_/      ";

    public static final String PYRODRAKE_SPRITE =
        "           /\\_             _/\\\n" +
        "          /   \\           /   \\\n" +
        "         |  /\\ \\         / /\\  |\n" +
        "         | |  \\ \\_______/ /  | |\n" +
        "         \\ \\   \\ (o   o) /   / /    (~~)\n" +
        "          \\ \\_  \\ = v = /  _/ /   .'  /\n" +
        "           \\  \\  |     |  /  /   /  .' \n" +
        "            \\  \\_/ / \\ \\_/  /   /  /   \n" +
        "             \\____/   \\____/   /  /    \n" +
        "              /   |   |   \\  .'  /     \n" +
        "             (___/\\___/\\___)'._.'      ";

    public static final String AQUASHELL_SPRITE =
        "              .-----.\n" +
        "            .' -   - '.    (~)\n" +
        "           /  (o) (o)  \\  ~ .\n" +
        "          |   .----.   |   ~\n" +
        "          '--'|====|'--'\n" +
        "         /'.'.|====|.' .'\\\n" +
        "        ( (_/ '----' \\_) )\n" +
        "         '._          _.'\n" +
        "            `\"\"\"\"\"\"\"\"`   ";

    public static final String HYDROCLAW_SPRITE =
        "               /\\_/\\\n" +
        "             _/ ___ \\_\n" +
        "            / /'---'\\ \\\n" +
        "      |\\_  | ( -o-o- ) |  _/|\n" +
        "     /   \\_|  \\  =  /  |_/   \\\n" +
        "    |  /\\  \\   `---'   /  /\\  |\n" +
        "    | (  )  | [=====] |  (  ) |\n" +
        "    \\  \\/  /  |[===]|  \\  \\/  /\n" +
        "     `.__.'   |     |   `.__.'\n" +
        "             /|     |\\\n" +
        "            (_|_____|_)       ";

    public static final String LEAFOX_SPRITE =
        "              /\\     /\\    __\n" +
        "             /  \\___/  \\  (  )\n" +
        "            /   '   '   \\  ||\n" +
        "           |  ( o   o )  | //\n" +
        "           |   \\  ^  /   |//   (v)\n" +
        "            \\   `---'   /     //\n" +
        "            /`-.     .-'\\    //\n" +
        "           / /  `---'  \\ \\  //\n" +
        "          (_/           \\_)v  ";

    public static final String THORNSTALKER_SPRITE =
        "               /\\     /\\\n" +
        "              /  \\___/  \\\n" +
        "             /  ^     ^  \\\n" +
        "      |\\_   / ( -o- -o- ) \\   _/|\n" +
        "      \\  \\_/   \\   v   /   \\_/  /\n" +
        "       \\       /`-----'\\       /\n" +
        "        \\___  / |=====| \\  ___/\n" +
        "            \\/  |=====|  \\/    (v)\n" +
        "            /   |     |   \\   //\n" +
        "           /    /     \\    \\ //\n" +
        "          (____/       \\____)v  ";

    public static final String VOLTROD_SPRITE =
        "             \\Z/     \\Z/\n" +
        "             /  \\___/  \\\n" +
        "            /  .     .  \\\n" +
        "           |  ( o   o )  |\n" +
        "           |   (  -  )   |     [Z]\n" +
        "           (  /'-----'\\  )   _//\n" +
        "            \\ | (===) | /   //_\n" +
        "           /  \\_______/  \\    //\n" +
        "          (___/       \\___)  /Z/ ";

    public static final String SPARKSTRIKE_SPRITE =
        "             /Z\\     /Z\\\n" +
        "            __/  \\___/  \\__\n" +
        "           /  \\  '   '  /  \\\n" +
        "      /Z/=/    \\( o   o )/    \\=/Z/\n" +
        "         \\___  /\\  =  /\\  ___/\n" +
        "             \\/  \\___/  \\/     /Z/\n" +
        "             / [=======] \\    //_\n" +
        "            /  |[z===z]|  \\  _//\n" +
        "           /   |       |   \\   //\n" +
        "          (____/       \\____) /Z/  ";

    public static final String BOULDERBUG_SPRITE =
        "                  /\\\n" +
        "                 /__\\\n" +
        "                /    \\\n" +
        "              .'      '.\n" +
        "            .'  (o  o)  '.\n" +
        "           /  .--------.  \\\n" +
        "          |  / [======] \\  |\n" +
        "          | |  |======|  | |\n" +
        "          |  \\ [======] /  |\n" +
        "           \\  '--------'  /\n" +
        "           (_/          \\_) ";

    public static final String STONEMAUL_SPRITE =
        "              .--------.\n" +
        "             /  ______  \\\n" +
        "            |  / [**] \\  |\n" +
        "      .---. |  \\______/  | .---.\n" +
        "     /     \\|   [====]   |/     \\\n" +
        "    | [===] |   |====|   | [===] |\n" +
        "    | [===] |   |====|   | [===] |\n" +
        "     \\     /|   '----'   |\\     /\n" +
        "      '---'  \\__________/  '---'\n" +
        "             /|        |\\\n" +
        "            (_|________|_)      ";

    /**
     * Generates a graphical ASCII health bar that renders cleanly on all Windows terminals.
     * Example: [##########-----] 80/100 HP
     */
    public static String getHpBar(int current, int max, int barLength) {
        return Ansi.getHpBar(current, max, barLength);
    }

    /**
     * Generates an EXP progress bar.
     */
    public static String getExpBar(int currentExp, int maxExp, int barLength) {
        return Ansi.getExpBar(currentExp, maxExp, barLength);
    }

    public static String renderTournamentBracket(int currentRound) {
        String r1Check = (currentRound > 1) ? "[DONE]" : (currentRound == 1 ? "-> LIVE" : "      ");
        String r2Check = (currentRound > 2) ? "[DONE]" : (currentRound == 2 ? "-> LIVE" : "      ");
        String r3Check = (currentRound > 3) ? "[DONE]" : (currentRound == 3 ? "-> LIVE" : "      ");
        String r4Check = (currentRound > 4) ? "[DONE]" : (currentRound == 4 ? "-> LIVE" : "      ");

        return 
            "===================== GRAND JODIMON LEAGUE BRACKET =====================\n" +
            "  Round 1: Quarterfinals | Scout Timmy        " + r1Check + "\n" +
            "            \\___\n" +
            "                Round 2: Semifinals | Leader Terra  " + r2Check + "\n" +
            "                 \\___\n" +
            "                     Round 3: Finals | Ace Marina    " + r3Check + "\n" +
            "                      \\___\n" +
            "                          Grand Final: CHAMPION CYNTHIA " + r4Check + "\n" +
            "========================================================================\n";
    }
}
