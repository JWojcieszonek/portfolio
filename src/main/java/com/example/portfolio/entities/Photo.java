package com.example.portfolio.entities;

import org.apache.tomcat.jni.File;

import javax.persistence.*;
import java.awt.*;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "photoid")
    private Integer photoId;
    private String title;
    private String description;
    private byte[] image;

    public Photo(){}

    public Photo(Integer photoId, String title, String description, byte[] image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
