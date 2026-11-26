package com.monsterbattle.ui;

/**
 * Rich ASCII visuals, battle title banners, monster sprites, and brackets.
 */
public class AsciiArt {

    public static final String TITLE_BANNER = 
        "  ██████╗  ██████╗ ██╗  ██╗███████╗███╗   ███╗ ██████╗ ███╗   ██╗\n" +
        "  ██╔══██╗██╔═══██╗██║ ██╔╝██╔════╝████╗ ████║██╔═══██╗████╗  ██║\n" +
        "  ██████╔╝██║   ██║█████╔╝ █████╗  ██╔████╔██║██║   ██║██╔██╗ ██║\n" +
        "  ██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  ██║╚██╔╝██║██║   ██║██║╚██╗██║\n" +
        "  ██║     ╚██████╔╝██║  ██╗███████╗██║ ╚═╝ ██║╚██████╔╝██║ ╚████║\n" +
        "  ╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝\n" +
        "        == TURN-BASED MONSTER ARENA CHAMPIONSHIP ==\n";

    public static final String VICTORY_BANNER =
        "  __      _______ _____ _______ ____  _______     __\n" +
        "  \\ \\    / /_   _/ ____|__   __/ __ \\|  __ \\ \\   / /\n" +
        "   \\ \\  / /  | || |       | | | |  | | |__) \\ \\_/ / \n" +
        "    \\ \\/ /   | || |       | | | |  | |  _  / \\   /  \n" +
        "     \\  /   _| || |____   | | | |__| | | \\ \\  | |   \n" +
        "      \\/   |_____\\_____|  |_|  \\____/|_|  \\_\\ |_|   \n";

    public static final String EVOLUTION_BANNER =
        "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
        "  * * *       *** MONSTER EVOLUTION CEREMONY ***       * * *\n" +
        "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";

    // --- MONSTER SPRITES (Clean ASCII) ---

    public static final String IGNISPARK_SPRITE =
        "    /\\_/\\  [FIRE]\n" +
        "   ( o.o )  / \n" +
        "    > ^ < -'  ";

    public static final String PYRODRAKE_SPRITE =
        "   <\\_  _/>   [FIRE/DRG]\n" +
        "    (o\\/o)    //===o\n" +
        "   /| == |\\  //     \n" +
        "    /_/\\_\\  /'      ";

    public static final String AQUASHELL_SPRITE =
        "     _.~._  [WATER]\n" +
        "   =( 'o' )=  \n" +
        "    /(___)\\   \n" +
        "   (_/   \\_)  ";

    public static final String HYDROCLAW_SPRITE =
        "     /\\___/\\  [WATER]\n" +
        "   >-( 'o' )-<  ~~\n" +
        "    / [===] \\  (claw)\n" +
        "   (__(   )__)       ";

    public static final String LEAFOX_SPRITE =
        "     \\_._/  [GRASS]\n" +
        "     (>.<)  //\n" +
        "     /(v)\\ // \n" +
        "     \"\" \"\"    ";

    public static final String THORNSTALKER_SPRITE =
        "     \\\\_//   [GRASS]\n" +
        "    -(o_o)-  <===\n" +
        "    /[|||]\\   // \n" +
        "   // ||| \\\\-''  ";

    public static final String VOLTROD_SPRITE =
        "     (\\__/)  [ELEC]\n" +
        "     ( o_o)  --z\n" +
        "    c(\")(\")  zz ";

    public static final String SPARKSTRIKE_SPRITE =
        "     /\\/\\    [ELEC]\n" +
        "    (z_z)  ==Z===!\n" +
        "    /|zz|\\   //\n" +
        "   \"\"   \"\"  ''  ";

    public static final String BOULDERBUG_SPRITE =
        "     _(')_   [EARTH]\n" +
        "    / o_o \\  \n" +
        "   |[=====]|\n" +
        "    /\"\" \"\"\\ ";

    public static final String STONEMAUL_SPRITE =
        "    [=====]  [EARTH]\n" +
        "    |(o_o)|  [MAUL]\n" +
        "   /[=====]\\=={[]}\n" +
        "   \"\"\"   \"\"\"      ";

    /**
     * Generates a graphical ASCII health bar that renders cleanly on all Windows terminals.
     * Example: [##########-----] 80/100 HP
     */
    public static String getHpBar(int current, int max, int barLength) {
        if (max <= 0) max = 1;
        double ratio = Math.max(0.0, Math.min(1.0, (double) current / max));
        int filledBlocks = (int) Math.round(ratio * barLength);
        int emptyBlocks = barLength - filledBlocks;

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < filledBlocks; i++) {
            sb.append("#");
        }
        for (int i = 0; i < emptyBlocks; i++) {
            sb.append("-");
        }
        sb.append(String.format("] %3d/%-3d HP", current, max));
        return sb.toString();
    }

    /**
     * Generates an EXP progress bar.
     */
    public static String getExpBar(int currentExp, int maxExp, int barLength) {
        if (maxExp <= 0) maxExp = 1;
        double ratio = Math.max(0.0, Math.min(1.0, (double) currentExp / maxExp));
        int filledBlocks = (int) Math.round(ratio * barLength);
        int emptyBlocks = barLength - filledBlocks;

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < filledBlocks; i++) {
            sb.append("=");
        }
        for (int i = 0; i < emptyBlocks; i++) {
            sb.append(".");
        }
        sb.append(String.format("] %d/%d EXP", currentExp, maxExp));
        return sb.toString();
    }

    public static String renderTournamentBracket(int currentRound) {
        String r1Check = (currentRound > 1) ? "[DONE]" : (currentRound == 1 ? "-> LIVE" : "      ");
        String r2Check = (currentRound > 2) ? "[DONE]" : (currentRound == 2 ? "-> LIVE" : "      ");
        String r3Check = (currentRound > 3) ? "[DONE]" : (currentRound == 3 ? "-> LIVE" : "      ");
        String r4Check = (currentRound > 4) ? "[DONE]" : (currentRound == 4 ? "-> LIVE" : "      ");

        return 
            "======================= TOURNAMENT BRACKET =======================\n" +
            "  Round 1: Quarterfinals | Scout Timmy        " + r1Check + "\n" +
            "            \\___\n" +
            "                Round 2: Semifinals | Leader Terra  " + r2Check + "\n" +
            "                 \\___\n" +
            "                     Round 3: Finals | Ace Marina    " + r3Check + "\n" +
            "                      \\___\n" +
            "                          Grand Final: CHAMPION CYNTHIA " + r4Check + "\n" +
            "==================================================================\n";
    }
}
