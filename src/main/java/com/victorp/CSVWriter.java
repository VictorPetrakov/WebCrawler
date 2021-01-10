package com.victorp;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.supercsv.prefs.CsvPreference.STANDARD_PREFERENCE;

public class CSVWriter {

    public CSVWriter() throws IOException {
    };

    private static CellProcessor[] getProcessors(List<String> list) {

        final CellProcessor[] processors = new CellProcessor[list.size()];

        return processors;
    }

    public void writeAllResultToList(Map<String, Integer> resultList) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/resultList.csv");
        ICsvListWriter csvWriter = new CsvListWriter(writer, STANDARD_PREFERENCE);
        List<String> list = new ArrayList<>();
        resultList.entrySet().parallelStream().forEach(entry -> list.add(entry.getKey() + " " + entry.getValue()));
        final CellProcessor[] processors = getProcessors(list);
        csvWriter.write(list, processors);
        csvWriter.flush();
        csvWriter.close();
    }

    public void writeTop10ResultToList(Map<String, Integer> top10ResultList) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/top10ResultList.csv");
        ICsvListWriter csvWriter = new CsvListWriter(writer, STANDARD_PREFERENCE);
        List<String> list = new ArrayList<>();
        top10ResultList.entrySet().parallelStream().forEach(entry -> list.add(entry.getKey() + " " + entry.getValue()));
        final CellProcessor[] processors = getProcessors(list);
        csvWriter.write(list, processors);
        csvWriter.flush();
        csvWriter.close();
    }

}
