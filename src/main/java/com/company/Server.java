package com.company;

import java.io.IOException;
import java.net.Socket;

interface Server {
    default void init(Socket session, Receiver<String> input, Dispatcher<String> output) throws IOException{}
    void execute(Socket session, Receiver<String> input, Dispatcher<String> output) throws IOException;
}
