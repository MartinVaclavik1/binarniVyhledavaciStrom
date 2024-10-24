package com.example.binarnivyhledavacistrom.FIFO;

import com.example.binarnivyhledavacistrom.abstrDoubleList.AbstrDoubleListException;
import com.example.binarnivyhledavacistrom.abstrDoubleList.IAbstrDoubleList;

public class AbstrFIFO<T> implements IAbstrFIFO<T> {

    IAbstrDoubleList<T> list;

    public AbstrFIFO(IAbstrDoubleList<T> list) {
        this.list = list;
    }

    @Override
    public void zrus() {
        list.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return list.jePrazdny();
    }

    @Override
    public void vloz(T data) {
        try {
            list.vlozPrvni(data);
        } catch (AbstrDoubleListException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T odeber() {
        try {
            return list.odeberPosledni();
        } catch (AbstrDoubleListException e) {
            throw new RuntimeException(e);
        }
    }
}
