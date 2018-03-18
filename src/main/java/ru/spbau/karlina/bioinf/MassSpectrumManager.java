package ru.spbau.karlina.bioinf;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import uk.ac.ebi.pride.tools.mzxml_parser.MzXMLFile;
import uk.ac.ebi.pride.tools.mzxml_parser.MzXMLParsingException;
import uk.ac.ebi.pride.tools.mzxml_parser.mzxml.model.Scan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MassSpectrumManager {

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

    public static LinkedList<MassSpectrum> getData(String fileName) {
        LinkedList<MassSpectrum> list = new LinkedList<>();
        File mzxmlFile = new File(fileName);
        try {
            MzXMLFile inputParser = new MzXMLFile(mzxmlFile);

            for (long i : inputParser.getScanNumbers()) {
                Scan scan = inputParser.getScanByNum(i);
                System.out.println(scan.getNum());
                if (scan.getMsLevel() == 2) {
                    list.add(new MassSpectrum(scan.getNum(), scan.getRetentionTime(),
                            scan.getPrecursorMz().get(0).getValue()));
                }
            }
        } catch (MzXMLParsingException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static HashMap<MassSpectrum, LinkedList<MassSpectrum>> findingCandidates(
            ArrayList<MassSpectrum> spectrums, ArrayList<MassSpectrum> scans) {
        HashMap<MassSpectrum, LinkedList<MassSpectrum>> hashMap = new HashMap<>();
        spectrums.sort(Comparator.comparingInt(o -> o.getRetentionTime().getSeconds()));

        return hashMap;
    }
}


/*

                for (Iterator<Spectrum> it = inputParser.getSpectrumIterator(); it.hasNext() && co++ < 50; ) {
                Spectrum spectrum = it.next();
                //System.out.println(spectrum.getId());
                if (spectrum.getMsLevel() == 2){
                    list.add(new MassSpectrum(spectrum.getId(), spectrum.getPrecursorMZ()));
                    //System.out.println(spectrum.getPrecursorMZ());
                }*/
