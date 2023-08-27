package br.com.hsob.scannerserver.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @author carlos
 */
@Document(collection = "scanners")
public class Scanner {
    @MongoId
    private ObjectId id;
    private String code;
    private Long lastCalibration;
    private String device;

    public Scanner() {
    }

    public Scanner(String code) {
        this.id = new ObjectId();
        this.code = code;
        this.lastCalibration = System.currentTimeMillis();
        this.device = "default";
    }

    public ObjectId getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getLastCalibration() {
        return lastCalibration;
    }

    public void setLastCalibration(Long lastCalibration) {
        this.lastCalibration = lastCalibration;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
