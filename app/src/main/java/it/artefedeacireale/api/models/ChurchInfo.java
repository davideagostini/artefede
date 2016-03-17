package it.artefedeacireale.api.models;

/**
 * Created by davide on 17/03/16.
 */
public class ChurchInfo {

    private String text;
    private int type;

    public ChurchInfo(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
