package com.code;
import java.util.Scanner;
/**
 * Class for calculating N Fibonacci numbers. Сalculating values for determining the truth of the statement.
 *
 * @author Yarmoliuk Solomiya
 */
public class Fibonacci {
    private int value;
    private int number;
    /**
     * Default constructor.
     */
    public Fibonacci() {
        value = 0;
        number = 0;
    }
    /**
     * Setter for number field
     *
     * @param number of Fibonacci number
     */
    public void setNumber(int number) {
        this.number = number;
    }
    /**
     * Get a number of Fibonacci from console.
     */
    public void initWithConsoleData(){
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("Enter a number of Fibonacci:");
            number = scanner.nextInt();
        }while(number <= 0);
    }
    /**
     * Calculating the value of the N Fibonacci number
     * @param fib is a number of Fibonacci
     * @return value of Fibonacci number
     */
    public int calculateFibonacci(int fib) {
        if (fib == 1 || fib == 2) {
            value = 1;
        } else {
            value =  calculateFibonacci(fib - 1) + calculateFibonacci(fib - 2);
        }
        return value;
    }
    /**
     * Determining the truth of a statement.
     * Output of intermediate calculation results.
     */
    public void checkingTheStatement() {
        int num = number+2;
        value = calculateFibonacci(num);
        System.out.println("value of " + num + " Fibonacci number:");
        System.out.println(value);
        int powValue = (int) Math.pow(2, number);
        System.out.println("value 2 to the power of n(" + number + "):");
        System.out.println(powValue);
        if(value < powValue){
            System.out.println("Тhe statement "+ value + " < " + powValue +" is correct!");
        }else{
            System.out.println("Тhe statement "+ value + " < " + powValue +" isn't correct!");
        }
    }
    /**
     * Method to demonstrate functionality of class.
     *
     * @param args : number of Fibonacci number
     */
    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        if (args.length != 0) {
            getDataFromCommandParameters(args, fibonacci);
        } else {
            fibonacci.initWithConsoleData();
        }
        fibonacci.value = fibonacci.calculateFibonacci(fibonacci.number);
        System.out.println("Fibonacci number value:");
        System.out.println(fibonacci.value);
        fibonacci.checkingTheStatement();
    }
    /**
     * Parse number of Fibonacci number from command line parameters.
     *
     * @param args command line parameters
     * @param fibonacci object for saving data
     */
    private static void getDataFromCommandParameters(String[] args, Fibonacci fibonacci) {
        int temp = Integer.parseInt(args[0]);
        if (temp <= 0) {
            System.out.println("A number of element can't be less than 0");
        }
        fibonacci.setNumber(temp);
    }
}