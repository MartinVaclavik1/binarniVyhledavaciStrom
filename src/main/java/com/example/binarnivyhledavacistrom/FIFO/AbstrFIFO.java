package com.example.binarnivyhledavacistrom.FIFO;

public class AbstrFIFO<T> implements IAbstrFIFO<T> {
    @Override
    public void zrus() {

    }

    @Override
    public boolean jePrazdny() {
        return false;
    }

    @Override
    public void vloz(T data) {

    }

    @Override
    public T odeber() {
        return null;
    }
}
