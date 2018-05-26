package ru.spbau.karlina.bioinf.verification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Verification {
    Peptid peptid;
    ArrayList<Double> theoryMass = new ArrayList<>();
    BufferedWriter writer;


    public static void main(String[] argc) {
        String peptid = "YVWLVYEQEGPLKC+57.021DEPILSNR";
        String fileName = "src/main/resources/theory_mass.txt";
        String resultFileName = "src/main/resources/results.txt";

        try {
            Verification verification = new Verification(fileName, peptid, resultFileName);
            verification.makeVerification();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load information constructor
     */
    public Verification(String fileName, String peptidStr, String resultFileName) throws IOException {
        Path path = Paths.get(resultFileName);

        writer = Files.newBufferedWriter(path);

        try {
            Scanner scanner = new Scanner(new File(fileName));
            int i = 0;
            for (; i < 7; ++i)
                scanner.nextLine();
            for (; i < 35; ++i) {
                String current = scanner.nextLine();
                theoryMass.add(Double.parseDouble(current.split("\t")[0]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not such file " + fileName);
        }

        theoryMass.sort(Double::compareTo);
        peptid = new Peptid(peptidStr, theoryMass);
    }

    public void makeVerification() throws IOException {
        peptid.makePeptidVerification(writer);
        writer.flush();
    }

}
