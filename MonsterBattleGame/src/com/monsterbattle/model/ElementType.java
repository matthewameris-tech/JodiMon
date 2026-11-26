package com.monsterbattle.model;

/**
 * Represents the elemental affinity of monsters and moves.
 * Includes type advantage and weakness multipliers.
 */
public enum ElementType {
    FIRE("Fire", "[FIRE]"),
    WATER("Water", "[WATER]"),
    GRASS("Grass", "[GRASS]"),
    ELECTRIC("Electric", "[ELEC]"),
    EARTH("Earth", "[EARTH]"),
    NORMAL("Normal", "[NORM]");

    private final String displayName;
    private final String badge;

    ElementType(String displayName, String badge) {
        this.displayName = displayName;
        this.badge = badge;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBadge() {
        return badge;
    }

    /**
     * Calculates the damage multiplier for an attack of this type
     * against a defender of the target type.
     *
     * @param defender The defending monster's elemental type
     * @return 2.0 for Super Effective, 0.5 for Not Very Effective, 0.0 for Immune, 1.0 for Neutral
     */
    public double getMultiplierAgainst(ElementType defender) {
        if (defender == null) {
            return 1.0;
        }

        switch (this) {
            case FIRE:
                if (defender == GRASS) return 2.0;
                if (defender == WATER || defender == FIRE || defender == EARTH) return 0.5;
                return 1.0;

            case WATER:
                if (defender == FIRE || defender == EARTH) return 2.0;
                if (defender == WATER || defender == GRASS) return 0.5;
                return 1.0;

            case GRASS:
                if (defender == WATER || defender == EARTH) return 2.0;
                if (defender == FIRE || defender == GRASS) return 0.5;
                return 1.0;

            case ELECTRIC:
                if (defender == WATER) return 2.0;
                if (defender == EARTH) return 0.0; // Ground/Earth grounds electricity
                if (defender == ELECTRIC || defender == GRASS) return 0.5;
                return 1.0;

            case EARTH:
                if (defender == FIRE || defender == ELECTRIC) return 2.0;
                if (defender == GRASS) return 0.5;
                return 1.0;

            case NORMAL:
            default:
                return 1.0;
        }
    }

    public String getMatchupDescription(ElementType defender) {
        double mult = getMultiplierAgainst(defender);
        if (mult > 1.0) {
            return "Super effective! (x" + mult + ")";
        } else if (mult == 0.0) {
            return "No effect! (x0.0)";
        } else if (mult < 1.0) {
            return "Not very effective... (x" + mult + ")";
        } else {
            return "Effective (x1.0)";
        }
    }
}
