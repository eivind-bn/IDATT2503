package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        RegMatch.start(Main::readNext, System.out::print);
    }

    private static String readNext(){
        if(!sc.hasNext()) System.exit(0);
        return sc.nextLine();
    }
}
