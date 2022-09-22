package org.game.entity.taijutsu;

import org.game.entity.Shinobi;
import org.game.entity.ShinobiClasses;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShinobiMelee extends Shinobi {

    private static final double W_HEALTH = 1000;
    private static final double W_ARMOR = 3000;
    private static final double W_DAMAGE = 5;

    private double ultra = 5;

    private String teamFeature = "Increase armor for several team Shinobi";

    /**
     * Default constructor
     * */
    public ShinobiMelee() {
        super();
        this.setdClass(ShinobiClasses.MELLE);
    }

    /**
     * Constructor for creating new Shinobi;
     * */
    public ShinobiMelee(String name, String teamName) {
        super(name, W_HEALTH, W_ARMOR, W_DAMAGE, teamName, ShinobiClasses.MELLE);
    }

    /**
     * Constructor for reading from collection
     * */
    public ShinobiMelee(String name, double health, double armor, double damage, String teamName, double ultra, String teamFeature) {
        super(name, health, armor, damage, teamName, ShinobiClasses.MELLE);
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
        return new ShinobiMelee(m.find() ? m.group() : null,
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
    public String feature(Shinobi... shinobi) {
        for (Shinobi value : shinobi) {
            value.setArmor(value.getArmor() + ultra);
        }
        return "Shinobi use super skill: " + teamFeature;
    }

    @Override
    public Shinobi design() {
        Scanner scanner = new Scanner(System.in);
        ShinobiMelee shinobi = new ShinobiMelee("", "");
        System.out.println("Name = ");
        shinobi.setName(scanner.nextLine());
        System.out.println("Team name = ");
        shinobi.setTeamName(scanner.nextLine());
        return shinobi;
    }

    @Override
    public double attack(Shinobi shinobi) {
        return updateStats(shinobi, getDamage());
    }

    @Override
    public String teamAttack(List<Shinobi> team, List<Shinobi> enemy) {
        int indexOfAttackShinobi = (int) (Math.random() * 100000) % enemy.size();
        if (updateStats(enemy.get(indexOfAttackShinobi), this.getDamage()) <= 0) {
            enemy.remove(indexOfAttackShinobi);
        }
        int indexOfTeamShinobi = (int) (Math.random() * 100000) % team.size();
        return feature(team.get(indexOfTeamShinobi));
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() +  "   ultra armor supply = " + ultra;
    }

}
