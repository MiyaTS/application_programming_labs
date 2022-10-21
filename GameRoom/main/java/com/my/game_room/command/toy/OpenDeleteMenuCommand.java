package com.my.game_room.command.toy;

import com.my.game_room.command.Command;
import com.my.game_room.util.Menu;

public class OpenDeleteMenuCommand implements Command {
    @Override
    public void execute() {
        Menu.getInstance().renderDeleteMenu();
    }
}
