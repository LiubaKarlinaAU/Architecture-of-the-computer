package ru.spbau.karlina.bioinf;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class MassSpectrumManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void showFirst3MassSpectrums() throws Exception {
        System.setOut(new PrintStream(outContent));

        String fileName = "src/main/resources/001.tsv";
        MassSpectrum[] array = MassSpectrumManager.getMassSpectrumsId(3, fileName);
        for (MassSpectrum spectrum : array) {
            spectrum.showMainDetails();
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
            spectrum.showMainDetails();
        }
    }

    @Test
    public void getData() throws ParserConfigurationException, SAXException, IOException {
        String fileName = "/home/liuba/selfwork/bioSupport/data/140509QXc1_car_anh_tryp_001.mzXML";
        LinkedList<MassSpectrum> list = MassSpectrumManager.getData(fileName);
        for (MassSpectrum spectrum : list) {
            spectrum.showCandidateDetails();
        }
    }
}