package com.my.game_room.command.toy;

import com.my.game_room.command.Command;
import com.my.game_room.service.ToyService;

/**
 * Edit Toy Command
 */
public class EditToyCommand implements Command {
    private final ToyService toyService;

    public EditToyCommand() {
        toyService = new ToyService();
    }

    @Override
    public void execute() {
        toyService.edit();
    }
}
