package com.liu.core.entity;

import java.util.Date;

public class TOuInfo {
    private Long id;

    private Integer ouId;

    private String ouName;

    private String ouAddress;

    private Date cretaeTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOuId() {
        return ouId;
    }

    public void setOuId(Integer ouId) {
        this.ouId = ouId;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName == null ? null : ouName.trim();
    }

    public String getOuAddress() {
        return ouAddress;
    }

    public void setOuAddress(String ouAddress) {
        this.ouAddress = ouAddress == null ? null : ouAddress.trim();
    }

    public Date getCretaeTime() {
        return cretaeTime;
    }

    public void setCretaeTime(Date cretaeTime) {
        this.cretaeTime = cretaeTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}