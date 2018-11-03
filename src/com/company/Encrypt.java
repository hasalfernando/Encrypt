package com.company;

import java.util.ArrayList;

public class Encrypt {
    enum Directions {LEFT, RIGHT};

    private String message;
    private String gamma;
    private Directions direct;
    private int bits;

    Encrypt(String message,String gamma,Directions direct,int bits) {
        this.message=message;
        this.gamma=gamma;
        this.direct=direct;
        this.bits=bits;
    }

    private String gammingString(String message, String gamma) {
        String gammingMessage = "";
        for (int i = 0; i < message.length(); i++) {
            int mes;
            int gam;
            if (i < gamma.length()) {
                mes = message.charAt(i);
                gam = gamma.charAt(i);
                gammingMessage += (char) ((mes ^ gam) % 128);
            } else {
                mes = message.charAt(i);
                int gammaLenght = i;
                while (gammaLenght >= gamma.length()) {
                    gammaLenght = Math.abs(gammaLenght - gamma.length());
                }
                gam = gamma.charAt(gammaLenght);
                gammingMessage += (char) ((mes ^ gam) % 128);
            }
        }
        return gammingMessage;
    }

    private String shiftString(Directions direct, int bits, String message) {
        String shiftedMessage="";
        switch (direct) {
            case LEFT: {
                shiftedMessage = message.substring(message.length() - bits) + message.substring(0, message.length()-bits);
                return shiftedMessage;
            }
            case RIGHT: {
                shiftedMessage = message.substring(bits) + message.substring(0,bits);
                return shiftedMessage;
            }
            default:
                return null;
        }
    }

    private ArrayList<String> messageToArray(String message) {
        int count=0;
        String word="";
        ArrayList<String> words=new ArrayList<>();
        while (count<message.length()) {
            while (count<message.length()) {
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

    private void gammingArray(ArrayList<String> words, String gamma) {
        for (int i=0;i<words.size();i++) {
            words.set(i, gammingString(words.get(i),gamma));
        }
    }

    private String arrayToBinar(ArrayList<String> words) {
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
    /*private String messageToBinar(String message) {
        String binar = "";
        for (int i = 0; i < message.length(); i++) {
            int bin = message.charAt(i);
            StringBuilder builder = new StringBuilder(Integer.toBinaryString(bin));
            while (builder.length() != 7) {
                builder.insert(0, '0');
            }
            binar += builder.toString();
        }
        return binar;
    }*/

   private ArrayList<String> binaryToArray(String binary) {
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

    private String binaryToString(String binary) {
        ArrayList<String> words = new ArrayList<>();
        String word = "";
        String message = "";
        for (int i = 0; i < binary.length(); i++) {
            if (i % 7 == 0 && i != 0) {
                if(!word.isEmpty()) {
                    System.out.println(word);
                    message += (char) Integer.parseInt(word, 2);
                }
                word = "";
                word += binary.charAt(i);
            } else {
                word += binary.charAt(i);
            }
        }
        return message;
    }

    private void changeArray(ArrayList<String> words,ArrayList<String> symbols) {
        String word="";
        int count=0;
        for(int i=0;i<words.size();i++) {
            for(int j=0;j<words.get(i).length();j++) {
                word+=(char)Integer.parseInt(symbols.get(count),2);
                count++;
            }
            words.set(i,word);
            word="";
        }
    }

   private String arrayToMessage(ArrayList<String> words) {
        String message="";
        for(int i=0;i<words.size();i++) {
            message+=words.get(i);
        }
        return message;
    }

    public void encrypt () {
        /*message=gammingString(message,gamma);
        System.out.println(message);
        message= binaryToString(shiftString(direct,bits,messageToBinar(message)));
        System.out.println(message);*/
        ArrayList<String> words = messageToArray(message);
        gammingArray(words, gamma);
        ArrayList<String> shiftSymbols = binaryToArray(shiftString(direct, bits, arrayToBinar(words)));
        changeArray(words, shiftSymbols);
        message= arrayToMessage(words);
        System.out.println(message);
    }
    public void de_encrypt () {
        Directions newDirect;
        if (direct == Directions.LEFT) {
            newDirect = Directions.RIGHT;
        } else {
            newDirect = Directions.LEFT;
        }
        ArrayList<String> words = messageToArray(message);
        ArrayList<String> shiftSymbols = new ArrayList<>();
        shiftSymbols = binaryToArray(shiftString(newDirect, bits, arrayToBinar(words)));
        changeArray(words, shiftSymbols);
        gammingArray(words, gamma);
        message = arrayToMessage(words);
        System.out.println(message);
       /* Directions newDirect;
        if (direct == Directions.LEFT) {
            newDirect = Directions.RIGHT;
        } else {
            newDirect = Directions.LEFT;
        }
        message= binaryToString(shiftString(direct,bits,messageToBinar(message)));
        message=gammingString(message,gamma);
        System.out.println(message);*/
    }

    public void howDoYouWork() {
        ArrayList<String> words = messageToArray(message);
        System.out.println("1) Берется сообщение и разделяется на массив слов\n" +
                "Из"+message+" получается "+words);
        gammingArray(words,gamma);
        System.out.println("2) Далее этот массив слов гаммируется с помощью ключа\n" +
                "Из"+messageToArray(message)+" получается "+words);
        ArrayList<String> shiftSymbols = binaryToArray(shiftString(direct, bits, arrayToBinar(words)));
        System.out.println("3) Далее массив переводится в бинарное представление(для символов бинарная форма которого меьнше 7 дописываются вначале 0)\n" +
                "Из"+ words+" получается "+arrayToBinar(words));
        System.out.println("4) Далее бинарное представление сдвигается по битам\n" +
                "Из"+ arrayToBinar(words)+" получается "+shiftString(direct,bits,arrayToBinar(words)));
        System.out.println("5) Далее бинарное представление со сдвигом делится на массив символов\n" +
                "Из"+shiftString(direct,bits,arrayToBinar(words))+" получается "+shiftSymbols);
        changeArray(words, shiftSymbols);
        System.out.println("6) Далее из массива символв с помощью гаммированного массива мы формируем зашифрованный массив \n" +
                "Из"+shiftSymbols+" получается "+ words);
        System.out.println("7) Зашифрованный массив переводится в строку\n" +
                "Из"+ words+" получается "+arrayToMessage(words));
    }
}