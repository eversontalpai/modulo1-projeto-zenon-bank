package br.com.zenon.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CsvReaderWithStream {
    private static final String PATH = "data/";

    public static Stream<String> readCsv(String csvFileName) throws IOException {
        Path csvFile = Path.of(PATH.concat(csvFileName));
        return Files.lines(csvFile).skip(1);

    }

}
