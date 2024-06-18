package com.team4.museum.vo;

import java.sql.Date;

public class ReviewVO {

    private Integer rseq;
    private String title;
    private String author;
    private Integer aseq;
    private Date writedate;
    private String content;

    public Integer getRseq() {
        return rseq;
    }

    public void setRseq(Integer rseq) {
        this.rseq = rseq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getAseq() {
        return aseq;
    }

    public void setAseq(Integer aseq) {
        this.aseq = aseq;
    }

    public Date getWritedate() {
        return writedate;
    }

    public void setWritedate(Date writedate) {
        this.writedate = writedate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
