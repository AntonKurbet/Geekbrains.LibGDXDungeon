package ru.geekbrains.dungeon.game;

import lombok.Data;

@Data
public class Weapon {
    public enum Type {
        SPEAR, SWORD, MACE, AXE, BOW
    }

    Type type;
    int damage;
    int radius;

    public Weapon(Type type, int damage, int radius) {
        this.type = type;
        this.damage = damage;
        this.radius = radius;
    }
}
