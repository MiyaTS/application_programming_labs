package com.my.game_room.command.room;

import com.my.game_room.command.Command;
import com.my.game_room.service.GameRoomService;
import com.my.game_room.util.Menu;

public class DisplayRoomsCommand implements Command {
    private final GameRoomService gameRoomService;

    public DisplayRoomsCommand() {
        gameRoomService = new GameRoomService();
    }

    @Override
    public void execute() {
        if (gameRoomService.viewRooms()) {
            System.out.println("Немає кімнат");
            return;
        }
        Menu.getInstance().renderGameRoomUtilMenu();
    }
}
