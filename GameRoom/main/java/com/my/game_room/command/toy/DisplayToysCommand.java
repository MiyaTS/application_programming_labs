package com.my.game_room.command.toy;

import com.my.game_room.command.Command;
import com.my.game_room.service.ToyService;
import com.my.game_room.util.Menu;

public class DisplayToysCommand implements Command {
    private final ToyService toyService;

    public DisplayToysCommand() {
        toyService = new ToyService();
    }

    @Override
    public void execute() {
        if (toyService.viewAllToys()) {
            System.out.println("Немає іграшок");
            return;
        }
        Menu.getInstance().renderToyUtilMenu();
    }
}
