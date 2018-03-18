package ru.spbau.karlina.bioinf;

import javax.xml.datatype.Duration;
import java.util.LinkedList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;


public class MassSpectrum {
    private Double ProtonMass = 1.6726219e-27;
    private int id;
    private Long num;
    private int charge;
    private Double precursorMass;

    private Double precursorMz;
    private Duration retentionTime;

    private double evalue;
    private double delta;
    private String peptid;

    private LinkedList<Double> makePossiblePrecursorMass() {
       LinkedList<Double> list = new LinkedList<Double>();
       for (int i = 2; i < 6; ++i) {
           list.add((precursorMz - precursorMass) * i);
       }

       return list;
    }

    public MassSpectrum(String[] line) {
        id = parseId(line[1]);
        num = parseLong(line[2]);
        evalue = parseDouble(line[13]);
        charge = parseInt(line[7]);
        peptid = line[8];
        precursorMass = parseDouble(line[4]);
        delta = parseDouble(line[6]);
    }

    public MassSpectrum(Long num, Duration retentionTime, float precursorMz) {
        this.num = num;
        this.retentionTime = retentionTime;
        this.precursorMz = (double) precursorMz;
    }

    public MassSpectrum(String num, Double precursorMz) {
        this.num = parseLong(num);
        this.precursorMz = precursorMz;
    }

    public Duration getRetentionTime() {
        return retentionTime;
    }

    private int parseId(String string) {
        return parseInt(string.substring(5));
    }

    public void showMainDetails() {
        System.out.print("num = " + num + ";");
        System.out.print("charge = " + charge + ";");
        System.out.println("prMass = " + precursorMass + ";");
    }

    public void showCandidateDetails() {
        System.out.print("num = " + num + ";");
        System.out.print("rTime = " + retentionTime + ";");
        System.out.println("prMz = " + precursorMz + ";");
    }

    public int getId() {
        return id;
    }
}