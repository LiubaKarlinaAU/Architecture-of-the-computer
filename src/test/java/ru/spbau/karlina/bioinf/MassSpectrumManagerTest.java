package ru.spbau.karlina.bioinf;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.*;

public class MassSpectrumManagerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private String mzXMLFileName1 = "/home/liuba/selfwork/bioSupport/data/140509QXc1_car_anh_tryp_001.mzXML";
    private String mzXMLFileName2 = "/home/liuba/selfwork/bioSupport/data/140509QXc1_car_anh_tryp_004.mzXML";
    private String tsvFileName1 = "src/main/resources/001.tsv";
    private String tsvFileName2 = "src/main/resources/004.tsv";


    @Test
    public void showFirst3MassSpectrums() throws Exception {
        System.setOut(new PrintStream(outContent));

        ArrayList<MassSpectrum> spectrumList = MassSpectrumManager.getReliableMassSpectrums(3, tsvFileName1);
        for (MassSpectrum spectrum : spectrumList) {
            spectrum.showMainDetails();
        }

        String expected = "id = 8314;charge = 3;rTime = 0.0;prMass = 1157.548;\n" +
                "peptid = MVNNGHSFNVEYDDSQDKAVLKDGPLTGTYR\n" +
                "id = 8195;charge = 3;rTime = 0.0;prMass = 1157.5471;\n" +
                "peptid = MVNNGHSFNVEYDDSQDKAVLKDGPLTGTYR\n" +
                "id = 8258;charge = 3;rTime = 0.0;prMass = 1157.5474;\n" +
                "peptid = MVNNGHSFNVEYDDSQDKAVLKDGPLTGTYR";
        assertEquals(expected,
                outContent.toString().trim());
        System.setOut(null);
    }

    /**
     * GetData test
     */
    @Test
    public void getData() throws ParserConfigurationException, SAXException, IOException {
        ArrayList<MassSpectrum> list = MassSpectrumManager.getData(mzXMLFileName1);

        assertEquals(22292, list.size());
    }

    @Test
    public void takeFirst3SpectrumAndSortThem() throws Exception {
        System.setOut(new PrintStream(outContent));

        String fileName = "src/main/resources/001.tsv";
        ArrayList<MassSpectrum> spectrumList = MassSpectrumManager.getReliableMassSpectrums(3, fileName);
        spectrumList.sort(Comparator.comparingLong(o -> o.getId()));

        for (MassSpectrum spectrum : spectrumList) {
            spectrum.showMainDetails();
        }

        String expected = "id = 8195;charge = 3;rTime = 0.0;prMass = 1157.5471;\n" +
                "peptid = MVNNGHSFNVEYDDSQDKAVLKDGPLTGTYR\n" +
                "id = 8258;charge = 3;rTime = 0.0;prMass = 1157.5474;\n" +
                "peptid = MVNNGHSFNVEYDDSQDKAVLKDGPLTGTYR\n" +
                "id = 8314;charge = 3;rTime = 0.0;prMass = 1157.548;\n" +
                "peptid = MVNNGHSFNVEYDDSQDKAVLKDGPLTGTYR";

        assertEquals(expected,
                outContent.toString().trim());
        System.setOut(null);
    }

    @Test
    public void representOfWorking() throws Exception {
        ArrayList<MassSpectrum> spectrumList = MassSpectrumManager.getReliableMassSpectrums(250, tsvFileName1);

        ArrayList<MassSpectrum> list = MassSpectrumManager.getData(mzXMLFileName1);

        HashMap<MassSpectrum, LinkedList<MassSpectrum>> hashMap = MassSpectrumManager.findingCandidates(spectrumList, list);

        for (Map.Entry entry : hashMap.entrySet()) {
            ((MassSpectrum) entry.getKey()).showMainDetails();
            for (MassSpectrum spectrum : (LinkedList<MassSpectrum>) entry.getValue()) {
                spectrum.showCandidateDetails();
            }
            System.out.println("");
        }
    }

}