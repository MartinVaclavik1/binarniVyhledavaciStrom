package com.example.binarnivyhledavacistrom.agendaKraj;

import com.example.binarnivyhledavacistrom.Obec;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTable;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTableException;
import com.example.binarnivyhledavacistrom.abstrTable.IAbstrTable;
import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;

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
        int levaDelka = 0;
        int pravaDelka = 0;

        Obec[] levaStrana = null;
        Obec[] pravaStrana = null;


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
            for (int i = 0; i < levaDelka; i++) {
                levaStrana[i] = poleObci[i];
            }
            vlozDoStromu(levaStrana);
        }

        pravaStrana = new Obec[pravaDelka];
        if (zbytekPoDeleni == 1) {
            for (int i = 0; i < pravaDelka; i++) {
                pravaStrana[i] = poleObci[poleObci.length / 2 + 1 + i];
            }
        } else {
            for (int i = 0; i < pravaDelka; i++) {
                pravaStrana[i] = poleObci[poleObci.length / 2 + i];
            }
        }
        vlozDoStromu(pravaStrana);

    }

    @Override
    public Iterator<Obec> vytvorIterator() {
        return strom.vytvorIterator(eTypProhl.DO_HLOUBKY);
    }

    @Override
    public void generuj() {

    }
}
