package com.my.game_room.service;

import com.my.game_room.constant.AgeCategory;
import com.my.game_room.constant.SizeType;
import com.my.game_room.constant.ToyType;
import com.my.game_room.dao.Dao;
import com.my.game_room.dao.GameRoomDao;
import com.my.game_room.dao.ToyDao;
import com.my.game_room.model.GameRoom;
import com.my.game_room.model.toy.AbstractToy;
import com.my.game_room.util.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * game room service class
 * receiver class
 */
public class GameRoomService {
    private static final Logger LOG = LogManager.getLogger();
    private final FilterService filterService;
    private final Dao<GameRoom> gameRoomDao;
    private final Dao<AbstractToy> toyDao;

    public GameRoomService() {
        filterService = new FilterService();
        gameRoomDao = new GameRoomDao();
        toyDao = new ToyDao();
    }

    public GameRoomService(FilterService filterService, Dao<GameRoom> gameRoomDao, Dao<AbstractToy> toyDao) {
        this.filterService = filterService;
        this.gameRoomDao = gameRoomDao;
        this.toyDao = toyDao;
    }

    /**
     * copying a room
     * giving the copied room a new name
     * @param id game room id
     */
    public void copyRoom(long id) {
        LOG.info("Копіювання кімнати за айді {}", id);
        var room = gameRoomDao.getById(id);
        if (room.isEmpty()) {
            LOG.error("Кімнату не знайдено");
            throw new IllegalArgumentException();
        }
        if (!renameRoom(room.get())) {
            LOG.warn("Неправильне імя");
            System.err.println("Неправильне імя");
            return;
        }
        gameRoomDao.save(room.get());
    }

    /**
     * renaming game room
     * @param room info about game room from database
     * @return true if new name is set
     */
    public boolean renameRoom(GameRoom room) {
        LOG.info("Зміна назви для кімнати {}", room);
        System.out.println("Введіть нову назву");
        var newName = Reader.getLine();
        if (room.getName().equals(newName)) {
            return false;
        }
        room.setName(newName);
        return true;
    }

    /**
     * clearing game room
     * @param room info about game room from database
     */
    public void clearRoom(GameRoom room) {
        LOG.info("Очищення кімнати {}", room);
        room.setAges(new ArrayList<>());
        room.setToys(new ArrayList<>());
    }

    /**
     * outputs all game rooms from the database
     * @return true if this list contains no elements
     */
    public boolean viewRooms() {
        var list = gameRoomDao.getAll();
        for (GameRoom room : list) {
            System.out.println(room.getId() + " - " + room);
        }
        return list.isEmpty();
    }

    /**
     * Game creation editor
     * creating a game room
     * saving the game room to the database
     */
    public void create() {
        LOG.info("Створення кімнати");
        System.out.println("Редактор створення кімнат");
        System.out.println("Введіть дані про кімнату:");
        var room = new GameRoom();
        setGeneralGameRoomConfig(room);
        room.setToys(setToyCollection(room));
        System.out.println("Введені дані:" + System.lineSeparator() + room);
        gameRoomDao.save(room);
    }

    /**
     * Reading and setting shared Data
     */
    private void setGeneralGameRoomConfig(GameRoom room) {
        LOG.info("Загальні дані");
        System.out.println("Зчитування загальних даних");
        System.out.println("Введіть назву кімнати:");
        room.setName(Reader.getLine());
        System.out.println("Введіть опис:");
        room.setDescription(Reader.getLine());
        System.out.println("Введіть загальну вартість іграшок в кімнаті:");
        room.setTotalPrice(Reader.getDouble());
        room.setSize(getRoomSize());
        List<AgeCategory> list = Reader.getAgeCategories();
        room.setAges(list);
    }

    /**
     * Getting max toy size for game room
     * @return size type
     */
    private SizeType getRoomSize() {
        System.out.println("Введіть максимальний розмір іграшок:");
        return Reader.readSize();
    }

    /**
     * Setting toy collection to game room
     * @param room info about game room from database
     * @return collection of toys
     */
    private List<AbstractToy> setToyCollection(GameRoom room) {
        List<AbstractToy> list = new ArrayList<>();
        System.out.println("Зчитування даних у циклі");
        while (true) {
            System.out.println("Оберіть дію");
            System.out.println("1. Добавити іграшку");
            System.out.println("0. Зберегти та вийти");
            int status = Reader.getInt();
            if (status == 0) {
                break;
            }
            ToyType toyTypeStatus = Reader.readToyType();
            var globalInfo = filterService.filterByType(toyTypeStatus);
            System.out.println("Бажаєте пошук за параметрами?");
            System.out.println("1. Так");
            System.out.println("2. Ні");
            int parameterStatus = Reader.getInt();
            if (parameterStatus == 1) {
                list.add(getToyByParameters(room, globalInfo));
            } else {
                System.out.println("Введіть номер іграшки:");
                for (AbstractToy toy: globalInfo) {
                    System.out.println(toy.getId() + " - " + toy);
                }
                long menuStatus = Reader.getLong();
                if (menuStatus == 0) {
                    break;
                }
                toyDao.getById(menuStatus).ifPresent(list::add);
                System.out.println("Добавлено елемент з айді: " + menuStatus);
            }
            list.forEach(e -> System.out.println(e.getId() + " - " + e));
        }
        System.out.println("Формування колекції іграшок завершено");
        return list;
    }

