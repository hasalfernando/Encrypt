package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String gamma;
        String message;
        System.out.println("Введите cообщение");
        message = scan.nextLine();
        System.out.println("Введите ключ");
        gamma = scan.nextLine();
        Encrypt test=new Encrypt(message,gamma, Encrypt.Directions.LEFT,3);
        test.encrypt();
        test.howDoYouWork();
        ///////////
    }
}
