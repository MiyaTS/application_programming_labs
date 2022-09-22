package org.game.tools;

import org.game.api.ShinobiEditorAPI;
import org.game.entity.Shinobi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.game.entity.ShinobiClasses;
import org.game.entity.ninjutsu.ShinobiFire;
import org.game.entity.ninjutsu.ShinobiHealer;
import org.game.entity.ninjutsu.ShinobiCurses;
import org.game.entity.taijutsu.ShinobiMelee;
import org.game.entity.taijutsu.ShinobiSeals;

import static org.game.tools.Menu.getMenuStatus;

/**
 * <p>
 * Class for designing Shinobi and processing collection.
 * </p>
 *
 * @author Yarmoliuk Solomiya
 */
public class ShinobiEditor implements ShinobiEditorAPI {
    private static final String PATH = "userdata\\droid_list.txt";
    private static final Pattern PATTERN = Pattern.compile("(?<=[;]|^)[^;].[^;]+(?=[;])");

    /**
     * Getting and printing a Shinobi collection
     */
    public void ShinobiList() {
        List<Shinobi> list = getShinobi();
        showShinobiList(list);
    }

    /**
     * Print Shinobi collection
     * */
    public void showShinobiList(List<Shinobi> list) {
        if (list != null && list.isEmpty()) {
            System.out.println("No one Shinobi created yet");
        } else {
            System.out.println("       Your Shinobi Collection");
            System.out.println(list);
        }
    }

    /**
     * Reading Shinobi from file and entering it in the Shinobi collection
     * @return collection of the Shinobi
     */
    @Override
    public List<Shinobi> getShinobi() {
        List<Shinobi> list = new ArrayList<>();
        String line;
        try (Scanner scanner = new Scanner(new File(PATH))) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                list.add(process(line));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
            return null;
        }
        return list;
    }

    /**
     * Checking for a regular expression match and initializing Shinobi from file
     * @param data string with data from file
     * @return droid from collection scanned from file
     */
    private Shinobi process(String data) {
        Matcher matcher = PATTERN.matcher(data);
        if (!matcher.find()) {
            throw new IllegalArgumentException();
        }
        switch (ShinobiClasses.valueOf(matcher.group())) {
            default:
                return null;
            case SEALS:
                return ShinobiSeals.scanFromFile(matcher);
            case FIRE:
                return ShinobiFire.scanFromFile(matcher);
            case MELLE:
                return ShinobiMelee.scanFromFile(matcher);
            case CURSES:
                return ShinobiCurses.scanFromFile(matcher);
            case HEALER:
                return ShinobiHealer.scanFromFile(matcher);
        }
    }

    /**
     * Creating a new Shinobi
     * @return created Shinobi object
     */
    @Override
    public Shinobi createShinobi() {
        Shinobi shinobi = null;
        printShinobiClasses();
        switch (getMenuStatus()) {
            case 0:
            default:
                System.out.println("No one template chosen. Exit from creator!");
                break;
            case 1:
                shinobi = new ShinobiSeals().design();
                break;
            case 2:
                shinobi = new ShinobiFire().design();
                break;
            case 3:
                shinobi = new ShinobiMelee().design();
                break;
            case 4:
                shinobi = new ShinobiCurses().design();
                break;
            case 5:
                shinobi = new ShinobiHealer().design();
                break;
        }
        if (shinobi != null) {
            saveShinobiInCollection(shinobi);
        }
        return shinobi;
    }

    /**
     * Saving a new Shinobi to a collection in a file
     * @param shinobi new created Shinobi
     */
    public void saveShinobiInCollection(Shinobi shinobi) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(PATH, true);
            writer.write(shinobi.info() + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Droid can't be saved in collection");
        } finally {
            try {
                Objects.requireNonNull(writer).close();
            } catch (IOException e) {
                System.out.println("Collection file can't be closed!");
            }
        }
    }

    /**
     * Output of Shinobi classes
     */
    private void printShinobiClasses() {
        System.out.println("    Shinobi classes");
        System.out.println("1. Seals");
        System.out.println("2. Fire");
        System.out.println("3. Melle");
        System.out.println("4. Curses");
        System.out.println("5. Healer");
        System.out.println("0. Exit");
        System.out.println("Enter the number of shinobi class:");
    }
}
