package com.example.binarnivyhledavacistrom.abstrTable;

import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;

public class AbstrTable<K, V> implements IAbstrTable<K, V> {

    private Prvek<K, V> koren;
    private Prvek<K, V> aktualni;

    private static class Prvek<K, V> {
        private Prvek<K, V> rodic;
        private K key;
        private V value;
        private Prvek<K, V> synL;
        private Prvek<K, V> synP;

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
        //porovnání, jestli K (název obce) je menší, nebo větší, než aktuální a takhle traverzovat dokud se nenajde,
        // nebo dokud nebude slepá ulice
        return null;
    }

    @Override
    public void vloz(K key, V value) {
        //travertuje po listu a hledá velné místo, kam by seděl prvek. když najde stejný prvek,
        // vyhodí chybu, že už existuje

        if (jePrazdny()) {
            koren = new Prvek<>(null, key, value, null, null);
        } else {
            Prvek<K, V> novyPrvek = new Prvek<>(null, key, value, null, null);
            aktualni = koren;
            if (compareTo(novyPrvek.key) == 0) {

            }
        }
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
    public int compareTo(K prvek) {

        char[] aktualniChar = this.aktualni.key.toString().toCharArray();
        char[] prvekChar = prvek.toString().toCharArray();
        int pocetOpakovani = Math.min(aktualniChar.length, prvekChar.length);
        int mensi = -1;
        int vetsi = 1;

        //porovnává aktuální nastavený a prvek charakter po charakteru. Když jsou stejné, vrátí 0.
        // Když je prvek větší vrátí -1 a když je aktuální větší, vrátí 1
        for (int i = 0; i < pocetOpakovani; i++) {
            //větší než aktuální
            if (aktualniChar[i] < prvekChar[i]) {
                return mensi;
                //menší, než aktuální
            } else if (aktualniChar[i] > prvekChar[i]) {
                return vetsi;
            }
        }

        return Integer.compare(prvekChar.length, aktualniChar.length);
    }
}
