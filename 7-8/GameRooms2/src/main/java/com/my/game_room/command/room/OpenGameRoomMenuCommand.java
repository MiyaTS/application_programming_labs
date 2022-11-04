package com.my.game_room.command.room;

import com.my.game_room.command.Command;
import com.my.game_room.util.Menu;

public class OpenGameRoomMenuCommand implements Command {
    @Override
    public void execute() {
        Menu.getInstance().renderGameRoomMenu();
    }
}
