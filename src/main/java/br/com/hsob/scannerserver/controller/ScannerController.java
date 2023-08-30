package br.com.hsob.scannerserver.controller;

import br.com.hsob.scannerserver.model.ScannerResponse;
import br.com.hsob.scannerserver.service.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("scanner/")
public class ScannerController {
    @Autowired
    private ScannerService scannerService;

    @GetMapping()
    public ResponseEntity<ScannerResponse> checkScanner(@RequestParam("code") String code){
        return ResponseEntity.ok(scannerService.checkScannerStatus(code));
    }

    @GetMapping("connect/")
    public ResponseEntity<ScannerResponse> connectScanner(@RequestParam("code") String code){
        return ResponseEntity.ok(scannerService.connectScanner(code));
    }

    @GetMapping("calibrate/")
    public ResponseEntity<ScannerResponse> calibrateScanner(@RequestParam("code") String code){
        return ResponseEntity.ok(scannerService.calibrateScanner(code));
    }
}
