package ru.spbau.karlina.bioinf;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.ac.ebi.pride.tools.jmzreader.JMzReader;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.Param;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.mzxml_parser.MzXMLFile;
import uk.ac.ebi.pride.tools.mzxml_parser.MzXMLParsingException;
import uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model.Scan;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MassSpectrumManager {
    private static FileReader readerMzxml;

    public static MassSpectrum[] getMassSpectrumsId(int count, String fileName) throws FileNotFoundException {
        MassSpectrum[] array = new MassSpectrum[count];

        TsvParserSettings settings = new TsvParserSettings();
        settings.setNumberOfRecordsToRead(count);
        TsvParser parser = new TsvParser(settings);

        List<String[]> rows = parser.parseAll(new FileReader(fileName));
        for (int i = 0; i < count; ++i) {
            array[i] = new MassSpectrum(rows.get(i));
        }

        return array;
    }

    /*
        public static void setMzxmlFileReader(String fileName) {}

        public static MassSpectrum[] getCandidate(int count, String fileName) throws FileNotFoundException {
            MassSpectrum[] array = new MassSpectrum[count];

            TsvParserSettings settings = new TsvParserSettings();
            settings.setNumberOfRecordsToRead(count);
            TsvParser parser = new TsvParser(settings);

            List<String[]> rows = parser.parseAll(new FileReader(fileName));
            for (int i = 0; i < count; ++i) {
                array[i] = new MassSpectrum(rows.get(i));
            }

            return array;
        } */
/*
    public static LinkedList<MassSpectrum> getData(String fileName) throws IOException, SAXException, ParserConfigurationException {
        LinkedList<MassSpectrum> list = new LinkedList<MassSpectrum>();
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = f.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));

        NodeList children = doc.getChildNodes().item(0).getChildNodes().item(1).getChildNodes();
        System.out.println(children.getLength());
        int counter = 0;
        for (int i = 7; i < children.getLength() ; i += 2) {
            Node node = children.item(i);
            String level = node.getAttributes().item(5).getTextContent();
            if (level == "2") {
                list.add(new MassSpectrum(node.getAttributes().item(6).getTextContent(), node.getAttributes().item(9).getTextContent()));
                System.out.println(node.getNodeName());
                System.out.println(node.getTextContent());
            }
        }
        System.out.println(counter);
        return list;
    }*/
    public static LinkedList<MassSpectrum> getData(String fileName) {
        LinkedList<MassSpectrum> list = new LinkedList<MassSpectrum>();
        File mzxmlFile = new File(fileName);
        try {
            int co = 0;
            MzXMLFile inputParser = new MzXMLFile(mzxmlFile);
            for (Iterator<Spectrum> it = inputParser.getSpectrumIterator(); it.hasNext() && co++ < 50; ) {
                Spectrum spectrum = it.next();
                System.out.println(spectrum.getId());
                if (spectrum.getMsLevel() == 2){
                    list.add(new MassSpectrum(spectrum.getId(), spectrum.getPrecursorMZ()));
                    //System.out.println(spectrum.getPrecursorMZ());
                }
            }
        } catch (MzXMLParsingException e) {
            e.printStackTrace();
        }

        return list;
    }
}
