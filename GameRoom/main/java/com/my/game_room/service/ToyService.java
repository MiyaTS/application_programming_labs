package com.my.game_room.service;

import com.my.game_room.constant.ToyType;
import com.my.game_room.dao.Dao;
import com.my.game_room.dao.ToyDao;
import com.my.game_room.model.toy.*;
import com.my.game_room.util.Reader;

import java.util.List;

/**
 * toy service class
 * receiver class
 */

public class ToyService {
    private final FilterService filterService;
    private final Dao<AbstractToy> toyDao;

    public ToyService() {
        filterService = new FilterService();
        toyDao = new ToyDao();
    }

    /**
     * delete toys by price from database
     */
    public void deleteToysByPrice() {
        var list = toyDao.getAll();
        if (list.isEmpty()) {
            System.out.println("Немає іграшок");
            return;
        }
        for (AbstractToy toy : filterService.filterByPrice(list)) {
            toyDao.delete(toy.getId());
        }
    }

    /**
     * Toy creation editor
     * creating a toy depending on the type
     * saving the toy to the database
     */
    public void create() {
        System.out.println("Редактор створення іграшок");
        System.out.println("Введіть дані про іграшку:");
        ToyType type = Reader.readToyType();
        AbstractToy toy = null;
        switch (type) {
            case CAR -> {
                toy = createCar();
            }
            case DOLL -> {
                toy = createDoll();
            }
            case CUBES -> {
                toy = createCubes();
            }
            case MUSICAL -> {
                toy = createMusical();
            }
        }
        if (toy != null) {
            toyDao.save(toy);
        }

    }

    /**
     * Creating a musical instrument
     * @return musical toy
     */
    private Musical createMusical() {
        System.out.println("Створення музичного інструменту");
        var toy = new Musical();
        toy.setToyType(ToyType.MUSICAL);
        System.out.println("Введіть назву:");
        toy.setName(Reader.getLine());
        System.out.println("Введіть опис:");
        toy.setDescription(Reader.getLine());
        System.out.println("Введіть ціну:");
        toy.setPrice(Reader.getDouble());
        System.out.println("Оберіть розмір іграшки");
        toy.setSize(Reader.readSize());
        System.out.println("Оберіть вікову категорію");
        toy.setAge(Reader.readAge());
        System.out.println("Введіть чи можливий автопрогравання музики у форматі (true/false)");
        toy.setAutoPlayEnabled(Reader.getBoolean());
        System.out.println("Введіть тип музики");
        toy.setMusicType(Reader.getLine());
        System.out.println("Введіть матеріал");
        toy.setMaterial(Reader.getLine());
        return toy;
    }

    /**
     * Creating a cubes
     * @return cubes toys
     */
    private Cubes createCubes() {
        System.out.println("Створення кубиків");
        var toy = new Cubes();
        toy.setToyType(ToyType.CUBES);
        System.out.println("Введіть назву:");
        toy.setName(Reader.getLine());
        System.out.println("Введіть опис:");
        toy.setDescription(Reader.getLine());
        System.out.println("Оберіть розмір іграшки");
        toy.setSize(Reader.readSize());
        System.out.println("Оберіть вікову категорію");
        toy.setAge(Reader.readAge());
        System.out.println("Введіть кількість кубиків в наборі");
        toy.setAmount(Reader.getInt());
        System.out.println("Введіть введіть ціну за один");
        toy.setPriceByOne(Reader.getDouble());
        System.out.println("Введіть вагу");
        toy.setWeight(Reader.getDouble());
        return toy;
    }

    /**
     * Creating a doll
     * @return doll toy
     */
    private Doll createDoll() {
        System.out.println("Створення ляльки");
        var toy = new Doll();
        toy.setToyType(ToyType.DOLL);
        System.out.println("Введіть назву:");
        toy.setName(Reader.getLine());
        System.out.println("Введіть опис:");
        toy.setDescription(Reader.getLine());
        System.out.println("Введіть ціну:");
        toy.setPrice(Reader.getDouble());
        System.out.println("Оберіть розмір іграшки");
        toy.setSize(Reader.readSize());
        System.out.println("Оберіть вікову категорію");
        toy.setAge(Reader.readAge());
        System.out.println("Введіть колір волосся");
        toy.setHairColor(Reader.getLine());
        System.out.println("Введіть особивості");
        toy.setSpecialAbility(Reader.getLine());
        System.out.println("Введіть прикраси");
        toy.setJewellery(Reader.getLine());
        return toy;
    }

    /**
     * Creating a car
     * @return car toy
     */
    private Car createCar() {
        System.out.println("Створення машинки");
        var toy = new Car();
        toy.setToyType(ToyType.CAR);
        System.out.println("Введіть назву:");
        toy.setName(Reader.getLine());
        System.out.println("Введіть опис:");
        toy.setDescription(Reader.getLine());
        System.out.println("Введіть ціну:");
        toy.setPrice(Reader.getDouble());
        System.out.println("Оберіть розмір іграшки");
        toy.setSize(Reader.readSize());
        System.out.println("Оберіть вікову категорію");
        toy.setAge(Reader.readAge());
        System.out.println("Введіть швидкість машинки");
        toy.setSpeed(Reader.getDouble());
        System.out.println("Введіть матеріал машинки");
        toy.setMaterial(Reader.getLine());
        System.out.println("Введіть колір машинки");
        toy.setColor(Reader.getLine());
        return toy;
    }

    /**
     * editing a toy by id
     * updating toy data in the database
     */
    public void edit() {
        System.out.println("Введіть номер іграшки");
        long id = Reader.getLong();
        var toy = viewInfo(id);
        switch (toy.getToyType()) {
            case CAR -> {
                toy = editCar(toy);
            }
            case DOLL -> {
                toy = editDoll(toy);
            }
            case CUBES -> {
                toy = editCubes(toy);
            }
            case MUSICAL -> {
                toy = editMusical(toy);
            }
        }
        toyDao.update(toy);
    }

