package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("The calculator is able to calculate the value of a mathematical expression that you enter from the keyboard.\n" +
                "Supports operations:\n" +
                "+\n" +
                "-\n" +
                "*\n" +
                "/\n" +
                "(\n" +
                ")");
        Scanner sc = new Scanner(System.in);
        String exp = sc.nextLine();
        Solver pars;
            while (exp != "") {
                pars = new Solver(exp);
                try {
                    pars.qualifier();
                    System.out.println(pars.toString());
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                exp = sc.nextLine();
            }
    }
}
