package ru.spbau.karlina.bioinf;

import java.io.OutputStream;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class MassSpectrum {
    private int id;
    private int charge;
    private double precursorMass;
    private double evalue;
    private double delta;
    private String peptid;

    public MassSpectrum(String[] line) {
        id = parseId(line[1]);
        evalue = parseDouble(line[13]);
        charge = parseInt(line[7]);
        peptid = line[8];
        precursorMass = parseDouble(line[4]);
        delta = parseDouble(line[6]);
    }

    private int parseId(String string) {
        return parseInt(string.substring(5));
    }


    public void showDetails() {
        System.out.print("id = " + id + ";");
        System.out.print("charge = " + charge + ";");
        System.out.println("prMass = " + precursorMass + ";");
    }

    public int getId() {
        return id;
    }
}