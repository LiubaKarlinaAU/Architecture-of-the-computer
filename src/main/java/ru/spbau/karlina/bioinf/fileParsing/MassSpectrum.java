package ru.spbau.karlina.bioinf.fileParsing;

import java.util.LinkedList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Math.abs;

/**
 * Mass spectrum representation class
 * Is using for storing data, setting and showing it
 */
public class MassSpectrum {

    /**
     * Constants
     */
    public enum TYPE {
        IDENTIFIED, CANDIDATE
    }

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

    /**
     * Constructor for mass spectrum from tsv file format
     *
     * @param line - string of all needed information
     */
    public MassSpectrum(String[] line) {
        num = parseLong(line[2]);
        evalue = parseDouble(line[13]);
        charge = parseInt(line[7]);
        peptid = line[8];
        precursorMass = parseDouble(line[4]);
        delta = parseDouble(line[6]);
        type = TYPE.IDENTIFIED;
    }

    /**
     * Constructor for candidate mass spectrums from mzXML file format
     *
     * @param num           - spectrum id
     * @param retentionTime - spectrum retention time
     * @param precursorMz   - spectrum mass that we can use to calculate
     *                      precursor mass using spectrum charge
     */
    public MassSpectrum(long num, double retentionTime, double precursorMz) {
        this.num = num;
        this.retentionTime = retentionTime;
        this.precursorMz = precursorMz;
        type = TYPE.CANDIDATE;
    }

    /**
     * Showing main spectrum details
     * It is intended to be used for identified spectrum
     */
    public void showMainDetails() {
        System.out.print("id = " + num + ";");
        System.out.print("charge = " + charge + ";");
        System.out.print("rTime = " + retentionTime + ";");
        System.out.println("prMass = " + precursorMass + ";");
        System.out.println("peptid = " + peptid);
    }

    /**
     * Showing main spectrum details
     * It is intended to be used for candidate spectrum
     */
    public void showCandidateDetails() {
        System.out.print("num = " + num + ";");
        System.out.print("rTime = " + retentionTime + ";");
        System.out.println("prMass = " + precursorMass + ";");
    }

    /**
     * Check is this spectrum suitable to be candidate for given one
     * Make possible precursor mass and compare difference
     *
     * @param spec - spectrum for searching candidates
     * @return true if one of possible mass is suit given spectrum(and that mass is setted)
     *         false - otherwise
     */
    public boolean match(MassSpectrum spec) {
        LinkedList<Double> list = makePossiblePrecursorMass();

        for (Double mass : list) {
            if (abs(spec.getPrecursorMass() - mass) < ERROR * spec.getPrecursorMass()) {
                precursorMass = mass;
                return true;
            }
        }

        return false;
    }

    /**
     * Retention time setter
     *
     * @param retentionTime - to be set
     */
    public void setRetentionTime(double retentionTime) {
        this.retentionTime = retentionTime;
    }

    /**
     * Retention time getter
     *
     * @return (double) retention time
     */
    public double getRetentionTime() {
        return retentionTime;
    }

    /**
     * Id getter
     *
     * @return (long) id
     */
    public long getId() {
        return num;
    }

    /**
     * Precursor mass getter
     *
     * @return (double) precursor mass
     */
    public double getPrecursorMass() {
        return precursorMass;
    }

    private LinkedList<Double> makePossiblePrecursorMass() {
        LinkedList<Double> list = new LinkedList<Double>();
        for (int i = 2; i < 6; ++i) {
            list.add((precursorMz - PROTON_MASS) * i);
        }

        return list;
    }

    public String getPeptid() { return peptid; }
}