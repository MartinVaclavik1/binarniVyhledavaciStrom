package com.example.binarnivyhledavacistrom;

public class AbstrLIFO<T> implements IAbstrLIFO<T> {
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
