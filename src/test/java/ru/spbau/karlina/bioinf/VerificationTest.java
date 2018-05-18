package ru.spbau.karlina.bioinf;

import org.junit.Test;
import ru.spbau.karlina.bioinf.verification.Verification;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class VerificationTest {
    @Test
    public void makeVerification() throws Exception {
        String peptid = "YVWLVYEQEGPLKC+57.021DEPILSNR";
        String fileName = "src/main/resources/theory_mass.txt";
        String resultFileName = "src/main/resources/results.txt";

        Verification verification = new Verification(fileName, peptid, resultFileName);
        verification.makeVerification();
    }

    @Test
    public void makeVerification2() throws Exception {
        ArrayList<Double> components = new ArrayList<>();
        components.add(new Double(1));
        components.add(new Double(2));

        components.add(new Double(3));

        components.add(new Double(4));
        ArrayList<Double> array = new ArrayList<>();

        Double suffSum = new Double(0), prefSum = new Double(0);

        for (int i = 0; i < components.size(); ++i) {
            suffSum += components.get(i);
            prefSum += components.get(components.size() - i - 1);

            array.add(suffSum);
            array.add(prefSum);
        }

        System.out.println(array);
    }
/*
    @Test
    public void makeVerification3() throws Exception {
        String peptid = "YVWLVYEQEGPLKC+57.021DEPILSNR";
        Verification verification = new Verification("src/main/resources/theory_mass.txt", peptid);

    }*/
}