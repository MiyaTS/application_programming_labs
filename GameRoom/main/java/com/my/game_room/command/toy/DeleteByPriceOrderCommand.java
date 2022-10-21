package com.my.game_room.command.toy;

import com.my.game_room.command.Command;
import com.my.game_room.service.ToyService;

public class DeleteByPriceOrderCommand implements Command {
    private final ToyService toyService;

    public DeleteByPriceOrderCommand() {
        toyService = new ToyService();
    }
    @Override
    public void execute() {
        toyService.deleteToysByPrice();
    }
}
