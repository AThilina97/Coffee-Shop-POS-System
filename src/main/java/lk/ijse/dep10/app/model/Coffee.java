package lk.ijse.dep10.app.model;

import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.sql.Blob;

public class Coffee implements Serializable {
    private String code;
    private String name;
    private int price;
    private ImageView imageView;
    private Blob imgBlob;

    public Coffee() {
    }

    public Coffee(String code, String name, int price, ImageView imageView, Blob imgBlob) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.imageView = imageView;
        this.imgBlob = imgBlob;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Blob getImgBlob() {
        return imgBlob;
    }

    public void setImgBlob(Blob imgBlob) {
        this.imgBlob = imgBlob;
    }

}
