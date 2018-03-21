package ru.spbau.karlina.bioinf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

/** Special parser for mzXML
 *  Recognizes only scan number, retention time and precursorMZ */
public class ParserXML {
    public static int count = 0;
    public static int count1 = 0;

    static public ArrayList<MassSpectrum> getData(String fileName) {
        ArrayList<MassSpectrum> list = new ArrayList<>();
        try {
            Scanner scanner  = new Scanner(new File(fileName));
            while (scanner.hasNextLine() && count < 100) {
                String current = scanner.nextLine();
                if (current.startsWith("  <scan")) {
                    parseOneSpectrum(current, scanner, list);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Parsing mzXML file problem.");
            e.printStackTrace();
        }
        System.out.println(count1);
         return list;

    }

    static private void parseOneSpectrum(String firstLine, Scanner scanner,
                                          ArrayList<MassSpectrum> list) {
      String second = scanner.nextLine();
        if (second.charAt(12) == '2') {
          long num = parseLong(firstLine.substring(5));
          double precMz = 0, rTime = 0;
          while (scanner.hasNextLine()) {
              String current = scanner.nextLine();

              if (current.equals("</scan>")) {
                  list.add(new MassSpectrum(num, rTime, precMz));
                  count++;
                  return;
              }

              if (current.matches("^<retentionTime")) {
                  rTime = parseDouble(current.substring(17));
              } else if (current.matches("^<precursorMz")) {
                  precMz = 0;
              }
          }
      } else {
            System.out.println(second.charAt(12));
            count1++;
        }
    }
}
