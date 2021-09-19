package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;
        try {
            port = Integer.parseInt(args[0]);
        } catch (IndexOutOfBoundsException | NumberFormatException ignored){}
        EasySocket.start(new RegMatch(), port);
    }
}
