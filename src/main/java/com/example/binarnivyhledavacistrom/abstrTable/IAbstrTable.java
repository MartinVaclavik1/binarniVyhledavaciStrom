package com.example.binarnivyhledavacistrom.abstrTable;

import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;

public interface IAbstrTable<K,V> extends Comparable<K> {
    void zrus();
    boolean jePrazdny();
    V najdi(K key);
    void vloz(K key, V value);
    V odeber(K key);
    Iterator<V> vytvorIterator(eTypProhl typ);
}
