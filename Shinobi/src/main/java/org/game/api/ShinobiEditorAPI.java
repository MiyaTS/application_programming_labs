package org.game.api;

import org.game.entity.Shinobi;

import java.util.List;

public interface ShinobiEditorAPI {

    /**
     * Read collection from file and print one to console.
     * */
    void ShinobiList();

    /**
     *  Print collection to console.
     * */
    void showShinobiList(List<Shinobi> list);

    /**
     * Reading Shinobi from file.
     * */
    List<Shinobi> getShinobi();

    /**
     * Creating new Shinobi and saving in file.
     * */
    Shinobi createShinobi();
}