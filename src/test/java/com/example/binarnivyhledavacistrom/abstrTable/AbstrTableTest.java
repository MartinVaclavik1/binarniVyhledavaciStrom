package com.example.binarnivyhledavacistrom.abstrTable;

import com.example.binarnivyhledavacistrom.enumy.eTypProhl;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AbstrTableTest {

    private static class TestClass {

        int a;

        public TestClass(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "T" + a;
        }

    }

    private final TestClass T1 = new TestClass(1);
    private final TestClass T2 = new TestClass(2);
    private final TestClass T3 = new TestClass(3);
    private final TestClass T4 = new TestClass(4);
    private final TestClass T5 = new TestClass(5);
    private final TestClass T6 = new TestClass(6);
    private final TestClass T7 = new TestClass(7);
    private final TestClass T8 = new TestClass(8);
    private final TestClass T9 = new TestClass(9);

    @Test
    void testVloz01() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T9", T9);
            strom.vloz("T1", T1);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testNajdi01() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {

            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T9", T9);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
            assertEquals(strom.najdi("T9"), T9);
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testNajdi02() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);
            assertEquals(strom.najdi("T9"), T9);
            fail();
        } catch (AbstrTableException e) {
            System.err.println(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    void testIterator01() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);

            Iterator<TestClass> iterator = strom.vytvorIterator(eTypProhl.DO_SIRKY);
            while (iterator.hasNext()){
                System.out.println(iterator.next().toString());
            }

        } catch (AbstrTableException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void testIterator02() {
        IAbstrTable<String, TestClass> strom = new AbstrTable<>();
        try {
            strom.vloz("T3", T3);
            strom.vloz("T5", T5);
            strom.vloz("T1", T1);
            strom.vloz("T2", T2);
            strom.vloz("T4", T4);
            strom.vloz("T6", T6);
            strom.vloz("T8", T8);
            strom.vloz("T7", T7);

            Iterator<TestClass> iterator = strom.vytvorIterator(eTypProhl.DO_HLOUBKY);
            while (iterator.hasNext()){
                System.out.println(iterator.next().toString());
            }

        } catch (AbstrTableException | NoSuchElementException e) {
            System.err.println(e.getMessage());
        }
    }
}