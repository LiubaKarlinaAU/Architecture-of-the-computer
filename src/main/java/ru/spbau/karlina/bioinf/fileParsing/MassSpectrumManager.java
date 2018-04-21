package ru.spbau.karlina.bioinf.fileParsing;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Special class for working with spectrums data files
 * Load data from tsv and mzXML file format and finding candidates for identified spectrums
 */
public class MassSpectrumManager {
    private static final int TIME_SECONDS = 12;

    /**
     * Load some first (more reliable) spectrums and return them like ArrayList
     *
     * @param count    - certain number of spectrum that you need to load
     * @param fileName - name of tsv file format file
     * @return - (ArrayList) of loaded spectrums
     */
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

    /**
     * Load spectrums from mzXML file format and return them like ArrayList
     *
     * @param fileName - name of mzXML file format file
     * @return - (ArrayList) of loaded spectrums
     */
    public static ArrayList<MassSpectrum> getData(String fileName) {
        return ParserMZXML.getData(fileName);
    }

    /**
     * Trying to find candidates in given identified spectrums and all spectrums
     *
     * @param spectrums - identified spectrums
     * @param scans     - possible candidate spectrums
     * @return - (HashMap) pairs of spectrums and their candidates
     */
    public static HashMap<MassSpectrum, LinkedList<MassSpectrum>> findingCandidates(
            ArrayList<MassSpectrum> spectrums, ArrayList<MassSpectrum> scans) {
        HashMap<MassSpectrum, LinkedList<MassSpectrum>> hashMap = new HashMap<>();
        spectrums.sort(Comparator.comparingLong(o -> o.getId()));

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

            candidate++;
            while (candidate < scans.size() && abs(rTime - scans.get(candidate).getRetentionTime()) < TIME_SECONDS) {
                if (scans.get(candidate).match(spectrum)) {
                    list.add(scans.get(candidate));
                }
                candidate++;
            }


            if (list.size() != 0) {
                spectrum.setRetentionTime(rTime);
                hashMap.put(spectrum, list);
            }
        }
        return hashMap;
    }
}