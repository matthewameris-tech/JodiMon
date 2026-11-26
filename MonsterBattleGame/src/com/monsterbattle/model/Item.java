package com.monsterbattle.model;

/**
 * Abstract base class for usable battle and inventory items.
 * Demonstrates inheritance and polymorphism in inventory management.
 */
public abstract class Item {
    protected String name;
    protected String description;
    protected int quantity;

    public Item(String name, String description, int quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    public boolean consumeOne() {
        if (quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }

    public abstract boolean canUseOn(Monster target);

    public abstract String use(Monster target);

    @Override
    public String toString() {
        return String.format("%-16s (x%d) - %s", name, quantity, description);
    }
}
