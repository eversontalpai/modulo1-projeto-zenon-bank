package br.com.zenon.reader;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class CsvReader {
    private static final String PATH = "/Users/eversontalpai/projetos/pessoal/modulo1-projeto-zenon-bank/data/";

    public static List<String> readCsv(String csvFileName, int limit) throws IOException {
        Path csvFile = Path.of(PATH.concat(csvFileName));
        return Files.readAllLines(csvFile).stream().skip(1).limit(limit).toList();

    }

}
