package com.my.game_room;

import com.my.game_room.util.Menu;

/**
 * the main class that runs the program
 */

public class GameRoomApplication {
    public static void main(String... args) {
        Menu.getInstance().launch();
        Menu.getInstance().destroyMenu();
    }
}
