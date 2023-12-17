package com.example.dkfz.dnasequenceapp.runner;

import com.example.dkfz.dnasequenceapp.model.DNABase;
import com.example.dkfz.dnasequenceapp.model.DNAStrand;
import com.example.dkfz.dnasequenceapp.service.DNASequenceProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class DNASequenceRunner implements CommandLineRunner {

    private final DNASequenceProcessor processor;
    private ExecutorService executorService;

    @Value("${app.thread-pool-size}")
    private int threadPoolSize;

    @Value("${app.output-file-path}")
    private String outputFilePath;

    public DNASequenceRunner(DNASequenceProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length < 1) {
            System.out.println("Please provide the input file path as the first argument.");
            return;
        }

        String inputFilePath = args[0];
        List<DNAStrand> strands = readDNAStrandsFromFile(inputFilePath);

        executorService = Executors.newFixedThreadPool(threadPoolSize);
        executorService.submit(() -> {
            List<String> results = processor.processSequences(strands);
            writeResultsToFile(results, outputFilePath);
        });

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
      //  System.exit(0);

      /*  if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            System.err.println("All tasks did not finish in the given time. Forcing shutdown...");
            executorService.shutdownNow();
        } else {
            System.out.println("All tasks completed successfully.");
        }*/
    }

    private List<DNAStrand> readDNAStrandsFromFile(String filePath) throws IOException {
        List<DNAStrand> strands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<DNABase> bases = Arrays.stream(line.split("\\s+"))
                        .map(DNABase::valueOf)
                        .collect(Collectors.toList());
                strands.add(new DNAStrand(bases));
            }
        }
        return strands;
    }

    private void writeResultsToFile(List<String> results, String outputPath) {
        try {
            Path path = Paths.get(outputPath);
            Files.write(path, results);
            System.out.println("File successfully created at: " + path.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

}
