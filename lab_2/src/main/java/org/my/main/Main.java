package org.my.main;
import org.my.phone.Phone;
import java.util.ArrayList;
import java.util.List;

/**
 * Phone: id, Прізвище, Ім'я, По батькові, Номер рахунку, Час міських
 *
 * розмов, Час міжміських розмов.
 * Скласти масив об'єктів. Вивести:
 * a) відомості про абонентів, у яких час міських розмов перевищує
 * заданий;
 * b) відомості про абонентів, які користувались міжміським зв'язком;
 * c) відомості про абонентів чий номер рахунку знаходиться у
 * вказаному діапазоні.
 */

/**
 * Class for demonstrating of functionality of Phone class.
 * <p>
 * Additional functionality: reading data from the console and filtering data by distance communication, city call time, and getting data by number from the range.
 * </p>
 *
 * @author Yarmoliuk Solomiya
 */

public class Main {

    /**
     * Demonstrative methods.
     *
     * @param args array of command line parameters
     */
    public static void main(String[] args) {
        var array = initArray();
        filterByCityHours(array);
        filterByDistanceHours(array);
        filterByNumber(array);
    }

    /**
     * Filter and print object by city conversation time that is longer than the specified one
     * @param phones list of objects of Phone class
     */
    public static void filterByCityHours(List<Phone> phones) {
        System.out.println(System.lineSeparator() + "Enter the city hour: ");
        int hours = Integer.parseInt(Phone.scanner.nextLine());
        System.out.println(System.lineSeparator() + "Information about subscribers whose city сonversation time is longer than " + hours);

        boolean isEmpty = false;
        for (Phone phone : phones) {
            if (phone.getCityHours() > hours){
                System.out.println(phone);
            }else{
                isEmpty = true;
            }
        }
        if (isEmpty){
            System.out.println("none");
        }
    }

    /**
     * Filter and print object that used distance communication
     * @param phones list of objects of Phone class
     */
    public static void filterByDistanceHours(List<Phone> phones) {
        System.out.println(System.lineSeparator() + "Information about subscribers who used distance communication");
        boolean isEmpty = false;
        for (Phone phone : phones) {
            if (phone.getDistanceHours() > 0){
                System.out.println(phone);
            }else{
                isEmpty = true;
            }
        }
        if (isEmpty){
            System.out.println("none");
        }
    }

    /**
     * Filter and print object whose number is in the specified range
     * @param phones list of objects of Phone class
     */
    public static void filterByNumber(List<Phone> phones) {
        System.out.println(System.lineSeparator() + "Enter a range: ");
        System.out.println("Enter 1 number from range: ");
        int num1 = Integer.parseInt(Phone.scanner.nextLine());
        System.out.println("Enter 2 number from range: ");
        int num2 = Integer.parseInt(Phone.scanner.nextLine());
        if(num2 < num1){
            int temp = num2;
            num2 = num1;
            num1 = temp;
        }
        int finalNum2 = num2;
        int finalNum1 = num1;
        System.out.println(System.lineSeparator() + "Information about subscribers whose number is in the range from " + finalNum1 + " to " + finalNum2);
        boolean isEmpty = false;
        for (Phone phone : phones) {
            if (phone.getAccountNumber() < finalNum2 && phone.getAccountNumber() > finalNum1){
                System.out.println(phone);
            }else{
                isEmpty = true;
            }
        }
        if (isEmpty){
            System.out.println("none");
        }
    }

    /**
     * Read data from console and inits objects.
     *
     * @return List of objects of Phone class
     */
    public static List<Phone> initArray() {
        var array = new ArrayList<Phone>();
        boolean isRead = true;
        while (isRead) {
            Phone phone = new Phone();
            int num;
            System.out.println("Enter data:" + System.lineSeparator());
            do{
                System.out.println("Enter id (should be more than 0): ");
            }while((num = Integer.parseInt(Phone.scanner.nextLine())) <= 0);
            phone.setId(num);
            System.out.println("Enter the surname: ");
            phone.setSurname(Phone.scanner.nextLine());
            System.out.println("Enter the name: ");
            phone.setName(Phone.scanner.nextLine());
            System.out.println("Enter the lastname: ");
            phone.setLastname(Phone.scanner.nextLine());
            System.out.println("Enter the account number: ");
            phone.setAccountNumber(Integer.parseInt(Phone.scanner.nextLine()));
            System.out.println("Enter the city hours: ");
            phone.setCityHours(Integer.parseInt(Phone.scanner.nextLine()));
            System.out.println("Enter the distance hours: ");
            phone.setDistanceHours(Integer.parseInt(Phone.scanner.nextLine()));
            System.out.println("Enter 0 to finish or 1 to continue writing data");
            if (Integer.parseInt(Phone.scanner.nextLine()) == 0) {
                isRead = false;
            }
            array.add(phone);
        }
        return array;
    }
}