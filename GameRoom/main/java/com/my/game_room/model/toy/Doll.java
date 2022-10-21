package com.my.game_room.model.toy;

import com.my.game_room.constant.AgeCategory;
import com.my.game_room.constant.SizeType;
import com.my.game_room.constant.ToyType;

public class Doll extends AbstractToy {
    private String hairColor;
    private String specialAbility;
    private String jewellery;

    public Doll() {
    }

    public Doll(long id, String name, String description, double price, SizeType size, AgeCategory age, String hairColor, String jewellery, String specialAbility) {
        super(id, name, description, price, ToyType.DOLL, size, age);
        this.hairColor = hairColor;
        this.jewellery = jewellery;
        this.specialAbility = specialAbility;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getJewellery() {
        return jewellery;
    }

    public void setJewellery(String jewellery) {
        this.jewellery = jewellery;
    }

    public String getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbility(String specialAbility) {
        this.specialAbility = specialAbility;
    }

    @Override
    public double calcPrice() {
        if (!jewellery.isBlank()) {
            return this.price * 2;
        }
        return this.price;
    }

    @Override
    public String toString() {
        return "Toy {Type='" + ToyType.DOLL + "', " +
                getStringTemplate() +
                ", hairColor='" + hairColor + '\'' +
                ", specialAbility='" + specialAbility + '\'' +
                ", jewellery='" + jewellery + '\'' +
                '}';
    }
}
