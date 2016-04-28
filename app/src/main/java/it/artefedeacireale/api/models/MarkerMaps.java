package it.artefedeacireale.api.models;

import java.io.Serializable;

/**
 * Created by davide on 28/04/16.
 */
public class MarkerMaps implements Serializable {

    private int id;
    private String nome;
    private String latitudine;
    private String longitudine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }
}
