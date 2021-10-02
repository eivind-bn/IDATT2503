package com.company;

import java.io.IOException;
import java.net.Socket;

interface Server {
    default void init(Receiver<String> input, Dispatcher<String> output) throws IOException{}
    void execute(Receiver<String> input, Dispatcher<String> output) throws IOException;
}
