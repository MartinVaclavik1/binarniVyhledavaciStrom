package com.example.binarnivyhledavacistrom.abstrTable;

import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;

public class AbstrTable<K, V> implements IAbstrTable<K, V> {

    private Prvek<K, V> koren;

    private static class Prvek<K, V> {
        private Prvek<K,V> rodic;
        private K key;
        private V value;
        private Prvek<K,V> synL;
        private Prvek<K,V> synP;

        public Prvek(Prvek<K, V> rodic, K key, V value, Prvek<K, V> synL, Prvek<K, V> synP) {
            this.rodic = rodic;
            this.key = key;
            this.value = value;
            this.synL = synL;
            this.synP = synP;
        }
    }

    @Override
    public void zrus() {
        koren = null;
    }

    @Override
    public boolean jePrazdny() {
        return koren == null;
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
        return new Iterator<V>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public V next() {
                return null;
            }
        };
    }

    @Override
    public int compareTo(K o) {
        return 0;
    }
}
