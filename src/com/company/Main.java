package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String gamma = scan.nextLine();
        String message = scan.nextLine();
        Encrypt test=new Encrypt(message,gamma, Encrypt.Directions.LEFT,7);
        test.encrypt();
        test.de_encrypt();
        test.howDoYouWork();
        ///////////
    }
}
