package br.com.hsob.scannerserver.service;

import br.com.hsob.scannerserver.model.Scanner;
import br.com.hsob.scannerserver.model.ScannerPoll;
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
    private static final String OFFLINE = "offline";
    private static final String ONLINE = "online";

    public String checkScannerStatus(String code){
        var status = OFFLINE;
        var scanner = hsobdb.findOne(new Query(Criteria.where("code").is(code.toUpperCase())), Scanner.class);
        ScannerPoll scannerPoll = null;

        if (Objects.isNull(scanner)){
            hsobdb.save(new Scanner(code.toUpperCase()));
            return status;
        } else {
            scannerPoll = checkScannerPoll(scanner);
            if (scannerPoll.getState().getOnline()) status = ONLINE;
        }

        return status;
    }

    public ScannerPoll connectScanner(String code) {
        Query query = new Query(Criteria.where("code").is(code.toUpperCase()));
        var scanner = hsobdb.findOne(query, Scanner.class);
        ScannerPoll scannerPoll = null;

        if (Objects.nonNull(scanner)){
            scannerPoll = checkScannerPoll(scanner);
            scannerPoll.getState().setOnline(true);
            Update update = new Update();
            update.set("state", scannerPoll.getState());
            hsobdb.updateFirst(query, update, ScannerPoll.class);
            scannerPoll = checkScannerPoll(scanner);
        }else {
            Scanner newScanner = hsobdb.save(new Scanner(code.toUpperCase()));
            scannerPoll = checkScannerPoll(newScanner);
        }

        return scannerPoll;
    }

    private ScannerPoll checkScannerPoll(Scanner scanner) {
        Query query = new Query(Criteria.where("code").is(scanner.getCode().toUpperCase()));
        var scannerPoll = hsobdb.findOne(query, ScannerPoll.class);
        if (Objects.isNull(scannerPoll)) {
            hsobdb.save(new ScannerPoll(scanner.getCode(), scanner.getLastCalibration(), scanner.getDevice(), null));
            scannerPoll = hsobdb.findOne(query, ScannerPoll.class);
            return Objects.requireNonNull(scannerPoll);
        }
        return scannerPoll;
    }
}