    /**
     * Editing musical toy
     * @param toy info about toy from database
     * @return edited toy
     */
    private Musical editMusical(AbstractToy toy) {
        var musical = (Musical) editCommon(toy);
        while (true) {
            System.out.println("Оберіть параметр іграшки для зміни");
            System.out.println("1. Чи доступний автоплей (true/false)");
            System.out.println("2. Тип музики");
            System.out.println("3. Матеріал");
            System.out.println("0. Зберегти та вийти");
            System.out.println("Оберіть дію:");
            var status = Reader.getInt();
            if (status == 0) {
                return musical;
            }
            System.out.println("Введіть дані:");
            switch (status) {
                case 1 -> musical.setAutoPlayEnabled(Reader.getBoolean());
                case 2 -> musical.setMusicType(Reader.getLine());
                case 3 -> musical.setMaterial(Reader.getLine());
            }
        }
    }

    /**
     * Editing cubes toys
     * @param toy info about toy from database
     * @return edited toy
     */
    private Cubes editCubes(AbstractToy toy) {
        var cubes = (Cubes) editCommon(toy);
        while (true) {
            System.out.println("Оберіть параметр іграшки для зміни");
            System.out.println("1. Кількість");
            System.out.println("2. Ціна за один кубик");
            System.out.println("3. Вага");
            System.out.println("0. Зберегти та вийти");
            System.out.println("Оберіть дію:");
            var status = Reader.getInt();
            if (status == 0) {
                return cubes;
            }
            System.out.println("Введіть дані:");
            switch (status) {
                case 1 -> cubes.setAmount(Reader.getInt());
                case 2 -> cubes.setPriceByOne(Reader.getDouble());
                case 3 -> cubes.setWeight(Reader.getDouble());
            }
        }
    }

    /**
     * Editing doll toy
     * @param toy info about toy from database
     * @return edited toy
     */
    private Doll editDoll(AbstractToy toy) {
        var doll = (Doll) editCommon(toy);
        while (true) {
            System.out.println("Оберіть параметр іграшки для зміни");
            System.out.println("1. Колір волосся");
            System.out.println("2. Особливі здібності");
            System.out.println("3. Прикраси");
            System.out.println("0. Зберегти та вийти");
            System.out.println("Оберіть дію:");
            var status = Reader.getInt();
            if (status == 0) {
                return doll;
            }
            System.out.println("Введіть дані:");
            switch (status) {
                case 1 -> doll.setHairColor(Reader.getLine());
                case 2 -> doll.setSpecialAbility(Reader.getLine());
                case 3 -> doll.setJewellery(Reader.getLine());
            }
        }
    }

    /**
     * Editing carr toy
     * @param toy info about toy from database
     * @return edited toy
     */
    private Car editCar(AbstractToy toy) {
        var car = (Car) editCommon(toy);
        while (true) {
            System.out.println("Оберіть параметр машинки для зміни");
            System.out.println("1. Швидкість");
            System.out.println("2. Матеріал");
            System.out.println("3. Колір");
            System.out.println("0. Зберегти та вийти");
            System.out.println("Оберіть дію:");
            var status = Reader.getInt();
            if (status == 0) {
                return car;
            }
            System.out.println("Введіть дані:");
            switch (status) {
                case 1 -> car.setSpeed(Reader.getDouble());
                case 2 -> car.setMaterial(Reader.getLine());
                case 3 -> car.setColor(Reader.getLine());
            }
        }
    }

    /**
     * general editing
     * @param toy info about toy from database
     * @return edited toy
     */
    private AbstractToy editCommon(AbstractToy toy) {
        while (true) {
            System.out.println("Оберіть параметр для зміни");
            System.out.println("1. Імя");
            System.out.println("2. Опис");
            System.out.println("3. Розмір");
            System.out.println("0. Зберегти та вийти");
            System.out.println("Оберіть дію:");
            var status = Reader.getInt();
            if (status == 0) {
                return toy;
            }
            switch (status) {
                case 1 -> renameToy(toy);
                case 2 -> {
                    System.out.println("Введіть Опис");
                    String value = Reader.getLine();
                    toy.setDescription(value);
                }
                case 3 -> toy.setSize(Reader.readSize());
            }
        }
    }

    /**
     * Rename toy
     * @param toy info about toy from database
     */
    private void renameToy(AbstractToy toy) {
        System.out.println("Введіть нову назву");
        var newName = Reader.getLine();
        toy.setName(newName);
    }

    /**
     * Deleting toy by type and id
     */
    public void delete() {
        var type = Reader.readToyType();
        List<AbstractToy> toys = filterService.filterByType(type);
        for (AbstractToy toy : toys) {
            System.out.println(toy.getId() + " - " + toy);
        }
        System.out.println("Введіть номер іграшки");
        int index = Reader.getInt();
        toyDao.delete(index);
    }

    /**
     * view info about toy
     * @param id toy id
     * @return value of toy
     */
    public AbstractToy viewInfo(long id) {
        var toy = toyDao.getById(id);
        if (toy.isEmpty()) {
            System.err.println("Не існує такого обєкту");
            throw new IllegalArgumentException();
        }
        System.out.println(toy.get());
        return toy.get();
    }

    /**
     * outputs all toys from the database
     * @return true if this list contains no elements
     */
    public boolean viewAllToys() {
        System.out.println("Всі іграшки:");
        var toys = filterService.getAllToys();
        for (AbstractToy toy : filterService.getAllToys()) {
            System.out.println(toy.getId() + " - " + toy);
        }
        return toys.isEmpty();
    }
}
