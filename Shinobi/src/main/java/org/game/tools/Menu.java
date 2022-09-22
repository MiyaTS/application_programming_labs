package org.game.tools;

import org.game.mode.BaseGameMode;
import org.game.mode.TeamMode;

import java.util.Scanner;

/**
 * <p>
 *     Standard menu with methods for communicating with user and starting different game process.
 * </p>
 *
 * @author Yarmoliuk Solomiya
 * */
public class Menu {

    /**
     *  Read from console user's choice (int value).
     *
     * @return int user's choice
     * */
    public static int getMenuStatus() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        } catch (NumberFormatException ex) {
            System.err.println("Not correct choice. Program failed.");
            return 0;
        }
    }

    /**
     * Method to clear console.
     * */
    public static void clear() {
        try {
            Runtime.getRuntime().exec("clear");
        } catch (Exception e) {
            System.err.println("Can't clear console screen");
        }
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
    }

    /**
     * Show start menu to console and choose:
     * <ol>
     *     <li>
     *          Game  -> start new game or replay one from previous
     *     </li>
     *     <li>
     *          Droid collection -> create new droid in collection or show list of created droids
     *     </li>
     * </ol>
     * */
    public void start() {
        showStartMenu();
        switch (getMenuStatus()) {
            case 0:
            default:
                return;
            case 1:
                gameMenu();
                start();
                break;
            case 2:
                shinobiCollection();
                start();
                break;
        }
    }

    /**
     * Just print welcome menu to console.
     * */
    private void showStartMenu() {
        System.out.println(System.lineSeparator() + "           SHINOBI FIGHT");
        System.out.println();
        System.out.println("        Menu:");
        System.out.println("1. Game");
        System.out.println("2. Shinobi Collection");
        System.out.println("0. Exit");
        System.out.println(System.lineSeparator() + "Please enter the item number: ");
    }

    /**
     * Print game view to user and start new game or replay
     * */
    private void gameMenu() {
        clear();
        System.out.println("        Game");
        System.out.println();
        System.out.println("1. New game");
        System.out.println("2. Replay game");
        System.out.println("0. back");
        System.out.println(System.lineSeparator() + "Please enter the item number: ");
        switch (getMenuStatus()) {
            case 0:
            default:
                return;
            case 1:
                newGame();
                gameMenu();
                break;
            case 2:
                GameReplayWriter.replay();
                gameMenu();
                break;
        }
    }

    /**
     * Show in console game modes and allow user to start one of them.
     * */
    private void newGame() {
        clear();
        System.out.println("        Modes");
        System.out.println();
        System.out.println("1. 1 vs 1");
        System.out.println("2. Team fight");
        System.out.println("0. back");
        System.out.println(System.lineSeparator() + "Please enter the item number: ");
        switch (getMenuStatus()) {
            case 0:
            default:
                return;
            case 1:
                new BaseGameMode().start();
                newGame();
                break;
            case 2:
                new TeamMode().start();
                newGame();
                break;
        }
    }

    /**
     * Show in console menu to create new droid or print list of created ones.
     * */
    private void shinobiCollection() {
        System.out.println("        Shinobi Collection");
        System.out.println();
        System.out.println("1. New shinobi");
        System.out.println("2. List of shinobi");
        System.out.println("0. back");
        System.out.println(System.lineSeparator() + "Please enter the item number: ");
        switch (getMenuStatus()) {
            case 0:
            default:
                return;
            case 1:
                creteShinobi();
                shinobiCollection();
                break;
            case 2:
                showShinobiList();
                shinobiCollection();
                break;
        }
    }

    /**
     * Call methods from ShinobiEditor to create and save a new Shinobi.
     * */
    private void creteShinobi() {
        new ShinobiEditor().createShinobi();
    }

    /**
     * Print Shinobi collection.
     * */
    private void showShinobiList() {
        new ShinobiEditor().ShinobiList();
    }
}