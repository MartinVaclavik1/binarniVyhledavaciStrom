package com.example.binarnivyhledavacistrom.agendaKraj;

import com.example.binarnivyhledavacistrom.Obec;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTable;
import com.example.binarnivyhledavacistrom.enumy.eTypProhl;

import java.util.Iterator;

public interface IAgendaKraj {
    Obec najdi(String nazev) throws AgendaKrajException;
    void vloz(Obec obec) throws AgendaKrajException;
    Obec odeber(String nazev) throws AgendaKrajException;
    void vybuduj() throws AgendaKrajException;
    Iterator<Obec> vytvorIterator(eTypProhl typProhlidky);
    void generuj() throws AgendaKrajException;
    public AbstrTable.Prvek<String,Obec> getKoren();
}
