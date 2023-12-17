package com.example.dkfz.dnasequenceapp.service;

import com.example.dkfz.dnasequenceapp.model.DNABase;
import com.example.dkfz.dnasequenceapp.model.DNAStrand;
import com.example.dkfz.dnasequenceapp.util.LaserVisibilityCount;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DNASequenceProcessor {

    public List<String> processSequences(List<DNAStrand> sequences) {
        Map<Integer, LaserVisibilityCount> cycleResults = new HashMap<>();

        for (int cycle = 0; cycle < getMaxSequenceLength(sequences); cycle++) {
            LaserVisibilityCount count = new LaserVisibilityCount();
            for (DNAStrand strand : sequences) {
                if (cycle < strand.getBases().size()) {
                    DNABase base = strand.getBases().get(cycle);
                    updateVisibilityCount(base, count);
                }
            }
            cycleResults.put(cycle + 1, count);
        }

        return cycleResults.entrySet().stream()
                .map(entry -> entry.getKey() + ". red / green: " + entry.getValue())
                .collect(Collectors.toList());
    }

    private int getMaxSequenceLength(List<DNAStrand> sequences) {
        return sequences.stream().mapToInt(s -> s.getBases().size()).max().orElse(0);
    }

    private void updateVisibilityCount(DNABase base, LaserVisibilityCount count) {
        switch (base) {
            case C:
                count.incrementRed();
                break;
            case T:
                count.incrementGreen();
                break;
            case A:
                count.incrementRed();
                count.incrementGreen();
                break;
            // G case is not visible in either, so do nothing
        }
    }
}
