package org.game.entity.ninjutsu;

import org.game.entity.Shinobi;
import org.game.entity.ShinobiClasses;
import org.game.entity.taijutsu.ShinobiMelee;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShinobiFire extends Shinobi {

    private static final double W_HEALTH = 100;
    private static final double W_ARMOR = 30;
    private static final double W_DAMAGE = 50;

    private double ultra = 500;

    private String teamFeature = "Increase armor for several team Shinobi";

    /**
     * Default constructor
     * */
    public ShinobiFire() {
        super();
        this.setdClass(ShinobiClasses.FIRE);
    }

    /**
     * Constructor for creating new Shinobi;
     * */
    public ShinobiFire(String name, String teamName) {
        super(name, W_HEALTH, W_ARMOR, W_DAMAGE, teamName, ShinobiClasses.FIRE);
    }

    /**
     * Constructor for reading from collection
     * */
    public ShinobiFire(String name, double health, double armor, double damage, String teamName, double ultra, String teamFeature) {
        super(name, health, armor, damage, teamName, ShinobiClasses.FIRE);
        this.ultra = ultra;
        this.teamFeature = teamFeature;
    }

    public String getTeamFeature() {
        return teamFeature;
    }

    /**
     * Scan and initializing Shinobi from collection.
     * */
    public static Shinobi scanFromFile(Matcher m) {
        return new ShinobiFire(m.find() ? m.group() : null,
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
        double temp = getDamage();
        this.setDamage(getDamage() * ultra);
        attack(shinobi);
        this.setDamage(temp);
        return "Shinobi use super skill: " + teamFeature;
    }

    @Override
    public Shinobi design() {
        Scanner scanner = new Scanner(System.in);
        ShinobiFire shinobi = new ShinobiFire("", "");
        System.out.println("Name = ");
        shinobi.setName(scanner.nextLine());
        System.out.println("Team name = ");
        shinobi.setTeamName(scanner.nextLine());
        return shinobi;
    }

    @Override
    public double attack(Shinobi shinobi) {
        double currentDamage = this.getDamage() * (ultra * Math.abs(Math.random() * 10 - 9) % 1);
        return updateStats(shinobi, currentDamage);
    }

    @Override
    public String teamAttack(List<Shinobi> team, List<Shinobi> enemy) {
        int indexOfAttackShinobi = (int) (Math.random() * 100000) % enemy.size();
        if (updateStats(enemy.get(indexOfAttackShinobi), getDamage()) <= 0) {
            enemy.remove(indexOfAttackShinobi);
        }
        int indexOfEnemyShinobi = (int) (Math.random() * 100000) % enemy.size();
        return feature(enemy.get(indexOfEnemyShinobi));
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() +  "   ultra attack = " + ultra;
    }
}
