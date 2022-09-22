package org.game.mode;

import org.game.api.ShinobiEditorAPI;
import org.game.api.ModeAPI;
import org.game.entity.Shinobi;
import org.game.tools.ShinobiEditor;
import org.game.tools.GameReplayWriter;

import java.util.ArrayList;
import java.util.List;

import static org.game.tools.Menu.clear;
import static org.game.tools.Menu.getMenuStatus;

/**
 * Base game mode team to team.
 */
public class TeamMode implements ModeAPI {

    private static boolean isBattleStarted = false;

    private static int size;

    private List<Shinobi> TeamA;
    private List<Shinobi> TeamB;

    /**
     * Start of the game.
     * Creating teams.
     * Record the start of the game to a file.
     */
    @Override
    public void start() {
        ShinobiEditorAPI shinobiCreator = new ShinobiEditor();
        System.out.println("    New game started...");
        System.out.println("Enter size of teams: ");
        size = getMenuStatus();
        TeamA = new ArrayList<>(size);
        TeamB = new ArrayList<>(size);
        if (initTeam(shinobiCreator, TeamA, "Team A")) return;
        if (initTeam(shinobiCreator, TeamB, "Team B")) return;
        clear();
        GameReplayWriter.initWriter();
        System.out.println(GameReplayWriter.writeInOpenFile("    Fight start!!!" + System.lineSeparator()));
        System.out.println(GameReplayWriter.writeInOpenFile("TeamA vs TeamB" + System.lineSeparator()));
        System.out.println(GameReplayWriter.writeInOpenFile("TeamA" + System.lineSeparator() + TeamA + System.lineSeparator()));
        System.out.println(GameReplayWriter.writeInOpenFile("TeamB" + System.lineSeparator() + TeamB + System.lineSeparator()));
        battle();
        end();
    }

    /**
     * Initialization teams with initializing a Shinobi or selecting a shinobi from a collection.
     * @param shinobiCreator Shinobi
     * @param team list with team members
     * @param teamName team Name
     * @return boolean value
     */
    private boolean initTeam(ShinobiEditorAPI shinobiCreator, List<Shinobi> team, String teamName) {
        for (int i = 0; i < size; i++) {
            System.out.println("1. Create Shinobi");
            System.out.println("2. Choose Shinobi from list");
            System.out.println("0. Exit");
            System.out.println(System.lineSeparator() + "Please enter the item number: ");
            switch (getMenuStatus()) {
                case 0:
                default:
                    if (end()) {
                        return true;
                    }
                    i = 0;
                    continue;
                case 1:
                    Shinobi temp = shinobiCreator.createShinobi();
                    temp.setTeamName(teamName);
                    team.add(temp);
                    break;
                case 2:
                {
                    System.out.println("Available Shinobi");
                    List<Shinobi> tempList = shinobiCreator.getShinobi();
                    for (int j = 0; j < tempList.size(); j++) {
                        System.out.println(j + ". " + tempList.get(j));
                    }
                    System.out.println(System.lineSeparator() + "Enter number of Shinobi: ");
                    Shinobi tempG = tempList.get(getMenuStatus());
                    tempG.setTeamName(teamName);
                    team.add(tempG);
                    break;
                }
            }
        }
        return false;
    }

    /**
     * The main battle.
     * Write the main battle to a file.
     * Determining the winner team.
     */
    @Override
    public void battle() {
        int i = 0;
        isBattleStarted = true;
        while (true) {
            System.out.println(GameReplayWriter.writeInOpenFile("Round " + i++ + System.lineSeparator()));
            int indexOfAttackShinobi;
            int indexOfDamagedShinobi;
            if ( i % 2 == 0) {
                indexOfAttackShinobi = Math.max((int) (Math.random() * 1000000) % TeamA.size(), 0);
                indexOfDamagedShinobi = Math.max((int) (Math.random() * 1000000) % TeamB.size(), 0);
                System.out.println(GameReplayWriter.writeInOpenFile("Shinobi attack:    " + TeamA.get(indexOfAttackShinobi).stats() + System.lineSeparator()));
                System.out.println(GameReplayWriter.writeInOpenFile("Shinobi damaged:    " + TeamB.get(indexOfDamagedShinobi).stats() + System.lineSeparator()));
                System.out.println(GameReplayWriter.writeInOpenFile(TeamA.get(indexOfAttackShinobi).teamAttack(TeamA, TeamB) + System.lineSeparator()));
                if (TeamB.size() == 0) {
                    System.out.println(GameReplayWriter.writeInOpenFile("WINNER TeamA LET'S GLORY THEM!!!" + System.lineSeparator()));
                    break;
                }
            } else {
                indexOfAttackShinobi = Math.max((int) (Math.random() * 1000000) % TeamB.size(), 0);
                indexOfDamagedShinobi = Math.max((int) (Math.random() * 1000000) % TeamA.size(), 0);
                System.out.println(GameReplayWriter.writeInOpenFile("Shinobi attack:    " + TeamB.get(indexOfAttackShinobi).stats() + System.lineSeparator()));
                System.out.println(GameReplayWriter.writeInOpenFile("Shinobi damaged:    " + TeamA.get(indexOfDamagedShinobi).stats() + System.lineSeparator()));
                System.out.println(GameReplayWriter.writeInOpenFile(TeamB.get(indexOfAttackShinobi).teamAttack(TeamB, TeamA) + System.lineSeparator()));
                if (TeamA.size() == 0) {
                    System.out.println(GameReplayWriter.writeInOpenFile("WINNER TeamB LET'S GLORY THEM!!!" + System.lineSeparator()));
                    break;
                }
            }
        }
    }

    /**
     * Continue or end game menu.
     * @return game status
     */
    @Override
    public boolean end() {
        if (!isBattleStarted) {
            System.out.println("Do you want to exit?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            return getMenuStatus() == 1;
        }
        GameReplayWriter.flush();
        return false;
    }
}