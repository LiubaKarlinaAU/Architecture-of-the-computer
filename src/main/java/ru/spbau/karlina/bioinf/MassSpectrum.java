package ru.spbau.karlina.bioinf;

import javax.xml.datatype.Duration;
import java.util.LinkedList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Math.abs;


public class MassSpectrum {
    /** Constants */
    public enum TYPE {IDENTIFIED, CANDIDATE}
    private static final Double PROTON_MASS = 1.007825;
    private static final Double ERROR = 0.00001;

    private TYPE type;
    private long num;
    private int charge;
    private double precursorMass;

    private double precursorMz;
    private double retentionTime;

    private double evalue;
    private double delta;
    private String peptid;

    /** Constructor for mass spectrum from tsv file format
     * @param line - string of all needed information */
    public MassSpectrum(String[] line) {
        num = parseLong(line[2]);
        evalue = parseDouble(line[13]);
        charge = parseInt(line[7]);
        peptid = line[8];
        precursorMass = parseDouble(line[4]);
        delta = parseDouble(line[6]);
        type = TYPE.IDENTIFIED;
    }


    public MassSpectrum(long num, double retentionTime, double precursorMz) {
        this.num = num;
        this.retentionTime = retentionTime;
        this.precursorMz = precursorMz;
    }

    public double getRetentionTime() {
        return retentionTime;
    }

    public void showMainDetails() {
        System.out.print("id = " + num + ";");
        System.out.print("charge = " + charge + ";");
        System.out.println("prMass = " + precursorMass + ";");
    }

    public void showCandidateDetails() {
        System.out.print("num = " + num + ";");
        System.out.print("rTime = " + retentionTime + ";");
        System.out.println("prMz = " + precursorMz + ";");
    }

    public boolean match(MassSpectrum spec) {
        LinkedList<Double> list = makePossiblePrecursorMass();

        for (Double mass : list) {
            if (abs(spec.getPrecursorMass() - mass) < ERROR * spec.getPrecursorMass())
                return true;
        }

        return false;
    }

    public long getId() {
        return num;
    }

    public double getPrecursorMass() {
        return precursorMass;
    }

    private int parseRetentionTime(String string) {
        String str = string.substring(4);
        return parseInt(str.substring(0, str.length() - 1));
    }

    private LinkedList<Double> makePossiblePrecursorMass() {
        LinkedList<Double> list = new LinkedList<Double>();
        for (int i = 2; i < 6; ++i) {
            list.add((precursorMz - PROTON_MASS) * i);
        }

        return list;
    }
}