package com.monsterbattle.model;

/**
 * Item that revives fainted monsters back to battle.
 * Extends Item.
 */
public class ReviveItem extends Item {
    private final double revivePercent; // e.g. 0.5 for 50%

    public ReviveItem(String name, String description, int quantity, double revivePercent) {
        super(name, description, quantity);
        this.revivePercent = revivePercent;
    }

    @Override
    public boolean canUseOn(Monster target) {
        return target != null && target.isFainted();
    }

    @Override
    public String use(Monster target) {
        if (!consumeOne()) {
            return "No " + name + " remaining!";
        }

        int restoreHp = Math.max(1, (int) Math.round(target.getMaxHp() * revivePercent));
        target.heal(restoreHp);
        return String.format("Used %s on %s! %s was revived with %d HP!", 
                name, target.getName(), target.getName(), target.getCurrentHp());
    }
}
