package com.asworks.hrmobileapp_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeCertificateAndLanguage {

    @SerializedName("Title")
    @Expose
    private Object title;
    @SerializedName("OrganizationName")
    @Expose
    private Object organizationName;
    @SerializedName("ReadingRate")
    @Expose
    private Integer readingRate;
    @SerializedName("WritingRate")
    @Expose
    private Integer writingRate;
    @SerializedName("SpeakingRate")
    @Expose
    private Integer speakingRate;
    @SerializedName("CompletedDate")
    @Expose
    private String completedDate;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(Object organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getReadingRate() {
        return readingRate;
    }

    public void setReadingRate(Integer readingRate) {
        this.readingRate = readingRate;
    }

    public Integer getWritingRate() {
        return writingRate;
    }

    public void setWritingRate(Integer writingRate) {
        this.writingRate = writingRate;
    }

    public Integer getSpeakingRate() {
        return speakingRate;
    }

    public void setSpeakingRate(Integer speakingRate) {
        this.speakingRate = speakingRate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

}