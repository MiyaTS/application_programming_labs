package org.my.phone;
import java.util.Scanner;

/**
 * Class for storing data about Subscribers
 *
 * @author Yarmoliuk Solomiya
 */

public class Phone {
    private int id;
    private String surname;
    private String name;
    private String lastname;
    private int accountNumber;
    private int cityHours;
    private int distanceHours;
    public static final Scanner scanner = new Scanner(System.in);

    @Override
    public String toString() {
        return "Phone { " +
                "id=" + id + System.lineSeparator() +
                "surname='" + surname + '\'' + System.lineSeparator() +
                "name='" + name + '\'' + System.lineSeparator() +
                "lastname='" + lastname + '\'' + System.lineSeparator() +
                "accountNumber=" + accountNumber + System.lineSeparator() +
                "cityHours=" + cityHours + System.lineSeparator() +
                "distanceHours=" + distanceHours + System.lineSeparator() +
                " }";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCityHours() {
        return cityHours;
    }

    public void setCityHours(int cityHours) {
        this.cityHours = cityHours;
    }

    public int getDistanceHours() {
        return distanceHours;
    }

    public void setDistanceHours(int distanceHours) {
        this.distanceHours = distanceHours;
    }
}
