package org.example.model;

public enum Importance {
    ESSENTIAL("Essential"),
    MODERATE("Moderate"),
    LOW("Low");

    private final String i;
    Importance(String i){
        this.i = i;
    }

    public String getI() {
        return i;
    }
}
