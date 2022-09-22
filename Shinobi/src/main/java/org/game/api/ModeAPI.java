package org.game.api;

public interface ModeAPI {

    /**
     * Start battle. Init shinobi and preparing for writing battle in file.
     * */
    void start();

    /**
     * The main battle process.
     * */
    void battle();

    /**
     * Prefinish condition.
     * */
    boolean end();
}
