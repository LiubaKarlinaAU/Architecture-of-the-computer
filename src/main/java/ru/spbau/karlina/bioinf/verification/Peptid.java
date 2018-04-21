package ru.spbau.karlina.bioinf.verification;

import java.util.ArrayList;
import java.util.LinkedList;

public class Peptid {
    enum AminoAcid {A, R, N, D, C, E, Q, G, H, I, L, K, M, F, P, S, T, W, Y, V}

    final double AMINO_ACID_MASS[] = {71.03711, 156.10111, 114.04293, 115.02694, 103.00919, 129.04259, 128.05858, 57.02146, 137.05891,
    113.08406, 113.08406, 128.09496, 131.04049, 147.06841, 97.05276, 87.03203,
    101.04768, 186.07931, 163.06333, 99.06841};

    private ArrayList<PeptidPair> components = new ArrayList<>() ;

    public Peptid(String peptid) {
        for (int i = 0; i < peptid.length(); ++i) {
            char c = peptid.charAt(i);
            if (Character.isAlphabetic(c)) {
                AminoAcid acid = AminoAcid.valueOf(c + "");
                components.add(new PeptidPair(c + ""));
            } else if (c == '+') {
                int start = ++i;
                while (i < peptid.length() && !Character.isAlphabetic(peptid.charAt(i))) {
i++;
                }
                System.out.println(peptid.substring(start, i));
                double toAdd = Double.parseDouble(peptid.substring(start, i));
                i--;
                components.get(components.size() - 1).addMass(toAdd);
            }
        }
    }

    private class PeptidPair {
        AminoAcid acid;
        double mass;

        public PeptidPair(String character) {
            acid = AminoAcid.valueOf(character);
            mass = AMINO_ACID_MASS[acid.ordinal()];
        }

        public void changeMass(){}

        public void addMass(double toAdd) {
            mass += toAdd;
        }
    }
}
