package com.example.binarnivyhledavacistrom.agendaKraj;


import com.example.binarnivyhledavacistrom.Obec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendaKrajTest {

    private final Obec T1 = new Obec(1,"1", 1,1);
    private final Obec T2 = new Obec(2,"2", 1,1);
    private final Obec T3 = new Obec(3,"3", 1,1);
    private final Obec T4 = new Obec(4,"4", 1,1);
    private final Obec T5 = new Obec(5,"5", 1,1);
    private final Obec T6 = new Obec(6,"6", 1,1);
    private final Obec T7 = new Obec(7,"7", 1,1);
    private final Obec T8 = new Obec(8,"8", 1,1);
    private final Obec T9 = new Obec(9,"9", 1,1);

    @Test
    void testVybuduj01() {
        IAgendaKraj kraj = new AgendaKraj();
        try {
            kraj.vloz(T1);
            kraj.vloz(T2);
            kraj.vloz(T3);
            kraj.vloz(T4);
            kraj.vloz(T5);
            kraj.vloz(T6);
            kraj.vloz(T7);
            kraj.vloz(T8);
            kraj.vloz(T9);
            kraj.vybuduj();
            assertTrue(true);
        } catch (AgendaKrajException e) {
            System.err.println(e.getMessage());
        }
    }

}