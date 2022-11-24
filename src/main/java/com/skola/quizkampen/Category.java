package com.skola.quizkampen;

public enum Category {

    DJUR("Djur & natur"),
    FILM ("Film & TV"),
    HISTORIA ("Historia"),
    SPEL ("Dator- och TV_spel"),
    GEOGRAFI ("Geografi"),
    TEKNIK ("Teknik");

    public final String name;

    Category (String n){
        name = n;
    }
}
