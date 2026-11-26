package com.monsterbattle.ui;

import com.monsterbattle.model.ElementType;

/**
 * ANSI Color utility providing retro Game Boy / Pokémon color palette
 * for battle scenes, elemental type badges, dynamic HP bars, and sprites.
 */
public class Ansi {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";

    // Standard Colors
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // High Intensity Colors
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_MAGENTA = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static final String GRAY = "\u001B[90m";

    public static boolean ENABLED = true;

    public static String color(String text, String colorCode) {
        if (!ENABLED) return text;
        return colorCode + text + RESET;
    }

    public static String bold(String text) {
        if (!ENABLED) return text;
        return BOLD + text + RESET;
    }

    /**
     * Returns the color code associated with an elemental type.
     */
    public static String getTypeColor(ElementType type) {
        if (type == null) return WHITE;
        switch (type) {
            case FIRE: return BRIGHT_RED;
            case WATER: return BRIGHT_CYAN;
            case GRASS: return BRIGHT_GREEN;
            case ELECTRIC: return BRIGHT_YELLOW;
            case EARTH: return YELLOW;
            case NORMAL:
            default: return BRIGHT_WHITE;
        }
    }

    /**
     * Formats a colored elemental type badge (e.g., [FIRE], [WATER]).
     */
    public static String getTypeBadge(ElementType type) {
        if (!ENABLED) return type.getBadge();
        String c = getTypeColor(type);
        return BOLD + c + type.getBadge() + RESET;
    }

    /**
     * Generates a dynamic colored HP bar:
     * Green when > 50%, Yellow when between 20% and 50%, Red when <= 20%.
     */
    public static String getHpBar(int current, int max, int barLength) {
        if (max <= 0) max = 1;
        double ratio = Math.max(0.0, Math.min(1.0, (double) current / max));
        int filledBlocks = (int) Math.round(ratio * barLength);
        int emptyBlocks = barLength - filledBlocks;

        String barColor;
        if (ratio > 0.50) {
            barColor = BRIGHT_GREEN;
        } else if (ratio > 0.20) {
            barColor = BRIGHT_YELLOW;
        } else {
            barColor = BRIGHT_RED;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (ENABLED) sb.append(barColor);
        for (int i = 0; i < filledBlocks; i++) {
            sb.append("#");
        }
        if (ENABLED) sb.append(GRAY);
        for (int i = 0; i < emptyBlocks; i++) {
            sb.append("-");
        }
        if (ENABLED) sb.append(RESET);
        sb.append(String.format("] %3d/%-3d HP", current, max));
        return sb.toString();
    }

    /**
     * Generates an EXP progress bar with cyan coloring.
     */
    public static String getExpBar(int currentExp, int maxExp, int barLength) {
        if (maxExp <= 0) maxExp = 1;
        double ratio = Math.max(0.0, Math.min(1.0, (double) currentExp / maxExp));
        int filledBlocks = (int) Math.round(ratio * barLength);
        int emptyBlocks = barLength - filledBlocks;

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (ENABLED) sb.append(BRIGHT_CYAN);
        for (int i = 0; i < filledBlocks; i++) {
            sb.append("=");
        }
        if (ENABLED) sb.append(GRAY);
        for (int i = 0; i < emptyBlocks; i++) {
            sb.append(".");
        }
        if (ENABLED) sb.append(RESET);
        sb.append(String.format("] %d/%d EXP", currentExp, maxExp));
        return sb.toString();
    }

    /**
     * Enhances an ASCII sprite with elemental coloration.
     */
    public static String colorSprite(String sprite, ElementType type) {
        if (!ENABLED || sprite == null) return sprite;
        String color = getTypeColor(type);
        StringBuilder sb = new StringBuilder();
        for (String line : sprite.split("\n")) {
            sb.append(color).append(line).append(RESET).append("\n");
        }
        return sb.toString();
    }
}
