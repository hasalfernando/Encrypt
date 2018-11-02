package com.company;

import java.util.Random;
import java.util.Scanner;

public class Encrypt {
    final static int RAND_MAX = 32767;
    final static String binary[] = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};

    public String intToBinary(int num) {
        String binar = "";
        for (int i = 0; i < Integer.toString(num).length(); i++) {
            binar += (binary[Integer.parseInt(Character.toString(Integer.toString(num).charAt(i)))] + " ");
        }
        return binar;
    }



    public static void encrypted() {
        String gamma;
        String message;
        String encyptedMessage = "";
        Scanner scan = new Scanner(System.in);
        System.out.println("сообщение");
        message = scan.next();
        gamma = Integer.toString(new Random().nextInt(32000));
        for (int i = 0; i < message.length(); i++) {
            int mes;
            int gam;
            if (message.charAt(i) != ' ') {
                try {
                    mes = message.charAt(i);
                    gam = gamma.charAt(i);
                    encyptedMessage += (char) ((mes ^ gam) % 122);
                } catch (StringIndexOutOfBoundsException e) {
                    mes = message.charAt(i);
                    gam = gamma.charAt(i - gamma.length());
                    encyptedMessage += (char) ((mes ^ gam) % 122);
                }
            } else {
                encyptedMessage += message.charAt(i);
            }
        }
        message = "";
        for (int i = 0; i < encyptedMessage.length(); i++) {
            int mes;
            int gam;
            if (encyptedMessage.charAt(i) != ' ') {
                try {
                    mes = encyptedMessage.charAt(i);
                    gam = gamma.charAt(i);
                    message += (char) ((mes ^ gam) % 122);
                } catch (StringIndexOutOfBoundsException e) {
                    mes = encyptedMessage.charAt(i);
                    gam = gamma.charAt(i - gamma.length());
                    message += (char) ((mes ^ gam) % 122);
                }
            } else {
                message += encyptedMessage.charAt(i);
            }
        }
        System.out.println(encyptedMessage);
        System.out.println(message);
    }
}
