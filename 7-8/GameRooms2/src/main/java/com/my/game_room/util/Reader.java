package com.my.game_room.util;

import com.my.game_room.constant.AgeCategory;
import com.my.game_room.constant.SizeType;
import com.my.game_room.constant.ToyType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * class for reading information
 */
public class Reader {
    private static final Logger LOG = LogManager.getLogger();
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * reading boolean value
     * @return boolean value
     */
    public static boolean getBoolean() {
        if (!SCANNER.hasNextBoolean()) {
            LOG.warn("Неправильний boolean");
            throw new NumberFormatException();
        }
        boolean value = SCANNER.nextBoolean();
        SCANNER.nextLine();
        return value;
    }

    /**
     * reading int value
     * @return int value
     */
    public static int getInt() {
        if (!SCANNER.hasNextInt()) {
            LOG.warn("Неправильний формат int");
            throw new NumberFormatException();
        }
        int value = SCANNER.nextInt();
        SCANNER.nextLine();
        return value;
    }

    /**
     * reading long value
     * @return long value
     */
    public static long getLong() {
        if (!SCANNER.hasNextLong()) {
            LOG.warn("Неправильний формат long");
            throw new NumberFormatException();
        }
        long value = SCANNER.nextLong();
        SCANNER.nextLine();
        return value;
    }

    /**
     * reading double value
     * @return double value
     */
    public static double getDouble() {
        if (!SCANNER.hasNextDouble()) {
            LOG.warn("Неправильний формат double");
            throw new NumberFormatException();
        }
        double value = SCANNER.nextDouble();
        SCANNER.nextLine();
        return value;
    }

    /**
     * reading string value
     * @return string value
     */
    public static String getLine() {
        var line = SCANNER.nextLine();
        if (line.isBlank()) {
            LOG.warn("Введено порожній рядок");
            throw new IllegalArgumentException();
        }
        return line;
    }

    /**
     * reads the size of the toy
     * @return size type
     */
    public static SizeType readSize() {
        System.out.println("1. Малий");
        System.out.println("2. Середній");
        System.out.println("3. Великий");
        System.out.println("4. Надто великий");
        switch (Reader.getInt()) {
            case 1 -> {
                return SizeType.SMALL;
            }
            case 2 -> {
                return SizeType.MEDIUM;
            }
            case 3 -> {
                return SizeType.BIG;
            }
            case 4 -> {
                return SizeType.HUGE;
            }
        }
        LOG.warn("Немає такого розміру");
        throw new IllegalArgumentException();
    }

    /**
     * reads the type of the toy
     * @return toy type
     */
    public static ToyType readToyType() {
        System.out.println("Оберіть тип іграшки:");
        System.out.println("1. Машинка");
        System.out.println("2. Лялька");
        System.out.println("3. Кубики");
        System.out.println("4. Музикальний інструмент");
        var type = Reader.getInt();
        switch (type) {
            case 1 -> {
                return ToyType.CAR;
            }
            case 2 -> {
                return ToyType.DOLL;
            }
            case 3 -> {
                return ToyType.CUBES;
            }
            case 4 -> {
                return ToyType.MUSICAL;
            }
            default -> {
                LOG.warn("Неправильний тип");
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * reading of age categories
     * @return age category
     */
    public static AgeCategory readAge() {
        var categories = initAgeCategories();
        int status = Reader.getInt();
        return categories.get(status);
    }

    /**
     * selecting multiple age categories
     * @return list of age categories
     */
    public static List<AgeCategory> getAgeCategories() {
        System.out.println("По віку - оберіть вікові категорії кімнати по черзі (для завершення введіть 0)");
        Map<Integer, AgeCategory> ages = initAgeCategories();
        var list = new ArrayList<AgeCategory>();
        while (true) {
            int ageRule = Reader.getInt();
            if (ageRule < 1 || ageRule > 5) {
                break;
            }
            if (!list.contains(ages.get(ageRule))) {
                list.add(ages.get(ageRule));
            }
        }
        return list;
    }

    /**
     * output of age categories
     * @return HashMap of age
     */
    private static Map<Integer, AgeCategory> initAgeCategories() {
        Map<Integer, AgeCategory> ages = new HashMap<>();
        ages.put(1, AgeCategory.BABY);
        ages.put(2, AgeCategory.TODDLER);
        ages.put(3, AgeCategory.CHILD);
        ages.put(4, AgeCategory.TEEN);
        ages.put(5, AgeCategory.ADULT);
        System.out.println("1. Немовлята");
        System.out.println("2. Дитина");
        System.out.println("3. Школяр");
        System.out.println("4. Підліток");
        System.out.println("5. Дорослий");
        return ages;
    }
}
