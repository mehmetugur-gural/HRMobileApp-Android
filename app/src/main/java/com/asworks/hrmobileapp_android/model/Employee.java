package com.asworks.hrmobileapp_android.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("TCIdentityNo")
    @Expose
    private String tCIdentityNo;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("MailAddress")
    @Expose
    private String mailAddress;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("BirthDate")
    @Expose
    private String birthDate;
    @SerializedName("BirthPlace")
    @Expose
    private String birthPlace;
    @SerializedName("Gender")
    @Expose
    private Integer gender;
    @SerializedName("Height")
    @Expose
    private Integer height;
    @SerializedName("Weight")
    @Expose
    private Integer weight;
    @SerializedName("Picture")
    @Expose
    private String picture;
    @SerializedName("CVFile")
    @Expose
    private String cVFile;
    @SerializedName("Description")
    @Expose
    private Object description;
    @SerializedName("HairColor")
    @Expose
    private String hairColor;
    @SerializedName("EyeColor")
    @Expose
    private String eyeColor;
    @SerializedName("HasDriverLicense")
    @Expose
    private Boolean hasDriverLicense;
    @SerializedName("ActiveCarDriver")
    @Expose
    private Boolean activeCarDriver;
    @SerializedName("UpperBodySize")
    @Expose
    private String upperBodySize;
    @SerializedName("LowerBodySize")
    @Expose
    private String lowerBodySize;
    @SerializedName("ChestSize")
    @Expose
    private String chestSize;
    @SerializedName("WaistSize")
    @Expose
    private String waistSize;
    @SerializedName("HipSize")
    @Expose
    private String hipSize;
    @SerializedName("ShoeSize")
    @Expose
    private String shoeSize;
    @SerializedName("JacketSize")
    @Expose
    private String jacketSize;
    @SerializedName("PantSize")
    @Expose
    private String pantSize;
    @SerializedName("JeanSize")
    @Expose
    private String jeanSize;
    @SerializedName("SkirtSize")
    @Expose
    private String skirtSize;
    @SerializedName("EmployeeAvailability")
    @Expose
    private List<EmployeeAvailability> employeeAvailability = null;
    @SerializedName("EmployeeCertificateAndLanguage")
    @Expose
    private List<EmployeeCertificateAndLanguage> employeeCertificateAndLanguage = null;
    @SerializedName("EmployeeEducation")
    @Expose
    private List<EmployeeEducation> employeeEducation = null;
    @SerializedName("EmployeeJobExperience")
    @Expose
    private List<EmployeeJobExperience> employeeJobExperience = null;
    @SerializedName("Profession")
    @Expose
    private List<Profession> profession = null;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTCIdentityNo() {
        return tCIdentityNo;
    }

    public void setTCIdentityNo(String tCIdentityNo) {
        this.tCIdentityNo = tCIdentityNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCVFile() {
        return cVFile;
    }

    public void setCVFile(String cVFile) {
        this.cVFile = cVFile;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public Boolean getHasDriverLicense() {
        return hasDriverLicense;
    }

    public void setHasDriverLicense(Boolean hasDriverLicense) {
        this.hasDriverLicense = hasDriverLicense;
    }

    public Boolean getActiveCarDriver() {
        return activeCarDriver;
    }

    public void setActiveCarDriver(Boolean activeCarDriver) {
        this.activeCarDriver = activeCarDriver;
    }

    public String getUpperBodySize() {
        return upperBodySize;
    }

    public void setUpperBodySize(String upperBodySize) {
        this.upperBodySize = upperBodySize;
    }

    public String getLowerBodySize() {
        return lowerBodySize;
    }

    public void setLowerBodySize(String lowerBodySize) {
        this.lowerBodySize = lowerBodySize;
    }

    public String getChestSize() {
        return chestSize;
    }

    public void setChestSize(String chestSize) {
        this.chestSize = chestSize;
    }

    public String getWaistSize() {
        return waistSize;
    }

    public void setWaistSize(String waistSize) {
        this.waistSize = waistSize;
    }

    public String getHipSize() {
        return hipSize;
    }

    public void setHipSize(String hipSize) {
        this.hipSize = hipSize;
    }

    public String getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(String shoeSize) {
        this.shoeSize = shoeSize;
    }

    public String getJacketSize() {
        return jacketSize;
    }

    public void setJacketSize(String jacketSize) {
        this.jacketSize = jacketSize;
    }

    public String getPantSize() {
        return pantSize;
    }

    public void setPantSize(String pantSize) {
        this.pantSize = pantSize;
    }

    public String getJeanSize() {
        return jeanSize;
    }

    public void setJeanSize(String jeanSize) {
        this.jeanSize = jeanSize;
    }

    public String getSkirtSize() {
        return skirtSize;
    }

    public void setSkirtSize(String skirtSize) {
        this.skirtSize = skirtSize;
    }

    public List<EmployeeAvailability> getEmployeeAvailability() {
        return employeeAvailability;
    }

    public void setEmployeeAvailability(List<EmployeeAvailability> employeeAvailability) {
        this.employeeAvailability = employeeAvailability;
    }

    public List<EmployeeCertificateAndLanguage> getEmployeeCertificateAndLanguage() {
        return employeeCertificateAndLanguage;
    }

    public void setEmployeeCertificateAndLanguage(List<EmployeeCertificateAndLanguage> employeeCertificateAndLanguage) {
        this.employeeCertificateAndLanguage = employeeCertificateAndLanguage;
    }

    public List<EmployeeEducation> getEmployeeEducation() {
        return employeeEducation;
    }

    public void setEmployeeEducation(List<EmployeeEducation> employeeEducation) {
        this.employeeEducation = employeeEducation;
    }

    public List<EmployeeJobExperience> getEmployeeJobExperience() {
        return employeeJobExperience;
    }

    public void setEmployeeJobExperience(List<EmployeeJobExperience> employeeJobExperience) {
        this.employeeJobExperience = employeeJobExperience;
    }

    public List<Profession> getProfession() {
        return profession;
    }

    public void setProfession(List<Profession> profession) {
        this.profession = profession;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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