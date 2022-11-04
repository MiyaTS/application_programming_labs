package com.my.game_room.command.room;

import com.my.game_room.command.Command;
import com.my.game_room.service.GameRoomService;

public class CreateRoomCommand implements Command {
    private final GameRoomService gameRoomService;

    public CreateRoomCommand() {
        gameRoomService = new GameRoomService();
    }

    @Override
    public void execute() {
        gameRoomService.create();
    }

}
