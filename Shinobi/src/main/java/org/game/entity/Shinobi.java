package org.game.entity;

import java.util.List;
import java.util.regex.Matcher;

/**
 * <p>
 *     Common class for all Shinobi variants.
 * </p>
 *
 * @author Yarmoliuk Solomiya
 * */
public abstract class Shinobi {
    private String name;
    private double health;
    private double armor;
    private double damage;
    private String teamName;
    private ShinobiClasses dClass;

    public Shinobi() {
    }

    public Shinobi(String name, double health, double armor, double damage, String teamName, ShinobiClasses dClass) {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.damage = damage;
        this.teamName = teamName;
        this.dClass = dClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ShinobiClasses getdClass() {
        return dClass;
    }

    public void setdClass(ShinobiClasses dClass) {
        this.dClass = dClass;
    }

    /**
     * Getting general info about Shinobi.
     * Useful for writing in Shinobi collection.
     * */
    public String info() {
        return dClass + ";"
                + name + ";"
                + health + ";"
                + armor + ";"
                + damage + ";"
                + teamName + ";";
    }

    /**
     * Create general info for logs during battle.
     * @return main Shinobi info.
     * */
    public String stats() {
        return "name: " + name + " health:" + health + " armor:" + armor;
    }

    /**
     * Method to update Shinobi after attack.
     * @param shinobi to update.
     * @param currentDamage value of current damage.
     * */
    public static double updateStats(Shinobi shinobi, double currentDamage) {
        if (shinobi.getArmor() > 0) {
            double armor = shinobi.getArmor() - currentDamage;
            if (armor <= 0) {
                shinobi.setArmor(0);
                shinobi.setHealth(shinobi.getHealth() - Math.abs(armor));
            } else {
                shinobi.setArmor(armor);
            }
        } else {
            shinobi.setHealth(Math.max(shinobi.getHealth() - currentDamage, 0));
        }
        return shinobi.getHealth();
    }

    /**
     * Scan and initializing Shinobi from collection.
     * */
    public static Shinobi scanFromFile(Matcher m) {
        return null;
    }

    /**
     * Creating Shinobi by pattern.
     * @return new Shinobi.
     * */
    public abstract Shinobi design();

    /**
     * Logic for attack based on Shinobi type.
     * @param shinobi attacked Shinobi.
     * */
    public abstract double attack(Shinobi shinobi);

    /**
     * Special attacks using combination in Team Mode.
     * @param team list of teams Shinobi.
     * @param enemy list of enemies.
     * @return info about feature.
     * */
    public abstract String teamAttack(List<Shinobi> team, List<Shinobi> enemy);

    @Override
    public String toString() {
        return "Stats: " + System.lineSeparator() +
                "   name = '" + name + '\'' + System.lineSeparator() +
                "   health = " + health + System.lineSeparator() +
                "   armor = " + armor + System.lineSeparator() +
                "   damage = " + damage +
                (teamName.isEmpty() ? "" : System.lineSeparator() + "   team name: " + teamName);
    }
}
