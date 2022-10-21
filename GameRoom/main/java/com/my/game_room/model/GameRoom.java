package com.my.game_room.model;

import com.my.game_room.constant.AgeCategory;
import com.my.game_room.constant.SizeType;
import com.my.game_room.model.toy.AbstractToy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameRoom {
    private long id;
    private String name;
    private String description;
    private double totalPrice;
    private SizeType size;
    private List<AgeCategory> ages;
    private List<AbstractToy> toys;

    public GameRoom() {
    }

    public GameRoom(long id, String name, String description, double totalPrice, SizeType size) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice;
        this.size = size;
        this.ages = new ArrayList<>();
        this.toys = new ArrayList<>();
    }

    public GameRoom(long id, String name, String description, double totalPrice, SizeType size, List<AgeCategory> ages, List<AbstractToy> toys) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice;
        this.size = size;
        this.ages = ages;
        this.toys = toys;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public SizeType getSize() {
        return size;
    }

    public void setSize(SizeType size) {
        this.size = size;
    }

    public List<AgeCategory> getAges() {
        return ages;
    }

    public void setAges(List<AgeCategory> ages) {
        this.ages = ages;
    }

    public List<AbstractToy> getToys() {
        return toys;
    }

    public void setToys(List<AbstractToy> toys) {
        this.toys = toys;
    }

    /**
     * validation
     * @return boolean
     */
    public boolean validate() {
        double toyPrice = 0;
        if (name.isBlank()) {
            System.err.println("Порожнє імя!");
            return false;
        }
        for (AbstractToy toy : getToys()) {
            toyPrice += toy.calcPrice();
            if (toy.getSize().ordinal() > getSize().ordinal()) {
                System.err.println("Неправильний розмір");
                return false;
            }
            if (!getAges().contains(toy.getAge())) {
                System.err.println("Неправильний вік");
                return false;
            }
        }
        if (toyPrice > getTotalPrice()) {
            System.err.println("Занадто велика сукупна вартість");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameRoom{" + System.lineSeparator() +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", size='" + size + "', " +
                System.lineSeparator() +
                "ages: '" + agesToString() + "'," +
                System.lineSeparator() +
                "toys: " + toysToString() +
                System.lineSeparator() +
                '}';
    }

    private String agesToString() {
        StringBuilder line = new StringBuilder();
        for (AgeCategory ageCategory : ages) {
            line.append(ageCategory).append(" ");
        }
        return line.toString();
    }

    private String toysToString() {
        StringBuilder line = new StringBuilder();
        for (AbstractToy toy : toys) {
            line.append(System.lineSeparator());
            line.append(toy);
        }
        return line.toString();
    }
}
