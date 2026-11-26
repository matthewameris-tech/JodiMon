package com.monsterbattle.model;

/**
 * Usable restorative item that recovers HP.
 * Extends Item.
 */
public class HealingItem extends Item {
    private final int healAmount; // -1 represents full heal

    public HealingItem(String name, String description, int quantity, int healAmount) {
        super(name, description, quantity);
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    @Override
    public boolean canUseOn(Monster target) {
        if (target == null || target.isFainted()) {
            return false;
        }
        return target.getCurrentHp() < target.getMaxHp();
    }

    @Override
    public String use(Monster target) {
        if (!consumeOne()) {
            return "No " + name + " remaining!";
        }

        int restore = (healAmount == -1) ? (target.getMaxHp() - target.getCurrentHp()) : healAmount;
        int healed = target.heal(restore);
        return String.format("Used %s on %s! Restored %d HP. (%d/%d HP)", 
                name, target.getName(), healed, target.getCurrentHp(), target.getMaxHp());
    }
}