    /**
     * Getting toy by parameters
     * @param room info about game room from database
     * @param list list of toys in this game room
     * @return toy by parameters
     */
    public AbstractToy getToyByParameters(GameRoom room, List<AbstractToy> list) {
        System.out.println("Оберіть параметр");
        System.out.println("1. По ціні");
        System.out.println("2. По розміру");
        System.out.println("3. По віковій категорії");
        switch (Reader.getInt()) {
            case 1 -> {
                list = filterService.filterByPrice(list);
            }
            case 2 -> {
                list = filterService.filterBySize(room.getSize(), list);
            }
            case 3 -> {
                list = filterService.filterByAge(room.getAges(), list);
            }
        }
        if (list.isEmpty()) {
            System.out.println("Немає таких іграшок за параметрами");
        }
        System.out.println("Оберіть іграшку по номеру");
        list.forEach(toy -> System.out.println(toy.getId() + " - " + toy));
        long id = Reader.getLong();
        Optional<AbstractToy> toy = toyDao.getById(id);
        if (toy.isEmpty()) {
            LOG.error("Не існує такої кімнати");
            throw new IllegalArgumentException();
        }
        return toy.get();
    }

    /**
     * Editing commands for game room
     * updating game room in database after editing
     * @param id id of game room
     */
    public void edit(long id) {
        LOG.info("Редагування кімнати з айді {}", id);
        var room = viewInfo(id);
        System.out.println("Оберіть дію: ");
        System.out.println("1. Очистити кімнату");
        System.out.println("2. Переназвати кімнату");
        System.out.println("3. Переписати дані вручну");
        System.out.println("0. Назад");
        var menuStatus = Reader.getInt();
        switch (menuStatus) {
            case 1 -> clearRoom(room);
            case 2 -> {
                if (renameRoom(room)) {
                    System.out.println("Дані змінено");
                } else {
                    System.out.println("Неправильне імя");
                    return;
                }
            }
            case 3 -> manualEditing(room);
            default -> {
                System.out.println("Неправильні дані.");
                return;
            }
        }
        gameRoomDao.update(room);
    }

    /**
     * Manual editing
     * @param room info about game room from database
     */
    private void manualEditing(GameRoom room) {
        while (true) {
            System.out.println("Оберіть параметр для зміни");
            System.out.println("1. Імя");
            System.out.println("2. Опис");
            System.out.println("3. Розмір");
            System.out.println("4. Вікові категорії");
            System.out.println("5. Іграшки");
            System.out.println("0. Зберегти та вийти");
            var status = Reader.getInt();
            if (status == 0) {
                return;
            }
            switch (status) {
                case 1 -> renameRoom(room);
                case 2 -> {
                    System.out.println("Введіть Опис");
                    String value = Reader.getLine();
                    room.setDescription(value);
                }
                case 3 -> room.setSize(getRoomSize());
                case 4 -> room.setAges(Reader.getAgeCategories());
                case 5 -> room.setToys(editToyList(room));
            }
        }
    }

    /**
     * Editing toy list
     * @param room info about game room from database
     * @return result from choosing option
     */
    private List<AbstractToy> editToyList(GameRoom room) {
        List<AbstractToy> copy = room.getToys();
        while (true) {
            System.out.println("Виберіть опцію над колекцією");
            System.out.println("1. Редагувати наявний список");
            System.out.println("2. Добавити нові іграшки");
            System.out.println("0. Зберегти та вийти");
            System.out.println("Інше число, скасувати операцію");
            var status = Reader.getInt();
            if (status < 0 || status > 2) {
                return copy;
            }
            switch (status) {
                case 0 -> {
                    return room.getToys();
                }
                case 1 -> {
                    room.setToys(chooseToy(room));;
                }
                case 2 -> {
                    room.setToys(setToyCollection(room));
                }
            }
        }
    }

    /**
     * editing an existing list of toys
     * @param room info about game room from database
     * @return list of toys
     */
    private List<AbstractToy> chooseToy(GameRoom room) {
        List<AbstractToy> toys = room.getToys();
        for (int i = 0; i < toys.size(); i++) {
            System.out.println((i + 1) + " - " + toys.get(i));
        }
        while (true) {
            System.out.println("Оберіть номер іграшки для видалення");
            System.out.println("Введіть 0 для виходу");
            var index = Reader.getInt();
            if (index == 0 || toys.isEmpty()) {
                return toys;
            }
            if (toys.size() < index - 1) {
                System.out.println("Неправильний індекс");
                continue;
            }
            toys.remove(index - 1);
        }
    }

    /**
     * deleting game room from database
     * @param id id of game room
     */
    public void delete(long id) {
        LOG.info("Видалення кімнати за айді {}", id);
        gameRoomDao.delete(id);
    }

    /**
     * view info about game room
     * @param id game room id from database
     * @return value of game room
     */
    public GameRoom viewInfo(long id) {
        LOG.error("Перегляд даних за айді {}", id);
        var room = gameRoomDao.getById(id);
        if (room.isEmpty()) {
            LOG.error("Немає кімнати з айді {}", id);
            throw new IllegalArgumentException();
        }
        System.out.println(room.get());
        return room.get();
    }
}
