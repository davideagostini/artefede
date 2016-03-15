package it.artefedeacireale.api.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by davide on 14/03/16.
 */
public class ItineraryDetail implements Serializable{

    private int id;
    private String nome;
    private ArrayList<Church> chiese;

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

    public ArrayList<Church> getChiese() {
        return chiese;
    }

    public void setChiese(ArrayList<Church> chiese) {
        this.chiese = chiese;
    }

}
