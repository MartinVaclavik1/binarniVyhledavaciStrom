package com.example.binarnivyhledavacistrom.abstrTable;

import com.example.binarnivyhledavacistrom.FIFO.AbstrFIFO;
import com.example.binarnivyhledavacistrom.FIFO.IAbstrFIFO;
import com.example.binarnivyhledavacistrom.LIFO.AbstrLIFO;
import com.example.binarnivyhledavacistrom.LIFO.IAbstrLIFO;
import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrTable<K, V> implements IAbstrTable<K, V> {

    private Prvek<K, V> koren;
    private Prvek<K, V> aktualni;
    private int pocet = 0;

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
        koren = aktualni = null;
        pocet = 0;
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
        if (key == null || value == null) {
            throw new AbstrTableException("??");
        }
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
                        pocet++;
                        return;
                    } else {
                        aktualni = aktualni.synP;
                    }
                } else if (comparator > 0) {

                    if (aktualni.synL == null) {
                        aktualni.synL = novyPrvek;
                        novyPrvek.rodic = aktualni;
                        pocet++;
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

    //najde prvek metodou najdi. tam zůstane nastavený aktuální. z aktuálního vezme V do proměnné.
    //odebere se prvek a jestli má potomky, tak se najde prvek, který je nejvíce podobný odebranému
    //a ten se nastaví místo odebraného a rozředí se mu prvky dále ve struktuře
    @Override
    public V odeber(K key) throws AbstrTableException {
        //pro nastavení aktuálního na danou pozici
        //TODO
        try {
            najdi(key);
        } catch (AbstrTableException e) {
            throw new AbstrTableException(e.getMessage());
        }
        V value = aktualni.value;


        //najde největší-menší prvek a nejmenší-větší prvek a pak je porovná s aktuálním a kterej bude blíž,
        //(nezapomenout to dát jako abs value) tak se nastaví jako náhradník

        // když má prvek potomky
        if (aktualni.synL != null || aktualni.synP != null) {
            //TODO odebrat aktuální a nastavit místo něj jeden z potomků

            Prvek<K, V> nejblizsiNejmensi = aktualni.synL;
            Prvek<K, V> nejblizsiNejvetsi = aktualni.synP;
            Prvek<K, V> nastupce = null;

            if (nejblizsiNejmensi != null) {
                while (nejblizsiNejmensi.synP != null) {
                    nejblizsiNejmensi = nejblizsiNejmensi.synP;
                }
                nastupce = nejblizsiNejmensi;
            }

            if (nejblizsiNejvetsi != null) {
                while (nejblizsiNejvetsi.synL != null) {
                    nejblizsiNejvetsi = nejblizsiNejvetsi.synL;
                }
                nastupce = nejblizsiNejvetsi;
            }

            if (nejblizsiNejvetsi != null && nejblizsiNejmensi != null) {
                if (rozdilPrvku(aktualni, nejblizsiNejmensi) < rozdilPrvku(aktualni, nejblizsiNejvetsi)) {
                    nastupce = nejblizsiNejmensi;
                }
            }

            //propojí zbývající prvky po náhradníkovi odebraného s předkem
            if (nastupce == nejblizsiNejmensi) {
                if (nastupce.synL != null) {
                    if (nastupce.rodic != aktualni) {
                        nastupce.rodic.synP = nastupce.synL;
                        nastupce.synL.rodic = nastupce.rodic;
                        nastupce.synL = aktualni.synL;
                    }
                    nastupce.synP = aktualni.synP;

                }
            } else {
                if (nastupce.synP != null) {
                    if (nastupce.rodic != aktualni) {
                        nastupce.rodic.synL = nastupce.synP;
                        nastupce.synP.rodic = nastupce.rodic;
                        nastupce.synP = aktualni.synP;
                    }
                    nastupce.synL = aktualni.synL;
                }
            }

            //TODO odebrat link z rodiče nástupce na nástupce, z nástupce na rodiče
            // Pak nahradit všechny atributy aktuálního na nástupce
            // - nezapomenout i od

            //zjištění jaký syn je v rodiči pomocí comparatoru
            if (aktualni != koren) {
                switch (compareTo(aktualni.rodic.key)) {
                    //pravý (aktuální [odebraný] je větší, než rodič)
                    case 1 -> aktualni.rodic.synP = nastupce;
                    //levý (aktuální [odebraný] je menší, než rodič)
                    case -1 -> aktualni.rodic.synL = nastupce;
                }
                nastupce.rodic = aktualni.rodic;
                aktualni.rodic = null;
            }
            //TODO přepojit i zbytek


            if (aktualni.synP != null && aktualni.synP != nastupce) {
                aktualni.synP.rodic = nastupce;
            }
            if (aktualni.synL != null && aktualni.synL != nastupce) {
                aktualni.synL.rodic = nastupce;
            }

            //když prvek nemá žádné potomky
        } else {
            //když prvek není kořenový prvek, tak se odebere link z rodiče na něj
            if (aktualni.rodic != null) {
                //zjištění jaký syn je v rodiči
                if (aktualni.rodic.synL == aktualni) {
                    aktualni.rodic.synL = null;
                } else if (aktualni.rodic.synP == aktualni) {
                    aktualni.rodic.synP = null;
                }
                //odebrání linku z aktuálního na rodiče
                aktualni.rodic = null;
            } else {
                //když nemá potomky a je kořenový prvek, tak je jen 1 prvek v seznamu a může se zrušit celý
                zrus();
            }
        }
        aktualni = null;
        pocet--;
        return value;
    }

    //vrátí číslo, jaký je rozdíl mezi prvkem1 a prvkem2 v absolutní hodnotě
    private Integer rozdilPrvku(Prvek<K, V> prvek1, Prvek<K, V> prvek2) throws AbstrTableException {

        char[] prvek1Char = prvek1.key.toString().toCharArray();
        char[] prvek2Char = prvek2.toString().toCharArray();
        int pocetOpakovani = Math.min(prvek1Char.length, prvek2Char.length);

        for (int i = 0; i < pocetOpakovani; i++) {
            if (prvek1Char[i] != prvek2Char[i]) {
                return Math.abs(prvek1Char[i] - prvek2Char[i]);
            }
        }

        throw new AbstrTableException("Chyba při odečítání prvků");
    }

    @Override
    public Iterator<V> vytvorIterator(eTypProhl typ) {
        return new Iterator<>() {
            final IAbstrFIFO<Prvek<K, V>> fifo = new AbstrFIFO<>();
            final IAbstrLIFO<Prvek<K, V>> lifo = new AbstrLIFO<>();
            int zobrazenyPocet = 0;

            @Override
            public boolean hasNext() {
                return zobrazenyPocet <= pocet && !jePrazdny();
            }

            @Override
            public V next() {
                V odebrany = null;
                if (hasNext()) {

                    if (zobrazenyPocet == 0) {
                        fifo.vloz(koren);
                        lifo.vloz(koren);
                    }

                    switch (typ) {
                        case DO_SIRKY -> {
                            Prvek<K, V> odebranyPrvek = fifo.odeber();

                            if (odebranyPrvek.synL != null) {
                                fifo.vloz(odebranyPrvek.synL);
                            }
                            if (odebranyPrvek.synP != null) {
                                fifo.vloz(odebranyPrvek.synP);
                            }

                            odebrany = odebranyPrvek.value;
                        }
                        case DO_HLOUBKY -> {
                            Prvek<K, V> odebranyPrvek = lifo.odeber();

                            if (odebranyPrvek.synP != null) {
                                lifo.vloz(odebranyPrvek.synP);
                            }
                            if (odebranyPrvek.synL != null) {
                                lifo.vloz(odebranyPrvek.synL);
                            }

                            odebrany = odebranyPrvek.value;
                        }
                        //todo in order
                    }
                } else {
                    throw new NoSuchElementException();
                }
                zobrazenyPocet++;
                return odebrany;
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

    public int getPocet() {
        return pocet;
    }
}
