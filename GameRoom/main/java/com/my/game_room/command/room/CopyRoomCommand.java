package com.my.game_room.command.room;

import com.my.game_room.command.Command;
import com.my.game_room.service.GameRoomService;
import com.my.game_room.util.Reader;

public class CopyRoomCommand implements Command {
    private final GameRoomService gameRoomService;

    public CopyRoomCommand() {
        gameRoomService = new GameRoomService();
    }
    @Override
    public void execute() {
        System.out.println("Введіть номер кімнати");
        long id = Reader.getLong();
        gameRoomService.copyRoom(id);
    }
}
