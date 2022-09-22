package org.game;

import org.game.tools.Menu;

public class Main {
    public static void main(String... args) {
        Runnable game = () -> new Menu().start();
        game.run();
    }
}
