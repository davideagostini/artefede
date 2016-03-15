package it.artefedeacireale.api.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by davide on 14/03/16.
 */
public class Church implements Serializable {

    private int id;
    private String nome;
    private String anno_fondazione;
    private String indirizzo;
    private String citta;
    private String descrizione;
    private String orario_s_messe;
    private String orario_apertura;
    private String telefono;
    private String web;
    private String email;
    private String facebook;
    private String video;
    private String latitudine;
    private String longitudine;
    private String tempo;
    private ArrayList<ImageChurch> image_chiese;
    private ArrayList<Artwork> opere_chiese;

    public ArrayList<Artwork> getOpere_chiese() {
        return opere_chiese;
    }

    public void setOpere_chiese(ArrayList<Artwork> opere_chiese) {
        this.opere_chiese = opere_chiese;
    }

    public ArrayList<ImageChurch> getImage_chiese() {
        return image_chiese;
    }

    public void setImage_chiese(ArrayList<ImageChurch> image_chiese) {
        this.image_chiese = image_chiese;
    }

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

    public String getAnno_fondazione() {
        return anno_fondazione;
    }

    public void setAnno_fondazione(String anno_fondazione) {
        this.anno_fondazione = anno_fondazione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getOrario_s_messe() {
        return orario_s_messe;
    }

    public void setOrario_s_messe(String orario_s_messe) {
        this.orario_s_messe = orario_s_messe;
    }

    public String getOrario_apertura() {
        return orario_apertura;
    }

    public void setOrario_apertura(String orario_apertura) {
        this.orario_apertura = orario_apertura;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}
