package com.example.binarnivyhledavacistrom.agendaKraj;

import com.example.binarnivyhledavacistrom.Obec;

import java.util.Iterator;

public interface IAgendaKraj {
    Obec najdi(String nazev) throws AgendaKrajException;
    void vloz(Obec obec) throws AgendaKrajException;
    Obec odeber(String nazev) throws AgendaKrajException;
    void vybuduj() throws AgendaKrajException;
    Iterator<Obec> vytvorIterator();
    void generuj() throws AgendaKrajException;
}
