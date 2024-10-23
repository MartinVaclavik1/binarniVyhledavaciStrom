package com.example.binarnivyhledavacistrom.LIFO;

import com.example.binarnivyhledavacistrom.LIFO.IAbstrLIFO;

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
