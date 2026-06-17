package com.railway.assetvault.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QrCodeGeneratorService {

    public String generateQrCodeImage(String text, String assetId) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

            Path dirPath = Paths.get("uploads", "qrcodes");
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String filename = "asset-" + assetId + ".png";
            Path filePath = dirPath.resolve(filename);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);
            
            return "uploads/qrcodes/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Could not generate QR Code file", e);
        }
    }
}
