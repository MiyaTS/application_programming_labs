package org.game.entity.ninjutsu;

import org.game.entity.Shinobi;
import org.game.entity.ShinobiClasses;
import org.game.entity.taijutsu.ShinobiMelee;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShinobiCurses extends Shinobi {

    private static final double W_HEALTH = 1000;
    private static final double W_ARMOR = 3000;
    private static final double W_DAMAGE = 300;

    private double ultra = 5000;

    private String teamFeature = "Imposes a curses!!!!! KIIIILLLLL!!!!!";

    /**
     * Default constructor
     * */
    public ShinobiCurses() {
        super();
        this.setdClass(ShinobiClasses.CURSES);
    }

    /**
     * Constructor for creating new Shinobi;
     * */
    public ShinobiCurses(String name, String teamName) {
        super(name, W_HEALTH, W_ARMOR, W_DAMAGE, teamName, ShinobiClasses.CURSES);
    }

    /**
     * Constructor for reading from collection
     * */
    public ShinobiCurses(String name, double health, double armor, double damage, String teamName, double ultra, String teamFeature) {
        super(name, health, armor, damage, teamName, ShinobiClasses.CURSES);
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
        return new ShinobiCurses(m.find() ? m.group() : null,
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
        shinobi.setHealth(1);
        shinobi.setDamage(0);
        shinobi.setArmor(0);
        return "Droid use super skill: " + teamFeature;
    }

    @Override
    public Shinobi design() {
        Scanner scanner = new Scanner(System.in);
        ShinobiCurses shinobi = new ShinobiCurses("", "");
        System.out.println("Name = ");
        shinobi.setName(scanner.nextLine());
        System.out.println("Team name = ");
        shinobi.setTeamName(scanner.nextLine());
        return shinobi;
    }

    @Override
    public double attack(Shinobi shinobi) {
        double currentDamage = this.getDamage();
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