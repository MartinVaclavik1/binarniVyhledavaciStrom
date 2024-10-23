package com.example.binarnivyhledavacistrom;

import java.util.Iterator;

public class AbstrTable<K,V> implements IAbstrTable<K,V>{
    @Override
    public void zrus() {

    }

    @Override
    public boolean jePrazdny() {
        return false;
    }

    @Override
    public V najdi(K key) {
        return null;
    }

    @Override
    public void vloz(K key, V value) {

    }

    @Override
    public V odeber(K key) {
        return null;
    }

    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ) {
        return null;
    }

    @Override
    public int compareTo(K o) {
        return 0;
    }
}
