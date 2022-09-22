package org.game.mode;

import static org.game.tools.Menu.*;

import org.game.api.ModeAPI;
import org.game.entity.Shinobi;
import org.game.api.ShinobiEditorAPI;
import org.game.tools.ShinobiEditor;
import org.game.tools.GameReplayWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Base game mode 1 to 1.
 */
public class BaseGameMode implements ModeAPI {

    private static boolean isBattleStarted = false;

    private final List<Shinobi> shinobiList = new ArrayList<>(2);

    /**
     * Start of the game.
     * Initializing a Shinobi or selecting a shinobi from a collection.
     * Record the start of the game to a file.
     */
    @Override
    public void start() {
        ShinobiEditorAPI shinobiCreator = new ShinobiEditor();
        System.out.println("    New game started...");
        for (int i = 1; i < 3; i++) {
            System.out.println("1. Create Shinobi");
            System.out.println("2. Choose Shinobi from list");
            System.out.println("0. Exit");
            System.out.println(System.lineSeparator() + "Please enter the item number: ");
            switch (getMenuStatus()) {
                case 0:
                default:
                    if (end()) {
                        return;
                    }
                    i = 0;
                    continue;
                case 1:
                    shinobiList.add(shinobiCreator.createShinobi());
                    break;
                case 2:
                {
                    System.out.println("Available Shinobi");
                    List<Shinobi> tempList = shinobiCreator.getShinobi();
                    for (int j = 0; j < tempList.size(); j++) {
                        System.out.println(j + ". " + tempList.get(j));
                    }
                    System.out.println(System.lineSeparator() + "Enter number of Shinobi: ");
                    shinobiList.add(tempList.get(getMenuStatus()));
                    break;
                }
            }
        }
        clear();
        GameReplayWriter.initWriter();
        System.out.println(GameReplayWriter.writeInOpenFile("    Fight start!!!" + System.lineSeparator()));
        System.out.println(GameReplayWriter.writeInOpenFile("Shinobi 1: " + shinobiList.get(0).getName() + " vs Shinobi 2: " + shinobiList.get(1).getName() + System.lineSeparator()));
        battle();
        end();
    }

    /**
     * The main battle.
     * Write the main battle to a file.
     * Determining the winner.
     */
    @Override
    public void battle() {
        int i = 0;
        isBattleStarted = true;
        System.out.println(GameReplayWriter.writeInOpenFile("Shinobi 1:    " + shinobiList.get(0) + System.lineSeparator()));
        System.out.println(GameReplayWriter.writeInOpenFile("Shinobi 2:    " + shinobiList.get(1) + System.lineSeparator()));
        while (true) {
            System.out.println(GameReplayWriter.writeInOpenFile("Round " + i++ + System.lineSeparator()));
            System.out.println(GameReplayWriter.writeInOpenFile("Shinobi 1:    " + shinobiList.get(0).stats() + System.lineSeparator()));
            System.out.println(GameReplayWriter.writeInOpenFile("Shinobi 2:    " + shinobiList.get(1).stats() + System.lineSeparator()));
            int indexOfAttackShinobi = (int) (Math.random() * 10) % 2;
            System.out.println(GameReplayWriter.writeInOpenFile("Shinobi " + (indexOfAttackShinobi + 1) + " attack!" + System.lineSeparator()));
            if (shinobiList.get(indexOfAttackShinobi).attack(shinobiList.get(1 - indexOfAttackShinobi)) <= 0.0 ) {
                System.out.println(GameReplayWriter.writeInOpenFile("WINNER SHINOBI " + (indexOfAttackShinobi + 1) + " LET'S GLORY HIM!!!" + System.lineSeparator()));
                break;
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
