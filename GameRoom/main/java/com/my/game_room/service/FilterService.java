package com.my.game_room.service;

import com.my.game_room.constant.AgeCategory;
import com.my.game_room.constant.SizeType;
import com.my.game_room.constant.ToyType;
import com.my.game_room.dao.Dao;
import com.my.game_room.dao.ToyDao;
import com.my.game_room.model.toy.AbstractToy;
import com.my.game_room.util.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class for filtering by parameters
 */

public class FilterService {
    private final Dao<AbstractToy> toyDao;

    public FilterService() {
        toyDao = new ToyDao();
    }

    /**
     * @return all toys from database
     */
    public List<AbstractToy> getAllToys() {
        return toyDao.getAll();
    }

    /**
     * selecting toys with a given type among all from the database
     * @param toyType type of toy
     * @return list of filtered toys
     */
    public List<AbstractToy> filterByType(ToyType toyType) {
        return toyDao.getAll().stream().filter(e -> e.getToyType().equals(toyType)).collect(Collectors.toList());
    }

    /**
     * filter by price
     * @param list of toys
     * @return list of filtered toys
     */
    public List<AbstractToy> filterByPrice(List<AbstractToy> list) {
        System.out.println("По ціні");
        System.out.println("1. Вище ніж");
        System.out.println("2. Нижче ніж");
        int priceOrderRule = Reader.getInt();
        System.out.println("Введіть опорну ціну");
        double priceRule = Reader.getDouble();
        switch (priceOrderRule) {
            case 1 -> {
                return list.stream().filter(e -> e.calcPrice() >= priceRule).collect(Collectors.toList());
            }
            case 2 -> {
                return list.stream().filter(e -> e.calcPrice() <= priceRule).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    /**
     * filter by size
     * @param sizeRule max size
     * @param list of toys
     * @return list of filtered toys
     */
    public List<AbstractToy> filterBySize(SizeType sizeRule, List<AbstractToy> list) {
        System.out.println("Максимально дозволений розмір - " + sizeRule);
        System.out.println("По Розміру");
        System.out.println("1. Малий");
        System.out.println("2. Середній");
        System.out.println("3. Великий");
        System.out.println("4. Дуже великий");
        int sizeOrderRule = Reader.getInt();
        switch (sizeOrderRule) {
            case 1 -> {
                return list.stream().filter(e -> SizeType.SMALL.equals(e.getSize())).collect(Collectors.toList());
            }
            case 2 -> {
                if (SizeType.MEDIUM.ordinal() <= sizeRule.ordinal()) {
                    return list.stream().filter(e -> SizeType.MEDIUM.equals(e.getSize())).collect(Collectors.toList());
                }
                System.err.println("Розмір не відповідає правилу");
                return new ArrayList<>();
            }
            case 3 -> {
                if (SizeType.BIG.ordinal() <= sizeRule.ordinal()) {
                    return list.stream().filter(e -> SizeType.BIG.equals(e.getSize())).collect(Collectors.toList());
                }
                System.err.println("Розмір не відповідає правилу");
                return new ArrayList<>();
            }
            case 4 -> {
                if (SizeType.HUGE.ordinal() <= sizeRule.ordinal()) {
                    return list.stream().filter(e -> SizeType.HUGE.equals(e.getSize())).collect(Collectors.toList());
                }
                System.err.println("Розмір не відповідає правилу");
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    /**
     * filter by age
     * @param ages age
     * @param list list of toys
     * @return list of filtered toys
     */
    public List<AbstractToy> filterByAge(List<AgeCategory> ages, List<AbstractToy> list) {
        return list.stream().filter(e -> ages.contains(e.getAge())).collect(Collectors.toList());
    }
}
