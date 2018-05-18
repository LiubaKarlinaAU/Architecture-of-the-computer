package ru.spbau.karlina.bioinf.verification;

import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.StrictMath.abs;

public class Peptid {
    private enum AminoAcid {A, R, N, D, C, E, Q, G, H, I, L, K, M, F, P, S, T, W, Y, V}

    final double AMINO_ACID_MASS[] = {71.03711, 156.10111, 114.04293, 115.02694, 103.00919, 129.04259, 128.05858, 57.02146, 137.05891,
            113.08406, 113.08406, 128.09496, 131.04049, 147.06841, 97.05276, 87.03203,
            101.04768, 186.07931, 163.06333, 99.06841};

    private static final double ERROR = 0.02;
    private ArrayList<PeptidPair> components = new ArrayList<>();
    private ArrayList<Double> theoryMass = new ArrayList<>();

    private int countOfVariation;
    /**
     * Constructor that fill field using given data
     *
     * @param peptid - string of amino acid
     * @param masses - to convert string to peptid representation
     */
    public Peptid(String peptid, ArrayList<Double> masses) {
        theoryMass = masses;
        for (int i = 0; i < peptid.length(); ++i) {
            char c = peptid.charAt(i);
            if (Character.isAlphabetic(c)) {
                components.add(new PeptidPair(c + ""));
            } else if (c == '+') {
                int start = ++i;
                while (i < peptid.length() && !Character.isAlphabetic(peptid.charAt(i))) {
                    i++;
                }
                double toAdd = Double.parseDouble(peptid.substring(start, i));
                i--;
                components.get(components.size() - 1).addMass(toAdd);
            }
        }

        countOfVariation = countPossibleVariation();
    }

    /**
     * Count E and D amino acid in this peptid
     *
     * @return amount of E and D amino acid
     */
    private int countPossibleVariation() {
        int count = 0;
        for (PeptidPair pair : components) {
            if (pair.acid == AminoAcid.E || pair.acid == AminoAcid.D) {
                count++;
            }
        }

        return count;
    }

    /**
     * Calculate picks that we can prove with peptid variation
     * @param writer
     */
    public void makeVariationVerification(BufferedWriter writer) throws IOException {
        int index = 0;
        double diff = 22.989222 - 1.007825;
        for (PeptidPair pair : components) {
            if (pair.acid == AminoAcid.E || pair.acid == AminoAcid.D) {
                pair.changeMass(diff);
                makeVariationChecking(index, writer);
                writer.newLine();
                pair.changeMass(-diff);
            }

            index++;
        }

        makeVariationChecking(-1, writer);
    }

    /**
     * */
    private void makeVariationChecking(int index, BufferedWriter writer) throws IOException {
        int counter = 0;
        writer.write("Hypothesis:  ");
        for (PeptidPair pair : components) {
            if (counter == index) {
                writer.write(" " + pair.acid + " ");
            } else {
                writer.write("" + pair.acid);
            }
            counter++;
        }
        ArrayList<Pair<Double, Piece>> list  = massChecking(massCounting());
        writer.write("\nFrom " + theoryMass.size() + " are suited us only " + list.size() + "\n");
        showDifference(list, writer);
    }

    private void showDifference(ArrayList<Pair<Double, Piece>> list, BufferedWriter writer) throws IOException {
       for (Pair<Double, Piece> pair : list) {
           writer.write( pair.getValue().getType() +
                   " theory: "  + pair.getKey() +
                   " real: " + pair.getValue().getMass() +
                   " diff: " + Math.abs(pair.getValue().getMass() - pair.getKey()));
           writer.newLine();
           writer.newLine();
       }
    }

    /**
     * Calculate picks that we can prove with this peptid
     * @param writer
     */
    public boolean makePeptidVerification(BufferedWriter writer) throws IOException {
        ArrayList<Pair<Double, Piece>> list  = massChecking(massCounting());
        writer.write("\nFrom " + theoryMass.size() + " are suited us only " + list.size() + "\n");
        showDifference(list, writer);
        return false;
    }

    private class PeptidPair {
        AminoAcid acid;
        double mass;

        public PeptidPair(String character) {
            acid = AminoAcid.valueOf(character);
            mass = AMINO_ACID_MASS[acid.ordinal()];
        }

        public void changeMass(double mass) {
            this.mass = mass;
        }

        public void addMass(double toAdd) {
            mass += toAdd;
        }
    }

    private ArrayList<Piece> massCounting() {
        ArrayList<Piece> array = new ArrayList<>();

        double suffSum = 18.01057;
        double prefSum = 0;

        for (int i = 0; i < components.size() - 1; ++i) {
            prefSum += components.get(i).mass;
            suffSum += components.get(components.size() - i - 1).mass;

            array.add(new Piece(Piece.Type.B, i + 1, prefSum));
            array.add(new Piece(Piece.Type.Y, i + 1, suffSum));

        }

        return array;
    }

    private ArrayList<Pair<Double, Piece>> massChecking(ArrayList<Piece> candidatePieces) {
        ArrayList<Pair<Double, Piece>> list = new ArrayList<>();
        Collections.sort(candidatePieces);
        int ind = 0;
        for (Piece piece : candidatePieces) {
            while (ind < theoryMass.size() && theoryMass.get(ind) < piece.getMass()) {
                ind++;
            }

            if (ind >= theoryMass.size()) {
                return list;
            }

            if (abs(piece.getMass() - theoryMass.get(ind)) < ERROR) {
                list.add(new Pair<>(theoryMass.get(ind), piece));
            }
        }

        return list;
    }
}
