package it.artefedeacireale.api.models;

/**
 * Created by davide on 14/03/16.
 */
public class Quote {

    private int id;
    private String frase;
    private String autore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }
}
