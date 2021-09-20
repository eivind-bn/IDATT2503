package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        try {
            EasySocket.start(new RegMatch(), Integer.parseInt(args[0]));
        } catch (IndexOutOfBoundsException ignored){
            System.out.println("Please specify port as follows: 'java -jar RegMatch.jar 1234'.");
        } catch (NumberFormatException | IOException e){
            System.out.println(e.getMessage());
        }
    }
}
