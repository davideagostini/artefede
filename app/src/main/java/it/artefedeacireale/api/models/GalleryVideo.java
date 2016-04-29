package it.artefedeacireale.api.models;

import java.io.Serializable;

/**
 * Created by davide on 29/04/16.
 */
public class GalleryVideo implements Serializable {

    private int image;
    private String title;
    private String subtitle;
    private String url;

    public GalleryVideo(int image, String title, String subtitle, String url) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
