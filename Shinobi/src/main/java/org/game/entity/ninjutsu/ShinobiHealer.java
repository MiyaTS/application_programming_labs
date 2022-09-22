package org.game.entity.ninjutsu;

import org.game.entity.Shinobi;
import org.game.entity.ShinobiClasses;
import org.game.entity.taijutsu.ShinobiMelee;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShinobiHealer extends Shinobi {

    private static final double W_HEALTH = 1000;
    private static final double W_ARMOR = 30;
    private static final double W_DAMAGE = 4;

    private double ultra = 300;

    private String teamFeature = "Increase hp for team Shinobi";

    /**
     * Default constructor
     * */
    public ShinobiHealer() {
        super();
        this.setdClass(ShinobiClasses.HEALER);
    }

    /**
     * Constructor for creating new Shinobi;
     * */
    public ShinobiHealer(String name, String teamName) {
        super(name, W_HEALTH, W_ARMOR, W_DAMAGE, teamName, ShinobiClasses.HEALER);
    }

    /**
     * Constructor for reading from collection
     * */
    public ShinobiHealer(String name, double health, double armor, double damage, String teamName, double ultra, String teamFeature) {
        super(name, health, armor, damage, teamName, ShinobiClasses.HEALER);
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
        return new ShinobiHealer(m.find() ? m.group() : null,
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
        shinobi.setHealth(shinobi.getHealth() + ultra);
        return "Shinobi use super skill: " + teamFeature;
    }

    @Override
    public Shinobi design() {
        Scanner scanner = new Scanner(System.in);
        ShinobiHealer shinobi = new ShinobiHealer("", "");
        System.out.println("Name = ");
        shinobi.setName(scanner.nextLine());
        System.out.println("Team name = ");
        shinobi.setTeamName(scanner.nextLine());
        return shinobi;
    }

    @Override
    public double attack(Shinobi shinobi) {
        this.setHealth(getHealth() + 10);
        return updateStats(shinobi, getDamage());
    }

    @Override
    public String teamAttack(List<Shinobi> team, List<Shinobi> enemy) {
        int indexOfAttackShinobi = (int) (Math.random() * 100000) % enemy.size();
        if (updateStats(enemy.get(indexOfAttackShinobi), getDamage()) <= 0) {
            enemy.remove(indexOfAttackShinobi);
        }
        int indexOfTeamShinobi = (int) (Math.random() * 100000) % team.size();
        return feature(team.get(indexOfTeamShinobi));
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() +  "   ultra heal = " + ultra;
    }

}
