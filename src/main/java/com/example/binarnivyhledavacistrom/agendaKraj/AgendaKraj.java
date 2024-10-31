package com.example.binarnivyhledavacistrom.agendaKraj;

import com.example.binarnivyhledavacistrom.Obec;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTable;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTableException;
import com.example.binarnivyhledavacistrom.abstrTable.IAbstrTable;

import java.util.Iterator;

public class AgendaKraj implements IAgendaKraj {
    IAbstrTable<String, Obec> strom;

    public AgendaKraj() {
        strom = new AbstrTable<>();
    }

    @Override
    public Obec Najdi(String nazev) throws AgendaKrajException {
        try {
            return strom.najdi(nazev);
        } catch (AbstrTableException e) {
            throw new AgendaKrajException(e.getMessage());
        }
    }

    @Override
    public void Vloz(Obec obec) throws AgendaKrajException {
        try {
            strom.vloz(obec.getObec(), obec);
        } catch (AbstrTableException e) {
            throw new AgendaKrajException(e.getMessage());
        }
    }

    @Override
    public Obec Odeber(String nazev) throws AgendaKrajException {
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
    public void Vybuduj() {

    }

    @Override
    public Iterator<Obec> VytvorIterator() {
        return null;
    }

    @Override
    public void Generuj() {

    }
}
