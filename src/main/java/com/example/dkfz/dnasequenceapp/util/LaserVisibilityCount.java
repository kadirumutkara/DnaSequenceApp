package com.example.dkfz.dnasequenceapp.util;

public class LaserVisibilityCount {
    private int redCount;
    private int greenCount;

    public void incrementRed() {
        redCount++;
    }

    public void incrementGreen() {
        greenCount++;
    }

    @Override
    public String toString() {
        return redCount + " / " + greenCount;
    }

    public int getRedCount() {
        return redCount;
    }

    public void setRedCount(int redCount) {
        this.redCount = redCount;
    }

    public int getGreenCount() {
        return greenCount;
    }

    public void setGreenCount(int greenCount) {
        this.greenCount = greenCount;
    }
}