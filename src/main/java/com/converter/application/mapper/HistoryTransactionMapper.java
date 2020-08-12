package com.converter.application.mapper;

public class HistoryTransactionMapper {

    private String sourceValute;
    private String targetValute;
    private String originalAmount;
    private String receivedAmount;
    private String date;

    public String getSourceValute() {
        return sourceValute;
    }

    public void setSourceValute(String sourceValute) {
        this.sourceValute = sourceValute;
    }

    public String getTargetValute() {
        return targetValute;
    }

    public void setTargetValute(String targetValute) {
        this.targetValute = targetValute;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
