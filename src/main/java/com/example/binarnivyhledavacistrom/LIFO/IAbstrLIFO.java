package com.example.binarnivyhledavacistrom.LIFO;

public interface IAbstrLIFO<T> {
    void zrus();

    boolean jePrazdny();

    void vloz(T data);

    T odeber();
}
