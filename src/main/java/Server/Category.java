package Server;

import java.io.Serializable;

public enum Category implements Serializable {

    DJUR("Djur & natur"),
    FILM("Film & TV"),
    HISTORIA("Historia"),
    SPEL("Dator- och TV_spel"),
    GEOGRAFI("Geografi"),
    TEKNIK("Teknik");

    public final String name;

    Category(String n){
        name = n;
    }
}
