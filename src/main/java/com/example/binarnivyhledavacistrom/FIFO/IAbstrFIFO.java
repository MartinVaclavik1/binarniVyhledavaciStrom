package com.example.binarnivyhledavacistrom.FIFO;

import java.util.Iterator;

public interface IAbstrFIFO<T> {
    void zrus();

    boolean jePrazdny();

    void vloz(T data);

    T odeber();

    Iterator<T> vytvorIterator();
}
