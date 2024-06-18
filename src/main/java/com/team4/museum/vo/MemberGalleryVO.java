package com.team4.museum.vo;

import java.sql.Date;

public class MemberGalleryVO {

    private int mseq;
    private String authorId;
    private String authorName;
    private String title;
    private Date writedate;
    private String content;
    private int readcount;
    private String image;
    private String savefilename;

    public int getMseq() {
        return mseq;
    }

    public void setMseq(int mseq) {
        this.mseq = mseq;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getReadcount() {
        return readcount;
    }

    public void setReadcount(int readcount) {
        this.readcount = readcount;
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

}
