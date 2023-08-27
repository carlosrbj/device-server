package br.com.hsob.scannerserver.model;

import javax.xml.crypto.Data;
import java.util.Date;

public class ScannerResponse {
    private String code;
    private String status;
    private String ledColor;
    private Boolean isOnline;
    private Date lastCalibration;
    private Boolean isCalibrated;

    public ScannerResponse() {
    }

    public ScannerResponse(ScannerPoll scannerPoll) {
        this.code = scannerPoll.getCode();
        this.status = scannerPoll.getState().getLabel();
        this.ledColor = scannerPoll.getState().getLed();
        this.isOnline = scannerPoll.getState().getOnline();
        this.lastCalibration = new Date(scannerPoll.getLastCalibration());
        this.isCalibrated = scannerPoll.getState().getCalibrated();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLedColor() {
        return ledColor;
    }

    public void setLedColor(String ledColor) {
        this.ledColor = ledColor;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Date getLastCalibration() {
        return lastCalibration;
    }

    public void setLastCalibration(Date lastCalibration) {
        this.lastCalibration = lastCalibration;
    }

    public Boolean getCalibrated() {
        return isCalibrated;
    }

    public void setCalibrated(Boolean calibrated) {
        isCalibrated = calibrated;
    }
}

