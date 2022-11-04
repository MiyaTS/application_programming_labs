package com.my.game_room.command.toy;

import com.my.game_room.command.Command;
import com.my.game_room.util.Menu;

/**
 * Open Toy Menu Command
 */
public class OpenToyMenuCommand implements Command {
    @Override
    public void execute() {
        Menu.getInstance().renderToyMenu();
    }
}
