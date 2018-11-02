package com.company;

import javafx.scene.control.skin.TextInputControlSkin;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    enum Directions {LEFT,RIGHT};

    public static String encrypt(String message,String gamma) {
        String encyptedMessage = "";
        for (int i = 0; i < message.length(); i++) {
            int mes;
            int gam;
            if (message.charAt(i) != ' ') {
                if (i < gamma.length()) {
                    mes = message.charAt(i);
                    gam = gamma.charAt(i);
                    encyptedMessage += (char) ((mes ^ gam) % 128);
                } else {
                    mes = message.charAt(i);
                    int gammaLenght = i;
                    while (gammaLenght >= gamma.length()) {
                        gammaLenght = Math.abs(gammaLenght - gamma.length());
                    }
                    gam = gamma.charAt(gammaLenght);
                    encyptedMessage += (char) ((mes ^ gam) % 128);
                }
            } else {
                encyptedMessage += message.charAt(i);
            }
        }
        return encyptedMessage;
    }

    public static String shift(Directions direct,int bits,String message) {
        String encyptedMessage="";
        switch (direct) {
            case LEFT: {
                encyptedMessage = message.substring(message.length() - bits) + message.substring(0, message.length()-bits);
                return encyptedMessage;
            }
            case RIGHT: {
                encyptedMessage = message.substring(bits) + message.substring(0,bits);
                return encyptedMessage;
            }
            default:
                return null;
        }
    }

    public static ArrayList<String> messageToArray(String message) {
        int count=0;
        String word="";
        ArrayList<String> words=new ArrayList<>();
        while (count<message.length()) {
            while (count<message.length()&&message.charAt(count)!=' ') {
                word+=message.charAt(count);
                count++;
            }
            if(!word.isEmpty()){
                words.add(word);
            }
            word="";
            count++;
        }
        if(!word.isEmpty()) {
            words.add(word);
        }
        return words;
    }

    public static void encryptArray(ArrayList<String> words,String gamma) {
        for (int i=0;i<words.size();i++) {
            words.set(i,encrypt(words.get(i),gamma));
        }
    }

    public static String arrayToBinar(ArrayList<String> words) {
        String binar = "";
        for (int i = 0; i < words.size(); i++) {
            for (int j = 0; j < words.get(i).length(); j++) {
                int bin = words.get(i).charAt(j);
                StringBuilder builder = new StringBuilder(Integer.toBinaryString(bin));
                while (builder.length() != 7) {
                    builder.insert(0, '0');
                }
                binar += builder.toString();
            }
        }
        return binar;
    }

    public static ArrayList<String> binaryToArray(String binary) {
        ArrayList<String> words=new ArrayList<>();
        String word="";
        for (int i=0;i<binary.length();i++) {
            if(i%7==0&&i!=0) {
                words.add(word);
                word="";
                word+=binary.charAt(i);
            } else {
                word+=binary.charAt(i);
            }
        }
        words.add(word);
        return words;
    }

    public static void changeArray(ArrayList<String> words,ArrayList<String> words1) {
        String word="";
        int count=0;
        for(int i=0;i<words.size();i++) {
            for(int j=0;j<words.get(i).length();j++) {
                word+=(char)Integer.parseInt(words1.get(count),2);
                count++;
            }
            words.set(i,word);
            word="";
        }
    }

    public static String arrayToMessage(ArrayList<String> words) {
        String message="";
        for(int i=0;i<words.size();i++) {
            message+=words.get(i)+" ";
        }
        return message;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String gamma;
        String message;
        gamma = Integer.toString(new Random().nextInt(32767));
        System.out.println("Введите сообщение на английском языке");
        message = scan.nextLine();
        System.out.println("ШИФРОВВАНИЕ");
        ArrayList<String> words = messageToArray(message);
        encryptArray(words, gamma);
        System.out.println("Зашифрованный предложение методом гамирования:" + words);
        System.out.println("Его бинанрное представление: " + arrayToBinar(words));
        System.out.println("Бинарное предстваление со сдвигом: " + shift(Directions.LEFT, 9, arrayToBinar(words)));
        ArrayList<String> words1 = binaryToArray(shift(Directions.LEFT, 9, arrayToBinar(words)));
        changeArray(words, words1);
        System.out.println("Зашифрованное исходное предложение методом гамирования с пораздрядным циклическим сдивгом: " + words);
        System.out.println("ДЕШИФРОВАНИЕ");
        System.out.println("Зашифрованное исходное предложение методом гамирования с пораздрядным циклическим сдивгом: " + words);
        System.out.println("Его бинанрное представление: " + arrayToBinar(words));
        System.out.println("Бинарное предстваление со сдвигом: " + shift(Directions.RIGHT, 9, arrayToBinar(words)));
        words1 = binaryToArray(shift(Directions.RIGHT, 9, arrayToBinar(words)));
        changeArray(words, words1);
        encryptArray(words, gamma);
        System.out.println("Расшифорованное предложение: " + arrayToMessage(words));
        ///////////
    }
}
