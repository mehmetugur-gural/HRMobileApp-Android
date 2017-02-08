package com.asworks.hrmobileapp_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeEducation {

    @SerializedName("OrganizationName")
    @Expose
    private Object organizationName;
    @SerializedName("Institute")
    @Expose
    private Object institute;
    @SerializedName("Degree")
    @Expose
    private Object degree;
    @SerializedName("ActiveEducation")
    @Expose
    private Boolean activeEducation;
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

    public Object getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(Object organizationName) {
        this.organizationName = organizationName;
    }

    public Object getInstitute() {
        return institute;
    }

    public void setInstitute(Object institute) {
        this.institute = institute;
    }

    public Object getDegree() {
        return degree;
    }

    public void setDegree(Object degree) {
        this.degree = degree;
    }

    public Boolean getActiveEducation() {
        return activeEducation;
    }

    public void setActiveEducation(Boolean activeEducation) {
        this.activeEducation = activeEducation;
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