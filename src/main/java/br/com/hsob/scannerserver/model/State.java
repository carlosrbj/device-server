package br.com.hsob.scannerserver.model;

public class State{
        private String label;
        private String led;
        private Boolean isOnline;
        private Boolean isCalibrated;

    public State() {
    }

    public State(String label, String led, Boolean isOnline, Boolean isCalibrated) {
        this.label = label;
        this.led = led;
        this.isOnline = isOnline;
        this.isCalibrated = isCalibrated;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getCalibrated() {
        return isCalibrated;
    }

    public void setCalibrated(Boolean calibrated) {
        isCalibrated = calibrated;
    }
}
