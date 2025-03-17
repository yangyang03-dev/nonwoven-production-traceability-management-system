package com.example.product.material;

import com.google.gson.annotations.SerializedName;
public class Material {

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private String type;

    @SerializedName("supplier")
    private String supplier;

    @SerializedName("passRate")
    private Float passRate;

    @SerializedName("goodRate")
    private Float goodRate;

    @SerializedName("qualityLevel")
    private String qualityLevel;

    @SerializedName("Inspector")
    private String Inspector;


    private Integer version;

    // Constructors

    public Material(Integer id,String title, String type, String supplier, Float passRate, Float goodRate,
                    String qualityLevel, String Inspector, Integer version) {
        this.id=id;
        this.title = title;
        this.type = type;
        this.supplier = supplier;
        this.passRate = passRate;
        this.goodRate = goodRate;
        this.qualityLevel = qualityLevel;
        this.Inspector = Inspector;
        this.version = version;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Float getPassRate() {
        return passRate;
    }

    public void setPassRate(Float passRate) {
        this.passRate = passRate;
    }

    public Float getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(Float goodRate) {
        this.goodRate = goodRate;
    }

    public String getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(String qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    public String getInspector() {
        return Inspector;
    }

    public void setInspector(String Inspector) {
        this.Inspector = Inspector;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

