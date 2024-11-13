package com.example.binarnivyhledavacistrom.abstrTable;

import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;

public interface IAbstrTable<K extends Comparable<K> ,V>  {
    void zrus();
    boolean jePrazdny();
    V najdi(K key) throws AbstrTableException;
    void vloz(K key, V value) throws AbstrTableException;
    V odeber(K key) throws AbstrTableException;
    Iterator<V> vytvorIterator(eTypProhl typ);
    int getPocet();
    AbstrTable.Prvek<K, V> getKoren();
}
