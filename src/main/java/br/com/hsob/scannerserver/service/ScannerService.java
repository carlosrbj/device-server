package br.com.hsob.scannerserver.service;

import br.com.hsob.scannerserver.model.Scanner;
import br.com.hsob.scannerserver.model.ScannerPoll;
import br.com.hsob.scannerserver.model.ScannerResponse;
import br.com.hsob.scannerserver.repository.AbstractService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author carlos
 */
@Service
public class ScannerService extends AbstractService {

    public ScannerResponse checkScannerStatus(String code){
        var scanner = hsobdb.findOne(new Query(Criteria.where("code").is(code.toUpperCase())), Scanner.class);
        ScannerPoll scannerPoll = null;
        ScannerResponse scannerResponse = null;

        if (Objects.nonNull(scanner)){
            scannerPoll = checkScannerPoll(scanner);
            verifyStatus(scannerPoll);
        } else {
            scanner = new Scanner(code.toUpperCase());
            hsobdb.save(scanner);
        }

        scannerPoll = checkScannerPoll(scanner);
        scannerResponse = new ScannerResponse(scannerPoll);
        return scannerResponse;
    }

    public ScannerResponse connectScanner(String code) {
        Query query = new Query(Criteria.where("code").is(code.toUpperCase()));
        var scanner = hsobdb.findOne(query, Scanner.class);
        ScannerPoll scannerPoll = null;
        ScannerResponse scannerResponse= null;

        if (Objects.nonNull(scanner)){
            scannerPoll = checkScannerPoll(scanner);
            scannerPoll.getState().setLabel("connected");
            scannerPoll.getState().setOnline(true);
            scannerPoll.getState().setLed("green");
            scannerPoll.setLastCalibration(System.currentTimeMillis());
            Update update = new Update();
            update.set("state", scannerPoll.getState());
            hsobdb.updateFirst(query, update, ScannerPoll.class);
            scannerPoll = checkScannerPoll(scanner);
        }else {
            Scanner newScanner = hsobdb.save(new Scanner(code.toUpperCase()));
            scannerPoll = checkScannerPoll(newScanner);
        }
        scannerResponse = new ScannerResponse(scannerPoll);

        return scannerResponse;
    }

    private void verifyStatus(ScannerPoll scannerPoll) {
        Query query = new Query(Criteria.where("code").is(scannerPoll.getCode().toUpperCase()));
        Update update = new Update();

        if (!verifyIsOnline(scannerPoll) && verifyCalibrationStatus(scannerPoll)){
            scannerPoll.getState().setLabel("disconnected");
            scannerPoll.getState().setLed("red");
            scannerPoll.getState().setOnline(false);
        } else if (!verifyCalibrationStatus(scannerPoll)){
            scannerPoll.getState().setLabel("need_calibrate");
            scannerPoll.getState().setLed("yellow");
            scannerPoll.getState().setCalibrated(false);
            scannerPoll.getState().setOnline(verifyIsOnline(scannerPoll));
        }
        update.set("state", scannerPoll.getState());
        hsobdb.updateFirst(query, update, ScannerPoll.class);
    }

    private boolean verifyCalibrationStatus(ScannerPoll scannerPoll) {
        return System.currentTimeMillis() - scannerPoll.getLastCalibration() < 180000;
    }

    private boolean verifyIsOnline(ScannerPoll scannerPoll) {
        return System.currentTimeMillis() - scannerPoll.getLastConnection() > 60000;
    }

    private ScannerPoll checkScannerPoll(Scanner scanner) {
        Query query = new Query(Criteria.where("code").is(scanner.getCode().toUpperCase()));
        var scannerPoll = hsobdb.findOne(query, ScannerPoll.class);
        if (Objects.isNull(scannerPoll)) {
            hsobdb.save(new ScannerPoll(scanner.getCode(), scanner.getLastCalibration(), System.currentTimeMillis(), scanner.getDevice(), null));
            scannerPoll = hsobdb.findOne(query, ScannerPoll.class);
            return Objects.requireNonNull(scannerPoll);
        }
        return scannerPoll;
    }

    public ScannerResponse calibrateScanner(String code) {
        Long lastCalibration = System.currentTimeMillis();
        Update updateScanner = new Update();
        Update updateScannerPoll = new Update();
        ScannerResponse scannerResponse = new ScannerResponse();
        scannerResponse.setCode(code.toUpperCase());
        scannerResponse.setStatus("scanner_not_found");

        Query query = new Query(Criteria.where("code").is(code.toUpperCase()));
        var scannerPoll = hsobdb.findOne(query, ScannerPoll.class);
        var scanner = hsobdb.findOne(query, Scanner.class);

        if (Objects.nonNull(scannerPoll)) scannerResponse = new ScannerResponse(scannerPoll);

        if (Objects.nonNull(scannerPoll) && scannerPoll.getState().getOnline()){
            scannerPoll.getState().setCalibrated(true);
            if (Objects.nonNull(scanner)) scanner.setLastCalibration(lastCalibration);

            updateScannerPoll.set("state", scannerPoll.getState());
            updateScannerPoll.set("lastCalibration", lastCalibration);
            updateScanner.set("lastCalibration", lastCalibration);

            hsobdb.updateFirst(query, updateScannerPoll, ScannerPoll.class);
            hsobdb.updateFirst(query, updateScanner, Scanner.class);

            scannerResponse = new ScannerResponse(scannerPoll);
        }
        return scannerResponse;
    }
}
