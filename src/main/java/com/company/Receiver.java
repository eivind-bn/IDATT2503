package com.company;

import java.io.IOException;

interface Receiver<T>{
    T get() throws IOException;
}