package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    Solver test;
    @Test
    void solverTest1() throws Exception {
        test = new Solver("5+2");
        test.qualifier();
        assertEquals("7.0", test.toString());
    }

    @Test
    void solverTest2() throws Exception {
        test = new Solver("(20-60)+100");
        test.qualifier();
        assertEquals("60.0", test.toString());
    }

    @Test
    void solverTest3() throws Exception {
        test = new Solver("(5+2+(5+2+(5+2+(5+2+(5+2)))))-10");
        test.qualifier();
        assertEquals("-10.0", test.toString());
    }

    @Test
    void solverTest4() throws Exception {
        test = new Solver("(-100+78/2)*(34-234/80)");
        test.qualifier();
        assertEquals("-1895.575", test.toString());
    }

    @Test
    void solverTest5() throws Exception {
        test = new Solver("5+100^2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("In the entered mathematical expression, a symbol was found that is not defined in the calculator.\n Please check the entered expression for correctness.", ex.getMessage());
        }
    }

    @Test
    void solverTest6() throws Exception {
        test = new Solver("5++2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("Several operations are in a row. Please fix it.", ex.getMessage());
        }
    }

    @Test
    void solverTest7() throws Exception {
        test = new Solver("5+-2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("Several operations are in a row. Please fix it.", ex.getMessage());
        }
    }

    @Test
    void solverTest8() throws Exception {
        test = new Solver("5-+2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("Several operations are in a row. Please fix it.", ex.getMessage());
        }
    }

    @Test
    void solverTest9() throws Exception {
        test = new Solver("5--2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("Several operations are in a row. Please fix it.", ex.getMessage());
        }
    }

    @Test
    void solverTest10() throws Exception {
        test = new Solver("5**2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("Several operations are in a row. Please fix it.", ex.getMessage());
        }
    }

    @Test
    void solverTest11() throws Exception {
        test = new Solver("5*/2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("Several operations are in a row. Please fix it.", ex.getMessage());
        }
    }

    @Test
    void solverTest12() throws Exception {
        test = new Solver("5/*2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("Several operations are in a row. Please fix it.", ex.getMessage());
        }
    }

    @Test
    void solverTest13() throws Exception {
        test = new Solver("167(78-20)");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("No operation was placed before the opening bracket.", ex.getMessage());
        }
    }

    @Test
    void solverTest14() throws Exception {
        test = new Solver("567-283+5)456-2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("There are not enough open brackets in this mathematical expression!", ex.getMessage());
        }
    }

    @Test
    void solverTest15() throws Exception {
        test = new Solver("(46-23+)-200/2");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("No number found before the closed parenthesis. Please correct the expression.", ex.getMessage());
        }
    }

    @Test
    void solverTest16() throws Exception {
        test = new Solver("100/(15-15)");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("As a result of calculations, the denominator turned out to be 0. Division by 0 is impossible!", ex.getMessage());
        }
    }

    @Test
    void solverTest17() throws Exception {
        test = new Solver("156+23-(456+2/90");
        try {
            test.qualifier();
        }
        catch (Exception ex) {
            assertEquals("Not a paired number of brackets. Please check the correctness of the entered data.", ex.getMessage());
        }
    }


}