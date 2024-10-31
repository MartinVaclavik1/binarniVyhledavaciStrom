package com.example.binarnivyhledavacistrom.agendaKraj;

import com.example.binarnivyhledavacistrom.Obec;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTable;

import java.util.Iterator;

public interface IAgendaKraj {
    Obec Najdi(String nazev) throws AgendaKrajException;
    void Vloz(Obec obec) throws AgendaKrajException;
    Obec Odeber(String nazev) throws AgendaKrajException;
    void Vybuduj();
    Iterator<Obec> VytvorIterator();
    void Generuj();
}
