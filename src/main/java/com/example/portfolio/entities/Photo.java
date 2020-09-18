package com.example.portfolio.entities;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id")
    private Integer id;
    private String title;
    private String description;
    private String path;
    private String webpath;
    private String filename;
    public Photo(){}

    public Photo(Integer id, String title, String description, String path, String webpath, String filename) {
        this.title = title;
        this.description = description;
        this.path = path;
        this.webpath = webpath;
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getWebpath() {
        return webpath;
    }

    public void setWebpath(String webpath) {
        this.webpath = webpath;
    }
}
