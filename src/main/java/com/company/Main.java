package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {


    public static void main(String[] args) throws IOException {
        RegMatch.start(Main::readNext, System.out::print);
    }

    /*
    Read manually. Scanner relies on delimiters. For compatibility reasons, I had to resort to my
    this implementation. Here I simply read every byte available in very short time span, and assumes it to be a
    single message.
     */

    private static String readNext() throws IOException {
        LinkedList<Byte> byteList = new LinkedList<>();
        InputStream in = System.in;
        while (in.available() < 1){/*wait*/}
        while (in.available() > 0) byteList.add((byte) in.read());

        byte[] byteArr = new byte[byteList.size()];
        Iterator<Byte> byteIter = byteList.iterator();
        for (int i = 0; i < byteArr.length; i++) {
            byteArr[i] = byteIter.next();
        }
        return new String(byteArr, StandardCharsets.UTF_8);
    }
}
