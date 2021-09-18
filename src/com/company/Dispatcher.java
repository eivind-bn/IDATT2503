package com.company;

import java.io.IOException;

interface Dispatcher<T>{
    void dispatch(T data) throws IOException;
}
