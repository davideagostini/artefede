package it.artefedeacireale.api.models;

import java.io.Serializable;

/**
 * Created by davide on 14/03/16.
 */
public class Author implements Serializable {

    private int id;
    private String nome_cognome;
    private String anno;
    private String biografia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_cognome() {
        return nome_cognome;
    }

    public void setNome_cognome(String nome_cognome) {
        this.nome_cognome = nome_cognome;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
}
