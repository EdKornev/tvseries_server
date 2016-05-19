package com.ek.serialsserver.picture.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Eduard on 19.05.2016.
 */
@Document
public class PictureDoc {

    @Id
    private ObjectId id;

    private String fileName;
    private String contentType;
    private ObjectId originalFileId;
    private Date createdDate = new Date();

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ObjectId getOriginalFileId() {
        return originalFileId;
    }

    public void setOriginalFileId(ObjectId originalFileId) {
        this.originalFileId = originalFileId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
