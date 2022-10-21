package com.my.game_room.util;

import com.my.game_room.command.Command;
import com.my.game_room.command.room.*;
import com.my.game_room.command.toy.*;

import java.util.HashMap;
import java.util.Map;

/**
 * class that implements the main menu (invoker class)
 */
public class Menu {
    private static Menu menu;
    private Map<Integer, Command> mainMenuCommands;
    private Map<Integer, Command> mainGameRoomMenuCommands;
    private Map<Integer, Command> utilGameRoomMenuCommands;
    private Map<Integer, Command> mainToyMenuCommands;
    private Map<Integer, Command> utilToyMenuCommands;
    private Map<Integer, Command> deleteToyMenuCommands;

    /**
     * creates menu class object
     * @return menu class object
     */
    public static Menu getInstance() {
        if (menu == null) {
            menu = new Menu();
        }
        return menu;
    }

    /**
     * Initialization of commands
     * Launch the main menu
     */
    public void launch() {
        initCommands();
        renderMenu();
    }

    /**
     * Initialization of commands
     * creating a hashmaps
     */
    private void initCommands() {
        mainMenuCommands = new HashMap<>();
        mainMenuCommands.put(1, new OpenGameRoomMenuCommand());
        mainMenuCommands.put(2, new OpenToyMenuCommand());

        mainGameRoomMenuCommands = new HashMap<>();
        mainGameRoomMenuCommands.put(1, new DisplayRoomsCommand());
        mainGameRoomMenuCommands.put(2, new CreateRoomCommand());

        utilGameRoomMenuCommands = new HashMap<>();
        utilGameRoomMenuCommands.put(1, new EditRoomCommand());
        utilGameRoomMenuCommands.put(2, new CopyRoomCommand());
        utilGameRoomMenuCommands.put(3, new DeleteRoomCommand());

        mainToyMenuCommands = new HashMap<>();
        mainToyMenuCommands.put(1, new CreateToyCommand());
        mainToyMenuCommands.put(2, new DisplayToysCommand());

        utilToyMenuCommands = new HashMap<>();
        utilToyMenuCommands.put(1, new EditToyCommand());
        utilToyMenuCommands.put(2, new OpenDeleteMenuCommand());

        deleteToyMenuCommands = new HashMap<>();
        deleteToyMenuCommands.put(1, new DeleteToyCommand());
        deleteToyMenuCommands.put(2, new DeleteByPriceOrderCommand());

    }

    /**
     * Launch the main menu
     */
    public void renderMenu() {
        System.out.println("Вітаємо в сервісі створення ігрових кімнат!");
        System.out.println("Взаємодія з програмною виконується за допомогою введення числових значень, " +
                System.lineSeparator() + "що відповідають відповідному пункту меню або діям");
        while (true) {
            System.out.println("Головне меню");
            System.out.println("1. Меню кімнат");
            System.out.println("2. Меню іграшок");
            System.out.println("0. Вийти");
            System.out.println("Оберіть будь ласка дію: ");
            int menuStatus = Reader.getInt();
            if (menuStatus == 0) {
                return;
            }
            mainMenuCommands.get(menuStatus).execute();
        }
    }

    /**
     * The main game room menu
     */
    public void renderGameRoomMenu() {
        while (true) {
            System.out.println("Вітаємо в меню ігрових кімнат!");
            System.out.println("1. Переглянути список кімнат");
            System.out.println("2. Створити кімнату");
            System.out.println("0. В меню");
            System.out.println("Оберіть будь ласка дію: ");
            int menuStatus = Reader.getInt();
            if (menuStatus == 0) {
                return;
            }
            mainGameRoomMenuCommands.get(menuStatus).execute();
        }
    }

    /**
     * The game room operations menu
     */
    public void renderGameRoomUtilMenu() {
        System.out.println("Операції над кімнатами");
        System.out.println("1. Редагувати");
        System.out.println("2. Копіювати");
        System.out.println("3. Видалити");
        System.out.println("0. Назад");
        System.out.println("Оберіть будь ласка дію:");
        int menuStatus = Reader.getInt();
        if (menuStatus == 0) {
            return;
        }
        utilGameRoomMenuCommands.get(menuStatus).execute();
    }

    /**
     * The main toy menu
     */
    public void renderToyMenu() {
        while (true) {
            System.out.println("Вітаємо в меню конструктора іграшок!");
            System.out.println("1. Створити іграшку");
            System.out.println("2. Переглянути список іграшок");
            System.out.println("0. В меню");
            System.out.println("Оберіть будь ласка дію: ");
            int menuStatus = Reader.getInt();
            if (menuStatus == 0) {
                System.out.println("В головне меню!");
                return;
            }
            mainToyMenuCommands.get(menuStatus).execute();
        }
    }

    /**
     * The toy operations menu
     */
    public void renderToyUtilMenu() {
        System.out.println("Операції над іграшками");
        System.out.println("1. Редагувати");
        System.out.println("2. Відкрити меню видалення");
        System.out.println("0. Назад");
        System.out.println("Оберіть будь ласка дію:");
        int menuStatus = Reader.getInt();
        if (menuStatus == 0) {
            return;
        }
        utilToyMenuCommands.get(menuStatus).execute();
    }

    /**
     * The game room delete menu
     */
    public void renderDeleteMenu() {
        System.out.println("Меню видалення");
        System.out.println("1. Видалити за номером");
        System.out.println("2. Видалити іграшки за ціною");
        System.out.println("0. Назад");
        System.out.println("Оберіть будь ласка дію:");
        int menuStatus = Reader.getInt();
        //TODO можна поміняти тут з == 0 на < 1 OR > 2 тоді буде функціональніше меню
        if (menuStatus == 0) {
            return;
        }
        deleteToyMenuCommands.get(menuStatus).execute();
    }

    /**
     * shutdown and closure of connection
     */
    public void destroyMenu() {
        System.out.println("Завершення роботи");
        DatabaseManager.closeConnection();
    }
}
