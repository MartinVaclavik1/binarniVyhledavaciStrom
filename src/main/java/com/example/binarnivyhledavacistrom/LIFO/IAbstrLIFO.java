package com.example.binarnivyhledavacistrom.LIFO;

import java.util.Iterator;

public interface IAbstrLIFO<T> {
    void zrus();

    boolean jePrazdny();

    void vloz(T data);

    T odeber();
    Iterator<T> vytvorIterator();
}
