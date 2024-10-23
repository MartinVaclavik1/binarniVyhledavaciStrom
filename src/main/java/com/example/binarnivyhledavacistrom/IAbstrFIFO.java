package com.example.binarnivyhledavacistrom;

public interface IAbstrFIFO<T> {
    void zrus();

    boolean jePrazdny();

    void vloz(T data);

    T odeber();
}
