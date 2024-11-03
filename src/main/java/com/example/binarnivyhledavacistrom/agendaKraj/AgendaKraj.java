package com.example.binarnivyhledavacistrom.agendaKraj;

import com.example.binarnivyhledavacistrom.Obec;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTable;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTableException;
import com.example.binarnivyhledavacistrom.abstrTable.IAbstrTable;
import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;
import java.util.Random;

public class AgendaKraj implements IAgendaKraj {
    IAbstrTable<String, Obec> strom;

    public AgendaKraj() {
        strom = new AbstrTable<>();
    }

    @Override
    public Obec najdi(String nazev) throws AgendaKrajException {
        try {
            return strom.najdi(nazev);
        } catch (AbstrTableException e) {
            throw new AgendaKrajException(e.getMessage());
        }
    }

    @Override
    public void vloz(Obec obec) throws AgendaKrajException {
        try {
            strom.vloz(obec.getObec(), obec);
        } catch (AbstrTableException e) {
            throw new AgendaKrajException(e.getMessage());
        }
    }

    @Override
    public Obec odeber(String nazev) throws AgendaKrajException {
        try {
            return strom.odeber(nazev);
        } catch (AbstrTableException e) {
            throw new AgendaKrajException(e.getMessage());
        }
    }

    /*
     * pomocí iteratoru in order si nechá vypsat obce do pole. (délku pole zjistí ze strom.getPocet)
     * metoda vybere prostřední/nejblíže k prostřední hodnotě a vloží ho jako první, pak si rozdělí zbytek
     * na pravou a levou stranu, kam se zavolá znovu metoda na levou a pak pravou stranu a to do té doby,
     * dokud budou prvky v poli
     */
    @Override
    public void vybuduj() throws AgendaKrajException {
        Obec[] poleObci = new Obec[strom.getPocet()];
        Iterator<Obec> iterator = strom.vytvorIterator(eTypProhl.IN_ORDER);

        for (int i = 0; i < strom.getPocet(); i++) {
            poleObci[i] = iterator.next();
        }

        strom.zrus();
        vlozDoStromu(poleObci);
    }

    private void vlozDoStromu(Obec[] poleObci) throws AgendaKrajException {

        if (poleObci.length == 1) {
            vloz(poleObci[0]);
            return;
        }

        int zbytekPoDeleni = poleObci.length % 2;
        int levaDelka;
        int pravaDelka;

        Obec[] levaStrana;
        Obec[] pravaStrana;


        if (zbytekPoDeleni == 1) {
            vloz(poleObci[poleObci.length / 2]); //v případě lichého čísla (např 5 je výsledek 2)
            // polovina sedí rovnou na pole
            levaDelka = pravaDelka = poleObci.length / 2; //v případě lichého čísla jsou levá a pravá strana stejně dlouhé
        } else {
            vloz(poleObci[poleObci.length / 2 - 1]); //v případě sudého čísla (např 4 je výsledek 2)
            // se musí odešíst 1, aby číslo sedělo na pole
            levaDelka = poleObci.length / 2 - 1; //v případě sudého čísla je levá strana o 1 menší
            pravaDelka = poleObci.length / 2;
        }

        if (levaDelka > 0) {
            levaStrana = new Obec[levaDelka];
            System.arraycopy(poleObci, 0, levaStrana, 0, levaDelka);
            vlozDoStromu(levaStrana);
        }

        pravaStrana = new Obec[pravaDelka]; //nemusí být kontrolován počet jako u levé strany, protože nemůže být 0
        if (zbytekPoDeleni == 1) { //musí být rozděleno na sudé a liché z důvodu posunutí/neposunutí ukazatele o 1 doprava,
            //jinak se u lichých vkládá 2x stejná hodnota a u sudých překračuje limit pole
            for (int i = 0; i < pravaDelka; i++) {
                //pravaStrana[i] = poleObci[poleObci.length / 2 + 1 + i];
                System.arraycopy(poleObci, poleObci.length / 2 + 1, pravaStrana, 0, pravaDelka);
            }
        } else {
            System.arraycopy(poleObci, poleObci.length / 2, pravaStrana, 0, pravaDelka);
        }
        vlozDoStromu(pravaStrana);

    }

    @Override
    public Iterator<Obec> vytvorIterator() {
        return strom.vytvorIterator(eTypProhl.DO_HLOUBKY);
    }

    @Override
    public void generuj() throws AgendaKrajException {
        Random nahoda = new Random();
        int pocet = nahoda.nextInt(29) + 1;

        for (int i = 0; i < pocet; i++){
            int psc = nahoda.nextInt(89999) + 10000;
            StringBuilder nazev = new StringBuilder();
            for (int j = 0; j < nahoda.nextInt(5) + 5; j++){
                nazev.append((char) ('a' + nahoda.nextInt(25)));
            }
            int pocetM = nahoda.nextInt(5000)+1;
            int pocetZ = nahoda.nextInt(5000)+1;
            vloz(new Obec(psc, nazev.toString(), pocetM, pocetZ));
        }
    }
}
