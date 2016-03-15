package it.artefedeacireale.api.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by davide on 14/03/16.
 */
public class Artwork implements Serializable {

    private int id;
    private String nome;
    private String anno;
    private String tecnica;
    private String descrizione;
    private ArrayList<ImageArtwork> image_opera;
    private ArrayList<Author> artisti;

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

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public ArrayList<ImageArtwork> getImage_opera() {
        return image_opera;
    }

    public void setImage_opera(ArrayList<ImageArtwork> image_opera) {
        this.image_opera = image_opera;
    }

    public ArrayList<Author> getArtisti() {
        return artisti;
    }

    public void setArtisti(ArrayList<Author> artisti) {
        this.artisti = artisti;
    }
}
