package com.example.dkfz.dnasequenceapp.model;

import java.util.List;

public class DNAStrand {
    private List<DNABase> bases;

    public DNAStrand(List<DNABase> bases) {
        this.bases = bases;
    }

    public List<DNABase> getBases() {
        return bases;
    }

    public void setBases(List<DNABase> bases) {
        this.bases = bases;
    }



}