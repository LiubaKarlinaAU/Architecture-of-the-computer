package ru.spbau.karlina.bioinf;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class MassSpectrumManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private String mzXMLFileName = "/home/liuba/selfwork/bioSupport/data/140509QXc1_car_anh_tryp_001.mzXML";

    //@Test
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

    /**
     * GetData test
     */
    @Test
    public void getData() throws ParserConfigurationException, SAXException, IOException {
        ArrayList<MassSpectrum> list = MassSpectrumManager.getData(mzXMLFileName);

        for (MassSpectrum spectrum : list) {
            spectrum.showCandidateDetails();
        }
    }

    //@Test
    public void takeFirst3SpectrumAndSortThem() throws Exception {
        System.setOut(new PrintStream(outContent));

        String fileName = "src/main/resources/001.tsv";
        ArrayList<MassSpectrum> spectrumList = MassSpectrumManager.getReliableMassSpectrums(3, fileName);
        spectrumList.sort(Comparator.comparingLong(o -> o.getId()));

        for (MassSpectrum spectrum : spectrumList) {
            spectrum.showMainDetails();
        }

        assertEquals("id = 8195;charge = 3;prMass = 1157.5471;\n" +
                        "id = 8258;charge = 3;prMass = 1157.5474;\n" +
                        "id = 8314;charge = 3;prMass = 1157.548;",
                outContent.toString().trim());
        System.setOut(null);
    }

    //@Test
    public void representOfWorking() throws Exception {
        String fileName = "src/main/resources/001.tsv";
        ArrayList<MassSpectrum> spectrumList = MassSpectrumManager.getReliableMassSpectrums(100, fileName);

        ArrayList<MassSpectrum> list = MassSpectrumManager.getData(mzXMLFileName);

        HashMap<MassSpectrum, LinkedList<MassSpectrum>> hashMap = MassSpectrumManager.findingCandidates(spectrumList, list);

        System.out.println(hashMap.size());
    }
}