package br.com.zenon.reader;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class CsvReader {

    public List<String> readCsv(String folderPath) throws IOException {
        Path folder = Path.of(folderPath);
        Path csvFile;

        try(Stream<Path> files = Files.list(folder)){
            csvFile = files
                    .filter(Files::isRegularFile)
                    .filter(file-> isCsv(file))
                    .findFirst()
                    .orElseThrow(()->
                            new IllegalArgumentException(
                                    "Nenhum arquivo csv encontrado em:" + folderPath
                            ));

        }
        return Files.readAllLines(csvFile).stream().skip(1).toList();

    }

    private boolean isCsv(Path file) {
        return file.getFileName()
                .toString()
                .toLowerCase()
                .endsWith(".csv");
    }
}
