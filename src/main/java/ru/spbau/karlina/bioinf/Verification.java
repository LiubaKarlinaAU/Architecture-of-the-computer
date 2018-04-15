package ru.spbau.karlina.bioinf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Verification {
    ArrayList<Double> theoryMass = new ArrayList<>();

    /** Load information constructor */
    public Verification(String fileName)  {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            int i = 0;
            for (; i < 7; ++i)
                scanner.nextLine();
            for (;i < 35; ++i) {
                String current = scanner.nextLine();
               theoryMass.add(Double.parseDouble(current.split("\t")[0]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not such file " + fileName);
        }

        theoryMass.sort(Double::compareTo);
    }

    public boolean makeVerification(String peptid) {
        return false;
    }

    private boolean makeVerificationWithChoosenAminoAcid(){
        return false;
    }

}
