package com.my.game_room.command.toy;

import com.my.game_room.command.Command;
import com.my.game_room.service.ToyService;

public class DeleteToyCommand implements Command {
    private final ToyService toyService;

    public DeleteToyCommand() {
        toyService = new ToyService();
    }
    @Override
    public void execute() {
        toyService.delete();
    }
}
