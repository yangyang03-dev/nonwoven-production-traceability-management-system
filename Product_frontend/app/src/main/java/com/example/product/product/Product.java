package com.example.product.product;

import com.google.gson.annotations.SerializedName;
public class Product {


    private Integer id;

    @SerializedName("MCompany")
    private String MCompany;

    @SerializedName("MDestination")
    private String MDestination;

    @SerializedName("MQualityLevel")
    private String MQualityLevel;

    @SerializedName("MTime")
    private String MTime;

    @SerializedName("MaterialId1")
    private Integer MaterialId1;

    @SerializedName("MaterialId2")
    private Integer MaterialId2;

    @SerializedName("wsName1")
    private String wsName1;

    @SerializedName("wsStartTime1")
    private String wsStartTime1;

    @SerializedName("wsEndTime1")
    private String wsEndTime1;

    @SerializedName("wsName2")
    private String wsName2;

    @SerializedName("wsStartTime2")
    private String wsStartTime2;

    @SerializedName("wsEndTime2")
    private String wsEndTime2;

    @SerializedName("wsName3")
    private String wsName3;

    @SerializedName("wsStartTime3")
    private String wsStartTime3;

    @SerializedName("wsEndTime3")
    private String wsEndTime3;

    @SerializedName("InspectionStartTime")
    private String InspectionStartTime;

    @SerializedName("InspectionEndTime")
    private String InspectionEndTime;

    @SerializedName("InspectionResult")
    private String InspectionResult;

    @SerializedName("ResponsiblePerson")
    private String ResponsiblePerson;

    private Integer version;

    // Constructors, getters, and setters...
    public Product(Integer id,String MCompany, String MDestination, String MQualityLevel, String MTime, Integer MaterialId1,
                   Integer MaterialId2, String wsName1, String wsStartTime1, String wsEndTime1,
                   String wsName2, String wsStartTime2, String wsEndTime2, String wsName3,
                   String wsStartTime3, String wsEndTime3, String InspectionStartTime,
                   String InspectionEndTime, String InspectionResult, String ResponsiblePerson, Integer version) {
        this.id=id;
        this.MCompany = MCompany;
        this.MDestination = MDestination;
        this.MQualityLevel = MQualityLevel;
        this.MTime = MTime;
        this.MaterialId1 = MaterialId1;
        this.MaterialId2 = MaterialId2;
        this.wsName1 = wsName1;
        this.wsStartTime1 = wsStartTime1;
        this.wsEndTime1 = wsEndTime1;
        this.wsName2 = wsName2;
        this.wsStartTime2 = wsStartTime2;
        this.wsEndTime2 = wsEndTime2;
        this.wsName3 = wsName3;
        this.wsStartTime3 = wsStartTime3;
        this.wsEndTime3 = wsEndTime3;
        this.InspectionStartTime = InspectionStartTime;
        this.InspectionEndTime = InspectionEndTime;
        this.InspectionResult = InspectionResult;
        this.ResponsiblePerson = ResponsiblePerson;
        this.version = version;
    }
    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMCompany() {
        return MCompany;
    }

    public void setMCompany(String MCompany) {
        this.MCompany = MCompany;
    }

    public String getMDestination() {
        return MDestination;
    }

    public void setMDestination(String MDestination) {
        this.MDestination = MDestination;
    }

    public String getMQualityLevel() {
        return MQualityLevel;
    }

    public void setMQualityLevel(String MQualityLevel) {
        this.MQualityLevel = MQualityLevel;
    }

    public String getMTime() {
        return MTime;
    }

    public void setMTime(String MTime) {
        this.MTime = MTime;
    }

    public Integer getMaterialId1() {
        return MaterialId1;
    }

    public void setMaterialId1(Integer MaterialId1) {
        this.MaterialId1 = MaterialId1;
    }

    public Integer getMaterialId2() {
        return MaterialId2;
    }

    public void setMaterialId2(Integer MaterialId2) {
        this.MaterialId2 = MaterialId2;
    }

    public String getWsName1() {
        return wsName1;
    }

    public void setWsName1(String wsName1) {
        this.wsName1 = wsName1;
    }

    public String getWsStartTime1() {
        return wsStartTime1;
    }

    public void setWsStartTime1(String wsStartTime1) {
        this.wsStartTime1 = wsStartTime1;
    }

    public String getWsEndTime1() {
        return wsEndTime1;
    }

    public void setWsEndTime1(String wsEndTime1) {
        this.wsEndTime1 = wsEndTime1;
    }

    public String getWsName2() {
        return wsName2;
    }

    public void setWsName2(String wsName2) {
        this.wsName2 = wsName2;
    }

    public String getWsStartTime2() {
        return wsStartTime2;
    }

    public void setWsStartTime2(String wsStartTime2) {
        this.wsStartTime2 = wsStartTime2;
    }

    public String getWsEndTime2() {
        return wsEndTime2;
    }

    public void setWsEndTime2(String wsEndTime2) {
        this.wsEndTime2 = wsEndTime2;
    }

    public String getWsName3() {
        return wsName3;
    }

    public void setWsName3(String wsName3) {
        this.wsName3 = wsName3;
    }

    public String getWsStartTime3() {
        return wsStartTime3;
    }

    public void setWsStartTime3(String wsStartTime3) {
        this.wsStartTime3 = wsStartTime3;
    }

    public String getWsEndTime3() {
        return wsEndTime3;
    }

    public void setWsEndTime3(String wsEndTime3) {
        this.wsEndTime3 = wsEndTime3;
    }

    public String getInspectionStartTime() {
        return InspectionStartTime;
    }

    public void setInspectionStartTime(String InspectionStartTime) {
        this.InspectionStartTime = InspectionStartTime;
    }

    public String getInspectionEndTime() {
        return InspectionEndTime;
    }

    public void setInspectionEndTime(String InspectionEndTime) {
        this.InspectionEndTime = InspectionEndTime;
    }

    public String getInspectionResult() {
        return InspectionResult;
    }

    public void setInspectionResult(String InspectionResult) {
        this.InspectionResult = InspectionResult;
    }

    public String getResponsiblePerson() {
        return ResponsiblePerson;
    }

    public void setResponsiblePerson(String ResponsiblePerson) {
        this.ResponsiblePerson = ResponsiblePerson;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

