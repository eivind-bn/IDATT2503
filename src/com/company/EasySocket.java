package com.company;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;

public class EasySocket {

    private EasySocket() {}

    public static void start(Server context, int port) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));


        while (!serverSocket.isClosed()){
            try {
                connect(context, serverSocket);
            } catch (IOException ignored){}
        }
    }

    private static void connect(Server context, ServerSocket server) throws IOException, InterruptedException {
        Socket session = server.accept();
        BufferedInputStream i = new BufferedInputStream(session.getInputStream());
        BufferedOutputStream o = new BufferedOutputStream(session.getOutputStream());

        if(!session.isClosed()) context.init(session, () -> receiveNext(i), data -> send(data, o));
        while (!session.isClosed()) context.execute(session, () -> receiveNext(i), data -> send(data, o));
    }

    private static String receiveNext(BufferedInputStream input) throws IOException {
        LinkedList<Byte> bytes = new LinkedList<>();
        while (true){
            if(input.available() > 0) bytes.add((byte) input.read());
            else if(bytes.size() > 0) return parseToUtf8(bytes);
        }
    }

    private static void send(String data, BufferedOutputStream output) throws IOException {
        output.write(data.getBytes(StandardCharsets.UTF_8));
        output.flush();
    }

    private static String parseToUtf8(LinkedList<Byte> bytes){
        byte[] pArray = new byte[bytes.size()];
        Iterator<Byte> byteIterator = bytes.iterator();

        for (int i = 0; i < pArray.length; i++) pArray[i] = byteIterator.next();

        return new String(pArray, StandardCharsets.UTF_8);
    }
}
