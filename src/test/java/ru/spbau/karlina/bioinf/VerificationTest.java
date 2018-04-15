package ru.spbau.karlina.bioinf;

import org.junit.Test;

import static org.junit.Assert.*;

public class VerificationTest {
    @Test
    public void makeVerification() throws Exception {
       Verification verification = new Verification("src/main/resources/theory_mass.txt");
       assertFalse(verification.makeVerification(""));
    }


}