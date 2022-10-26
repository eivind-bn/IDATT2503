package ebn.regmatch;

import java.io.IOException;

interface Receiver<T>{
    T get() throws IOException;
}