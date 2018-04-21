package ru.spbau.karlina.bioinf;

import org.junit.Test;
import ru.spbau.karlina.bioinf.verification.Verification;

import static org.junit.Assert.*;

public class VerificationTest {
    @Test
    public void makeVerification() throws Exception {
        String peptid = "YVWLVYEQEGPLKC+57.021DEPILSNR";
       Verification verification = new Verification("src/main/resources/theory_mass.txt");
       assertFalse(verification.makeVerification(peptid));
    }


}