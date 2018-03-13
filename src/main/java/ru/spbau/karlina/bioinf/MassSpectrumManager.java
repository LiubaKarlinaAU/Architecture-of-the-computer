package ru.spbau.karlina.bioinf;

        import com.univocity.parsers.tsv.TsvParser;
        import com.univocity.parsers.tsv.TsvParserSettings;

        import java.io.FileNotFoundException;
        import java.io.FileReader;
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
    }
}
