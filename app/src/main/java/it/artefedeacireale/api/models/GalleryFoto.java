package it.artefedeacireale.api.models;

import java.io.Serializable;

/**
 * Created by davide on 29/04/16.
 */
public class GalleryFoto implements Serializable {

    private int nome;

    public GalleryFoto(int nome) {
        this.nome = nome;
    }

    public int getNome() {
        return nome;
    }

    public void setNome(int nome) {
        this.nome = nome;
    }

}
