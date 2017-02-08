package com.asworks.hrmobileapp_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeJobExperience {

    @SerializedName("CompanyName")
    @Expose
    private Object companyName;
    @SerializedName("Title")
    @Expose
    private Object title;
    @SerializedName("WorkYear")
    @Expose
    private Integer workYear;
    @SerializedName("WorkMonth")
    @Expose
    private Integer workMonth;
    @SerializedName("QuitReason")
    @Expose
    private Object quitReason;
    @SerializedName("Salary")
    @Expose
    private Integer salary;
    @SerializedName("ActiveJob")
    @Expose
    private Boolean activeJob;
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

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public Integer getWorkMonth() {
        return workMonth;
    }

    public void setWorkMonth(Integer workMonth) {
        this.workMonth = workMonth;
    }

    public Object getQuitReason() {
        return quitReason;
    }

    public void setQuitReason(Object quitReason) {
        this.quitReason = quitReason;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Boolean getActiveJob() {
        return activeJob;
    }

    public void setActiveJob(Boolean activeJob) {
        this.activeJob = activeJob;
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