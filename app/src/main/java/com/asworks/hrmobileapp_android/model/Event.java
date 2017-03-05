package com.asworks.hrmobileapp_android.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("EventType")
    @Expose
    private EventType eventType;
    @SerializedName("BeginDate")
    @Expose
    private String beginDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("EventDays")
    @Expose
    private String eventDays;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Restriction")
    @Expose
    private Object restriction;
    @SerializedName("EventDocument")
    @Expose
    private Object eventDocument;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getBeginDate() {
        return beginDate.substring(0, 10);
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate.substring(0, 10);
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEventDays() {
        return eventDays;
    }

    public void setEventDays(String eventDays) {
        this.eventDays = eventDays;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Object getRestriction() {
        return restriction;
    }

    public void setRestriction(Object restriction) {
        this.restriction = restriction;
    }

    public Object getEventDocument() {
        return eventDocument;
    }

    public void setEventDocument(Object eventDocument) {
        this.eventDocument = eventDocument;
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