package ru.spbau.karlina.bioinf;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.abs;

public class MassSpectrumManager {
    private static final int TIME_SECONDS = 12;
    static private ArrayList<Long> spectrumNumbers = new ArrayList<>();

    public static ArrayList<MassSpectrum> getReliableMassSpectrums(int count, String fileName) throws FileNotFoundException {
        ArrayList<MassSpectrum> list = new ArrayList<>(count);

        TsvParserSettings settings = new TsvParserSettings();
        settings.setNumberOfRecordsToRead(count);
        TsvParser parser = new TsvParser(settings);

        List<String[]> rows = parser.parseAll(new FileReader(fileName));
        for (int i = 0; i < count; ++i) {
            list.add(new MassSpectrum(rows.get(i)));
        }

        return list;
    }

    public static ArrayList<MassSpectrum> getData(String fileName) {
        return ParserXML.getData(fileName);
    }

/*
    public static ArrayList<MassSpectrum> getData(String fileName) throws IOException, ParserConfigurationException, org.xml.sax.SAXException {
        ArrayList<MassSpectrum> list = new ArrayList<>();
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));

        NodeList children = doc.getChildNodes().item(0).getChildNodes().item(1).getChildNodes();
        System.out.println(children.getLength());
        for (int i = 7; i < 11; i += 2) {
            Node node = children.item(i);
            list.add(new MassSpectrum(node.getAttributes().item(6).getTextContent(),
                    node.getAttributes().item(9).getTextContent()));
            //System.out.println(node.getNodeName());
            //System.out.println(node.getTextContent());
        }
        return list;
    }*/


/*
    public static ArrayList<MassSpectrum> getData(String fileName) {
        ArrayList<MassSpectrum> list = new ArrayList<>();
        File mzxmlFile = new File(fileName);
        try {
            MzXMLFile inputParser = new MzXMLFile(mzxmlFile);
            for (Scan scan : inputParser.getMS2ScanIterator()) {
                    list.add(new MassSpectrum(scan.getNum(), scan.getRetentionTime(),
                            scan.getPrecursorMz().get(0).getValue()));
                    //spectrumNumbers.add(scan.getNum());
                spectrumNumbers.add(scan.getNum());
            }
            System.out.println("level2 spec count -" + inputParser.getMS2ScanCount());
            System.out.println("level1 spec count -" + inputParser.getMS1ScanCount());
            System.out.println("spec count -" + inputParser.getScanNumbers().size());
            System.out.println(inputParser.getSpectraCount());
            //System.out.println(inputParser.getScanNumbers().get((int)(inputParser.getScanNumbers() - new Long(1));
            System.out.println(inputParser.getScanNumbers());
            //System.out.println(inputParser.getSp);
        } catch (MzXMLParsingException e) {
            e.printStackTrace();
        }

        //System.out.println(spectrumNumbers.size());
        return list;
    } */

    public static HashMap<MassSpectrum, LinkedList<MassSpectrum>> findingCandidates(
            ArrayList<MassSpectrum> spectrums, ArrayList<MassSpectrum> scans) {
        HashMap<MassSpectrum, LinkedList<MassSpectrum>> hashMap = new HashMap<>();
        spectrums.sort(Comparator.comparingLong(o -> o.getId()));
        System.out.println(spectrumNumbers.size());

        int current = 0;
        for (MassSpectrum spectrum : spectrums) {
            LinkedList<MassSpectrum> list = new LinkedList<>();

            while (current < scans.size() && scans.get(current).getId() < spectrum.getId()) {
                current++;
            }

            if (current >= scans.size() || scans.get(current).getId() != spectrum.getId()) {
                break;
            }

            MassSpectrum scan = scans.get(current);
            double rTime = scan.getRetentionTime();

            int candidate = current - 1;
            while (candidate > -1 && rTime - scans.get(candidate).getRetentionTime() < TIME_SECONDS) {
                candidate--;
            }

            while (candidate < scans.size() && abs(rTime - scans.get(candidate).getRetentionTime()) < TIME_SECONDS) {
                if (scans.get(candidate).match(spectrum)) {
                    list.add(scans.get(candidate));
                }
            }


            if (list.size() != 0) {
                hashMap.put(spectrum, list);
            }
        }
        return hashMap;
    }
}