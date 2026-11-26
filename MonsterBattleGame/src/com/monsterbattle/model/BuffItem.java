package com.monsterbattle.model;

/**
 * Usable battle enhancer item that raises monster battle stages.
 * Extends Item.
 */
public class BuffItem extends Item {
    public enum StatToBuff {
        ATTACK,
        DEFENSE,
        SPEED
    }

    private final StatToBuff stat;
    private final int stages;

    public BuffItem(String name, String description, int quantity, StatToBuff stat, int stages) {
        super(name, description, quantity);
        this.stat = stat;
        this.stages = stages;
    }

    @Override
    public boolean canUseOn(Monster target) {
        return target != null && !target.isFainted();
    }

    @Override
    public String use(Monster target) {
        if (!consumeOne()) {
            return "No " + name + " remaining!";
        }

        switch (stat) {
            case ATTACK:
                target.boostAttack(stages);
                return String.format("Used %s! %s's Attack surged!", name, target.getName());
            case DEFENSE:
                target.boostDefense(stages);
                return String.format("Used %s! %s's Defense hardened!", name, target.getName());
            case SPEED:
                target.boostSpeed(stages);
                return String.format("Used %s! %s's Speed quickened!", name, target.getName());
            default:
                return "Used " + name + "!";
        }
    }
}
