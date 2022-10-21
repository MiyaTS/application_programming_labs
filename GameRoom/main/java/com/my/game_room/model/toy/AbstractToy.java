package com.my.game_room.model.toy;

import com.my.game_room.constant.AgeCategory;
import com.my.game_room.constant.SizeType;
import com.my.game_room.constant.ToyType;

/**
 * Abstract Toy Class
 */
public abstract class AbstractToy {
    private long id;
    private String name;
    private String description;

    protected double price;
    private ToyType toyType;
    private SizeType size;
    private AgeCategory age;

    public AbstractToy() {
    }

    public AbstractToy(long id, String name, String description, ToyType toyType, SizeType size, AgeCategory age) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = 0;
        this.toyType = toyType;
        this.size = size;
        this.age = age;
    }

    public AbstractToy(long id, String name, String description, double price, ToyType toyType, SizeType size, AgeCategory age) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.toyType = toyType;
        this.size = size;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public ToyType getToyType() {
        return toyType;
    }

    public void setToyType(ToyType toyType) {
        this.toyType = toyType;
    }

    public SizeType getSize() {
        return size;
    }

    public void setSize(SizeType size) {
        this.size = size;
    }

    public AgeCategory getAge() {
        return age;
    }

    public void setAge(AgeCategory age) {
        this.age = age;
    }

    public abstract double calcPrice();

    protected String getStringTemplate() {
        return "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", size='" + size + '\'' +
                ", age='" + age + '\'';
    }
}
