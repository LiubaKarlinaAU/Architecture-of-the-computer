package ru.spbau.karlina.bioinf;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class MassSpectrum {
    private int id;
    private int num;
    private int charge;
    private double precursorMass;
    private int retentionTime;
    private double evalue;
    private double delta;
    private String peptid;

    public MassSpectrum(String[] line) {
        id = parseId(line[1]);
        num = parseInt(line[2]);
        evalue = parseDouble(line[13]);
        charge = parseInt(line[7]);
        peptid = line[8];
        precursorMass = parseDouble(line[4]);
        delta = parseDouble(line[6]);
    }

    public MassSpectrum(String numStr, String rtStr) {
        num = parseInt(numStr);
        retentionTime = parseRetentionTime(rtStr);
    }

    private int parseRetentionTime(String string) {
        String str = string.substring(4);
        return parseInt(str.substring(0, str.length() - 1));
    }

    private int parseId(String string) {
        return parseInt(string.substring(5));
    }


    public void showMainDetails() {
        System.out.print("id = " + id + ";");
        System.out.print("num = " + num + ";");
        System.out.print("charge = " + charge + ";");
        System.out.println("prMass = " + precursorMass + ";");
    }

    public void showCandidateDetails() {
        System.out.print("num = " + num + ";");
        System.out.print("rTime = " + retentionTime + ";");
        System.out.println("prMass = " + precursorMass + ";");
    }

    public int getId() {
        return id;
    }
}