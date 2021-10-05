package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        RegMatch.start(Main::readNext, System.out::print);
    }

    private static String readNext(){
        while (sc.hasNext()){
            String next = sc.nextLine();
            if(next.strip().equals("")) continue;
            return next;
        }
        System.exit(0);
        throw new RuntimeException();
    }
}
