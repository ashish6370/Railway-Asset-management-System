package com.railway.assetvault.controller;

import com.railway.assetvault.dto.response.AssetPassportDTO;
import com.railway.assetvault.service.AssetPassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin("*")
public class AssetPassportController {

    @Autowired
    private AssetPassportService assetPassportService;

    @GetMapping("/{id}/passport")
    public ResponseEntity<AssetPassportDTO> getAssetPassport(@PathVariable Long id) {
        AssetPassportDTO passport = assetPassportService.getPassport(id);
        return ResponseEntity.ok(passport);
    }

    @GetMapping("/{id}/export/passport-pdf")
    public ResponseEntity<byte[]> exportPassportPdf(@PathVariable Long id) {
        byte[] pdfBytes = assetPassportService.generatePassportPdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "asset-passport-" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
