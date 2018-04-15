package ru.spbau.karlina.bioinf;

import java.util.ArrayList;

public class Verification {
    ArrayList<Double> theoryMass = new ArrayList<>();

    /** Load information constructor */
    public Verification(String fileName) {
        theoryMass.sort(Double::compareTo);
    }

    public boolean makeVerification(String peptid) {
        return false;
    }

    private boolean makeVerificationWithChoosenAminoAcid(){
        return false;
    }

}
