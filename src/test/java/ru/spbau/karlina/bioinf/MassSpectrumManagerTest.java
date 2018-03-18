package ru.spbau.karlina.bioinf;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class MassSpectrumManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private String mzXMLFileName = "/home/liuba/selfwork/bioSupport/data/140509QXc1_car_anh_tryp_001.mzXML";

    @Test
    public void showFirst3MassSpectrums() throws Exception {
        System.setOut(new PrintStream(outContent));

        String fileName = "src/main/resources/001.tsv";
        ArrayList<MassSpectrum> spectrumList = MassSpectrumManager.getReliableMassSpectrums(3, fileName);
        for (MassSpectrum spectrum : spectrumList) {
            spectrum.showMainDetails();
        }

        assertEquals("num = 8314;charge = 3;prMass = 1157.548;\n" +
                        "num = 8195;charge = 3;prMass = 1157.5471;\n" +
                        "num = 8258;charge = 3;prMass = 1157.5474;",
                outContent.toString().trim());
        System.setOut(null);
    }

    @Test
    public void getData() throws ParserConfigurationException, SAXException, IOException {
        LinkedList<MassSpectrum> list = MassSpectrumManager.getData(mzXMLFileName);

        for (MassSpectrum spectrum : list) {
            spectrum.showCandidateDetails();
        }
    }
}