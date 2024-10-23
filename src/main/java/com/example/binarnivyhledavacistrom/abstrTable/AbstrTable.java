package com.example.binarnivyhledavacistrom.abstrTable;

import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;

public class AbstrTable<K, V> implements IAbstrTable<K, V> {

    private Prvek<K, V> koren;
    private Prvek<K, V> aktualni;

    private static class Prvek<K, V> {
        private Prvek<K, V> rodic;
        private final K key;
        private final V value;
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

    //porovnání, jestli K (název obce) je menší, nebo větší, než aktuální a takhle traverzovat dokud se nenajde,
    // nebo dokud nebude slepá ulice
    @Override
    public V najdi(K key) throws AbstrTableException {
        if (jePrazdny()) {
            throw new AbstrTableException("prázdné pole");
        }
        aktualni = koren;
        while (true) {
            int comparator = compareTo(key);
            if (comparator < 0) {

                if (aktualni.synP == null) {
                    throw new AbstrTableException("prvek " + key + " nenalezen");
                } else {
                    aktualni = aktualni.synP;
                }
            } else if (comparator > 0) {

                if (aktualni.synL == null) {
                    throw new AbstrTableException("prvek " + key + " nenalezen");
                } else {
                    aktualni = aktualni.synL;
                }
            } else {

                return aktualni.value;
            }
        }
    }

    //travertuje po listu a hledá velné místo, kam by seděl prvek. když najde stejný prvek,
    //vyhodí chybu, že už existuje
    @Override
    public void vloz(K key, V value) throws AbstrTableException {
        if (jePrazdny()) {
            koren = new Prvek<>(null, key, value, null, null);
        } else {
            Prvek<K, V> novyPrvek = new Prvek<>(null, key, value, null, null);
            aktualni = koren;

            while (true) {
                int comparator = compareTo(novyPrvek.key);
                if (comparator < 0) {

                    if (aktualni.synP == null) {
                        aktualni.synP = novyPrvek;
                        novyPrvek.rodic = aktualni;
                        return;
                    } else {
                        aktualni = aktualni.synP;
                    }
                } else if (comparator > 0) {

                    if (aktualni.synL == null) {
                        aktualni.synL = novyPrvek;
                        novyPrvek.rodic = aktualni;
                        return;
                    } else {
                        aktualni = aktualni.synL;
                    }
                } else {
                    throw new AbstrTableException("prvek " + key.toString() + " již existuje v seznamu!!");
                }
            }
        }
    }

    @Override
    public V odeber(K key) throws AbstrTableException {
        //pro nastavení aktuálního na danou pozici
        //TODO
        try {
            najdi(key);
        } catch (AbstrTableException e) {
            throw new AbstrTableException( e.getMessage());
        }
        return null;
    }

    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ) {
        //TODO
        return new Iterator<>() {
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

    //prvek vetsi = -1, prvek mensi = 1, prvek stejny = 0
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
