package org.game.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>
 *     Class for writing game in .txt file for further replay. Also consist of tool methods.
 * </p>
 *
 * @author Solomiya Yarmoliuk
 * */
public class GameReplayWriter {
    
    private static final String PATH = "userdata\\replay.txt";
    
    private static FileWriter writer;

    /**
     * Initialize FileWriter and create new unique file.
     * */
    public static void initWriter () {
        File newReplayFile = new File(PATH);
        try {
            writer = new FileWriter(newReplayFile);
        } catch (IOException e) {
            System.err.println("Creating new file for replay failed");
        }
    }

    /**
     * Write game details in file.
     * @param text text to write down.
     * @return text.
     * */
    public static String writeInOpenFile(String text) {
        try {
            writer.write(text);
        } catch (IOException e) {
            System.err.println("Can't write text in file");
        }
        return text;
    }

    /**
     * Flush writer and close file stream.
     * */
    public static void flush() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("Can't close writer");
        }
    }

    /**
     * Print list of replay to console
     * */
    public static void replay(){
        try {
            List<String> lines = Files.readAllLines(Paths.get(PATH), UTF_8);
            for (String s: lines) {
                System.out.println(s);
            }
        }
        catch (IOException e) {
            System.err.println("Game replay failed! File can't be read");
        }
    }
}
