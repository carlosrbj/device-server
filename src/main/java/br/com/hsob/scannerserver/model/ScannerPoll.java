package br.com.hsob.scannerserver.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Objects;

/**
 * @author carlos
 */
@Document(collection = "scannerPoll")
public class ScannerPoll {

    @MongoId
    private ObjectId id;
    private String code;
    private Long lastCalibration;
    private Long lastConnection;
    private String device;
    private State state;

    public ScannerPoll(String code, Long lastCalibration, Long lastConnection, String device, State state) {
        this.id = new ObjectId();
        this.code = code;
        this.lastCalibration = lastCalibration;
        this.lastConnection = lastConnection;
        this.device = device;
        if (Objects.isNull(state)) state = new State("connected","red", false, false);
        this.state = state;
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

    public Long getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Long lastConnection) {
        this.lastConnection = lastConnection;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
