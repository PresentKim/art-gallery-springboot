package com.team4.museum.vo;

import java.sql.Date;

public class ArtworkVO {

    private Integer aseq;
    private String name;
    private String category;
    private String artist;
    private String year;
    private String material;
    private String size;
    private String displayyn;
    private String content;
    private String image;
    private String savefilename;
    private Date indate;

    public Integer getAseq() {
        return aseq;
    }

    public void setAseq(Integer aseq) {
        this.aseq = aseq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSavefilename() {
        return savefilename;
    }

    public void setSavefilename(String savefilename) {
        this.savefilename = savefilename;
    }

    public String getFullSavefilename() {
        if (savefilename.startsWith("http")) {
            return savefilename;
        }

        return "static/image/artwork/" + savefilename;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getDisplayyn() {
        return displayyn;
    }

    public void setDisplayyn(String displayyn) {
        this.displayyn = displayyn;
    }

    public boolean isDisplay() {
        return displayyn.equals("Y");
    }

}
