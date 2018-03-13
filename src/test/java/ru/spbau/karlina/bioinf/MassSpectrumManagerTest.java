package ru.spbau.karlina.bioinf;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MassSpectrumManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void showFirst3MassSpectrums() throws Exception {
        System.setOut(new PrintStream(outContent));

        String fileName = "/home/liuba/selfwork/bio_inf/src/main/resources/001.tsv";
        MassSpectrum[] array = MassSpectrumManager.getMassSpectrumsId(3, fileName);
        for (MassSpectrum spectrum : array) {
            spectrum.showDetails();
        }

        assertEquals("id = 8314;charge = 3;prMass = 1157.548;\n" +
                        "id = 8195;charge = 3;prMass = 1157.5471;\n" +
                        "id = 8258;charge = 3;prMass = 1157.5474;",
                outContent.toString().trim());
        System.setOut(null);
    }

    @Test
    public void getMassSpectrumsId() throws Exception {
        String fileName = "/home/liuba/selfwork/bio_inf/src/main/resources/001.tsv";
        MassSpectrum[] array = MassSpectrumManager.getMassSpectrumsId(3, fileName);
        for (MassSpectrum spectrum : array) {
            spectrum.showDetails();
        }
    }
}