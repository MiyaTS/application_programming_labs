package org.game.entity.taijutsu;

import org.game.entity.Shinobi;
import org.game.entity.ShinobiClasses;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShinobiSeals extends Shinobi {

    private static final double W_HEALTH = 100;
    private static final double W_ARMOR = 30;
    private static final double W_DAMAGE = 10;

    private double ultra = 100;

    private String teamFeature = "Increase damage for one team Shinobi";

    /**
     * Default constructor
     * */
    public ShinobiSeals() {
        super();
        this.setdClass(ShinobiClasses.SEALS);
    }

    /**
     * Constructor for creating new Shinobi;
     * */
    public ShinobiSeals(String name, String teamName) {
        super(name, W_HEALTH, W_ARMOR, W_DAMAGE, teamName, ShinobiClasses.SEALS);
    }

    /**
     * Constructor for reading from collection
     * */
    public ShinobiSeals(String name, double health, double armor, double damage, String teamName, double ultra, String teamFeature) {
        super(name, health, armor, damage, teamName, ShinobiClasses.SEALS);
        this.ultra = ultra;
        this.teamFeature = teamFeature;
    }

    public String getTeamFeature() {
        return teamFeature;
    }

    /**
     * Scan and initializing droid from collection.
     * */
    public static Shinobi scanFromFile(Matcher m) {
        return new ShinobiSeals(m.find() ? m.group() : null,
                m.find() ? Double.parseDouble(m.group()) : 0.0,
                m.find() ? Double.parseDouble(m.group()) : 0.0,
                m.find() ? Double.parseDouble(m.group()) : 0.0,
                m.find() ? m.group() : "",
                m.find() ? Double.parseDouble(m.group()) : 0.0,
                m.find() ? m.group() : "");
    }

    /**
     * Getting general info about Shinobi.
     * Useful for writing in Shinobi collection.
     * */
    public String info() {
        return super.info() + ultra + ";" + teamFeature + ";";
    }

    /**
     * Enable super feature of current class.
     * */
    public String feature(Shinobi shinobi) {
        shinobi.setDamage(shinobi.getDamage() + 50);
        return "Droid use super skill: " + teamFeature;
    }

    @Override
    public Shinobi design() {
        Scanner scanner = new Scanner(System.in);
        ShinobiSeals shinobi = new ShinobiSeals("", "");
        System.out.println("Name = ");
        shinobi.setName(scanner.nextLine());
        System.out.println("Team name = ");
        shinobi.setTeamName(scanner.nextLine());
        return shinobi;
    }

    @Override
    public double attack(Shinobi shinobi) {
        double currentDamage = this.getDamage() + (ultra * Math.abs(Math.random() * 10 - 9) % 1);
        return updateStats(shinobi, currentDamage);
    }

    @Override
    public String teamAttack(List<Shinobi> team, List<Shinobi> enemy) {
        double currentDamage = this.getDamage() + (ultra * Math.abs(Math.random() * 10 - 9) % 1);
        int indexOfAttackShinobi = (int) (Math.random() * 100000) % enemy.size();
        if (updateStats(enemy.get(indexOfAttackShinobi), currentDamage) <= 0) {
            enemy.remove(indexOfAttackShinobi);
        }
        int indexOfTeamShinobi = (int) (Math.random() * 100000) % team.size();
        return feature(team.get(indexOfTeamShinobi));
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() +  "   ultra damage = " + ultra;
    }
}
